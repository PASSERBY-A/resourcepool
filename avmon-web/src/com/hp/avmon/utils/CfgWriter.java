package com.hp.avmon.utils;

import java.io.File;   
import java.io.FileNotFoundException;   
import java.io.PrintWriter;   

import com.hp.avmon.ireport.util.StringUtil;
 
public class CfgWriter {   
    private PrintWriter out;
    private static final String NOTE = "#";
    private static final String SPLIT = ",";   

    private EncryDecryUtil crypt = EncryDecryUtil.getInstance();  
    public CfgWriter(File file) throws FileNotFoundException {   
        super();   
        this.out = new PrintWriter(file);   
    }   
 
    public void writeComment(String comment) {   
        out.print(NOTE);   
        out.println(comment);   
    }   

    /**
     * 一行记录内容
     * 
     * @param 第1列：序号
     * @param 第2列：设备名称
     * @param 第3列：版本
     * @param 第4列：型号
     * @param 第5列：IP
     * @param 第6列：用户名
     * @param 第7列：密码
     * @param 第8列：命令
     * @param 第9列：空
     * @param 第10列：退出命令
     * 
     */
    public void writeProperty(Integer sn, 
    		String deviceNm, 
    		String deviceVersion, 
    		String deviceType, 
    		String deviceIp, 
    		String usr, 
    		String pwd, 
    		String commCode,
    		String empty, 
    		String quitMode) {
    	
        out.print(sn);
        out.print(SPLIT);
        out.print(deviceNm);
        out.print(SPLIT);
        out.print(deviceVersion);
        out.print(SPLIT);
        out.print(deviceType);
        out.print(SPLIT);
        out.print(deviceIp);
        out.print(SPLIT);
        out.print(usr);
        out.print(SPLIT);
        // 加密
        out.print(crypt.encryptAES(pwd));
        out.print(SPLIT);
        out.print(commCode);
        out.print(SPLIT);
        out.print(empty);
        out.print(SPLIT);
        out.println(quitMode);
    }

    /**
     * 关闭
     * 
     */
    public void close() {
        out.close();
    }
  
    public static void main(String[] args) throws Exception {
    	// 读取保存文件的路径
    	String filePath = PropertyUtils.getProperty("IDCommand.path");

    	if (!StringUtil.isEmpty(filePath)) {
            CfgWriter w = new CfgWriter(new File(filePath));
            w.writeComment("Inspect Device Command Info");
            w.writeProperty(1, "Id", "0001", "Id", "0001", "Id", "0001", "Id", "0001", "Id");
            w.writeProperty(2, "Id", "0001", "Id", "0001", "Id", "0001", "Id", "0001", "Id");
            w.writeProperty(3, "Id", "0001", "Id", "0001", "Id", "0001", "Id", "0001", "Id");

            w.close();
    	}
    }
}  
