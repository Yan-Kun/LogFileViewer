package com.yankun.logviewer.model;

/**
 * Created with IntelliJ IDEA.
 * User: yankun
 * Date: 13-1-9
 * Time: 下午3:20
 * To change this template use File | Settings | File Templates.
 */

/**
 * 文件位置标识类
 */
public class Offset {


    /**
     * 开始位置
     */
    private long start = 0;


    /**
     * 截止位置
     */
    private long end = 0;

    public Offset() {

    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    public Offset(long start) {
        this.start = start;
    }

    public Offset(long start, long end) {
        this.start = start;
        this.end = end;
    }

    public void skip(long offset) {
        setStart(this.start + offset);
    }


    @Override
    public String toString() {
        return ":::Offset:::start:::"+ this.start +":::end:::" + this.end;
    }
}