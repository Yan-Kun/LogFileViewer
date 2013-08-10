package com.yankun.logviewer.view;

/**
 * @author yankun
 * @version 1.0
 * @created 30-一月-2013 13:52:01
 */
public interface ViewerListener {

    /**
     *监听视图元素
     * @param viewer
     */
    public void listen(ViewerAction viewer);


    /**
     * 停止
     */
    public void stop();

}