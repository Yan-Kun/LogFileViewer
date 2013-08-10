package com.yankun.logviewer.utils;

import com.yankun.logviewer.Model;
import com.yankun.logviewer.Viewer;
import com.yankun.logviewer.ViewerController;
import com.yankun.logviewer.impl.TailerImpl;
import com.yankun.logviewer.impl.ViewerImpl;
import com.yankun.logviewer.model.ViewerDataModel;
import com.yankun.logviewer.model.ViewerItemDataModel;

import javax.swing.*;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;

/**
 * Created with IntelliJ IDEA.
 * User: yankun
 * Date: 13-1-10
 * Time: 下午10:54
 * To change this template use File | Settings | File Templates.
 */
public class ViewerUtils {

    /**
     * 根据配置参数构建视图
     *
     * @param controller
     * @param model
     * @param viewUI
     * @param executorService
     */
    public static void initComponent(final ViewerController controller, ViewerDataModel model, JComponent viewUI, final ExecutorService executorService) {
        //遍历
        for (int i = 0; i < model.getSelectedLogFiles().size(); i++) {
            String logFile = model.getSelectedLogFiles().get(i);
            JInternalFrame childFrame = new JInternalFrame(logFile, true, true, true, true);
            childFrame.setLocation(20 * (i + 1), 20 * (i + 1));
            childFrame.setSize(viewUI.getWidth() * 2 / 3, viewUI.getHeight() / 2);
            childFrame.setVisible(true);

            final java.util.List<Viewer> viewers = new ArrayList<Viewer>();

            //创建一个TabPanel
            JTabbedPane tabbedPane = new JTabbedPane();

            //根据服务器IP列表遍历
            for (String ip : model.getIpList()) {
                JTextArea jTextArea = new JTextArea();

                jTextArea.setLineWrap(true);//激活自动换行功能
                jTextArea.setWrapStyleWord(true);//激活断行不断字功能
                jTextArea.setEditable(false);  //不可编辑

                JPanel jPanel = new JPanel();
                jPanel.setLayout(new GridLayout(1, 1));
                jPanel.add(new JScrollPane(jTextArea));    //将组件加入容器

                ViewerItemDataModel viewerItemDataModel = new ViewerItemDataModel();
                viewerItemDataModel.setIp(ip);
                viewerItemDataModel.setDomain(model.getDomain());
                //设置选定的日志文件
                viewerItemDataModel.setLogFile(logFile);
                //日志输出目录
                viewerItemDataModel.setOutputPath(model.getOutputPath());

                //时间段
                viewerItemDataModel.setStartTime(model.getStartTime());
                viewerItemDataModel.setEndTime(model.getEndTime());

                //添加一个panel
                tabbedPane.add(ip, jPanel);

                //启动线程跟踪日志
                Viewer viewer = getViewer(model.getModel());
                //初始化
                viewer.init(viewerItemDataModel, jTextArea, controller);
                viewers.add(viewer);
                executorService.submit(viewer);
            }

            childFrame.add(tabbedPane);
            viewUI.add(childFrame);


            //注册内部窗口关闭事件
            childFrame.addInternalFrameListener(new InternalFrameAdapter() {
                @Override
                public void internalFrameClosed(InternalFrameEvent e) {
                    super.internalFrameClosed(e);

                    //停止当前窗口关联的线程
                    for (Viewer runnable : viewers) {
                        runnable.setCanWorking(false);
                    }
                }
            });
        }
    }

    /**
     * 根据不同的查看模式返回不同的接口实例
     *
     * @param model
     * @return
     */
    private static Viewer getViewer(Model model) {
        switch (model) {
            case View:
                return new ViewerImpl();
            default:
                return new TailerImpl();
        }
    }
}
