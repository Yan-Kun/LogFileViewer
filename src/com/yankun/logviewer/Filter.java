package com.yankun.logviewer;

/**
 * Created with IntelliJ IDEA.
 * User: yankun
 * Date: 13-1-11
 * Time: 上午9:38
 * To change this template use File | Settings | File Templates.
 */
public class Filter {
    /**
     * 关键字
     */
    private static volatile String keyWord = null;

    public static String getKeyWord() {
        return keyWord;
    }

    public static void setKeyWord(String keyWord) {
        Filter.keyWord = keyWord;
    }
}
