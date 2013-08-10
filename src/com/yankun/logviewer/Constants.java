package com.yankun.logviewer;

/**
 * Created with IntelliJ IDEA.
 * User: yankun
 * Date: 13-1-7
 * Time: 上午11:26
 * To change this template use File | Settings | File Templates.
 */
public class Constants {
    /**
     * 最大显示内容长度  24K
     */
    public static final int MAX_LENGTH = 1024 * 24;

    /**
     * 默认httpclient请求的超时时间
     */
    public static final int DEFAULT_TIMEOUT = 15000;


    /**
     * 默认为GBK
     */
    public static final String DEFAULT_ENCODING = "GBK";

    /**
     * 默认BUFFER长度  4k
     */
    public static final long DEFAULT_BUFFER_LENGTH = 4* 1024L;


    /**
     * 时间缀格式
     */
    public static final String DateFormat_yyyyMMddHHmmssSSS = "yyyy-MM-dd HH:mm:ss,SSS";

    /**
     * 小时分钟
     */
    public static final String DateFormat_HHmm = "HH:mm";


    //误差范围　默认３分钟
    public static final int ErrorLimits = 3;

}
