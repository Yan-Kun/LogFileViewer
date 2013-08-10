package com.yankun.logviewer.utils;

/**
 * Created with IntelliJ IDEA.
 * User: yankun
 * Date: 13-3-12
 * Time: 下午6:01
 * To change this template use File | Settings | File Templates.
 */
public enum CompareResult {
    Default(0),//默认初始值
    Larger(1),//大于
    Equal(2), //相等　
    Small(3),; //小于
    private int value;

    CompareResult(int value) {
        this.value = value;
    }
}
