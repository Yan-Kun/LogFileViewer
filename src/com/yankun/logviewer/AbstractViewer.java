package com.yankun.logviewer;


import com.yankun.logviewer.model.ViewerItemDataModel;

import javax.swing.text.JTextComponent;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author yankun
 * @version 1.0
 * @created 30-一月-2013 13:52:01
 */
public abstract class AbstractViewer implements Viewer {
    private static final SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 配置参数
     */
    private ViewerItemDataModel model;


    /**
     * 标识可运行状态
     */
    private boolean isCanWorking = true;

    /**
     * 视图ＵＩ对象
     */
    private JTextComponent viewUI;

    /**
     * 控制器
     */
    private ViewerController controller;

    private boolean isInit = false;

    /**
     * 初始化参数
     *
     * @param model
     * @param viewUI
     * @param controller
     */
    public void init(ViewerItemDataModel model, JTextComponent viewUI, ViewerController controller) {
        this.model = model;
        this.controller = controller;
        this.viewUI = viewUI;
        isInit = true;
    }

    @Override
    public void run() {
        if (!isInit) {
            controller.showDebugInfo("未初始化参数,需调用init方法.");
            return;
        }

        controller.showDebugInfo("已开始读取：" + model.getFileInfo());
        while (isCanWorking) {
            switch (controller.getStatus()) {
                case ON:
                    //读取日志信息
                    readLogInfo();
                    break;

                case PAUSE:
                    //什么都不干 纯暂停2秒
                    waitTime(2000);
                    break;

                case OFF:
                    //标记退出状态
                    isCanWorking = false;
                    break;
            }

            //延时
            waitTime(100);
        }

        controller.showDebugInfo("已停止读取：" + model.getFileInfo());

    }

    /**
     * 等待 毫秒
     *
     * @param time
     */
    public void waitTime(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
        }
    }


    @Override
    public void log(String content) {
        String fileName = buildFileName();
        if (fileName != null) {
            try {
                // 目录不存在的情况下，创建目录。
               File dirs = new File(buildDirNames());
               if(!dirs.exists()){
                    dirs.mkdirs();
                }

                FileWriter writer = new FileWriter(fileName, true);
                writer.write(content.replaceAll("\n", "\r\n"));
                writer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 构建文件目录
     *
     * @return
     */
    private String buildDirNames() {
        //如果没有设置输出日志文件路径
        if (model.getOutputPath() == null || "".equalsIgnoreCase(model.getOutputPath())) {
            return null;
        }

        //拼接目录名  yyyy-MM-dd/Domain/IP
        StringBuffer dirNames = new StringBuffer();
        dirNames.append(model.getOutputPath())
                .append("/")
                .append(formater.format(new Date()))
                .append("/")
                .append(model.getDomain())
                .append("/")
                .append(model.getIp());

        //返回目录名
        return dirNames.toString();
    }


    /**
     * 构建文件名
     *
     * @return
     */
    private String buildFileName() {
        //如果没有设置输出日志文件路径
        if (model.getOutputPath() == null || "".equalsIgnoreCase(model.getOutputPath())) {
            return null;
        }

        //拼接文件名  yyyy-MM-dd/Domain/IP/FileName.log
        StringBuffer fileName = new StringBuffer();
        fileName.append(model.getOutputPath())
                .append("/")
                .append(formater.format(new Date()))
                .append("/")
                .append(model.getDomain())
                .append("/")
                .append(model.getIp())
                .append("/")
                .append(model.getURL().substring(model.getURL().lastIndexOf("/") + 1));

        //返回文件名
        return fileName.toString();
    }

    /**
     * 设置工作状态
     *
     * @param isCanWorking
     */
    public void setCanWorking(boolean isCanWorking) {
        this.isCanWorking = isCanWorking;
    }


    /**
     * 显示文本内容
     *
     * @param content
     */
    public void info(final String content) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                StringBuffer strContent = new StringBuffer();
                strContent.append(viewUI.getText())
                        .append(content);


                //超过最大长度 截取
                if (strContent.length() > Constants.MAX_LENGTH) {
                    int index = strContent.length() - Constants.MAX_LENGTH;
                    strContent.delete(0, index);
                }
                //显示内容
                viewUI.setText(strContent.toString());
            }
        }).start();
    }

    public ViewerController getController() {
        return controller;
    }
    public ViewerItemDataModel getModel() {
        return model;
    }
}