package com.yankun.logviewer;

import com.yankun.logviewer.model.ViewerItemDataModel;

import javax.swing.text.JTextComponent;

/**
 * @author yankun
 * @version 1.0
 * @created 30-一月-2013 13:52:01
 */
public interface Viewer extends Runnable {

    /**
     * @param content
     */
    public void info(String content);

    /**
     * @param content
     */
    public void log(String content);


    /**
     * 初始化参数
     *
     * @param viewerItemDataModel
     * @param viewUI
     * @param controller
     */
    public void init(ViewerItemDataModel viewerItemDataModel, JTextComponent viewUI, ViewerController controller);


    /**
     * 读取日志信息
     */
    public void readLogInfo();


    /**
     * 设置工作状态
     *
     * @param isCanWorking
     */
    public void setCanWorking(boolean isCanWorking);

}