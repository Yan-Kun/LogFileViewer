package com.yankun.logviewer.utils;

import com.yankun.logviewer.Constants;
import com.yankun.logviewer.model.ViewerItemDataModel;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.*;

/**
 * Created with IntelliJ IDEA.
 * User: yankun
 * Date: 13-1-7
 * Time: 下午5:36
 * To change this template use File | Settings | File Templates.
 */
public class HttpReader {
    /**
     * 创建 HttpURLConnection
     *
     * @param model
     * @return
     * @throws IOException
     */
    private static HttpURLConnection createHttpURLConnection(ViewerItemDataModel model) throws IOException {
        URL url = new URL(model.getURL());
        String str[] = model.getIp().split("\\.");
        byte[] b =new byte[str.length];
        for(int i=0,len=str.length;i<len;i++){
            b[i] = (byte)(Integer.parseInt(str[i],10));
        }
        Proxy proxy = new Proxy(Proxy.Type.HTTP,
                new InetSocketAddress(InetAddress.getByAddress(b), 80));
        HttpURLConnection connection = (HttpURLConnection) url
                .openConnection(proxy);

        connection.setDoOutput(true);
        connection.setConnectTimeout(Constants.DEFAULT_TIMEOUT);
        connection.setReadTimeout(Constants.DEFAULT_TIMEOUT);
        connection.setDefaultUseCaches(false);
        connection.setUseCaches(false);

        return connection;
    }

    /**
     * 获取长度
     *
     * @param model
     * @return
     */
    public static long getLength(ViewerItemDataModel model) {
        long length = -1;
        HttpURLConnection connection = null;
        try {
            connection = createHttpURLConnection(model);
            //设置head请求
            connection.setRequestMethod("HEAD");

            if (connection.getHeaderField("Content-Length") != null) {
                //获取长度
                length = Long.parseLong(connection.getHeaderField("Content-Length"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null)
                connection.disconnect();
        }
        return length;
    }

    /**
     * 获取长度
     *
     * @param model
     * @param startOffset
     * @param endOffset
     * @return
     */
    public static byte[] read(ViewerItemDataModel model, long startOffset, long endOffset) {
//        System.out.println(model.getFileInfo() + ":::start: " + startOffset + "::::end:::" + endOffset);

        byte[] contentBytes = null;

        HttpURLConnection connection = null;
        try {
            connection = createHttpURLConnection(model);

            //设置GET请求
            connection.setRequestMethod("GET");
            //设置连接信息
            connection.setRequestProperty("Connection", "Keep-Alive");
            //设置请求的数据块
            connection.addRequestProperty("Range", "bytes=" + startOffset + "-" + endOffset);

            if (connection.getHeaderField("Content-Range") == null) {
                return null;
            }

            if ((connection.getResponseCode() < 200) || (connection.getResponseCode() > 300)) {
                return null;
            }

            byte[] src = converToByteArray(connection.getInputStream());
            //去掉尾部没有换行符的内容
            int lastIndex = -1;
            for (int i = src.length - 1; i >= 0; i--) {
                if (src[i] == '\n') {
                    lastIndex = i;
                    break;
                }
            }
            if (lastIndex == -1 || lastIndex==0) {
                contentBytes = src;
            } else {
                contentBytes = new byte[lastIndex];
                System.arraycopy(src, 0, contentBytes, 0, lastIndex);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null)
                connection.disconnect();
        }

        return contentBytes;
    }

    /**
     * 读取byte数据
     *
     * @param inputStream
     * @return
     */
    private static byte[] converToByteArray(InputStream inputStream) {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        byte[] data = new byte[2046];
        try {
            int read;
            while ((read = inputStream.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, read);
            }
            buffer.flush();
        } catch (IOException e) {
        }
        return buffer.toByteArray();
    }




}
