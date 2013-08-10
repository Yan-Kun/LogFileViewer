package com.yankun.logviewer;

/**
 * Created with IntelliJ IDEA.
 * User: yankun
 * Date: 13-1-7
 * Time: 上午11:02
 * To change this template use File | Settings | File Templates.
 */
public enum Switch {
    /**
     * 初始状态
     */
    DEFAULT(0),

    /**
     * 打开
     */
    ON(1),

    /**
     * 暂停
     */
    PAUSE(2),

    /**
     * 关闭
     */
    OFF(3);


    private int status;

    private Switch(int status) {
        this.status = status;
    }
}
