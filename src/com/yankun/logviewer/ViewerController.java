package com.yankun.logviewer;

/**
 * Created with IntelliJ IDEA.
 * User: yankun
 * Date: 13-1-30
 * Time: 下午5:50
 * To change this template use File | Settings | File Templates.
 */
public interface ViewerController {

    /**
     * 执行
     */
    public void run();

    /**
     * 暂停
     */
    public void pause();


    /**
     * 停止
     */
    public void stop();

    /**
     * 获取状态
     *
     * @return
     */
    public Switch getStatus();

    /**
     * 显示调试信息
     * @param message
     */
    public void showDebugInfo(String message);

}
