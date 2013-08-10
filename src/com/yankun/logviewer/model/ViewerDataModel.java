package com.yankun.logviewer.model;

import com.yankun.logviewer.Model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author yankun
 * @version 1.0
 * @created 30-一月-2013 13:52:02
 */
public class ViewerDataModel {

    private Model model = Model.Tail;


    /**
     * 域名
     */
    private String domain;

    /**
     * 服务器IP列表
     */
    private List<String> ipList = new ArrayList<String>();

    /**
     * 临控日志文件列表
     */
    private List<String> logFileList = new ArrayList<String>();


    /**
     * 选定的日志文件
     */
    private List<String> selectedLogFiles = new ArrayList<String>();


    /**
     * 输出路径
     */
    private String outputPath;


    /**
     * 开始时间
     */
    private String startTime;


    /**
     * 截止时间
     */
    private String endTime;



    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public List<String> getIpList() {
        return ipList;
    }

    public void setIpList(List<String> ipList) {
        this.ipList = ipList;
    }

    public List<String> getLogFileList() {
        return logFileList;
    }

    public void setLogFileList(List<String> logFileList) {
        this.logFileList = logFileList;
    }

    public String getOutputPath() {
        return outputPath;
    }

    public void setOutputPath(String outputPath) {
        this.outputPath = outputPath;
    }

    /**
     * 从文件加载
     *
     * @param fileName
     * @return
     */
    public static ViewerDataModel load(String fileName) {
        ViewerDataModel model = null;
        InputStream inputStream = null;
        try {
            //读取配置文件
            Properties props = new Properties();
            inputStream = new BufferedInputStream(new FileInputStream(fileName));
            props.load(inputStream);

            model = new ViewerDataModel();
            //读取域名
            model.setDomain(props.getProperty("domain"));

            //读取IP
            String[] ids = props.getProperty("ipList").split(",");
            List<String> ipList = new ArrayList<String>();
            for (String ip : ids) {
                ipList.add(ip);
            }
            model.setIpList(ipList);

            //读取日志文件列表
            String[] logFiles = props.getProperty("logFileList").split(",");
            List<String> logFileList = new ArrayList<String>();
            for (String logFile : logFiles) {
                logFileList.add(logFile);
            }
            model.setLogFileList(logFileList);

            //日志输出目录
            model.setOutputPath(props.getProperty("outputPath"));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return model;
    }

    /**
     * 保存为文件
     *
     * @param fileName
     */
    public void saveAs(String fileName) {
        OutputStream fos = null;
        try {
            Properties props = new Properties();
            props.setProperty("domain", domain);
            props.setProperty("ipList", conver(ipList, ","));
            props.setProperty("logFileList", conver(logFileList, ","));
            props.setProperty("outputPath", outputPath);

            //保存配置
            fos = new FileOutputStream(fileName);
            props.store(fos, "save config");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 将list转成string
     *
     * @param list
     * @param s
     * @return
     */
    private String conver(List<String> list, String s) {
        StringBuffer returnString = new StringBuffer();
        boolean isStart = true;
        for (String str : list) {
            if (isStart) {
                returnString.append(str);
                isStart = false;
            } else {
                returnString.append(s).append(str);
            }
        }
        return returnString.toString();
    }

    public List<String> getSelectedLogFiles() {
        return selectedLogFiles;
    }

    public void setSelectedLogFiles(List<String> selectedLogFiles) {
        this.selectedLogFiles = selectedLogFiles;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    /**
     * 获取要查看的文件数
     *
     * @return
     */
    public int size() {
        return ipList.size() * logFileList.size();

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