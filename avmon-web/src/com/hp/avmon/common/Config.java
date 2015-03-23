package com.hp.avmon.common;



public class Config {

    public final static String CURRENT_USER="CURRENT_USER";
    private String avmonServerUrl;
    private static Config config=null;
    private String runMode;
    private String reportTemplatePath;
    private String reportHtmlPath;
    private String reportActionUrl;
    private String downloadBaseUrl;
    private String mibfilesUploadPath;
    private String agentlessfilesUploadPath;
    
    public String getDownloadBaseUrl() {
        return downloadBaseUrl;
    }

    public void setDownloadBaseUrl(String downloadBaseUrl) {
        this.downloadBaseUrl = downloadBaseUrl;
    }

    private String licensePath;

    public String getRunMode() {
        return runMode;
    }

    public String getReportTemplatePath() {
        return reportTemplatePath;
    }

    public void setReportTemplatePath(String reportTemplatePath) {
        this.reportTemplatePath = reportTemplatePath;
    }

    public String getReportHtmlPath() {
        return reportHtmlPath;
    }

    public void setReportHtmlPath(String reportHtmlPath) {
        this.reportHtmlPath = reportHtmlPath;
    }

    public String getReportActionUrl() {
        return reportActionUrl;
    }

    public void setReportActionUrl(String reportActionUrl) {
        this.reportActionUrl = reportActionUrl;
    }

    public void setRunMode(String runMode) {
        this.runMode = runMode;
    }

    public String getAvmonServerUrl() {
        return avmonServerUrl;
    }

    public void setAvmonServerUrl(String avmonServerUrl) {
        this.avmonServerUrl = avmonServerUrl;
    }
    
    public String getLicensePath() {
		return licensePath;
	}

	public void setLicensePath(String licensePath) {
		this.licensePath = licensePath;
	}
	
	public String getMibfilesUploadPath() {
		return mibfilesUploadPath;
	}

	public void setMibfilesUploadPath(String mibfilesUploadPath) {
		this.mibfilesUploadPath = mibfilesUploadPath;
	}

	public String getAgentlessfilesUploadPath() {
		return agentlessfilesUploadPath;
	}

	public void setAgentlessfilesUploadPath(String agentlessfilesUploadPath) {
		this.agentlessfilesUploadPath = agentlessfilesUploadPath;
	}

	public static Config getInstance(){
        if(config==null){
            config=SpringContextHolder.getBean("systemConfig");
        }
        return config;
    }

    
//    public final static String IREPORT_TEMP_PATH = "C:\\mediaReport\\template\\";
//    public final static String IREPORT_HTML_PATH = "C:\\mediaReport\\html\\";
//    public final static String IREPORT_ACTION_URL = "../ireport/index.jsp?reportId=";
}
