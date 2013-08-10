package com.yankun.logviewer.utils;

import com.yankun.logviewer.Constants;
import com.yankun.logviewer.model.Offset;
import com.yankun.logviewer.model.ViewerItemDataModel;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: yankun
 * Date: 13-2-3
 * Time: 下午5:27
 * To change this template use File | Settings | File Templates.
 */
public class Locator {



    /**
     * 获取位置　（没找到就返回最接近的位置）
     *
     * @param model
     * @param queryDate
     * @param start
     * @param current   读取的中间位置
     * @param end
     * @return
     */
    private static long getOffset(ViewerItemDataModel model, Date queryDate, long start, long current, long end) {
//        System.out.println("start:" + start + ":::end:" + end);

        if (current == start
                || current == end
                || start == end
                || current - start == 1
                || end - current == 1
                || end - start == 1) {
            return current;
        }

        CompareResult compareResult = CompareResult.Default;
        BufferedReader br = null;
        try {
            //读取信息
            byte[] contentBytes = HttpReader.read(model, current, current + Constants.DEFAULT_BUFFER_LENGTH);

            //按行读取数据
            br = new BufferedReader(new InputStreamReader(
                    new DataInputStream(
                            new ByteArrayInputStream(contentBytes)), Constants.DEFAULT_ENCODING));

            String line;
            while ((line = br.readLine()) != null) {
                //如果是空行或不是时间开头的,则忽略
                if ("".equalsIgnoreCase(line) || !startsWithDate(line)) {
                    continue;
                }

                Date date = getLogDate(line);
                //在靠近的位置直接返回
                if (model.getStartDate().getTime() == queryDate.getTime()) {  //找开始位置  靠前三分钟内
                    if (queryDate.getTime() > date.getTime() && queryDate.getTime() - date.getTime() <= Constants.ErrorLimits * 60 * 1000) {
                        //找到就直接退出循环
                        compareResult = CompareResult.Equal;
                        break;
                    } else {
                        if (date.getTime() > queryDate.getTime()) {
                            compareResult = CompareResult.Larger;
                        } else if (date.getTime() < queryDate.getTime()) {
                            compareResult = CompareResult.Small;
                        }
                    }
                } else if (model.getEndDate().getTime() == queryDate.getTime()) {  //找截止位置  靠后三分钟内
                    if (date.getTime() > queryDate.getTime() && date.getTime() - queryDate.getTime() <= Constants.ErrorLimits * 60 * 1000) {
                        //找到就直接退出循环
                        compareResult = CompareResult.Equal;
                        break;
                    } else {
                        if (date.getTime() > queryDate.getTime()) {
                            compareResult = CompareResult.Larger;
                        } else if (date.getTime() < queryDate.getTime()) {
                            compareResult = CompareResult.Small;
                        }
                    }
                } else {

                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null)
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        switch (compareResult) {
            case Larger:
                return getOffset(model, queryDate, start, (current - start) / 2 + start, current);
            case Equal:
                return current;
            case Small:
                return getOffset(model, queryDate, current, (end - current) / 2 + current, end);
            default:
                if (current < Constants.DEFAULT_BUFFER_LENGTH) {
                    return getOffset(model, queryDate, start, current / 2, end);
                } else {
                    return getOffset(model, queryDate, start, current - Constants.DEFAULT_BUFFER_LENGTH, end);
                }
        }
    }


    /**
     * 获取开始和截止位置信息
     *
     * @param model
     * @return
     */
    public static Offset getOffset(ViewerItemDataModel model) {

        Offset offset = null;
        //获取文件总长度
        long length = HttpReader.getLength(model);

        if (length != -1) {
            Date lastDate = getLastDate(model, length);

            //为了防死循环，最多只读取１０次
            int maxTimes = 10;
            long startIndex = length - Constants.DEFAULT_BUFFER_LENGTH;
            while (lastDate == null && maxTimes > 0) {
                lastDate = getLastDate(model, startIndex);
                startIndex = startIndex - Constants.DEFAULT_BUFFER_LENGTH;
                maxTimes--;
            }

            if (lastDate != null) {
                //设置起止时间和截止时间
                setDate(model, lastDate);

                //不在有效时间范围内
                if (model.getStartDate().getTime() <= lastDate.getTime()) {

                    //查找起始位置
                    long start = getOffset(model, model.getStartDate(), 0, length / 2, length);
                    if (start != -1) {
                        offset = new Offset();
                        offset.setStart(start);

                        //查找截止位置
                        if (model.getEndDate().getTime() > lastDate.getTime()) {
                            offset.setEnd(length);
                        } else {
                            offset.setEnd(getOffset(model, model.getEndDate(), start, (length - start) / 2 + start, length));
                        }
                    }
                }
            }
        }
        return offset;
    }

    /**
     * 设置起止时间和截止时间
     *
     * @param model
     * @param lastDate
     */
    private static void setDate(ViewerItemDataModel model, Date lastDate) {
        try {
            String date = new SimpleDateFormat("yyyy-MM-dd").format(lastDate);
            model.setStartDate(new SimpleDateFormat(Constants.DateFormat_yyyyMMddHHmmssSSS).parse(date + " " + model.getStartTime() + ":00,000"));
            model.setEndDate(new SimpleDateFormat(Constants.DateFormat_yyyyMMddHHmmssSSS).parse(date + " " + model.getEndTime() + ":59,999"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取尾部时间缀
     *
     * @param line
     * @return
     */
    private static Date getLogDate(String line) {
        try {
            return new SimpleDateFormat(Constants.DateFormat_yyyyMMddHHmmssSSS).parse(line.substring(0, 23));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取日志尾部时间的小时数
     *
     * @param model
     * @param length
     * @return
     */
    private static Date getLastDate(ViewerItemDataModel model, long length) {
        Date date = null;
        BufferedReader br = null;
        try {
            //读取文件末尾内容信息
            byte[] contentBytes = HttpReader.read(model, length - Constants.DEFAULT_BUFFER_LENGTH, length);
            // 如果没有读到数据
            if (contentBytes == null || contentBytes.length == 0) {
                return null;
            }

            //按行读取数据
            br = new BufferedReader(new InputStreamReader(
                    new DataInputStream(
                            new ByteArrayInputStream(contentBytes)), Constants.DEFAULT_ENCODING));
            String line;
            while ((line = br.readLine()) != null) {
                //如果是空行,则忽略
                if ("".equalsIgnoreCase(line)) {
                    continue;
                }

                if (startsWithDate(line)) {
                    date = getLogDate(line);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null)
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        return date;
    }

    /**
     * 是否是时间格式开头的()
     *
     * @param line
     * @return
     */
    private static boolean startsWithDate(String line) {
        if (line == null
                || "".equalsIgnoreCase(line.trim())
                || !line.startsWith("2")
                || line.trim().length() < 23
                || !"-".equalsIgnoreCase(line.substring(4, 5))
                || !"-".equalsIgnoreCase(line.substring(7, 8))
                || !" ".equalsIgnoreCase(line.substring(10, 11))
                || !":".equalsIgnoreCase(line.substring(13, 14))
                || !":".equalsIgnoreCase(line.substring(16, 17))
                || !",".equalsIgnoreCase(line.substring(19, 20))
                ) {
            return false;
        }

        return true;
    }


    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        ViewerItemDataModel model = new ViewerItemDataModel();
        model.setDomain("caipiao.jd.com");
        model.setIp("172.17.24.32");
        model.setLogFile("/logs/lottery-all.log");
        model.setOutputPath("d:/logs");
        //model.setLogFile("/logs/checkprice/airplane-price.log");
        model.setStartTime("00:00");
        model.setEndTime("10:00");
        Offset offset = getOffset(model);
        if (offset == null) {
            System.out.println(model.getFileInfo()
                    + "读取总长度为: 0 byte"
                    + ":::耗费:::" + (System.currentTimeMillis() - startTime) + "ms"
            );
        } else {
            System.out.println(model.getFileInfo()
                    + "读取总长度为:" + (offset.getEnd() - offset.getStart()) + "byte"
                    + ":::耗费:::" + (System.currentTimeMillis() - startTime) + "ms"
            );

            while (offset.getStart()<offset.getEnd()){
                byte[] contentBytes = HttpReader.read(model, offset.getStart(), offset.getStart() + Constants.DEFAULT_BUFFER_LENGTH);
                if (contentBytes!=null){
                    System.out.println("read length:" + contentBytes.length);
                }else{
                    System.out.println("read null.") ;
                }
                offset.skip(Constants.DEFAULT_BUFFER_LENGTH);

            }

        }



    }


}
