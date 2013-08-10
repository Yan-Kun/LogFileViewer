package com.yankun.logviewer.impl;

import com.yankun.logviewer.Switch;
import com.yankun.logviewer.ViewerController;
import com.yankun.logviewer.model.ViewerDataModel;
import com.yankun.logviewer.utils.ViewerUtils;
import com.yankun.logviewer.view.ViewerAction;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created with IntelliJ IDEA.
 * User: yankun
 * Date: 13-1-30
 * Time: 下午6:16
 * To change this template use File | Settings | File Templates.
 */
public class ViewerControllerImpl implements ViewerController {

    /**
     * 设置参数
     */
    private ViewerDataModel viewerDataModel;



    /**
     * 视图UI对象
     */
    private ViewerAction viewerAction;

    /**
     * 初始状态
     */
    private Switch status = Switch.DEFAULT;

    /**
     * 线程池
     */
    ExecutorService executorService = null;


    public ViewerControllerImpl(ViewerDataModel viewerDataModel, ViewerAction viewerAction) {
        this.viewerDataModel = viewerDataModel;
        this.viewerAction = viewerAction;

        //实例化一个线程池
        executorService = Executors.newFixedThreadPool(viewerDataModel.size());

    }

    @Override
    public void run() {
        //如果为初始状态
        if (status == Switch.DEFAULT) {
            //初始化视图
            ViewerUtils.initComponent(this, viewerDataModel, viewerAction.getjDesktopPane(), executorService);
        }
        //标识打开状态
        status = Switch.ON;
    }


    @Override
    public void pause() {
        status = Switch.PAUSE;
    }

    @Override
    public void stop() {
        status = Switch.OFF;
        if (executorService != null) {
            executorService.shutdownNow();
        }
    }

    @Override
    public Switch getStatus() {
        return status;
    }

    @Override
    public void showDebugInfo(String message) {
       viewerAction.showDebugInfo(message);
    }

}
