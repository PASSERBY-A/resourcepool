package com.hp.xo.uip.cmdb.dao.template;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Row;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.PageFilter;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.Logger;

import com.hp.xo.uip.cmdb.exception.CmdbException;
import com.hp.xo.uip.cmdb.util.IdGenerator;
import com.hp.xo.uip.cmdb.util.StringUtil;

public class HBaseDaoTemplate {
	private static Logger log=Logger.getLogger(HBaseDaoTemplate.class);
	private String host="----";
	private String port="2181";
	public static String defTableName="cmdb_ci";
	public static String repairRecordTableName="cmdb_repair";
	public static String changeRecordTableName="cmdb_change";
	public String defColFamily="cf";
	
	public String getDefTableName() {
		return defTableName;
	}

	public void setDefTableName(String defTableName) {
		this.defTableName = defTableName;
	}

	public String getDefColFamily() {
		return defColFamily;
	}

	public void setDefColFamily(String defColFamily) {
		this.defColFamily = defColFamily;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	private static Configuration configuration;	
	public  void initHbase(){
		   try {
		   if(configuration==null){
		    configuration = HBaseConfiguration.create();
		    configuration.set("hbase.zookeeper.property.clientPort", port);  
		    configuration.set("hbase.zookeeper.quorum", host);  
//	        configuration.set("hbase.master", ip+":600000");
		    //创建基础CI表
			createTable();
		    }
		   } catch (CmdbException e) {
			log.error("", e);
			e.printStackTrace();
		   }
	   }
	   
	   public void createTable() throws CmdbException{
		   //当前默认一个 列族 cf
		   String columnFamily[]={defColFamily};
		   createTable(defTableName,columnFamily);
		   //创建维修记录表
		   createTable(repairRecordTableName,columnFamily);
		   //创建变更记录表
		   createTable(changeRecordTableName,columnFamily);
	   }
	   public void dropTable(String tableName) throws CmdbException{
		   HBaseAdmin admin=null;
	       try {
	           admin = new HBaseAdmin(configuration);  
	           admin.disableTable(tableName);  
	           admin.deleteTable(tableName);  
	       } catch (Exception e) {
	    	   log.error("",e);
	           throw new CmdbException(e);
	       } finally{
	    	   try {
	    	       if(admin!=null){admin.close();};
				} catch (IOException e) {
					log.error("",e);
					throw new CmdbException(e);
				}
	       }
	   }
	   /**
	    * 创建表
	    * @param tableName  表名
	    * @param colFamily  列族 ,
	 * @throws CmdbException 
	    */
	   public void createTable(String tableName,String[] colFamily) throws CmdbException{
		   log.debug("start create table ......"+tableName);
		   HBaseAdmin admin =null;
	       try {  
	           admin = new HBaseAdmin(configuration);
	           // 如果表不存在，则创建，
	           if (!admin.tableExists(tableName)) {  
	        	   HTableDescriptor tableDescriptor = new HTableDescriptor(TableName.valueOf(tableName));
	        	   if(colFamily!=null){
	        	    for(String colf:colFamily){
	        	      tableDescriptor.addFamily(new HColumnDescriptor(colf));
	        	    }
	        	   }
	        	   admin.createTable(tableDescriptor);
	           }    
	       } catch (Exception e) {
	    	   log.error("",e);
	           throw new CmdbException(e);
	       } finally{
	    	   try {
	    	       if(admin!=null){admin.close();};
				} catch (IOException e) {
					log.error("",e);
					throw new CmdbException(e);
				}
	       }
	       log.debug("end create table ......"+tableName); 
	   }
	   /**
	    * 插入数据
	    * @param tableName 表名称
	    * @param colFamily 列族
	    * @param rowKey 唯一主键
	    * @param mdata  列数据
	    * @throws CmdbException 
	    */
	   public  void save(String tableName,String colFamily,String rowKey,Map<String,String> mdata) throws CmdbException {  
	       log.debug("start insert or update data. rowKey"+rowKey+". "+mdata);
	       HTable table=null;
	       try {
	       table = new HTable(configuration,tableName);
	    // 一个PUT代表一行数据，再NEW一个PUT表示第二行数据,每行一个唯一的ROWKEY，此处rowkey为put构造方法中传入的值
	       Put put = new Put(rowKey.getBytes());
	       Iterator<String> itr= mdata.keySet().iterator();
	       while(itr.hasNext()){
	    	   String key=itr.next();
	    	   if(mdata.get(key)!=null){
	             put.add(colFamily.getBytes(), key.getBytes(), mdata.get(key).getBytes());
	           }
	       }  	         
	           table.put(put);  
	       } catch (Exception e) {
	    	   log.error("",e);
	           throw new CmdbException(e);
	       } finally{
//	    	   try {
//	    	       if(table!=null){table.close();};
//				} catch (IOException e) {
//					log.error("",e);
//					throw new CmdbException(e);
//				}
	       } 
	       log.debug("end insert data. rowKey"+rowKey+". "+mdata);  
	   }
	   public  void save(String tableName,List<Put> puts) throws CmdbException {  
	       HTable table=null;
	       try {
	          table = new HTable(configuration,tableName);
	          table.put(puts);  
	       } catch (Exception e) {
	    	   log.error("",e);
	           throw new CmdbException(e);
	       } finally{
//	    	   try {
//	    	       if(table!=null){table.close();};
//				} catch (IOException e) {
//					log.error("",e);
//					throw new CmdbException(e);
//				}
	       }   
	   }
	   public Put getPut(String colFamily,String rowKey,Map<String,String> mdata) throws CmdbException {  
		   Put put =null;
		   try {
	    // 一个PUT代表一行数据，再NEW一个PUT表示第二行数据,每行一个唯一的ROWKEY，此处rowkey为put构造方法中传入的值
	       put = new Put(rowKey.getBytes());
	       Iterator<String> itr= mdata.keySet().iterator();
	       while(itr.hasNext()){
	    	   String key=itr.next();
	    	   if(mdata.get(key)!=null){
	             put.add(colFamily.getBytes(), key.getBytes(), mdata.get(key).getBytes());
	           }
	       }  	         
	       } catch (Exception e) {
	    	   log.error("",e);
	           throw new CmdbException(e);
	       } 
	       return put;
	   }
	   public  void delete(String tableName,List<String> rowKeys) throws CmdbException {  
	       log.debug("start delete data.");
	       HTable table =null;
        try {
	       table = new HTable(configuration,tableName);
	       List<Delete> list = new ArrayList<Delete>();
	       for(String rowKey:rowKeys){
            Delete d1 = new Delete(rowKey.getBytes());  
            list.add(d1);
            log.debug("delete row:"+rowKey);
           }  
           table.delete(list);
           } catch (Exception e) {
	    	   log.error("",e);
	           throw new CmdbException(e);
	       } finally{
//	    	   try {
//	    	       if(table!=null){table.close();};
//				} catch (IOException e) {
//					log.error("",e);
//					throw new CmdbException(e);
//				}
	       }  
	       log.debug("end delete data.");  
	   }
	   public List<Cell> getRowCellByKey(String rowkey) throws CmdbException{
		   return getRowCellByKey(null, rowkey);
	   }
	   public List<Cell> getRowCellByKey(String tableName,String rowkey) throws CmdbException{
		   List<Cell> reli=new ArrayList<Cell>();
		   HTable table =null;
		   if(tableName==null||"".equals(tableName)){
			   tableName=defTableName;
		   }

		   try {
	           table = new HTable(configuration,tableName);
	           Get scan = new Get(rowkey.getBytes());// 根据rowkey查询
	           scan.isClosestRowBefore();
	           Result r = table.get(scan);
	           if(!r.isEmpty()){
	           log.info("rowkey:" + new String(r.getRow()));
	            for (Cell cell : r.listCells()) {  
	               log.debug("column：" + StringUtil.newStr(CellUtil.cloneQualifier(cell))  
	                       + "value:" + StringUtil.newStr(CellUtil.cloneValue(cell))
	                       + "family:"+StringUtil.newStr(CellUtil.cloneFamily(cell))
	                       + "row:"+StringUtil.newStr(CellUtil.cloneRow(cell))
	                       + "time:"+cell.getTimestamp()
	                       + "version:"+cell.getMvccVersion());  
	            }  
	            reli=r.listCells();
	           }
	       } catch (Exception e) {
	    	   log.error("", e);
	           throw new CmdbException(e);
	       }finally{
//				try {
//					if(table!=null)table.close();
//				} catch (IOException e) {
//					log.error("", e);
//			        throw new CmdbException(e);
//				}
		   }
	       return reli;
	   }
	   /**
	    * 
	    * @param tableName
	    * @return map<rowKey,list<cell>>
	    * @throws CmdbException
	    */
	   public Map<String,List<Cell>> getRowCellByKeyFilter(String tableName,String startRow,String stopRow,int size) throws CmdbException{
		   Map<String,List<Cell>> ma=new LinkedHashMap<String,List<Cell>>();
		   HTable table =null;
		   ResultScanner rs=null;
		   try {  
	           table = new HTable(configuration,tableName);
	           Scan scan=new Scan();
	           if(size>0){
	             PageFilter pf=new PageFilter(size);
	             scan.setFilter(pf);
	           }
	           if(startRow!=null){
	              scan.setStartRow(startRow.getBytes());
	           }
	           if(stopRow!=null){
	              scan.setStopRow(stopRow.getBytes());
	           }
	            rs = table.getScanner(scan);
	           Iterator<Result> ite=rs.iterator();
	           int count=0;
	           while(ite.hasNext()){
	        	Result r=ite.next();
	            if(!r.isEmpty()){
	            log.debug("rowkey:" + new String(r.getRow()));
	            if(size>0&&count>=size)break;
	            for (Cell cell : r.listCells()) {  
	               log.debug("column:" + StringUtil.newStr(CellUtil.cloneQualifier(cell))  
	                       + " value:" + StringUtil.newStr(CellUtil.cloneValue(cell))
	                       + " family:"+StringUtil.newStr(CellUtil.cloneFamily(cell))
	                       + " row:"+StringUtil.newStr(CellUtil.cloneRow(cell))
	                       + " time:"+cell.getTimestamp()
	                       + " version:"+cell.getMvccVersion());  
	            }
	            List<Cell> reli=new ArrayList<Cell>();
	            reli=r.listCells();
	            ma.put(StringUtil.newStr(r.getRow()), reli);
	            }
	            count++;
	           }
	       } catch (Exception e) {
	    	   log.error("", e);
	           throw new CmdbException(e);
	       }finally{
			   if(rs!=null)rs.close();
		   }
	       return ma;
	   }
	   /** 
	    * 组合条件查询 
	    * @param tableName
	    * @param conditions, key:family\qualifier\compareOp\value 
	 * @throws CmdbException 
	    */  
	   public Map<String,List<Cell>> queryByCondition(String tableName,String startRow,String stopRow,List<Map> conditions) throws CmdbException {  
		   Map<String,List<Cell>> ma_re=new HashMap<String,List<Cell>>();
		   HTable table =null;
		   ResultScanner rs=null;
		   try {  
	           table = new HTable(configuration,tableName);  
	           List<Filter> filters = new ArrayList<Filter>();  
	          for(Map ma:conditions){
	           String family=ma.get("family").toString();
	           String qualifier=ma.get("qualifier").toString();
	           String value=ma.get("value").toString();
	           CompareOp cp=(CompareOp)(ma.get("compareOp")==null?CompareOp.EQUAL:ma.get("compareOp"));
	           Filter filter1 = new SingleColumnValueFilter(
	        		   Bytes.toBytes(family),
	                   Bytes.toBytes(qualifier),
	                   cp,
	                   Bytes.toBytes(value));
	           log.debug("filter, table="+tableName+" family="+family+" qualifier="+qualifier
	        		   +" value="+value+" compareOp="+cp.name());
	           filters.add(filter1);  
	          }
	             
	           Scan scan = new Scan();
	           if(filters!=null&&filters.size()>0){
	            FilterList filterList1 = new FilterList(filters);
	            scan.setFilter(filterList1);
	           }
	               if(startRow!=null){
		              scan.setStartRow(startRow.getBytes());
		           }
		           if(stopRow!=null){
		              scan.setStopRow(stopRow.getBytes());
		           }
	           rs = table.getScanner(scan);  
	           Iterator<Result> ite=rs.iterator();
	           while(ite.hasNext()){
	        	Result r=ite.next();
	            if(!r.isEmpty()){
	            log.debug("rowkey:" + new String(r.getRow()));
	            for (Cell cell : r.listCells()) {  
	               log.debug("column:" + StringUtil.newStr(CellUtil.cloneQualifier(cell))  
	                       + " value:" + StringUtil.newStr(CellUtil.cloneValue(cell))
	                       + " family:"+StringUtil.newStr(CellUtil.cloneFamily(cell))
	                       + " row:"+StringUtil.newStr(CellUtil.cloneRow(cell))
	                       + " time:"+cell.getTimestamp()
	                       + " version:"+cell.getMvccVersion());  
	            }
	            List<Cell> reli=new ArrayList<Cell>();
	            reli=r.listCells();
	            ma_re.put(StringUtil.newStr(r.getRow()), reli);
	            }
	           }  
	       } catch (Exception e) {
	    	   log.error("",e);
	           throw new CmdbException(e);
	       } finally{
               if(rs!=null){rs.close();};
	       }  
	       return ma_re;
	   }
	   public Map<String,List<Cell>> getTableRowCells(String tableName) throws CmdbException{
		   Map<String,List<Cell>> ma=new HashMap<String,List<Cell>>();
		   HTable table =null;
		   ResultScanner rs=null;
		   try {  
	           table = new HTable(configuration,tableName);
	           Scan scan=new Scan();
	           rs = table.getScanner(scan);
	           Iterator<Result> ite=rs.iterator();
	           while(ite.hasNext()){
	        	Result r=ite.next();
	            if(!r.isEmpty()){
	            log.debug("rowkey:" + new String(r.getRow()));
	            for (Cell cell : r.listCells()) {  
	               log.debug("column:" + StringUtil.newStr(CellUtil.cloneQualifier(cell))  
	                       + " value:" + StringUtil.newStr(CellUtil.cloneValue(cell))
	                       + " family:"+StringUtil.newStr(CellUtil.cloneFamily(cell))
	                       + " row:"+StringUtil.newStr(CellUtil.cloneRow(cell))
	                       + " time:"+cell.getTimestamp()
	                       + " version:"+cell.getMvccVersion());  
	            }
	            List<Cell> reli=new ArrayList<Cell>();
	            reli=r.listCells();
	            ma.put(StringUtil.newStr(r.getRow()), reli);
	            }
	           }
	       } catch (Exception e) {
	    	   log.error("", e);
	           throw new CmdbException(e);
	       }finally{
               if(rs!=null)rs.close();
		   }
	       return ma;
	   }
	   public void clearTableRowCells(String tableName) throws CmdbException{
		   Map<String,List<Cell>> ma=getTableRowCells(tableName);
		   List<String> kli=new ArrayList<String>(ma.keySet());
		   delete(tableName, kli);
	   }
	   public static void main(String arg[]) throws CmdbException{
		   HBaseDaoTemplate ht=new HBaseDaoTemplate();
		   
		   ht.setHost("16.159.216.186");
		   ht.setPort("2181");
		   
		   ht.initHbase();
		   
		   IdGenerator idg=IdGenerator.createGenerator();

//		   String rowKey="Host_"+ idg.generate();
//		   String rowKey="Host_1001";
//		   Map<String,String> ma=new HashMap<String,String>();
//		   ma.put("host", "bossa-b");
//		   ma.put("Ip", "10.10.10.100");
//		   ht.save("test2", "column1", rowKey, ma);
		   
//		   ht.getByRowkey("test2", "row2");
//		   Map ma=ht.getRowCellByKeyFilter("test2", "Host_0000","Host_9999");
		   
//		   Map ma=ht.getRowCellByKeyFilter("test2", "row0","row9",100);
//		   Object[] objs=ma.keySet().toArray();
//		   List<String> li=new ArrayList<String>();
//		   for(Object o:objs){
//			   li.add(String.valueOf(o));
//		   }
//		   ht.delete("test2",li);
		   
//		   Map<String,Object> maf=new HashMap<String,Object>();
//		   maf.put("family", "column1");
//		   maf.put("qualifier","host");
//		   maf.put("compareOp", CompareOp.EQUAL);
//		   maf.put("value", "bossa-a");
//		
//		   Map<String,Object> maf2=new HashMap<String,Object>();
//		   maf2.put("family", "column1");
//		   maf2.put("qualifier","Ip");
//		   maf2.put("compareOp", CompareOp.EQUAL);
//		   maf2.put("value", "10.10.10.100");
//		   
//		   List<Map> li=new ArrayList<Map>();
//		   li.add(maf);
//		   li.add(maf2);
//		   Map mar= ht.QueryByCondition("test2", li);
		   
		  // ht.clearTableRowCells(defTableName);
	   }

}
