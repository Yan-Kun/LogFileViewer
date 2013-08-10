package com.yankun.logviewer;

/**
 * Created with IntelliJ IDEA.
 * User: yankun
 * Date: 13-1-30
 * Time: 上午10:46
 * To change this template use File | Settings | File Templates.
 */
public enum Model {
    Tail(0), View(1);

    private int model;

    private Model(int model) {
        this.model = model;
    }
}
