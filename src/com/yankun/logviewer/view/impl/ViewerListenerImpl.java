package com.yankun.logviewer.view.impl;

import com.yankun.logviewer.view.ViewerAction;
import com.yankun.logviewer.view.ViewerListener;

/**
 * @author yankun
 * @version 1.0
 * @created 30-一月-2013 13:52:01
 */
public class ViewerListenerImpl implements ViewerListener {
    private boolean isCanExit = false;

    /**
     * @param viewerAction
     */
    public void listen(final ViewerAction viewerAction) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {

                    if(isCanExit){
                        break;
                    }
                    //判断父窗口里还有没有子窗口
                    if (viewerAction.getjDesktopPane().getComponentCount() == 0) {
                        //调用stop
                        viewerAction.stop();
                        break;
                    }

                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }

    @Override
    public void stop() {
        isCanExit = true;
    }


}