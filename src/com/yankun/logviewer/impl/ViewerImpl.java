package com.yankun.logviewer.impl;

import com.yankun.logviewer.AbstractViewer;
import com.yankun.logviewer.Constants;
import com.yankun.logviewer.Filter;
import com.yankun.logviewer.Switch;
import com.yankun.logviewer.model.Offset;
import com.yankun.logviewer.utils.HttpReader;
import com.yankun.logviewer.utils.Locator;

import java.io.*;

/**
 * @author yankun
 * @version 1.0
 * @created 30-一月-2013 13:52:01
 */
public class ViewerImpl extends AbstractViewer {
    /**
     * 文件位置标记
     */
    private Offset offset = null;

    @Override
    public void readLogInfo() {

        BufferedReader br = null;
        try {
            if (offset == null) {
                long startTime = System.currentTimeMillis();
                //获取读取区域位置
               offset = Locator.getOffset(getModel());
               //如果没有查找到位置信息
                if(offset==null){
                    getController().showDebugInfo(getModel().getFileInfo()
                            + "读取总长度为: 0 byte"
                            + ":::耗费:::" + (System.currentTimeMillis() - startTime) + "ms"
                    );
                    setCanWorking(false);
                    return ;
                }else{
                    getController().showDebugInfo(getModel().getFileInfo()
                            + "读取总长度为:" + (offset.getEnd()-offset.getStart()) + "byte"
                            + ":::耗费:::" + (System.currentTimeMillis() - startTime) + "ms"
                            );
                }
            }


            //读取信息
            byte[] contentBytes = HttpReader.read(getModel(), offset.getStart(), offset.getStart()+ Constants.DEFAULT_BUFFER_LENGTH);
            // 如果没有读到数据
            if (contentBytes == null || contentBytes.length == 0) {
                return;
            }

            //移动开始位置
            offset.skip(contentBytes.length);

            //按行读取数据
            br = new BufferedReader(new InputStreamReader(
                    new DataInputStream(
                            new ByteArrayInputStream(contentBytes)), Constants.DEFAULT_ENCODING));


            StringBuilder strContent = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                //如果是空行,则忽略
                if ("".equalsIgnoreCase(line)) {
                    continue;
                }

                //关闭状态
                if (getController().getStatus() == Switch.OFF) {
                    break;
                }

                //如果设置有关键字过滤
                if (Filter.getKeyWord() != null
                        && !"".equalsIgnoreCase(Filter.getKeyWord())
                        && line.indexOf(Filter.getKeyWord()) == -1) {
                    continue;
                }

                //如果暂停
                while (getController().getStatus() == Switch.PAUSE) {
                    waitTime(1000);
                }
                strContent.append(line).append("\n");
            }
            if (strContent.length() > 0) {

                //显示内容
                info(strContent.toString());

                //记录内容
                log(strContent.toString());
            }


            //如果已读到截止位置
            if(offset.getEnd()!=-1 && offset.getStart()>= offset.getEnd()){
                setCanWorking(false);
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
    }
}