package com.hp.avmon.remote;

import java.util.Date;

import com.hp.avmonserver.entity.Alarm;

public class Alert {
  public static final int GRADE_CRITICAL = 5;
  public static final int GRADE_MAJOR = 4;
  public static final int GRADE_MINOR = 3;
  public static final int GRADE_WARN = 2;
  public static final int GRADE_INFO = 1;

	public String title;
	public int grade;
	public Date firstOccurTime;
	public Date lastOccurTime;
	public String content;
	public String nodeName;//avmon alarm中moid 为name;
	public String nodeType;//类型
	
    public static final int STATUS_UNKNOWN = -1;
    public static final int STATUS_NEW = 0;
    public static final int STATUS_AKNOWLEDGED = 1;
    public static final int STATUS_FORWARD = 2;
    public static final int STATUS_CLEAR = 9;
	
	public String status;
	
	public int gradeCount=1;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public Date getFirstOccurTime() {
		return firstOccurTime;
	}

	public void setFirstOccurTime(Date firstOccurTime) {
		this.firstOccurTime = firstOccurTime;
	}

	public Date getLastOccurTime() {
		return lastOccurTime;
	}

	public void setLastOccurTime(Date lastOccurTime) {
		this.lastOccurTime = lastOccurTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getNodeType() {
		return nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}    

	public int getGradeCount() {
		return gradeCount;
	}

	public void setGradeCount(int gradeCount) {
		this.gradeCount = gradeCount;
	}

	public static String getLevelDes(String level){
		  String re="";
		  if("5".equals(level)){
			  re="严重";
		  }else if("4".equals(level)){
			  re="主要";
		  }else if("3".equals(level)){
			  re="次要";
		  }else if("2".equals(level)){
			  re="一般";
		  }else if("1".equals(level)){
			  re="信息";
		  }
		  return re;
	}    
    

}
