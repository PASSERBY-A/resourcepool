package com.hp.xo.uip.cmdb.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class ChangeRecord implements Serializable{

	private static final long serialVersionUID = -8471384113215865459L;
       
     //序号”：按照时间顺序按照从小到大自动填写，即最上面一条就是最近一次更新的记录，以便该机器配置变更较多时，方便查看配置变更次数。
	 //“变更日期”：写入该条数据时，自动记录变更的日期。
	 //“变更内容”：记录具体的变更事项。
	 //“变更操作人”：执行变更操作的登录用户
	
	//rowKey: nodeKey_id ,指定表 cmdb_change
	private Long id;
	//nodeKey: nodeType_nodeId
	private String nodeKey;
    private Long sequence;
    //变更日期
    private Timestamp cdate;
    private String content;
    private String operator;
   
	public Map<String,String> getMapData(){
		Map<String,String> ma=new HashMap<String,String>();
		ma.put("id", String.valueOf(id));
		ma.put("nodeKey", nodeKey);ma.put("sequence", String.valueOf(sequence));
		ma.put("cdate", cdate==null?String.valueOf(System.currentTimeMillis()):String.valueOf(cdate.getTime()));
		ma.put("content", content);ma.put("operator", operator);
		return ma;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNodeKey() {
		return nodeKey;
	}
	public void setNodeKey(String nodeKey) {
		this.nodeKey = nodeKey;
	}
	public Long getSequence() {
		return sequence;
	}
	public void setSequence(Long sequence) {
		this.sequence = sequence;
	}

	public Timestamp getCdate() {
		return cdate;
	}

	public void setCdate(Timestamp cdate) {
		this.cdate = cdate;
	}

	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
    
}
