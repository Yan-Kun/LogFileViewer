package com.yankun.logviewer.model;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: yankun
 * Date: 13-1-30
 * Time: 下午6:08
 * To change this template use File | Settings | File Templates.
 */
public class ViewerItemDataModel {

    /**
     * 域名
     */
    private String domain;

    /**
     * 服务器IP
     */
    private String ip;

    /**
     * 日志文件
     */
    private String logFile;

    /**
     * 输出路径
     */
    private String outputPath;


    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 开始时间
     */
    private Date startDate;


    /**
     * 截止时间
     */
    private String endTime;

    /**
     * 截止时间
     */
    private Date endDate;


    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getLogFile() {
        return logFile;
    }

    public void setLogFile(String logFile) {
        this.logFile = logFile;
    }

    public String getOutputPath() {
        return outputPath;
    }

    public void setOutputPath(String outputPath) {
        this.outputPath = outputPath;
    }


    /**
     * 拼装URL
     *
     * @return
     */
    public String getURL() {
        return "http://" + this.getDomain() + this.getLogFile();
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }


    public String getFileInfo(){
        StringBuilder fileInfo = new StringBuilder();
        fileInfo.append("域名[").append(domain)
                .append("] 服务器ＩＰ[").append(ip)
                .append("] 日志文件[").append(logFile)
                .append("]");

        return fileInfo.toString();
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
