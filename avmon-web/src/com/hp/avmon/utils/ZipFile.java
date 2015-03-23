/**
 * 
 */
package com.hp.avmon.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipOutputStream;

import com.hp.avmon.common.Config;

/**
 * @author muzh
 *
 */
public class ZipFile {
	/*
    * inputFileName 输入一个文件夹
    * zipFileName 输出一个压缩文件夹
    */
    public static void zip(String inputFileName,String zipFileName) throws Exception {
        String zipFileFullName = Config.getInstance().getReportTemplatePath() + zipFileName + "_" + MyString.generateUniqueId() + ".zip"; //打包后文件名字
        zip(zipFileFullName, new File(inputFileName));
    }

    private static void zip(String zipFileName, File inputFile) throws Exception {
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFileName));
        zip(out, inputFile, "");
        //System.out.println("zip done");
        out.close();
    }

    private static void zip(ZipOutputStream out, File f, String base) throws Exception {
        if (f.isDirectory()) {
           File[] fl = f.listFiles();
           out.putNextEntry(new org.apache.tools.zip.ZipEntry(base + "/"));
           base = base.length() == 0 ? "" : base + "/";
           for (int i = 0; i < fl.length; i++) {
           zip(out, fl[i], base + fl[i].getName());
         }
        }else {
           out.putNextEntry(new org.apache.tools.zip.ZipEntry(base));
           FileInputStream in = new FileInputStream(f);
           int b;
           //System.out.println(base);
           while ( (b = in.read()) != -1) {
            out.write(b);
         }
         in.close();
       }
    }
    
    public static boolean deleteFile(String sPath) {   
        Boolean flag = false;   
        File file = new File(sPath);   
        // 路径为文件且不为空则进行删除   
        if (file.isFile() && file.exists()) {   
            file.delete();   
            flag = true;   
        }   
        return flag;   
    }
    
    public static boolean deleteDirectory(String sPath) {   
        //如果sPath不以文件分隔符结尾，自动添加文件分隔符   
        if (!sPath.endsWith(File.separator)) {   
            sPath = sPath + File.separator;   
        }   
        File dirFile = new File(sPath);   
        //如果dir对应的文件不存在，或者不是一个目录，则退出   
        if (!dirFile.exists() || !dirFile.isDirectory()) {   
            return false;   
        }   
        Boolean flag = true;   
        //删除文件夹下的所有文件(包括子目录)   
        File[] files = dirFile.listFiles();   
        for (int i = 0; i < files.length; i++) {   
            //删除子文件   
            if (files[i].isFile()) {   
                flag = deleteFile(files[i].getAbsolutePath());   
                if (!flag) break;   
            } //删除子目录   
            else {   
                flag = deleteDirectory(files[i].getAbsolutePath());   
                if (!flag) break;   
            }   
        }   
        if (!flag) return false;   
        //删除当前目录   
        if (dirFile.delete()) {   
            return true;   
        } else {   
            return false;   
        }   
    }  
}
