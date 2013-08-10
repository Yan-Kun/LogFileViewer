package com.yankun.logviewer.view;

import com.yankun.logviewer.*;
import com.yankun.logviewer.impl.ViewerControllerImpl;
import com.yankun.logviewer.model.ViewerDataModel;
import com.yankun.logviewer.view.impl.ViewerListenerImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 界面的控制逻辑
 *
 * @author yankun
 * @version 1.0
 * @created 30-一月-2013 13:52:01
 */
public class ViewerAction extends ViewerWindow{

    private ViewerListener viewerListener = new ViewerListenerImpl();

    private ViewerController viewerController;

    public ViewerAction() {

        getAddIpButton().addActionListener(new ActionListener() {
            @Override
            /**
             * 添加服务器IP
             */
            public void actionPerformed(ActionEvent e) {
                if (getIpTXT().getText() != null && !getIpTXT().getText().equalsIgnoreCase("")) {
                    DefaultListModel ipListModel = (DefaultListModel) getIpList().getModel();
                    //防重
                    if (ipListModel.contains(getIpTXT().getText())) {
                        showMessageDialog("服务器IP[" + getIpTXT().getText() + "]已经存在");
                    } else {
                        ipListModel.addElement(getIpTXT().getText());
                    }
                    getIpList().setModel(ipListModel);
                }
            }
        });

        //添加日志文件
        getAddLogFile().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (getLogFileTXT().getText() != null && !getLogFileTXT().getText().equalsIgnoreCase("")) {
                    DefaultListModel logFileListModel = (DefaultListModel) getLogFileList().getModel();
                    //防重
                    if (logFileListModel.contains(getLogFileTXT().getText())) {
                        showMessageDialog("日志文件[" + getLogFileTXT().getText() + "]已经存在");
                    } else {
                        logFileListModel.addElement(getLogFileTXT().getText());
                    }
                    getLogFileList().setModel(logFileListModel);
                }
            }
        });

        //设置关键字
        getFilterKeyWord().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (getKeyWordTXT().getText() != null) {
                    //关键字过滤
                    setFilterKeyWord(getKeyWordTXT().getText());
                }
            }
        });



        //加载配置
        getLoadButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fDialog = new JFileChooser(); //文件选择器
                int result = fDialog.showOpenDialog(getjFrame());
                if (result == JFileChooser.APPROVE_OPTION) {
                    loadData(fDialog.getSelectedFile().getAbsolutePath());
                }
            }
        });

        //保存配置文件
        getSaveButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fDialog = new JFileChooser();
                fDialog.setDialogType(JFileChooser.SAVE_DIALOG);//设置保存对话框
                int result = fDialog.showDialog(null, "保存文件");
                if (result == JFileChooser.APPROVE_OPTION) {
                    saveConfig(fDialog.getSelectedFile().getAbsolutePath());
                }
            }
        });
        getClearButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearConfig();
            }
        });

        //执行
        getExecButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isCanExecute()) {
                    //如果控制器存在,而且是活动的，则先尝试终止
                    if (viewerController != null) {
                        switch (viewerController.getStatus()) {
                            case PAUSE:
                                //运行
                                viewerController.run();
                                break;

                            case OFF:
                                //执行
                                execute();
                                break;
                        }
                    } else {
                        //执行
                        execute();
                    }
                    switchControlButton(Switch.ON);
                }
            }
        });
        //终止
        getStopButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stop();
            }
        });

        //暂停
        getPauseButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //如果logFileViewer存在
                if (viewerController != null) {
                    viewerController.pause();
                    showDebugInfo("已暂停读取日志");
                    switchControlButton(Switch.PAUSE);
                }

            }
        });

        //输出目录
        getOutputButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fDialog = new JFileChooser();
                //设置文件选择框的标题
                fDialog.setDialogTitle("请选择生成代码的输出目录");
                fDialog.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);// 只能选择目录
                //弹出选择框
                int returnVal = fDialog.showOpenDialog(null);
                // 如果是选择了文件
                if (JFileChooser.APPROVE_OPTION == returnVal) {
                    getOutputPathTXT().setText(fDialog.getSelectedFile().getAbsolutePath());
                }
            }
        });
        //ＩＰ列表右键
        getIpList().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == 3 && !getIpList().isSelectionEmpty()) {
                    getjPopupMenu().show(getIpList(), e.getX(), e.getY());
                }

            }
        });

        //日志文件列表
        getLogFileList().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(!getLogFileList().isSelectionEmpty()){
                    if (e.getButton() == 3) {
                        getjPopupMenu().show(getLogFileList(), e.getX(), e.getY());
                    }else{
                       getLogFileTXT().setText((String) getLogFileList().getSelectedValue());
                    }
                }
            }
        });


        //关于
        getAboutButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showMessageDialog("感谢信息部研发的N多同学支持和体验（陈月华、丁琼、薛韬、李扬帆、王美青、陈有存......） \n感谢CCTV，\n感谢CCAV，\n感谢......\n有问题请发邮件至yankun@jd.com");
            }
        });

        //关键字框回车
        getKeyWordTXT().addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER)   //按回车键执行相应操作;
                {
                    getFilterKeyWord().doClick();
                }

            }
        });

        //层叠子窗口
        getCenDieButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < getjDesktopPane().getAllFrames().length; i++) {
                    JInternalFrame childFrame = getjDesktopPane().getAllFrames()[i];
                    childFrame.setLocation(20 * (i + 1), 20 * (i + 1));
                    childFrame.setSize(getjDesktopPane().getWidth() * 2 / 3, getjDesktopPane().getHeight() / 2);
                    getjDesktopPane().getDesktopManager().openFrame(childFrame);
                }
            }
        });

        //横向排列
        getDuiDieButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < getjDesktopPane().getAllFrames().length; i++) {
                    JInternalFrame childFrame = getjDesktopPane().getAllFrames()[i];
                    childFrame.setLocation(0, i * getjDesktopPane().getHeight() / getjDesktopPane().getAllFrames().length);
                    childFrame.setSize(getjDesktopPane().getWidth(), getjDesktopPane().getHeight() / getjDesktopPane().getAllFrames().length);
                    getjDesktopPane().getDesktopManager().openFrame(childFrame);
                }
            }
        });
        //纵向排列
        getBingPaiButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < getjDesktopPane().getAllFrames().length; i++) {
                    JInternalFrame childFrame = getjDesktopPane().getAllFrames()[i];
                    childFrame.setLocation(i * getjDesktopPane().getWidth() / getjDesktopPane().getAllFrames().length, 0);
                    childFrame.setSize(getjDesktopPane().getWidth() / getjDesktopPane().getAllFrames().length, getjDesktopPane().getHeight());
                    getjDesktopPane().getDesktopManager().openFrame(childFrame);
                }
            }
        });


        //模式单选按钮
        getTailModelRadio().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getViewerModelRadio().setSelected(false);
                getTailModelRadio().setSelected(true);
            }
        });
        getViewerModelRadio().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getTailModelRadio().setSelected(false);
                getViewerModelRadio().setSelected(true);
            }
        });

    }


    /**
     * 停止
     */
    public void stop() {
        //如果控制器存在,而且是活动的，则先尝试终止
        if (viewerController != null) {
            viewerController.stop();
            //关闭所有子窗口
            for (JInternalFrame jInternalFrame : getjDesktopPane().getAllFrames()) {
                jInternalFrame.dispose();
            }
        }
        switchControlButton(Switch.OFF);
    }

    /**
     * 执行
     */
    private void execute() {
        if (viewerController != null) {
            //尝试停止
            viewerController.stop();
        }

        //构建一个控制器
        viewerController = new ViewerControllerImpl(getViewerDataModel(), this);

        //启动控制器
        viewerController.run();

        //启动检查线程　
        viewerListener.listen(this);

    }

    /**
     * 检查执行条件
     *
     * @return
     */
    private boolean isCanExecute() {
        if (getDomainTxT().getText() == null || "".equalsIgnoreCase(getDomainTxT().getText())) {
            showMessageDialog("请录入应用的域名");
            getDomainTxT().requestFocus(true);
            return false;
        }

        if (getIpList().getModel().getSize() == 0) {
            showMessageDialog("请录入服务器IP地址，并添加到列表中");
            getIpTXT().requestFocus();
            return false;
        }

        if (getLogFileList().isSelectionEmpty()) {
            showMessageDialog("请选中日志列表文件，可多选,如果列表为空，请添加。");
            getLogFileTXT().requestFocus(true);
            return false;
        }


        if(getViewerModelRadio().isSelected()){
            String startTime = new SimpleDateFormat("HHmm").format((Date) getStartTime().getValue());
            String endTime = new SimpleDateFormat("HHmm").format((Date) getEndTime().getValue());
            if(Integer.parseInt(startTime) > Integer.parseInt(endTime)){
                showMessageDialog("截止时间不能小于起始时间");
                getEndTime().requestFocus();
                return false;
            }
        }

        return true;
    }

    /**
     * 清空配置
     */
    private void clearConfig() {
        getDomainTxT().setText("");
        getIpTXT().setText("");
        getIpList().setModel(new DefaultListModel());
        getLogFileTXT().setText("");
        getLogFileList().setModel(new DefaultListModel());
        getKeyWordTXT().setText("");
        getOutputPathTXT().setText("");
        getTailModelRadio().setSelected(true);
        getViewerModelRadio().setSelected(false);
    }

    /**
     * 保存配置
     *
     * @param fileName
     */
    private void saveConfig(String fileName) {
        //获取 ViewerDataModel
        ViewerDataModel model = getViewerDataModel();

        //保存文件
        model.saveAs(fileName);
    }

    /**
     * 加截配置
     */
    private void loadData(String fileName) {
        ViewerDataModel model = ViewerDataModel.load(fileName);
        getDomainTxT().setText(model.getDomain());
        //加载IP列表
        DefaultListModel ipListModel = new DefaultListModel();
        for (String ip : model.getIpList()) {
            ipListModel.addElement(ip);
        }
        getIpList().setModel(ipListModel);

        //加载日志列表
        DefaultListModel logFileListModel = new DefaultListModel();
        for (String logFile : model.getLogFileList()) {
            logFileListModel.addElement(logFile);
        }
        getLogFileList().setModel(logFileListModel);

        //输出目录
        getOutputPathTXT().setText(model.getOutputPath());
    }

    /**
     * 获取Configure
     *
     * @return
     */
    private ViewerDataModel getViewerDataModel() {
        ViewerDataModel model = new ViewerDataModel();

        //设置域名
        model.setDomain(getDomainTxT().getText());

        //设置ip列表
        for (int i = 0; i < getIpList().getModel().getSize(); i++) {
            model.getIpList().add((String) getIpList().getModel().getElementAt(i));
        }

        //设置日文件列表
        for (int i = 0; i < getLogFileList().getModel().getSize(); i++) {
            model.getLogFileList().add((String) getLogFileList().getModel().getElementAt(i));
        }

        //设置选中的日志文件
        if (!getLogFileList().isSelectionEmpty()) {
            model.getSelectedLogFiles().clear();
            for (Object object : getLogFileList().getSelectedValues()) {
                model.getSelectedLogFiles().add((String) object);
            }
        }

        //设置输出目录
        model.setOutputPath(getOutputPathTXT().getText());

        //模式
        if(getViewerModelRadio().isSelected()){
            model.setModel(Model.View);
        }else{
            model.setModel(Model.Tail);
        }

        model.setStartTime(new SimpleDateFormat(Constants.DateFormat_HHmm).format(getStartTime().getValue()));
        model.setEndTime(new SimpleDateFormat(Constants.DateFormat_HHmm).format(getEndTime().getValue()));
        return model;
    }


    /**
     * 显示窗口
     */
    public void show() {
        setjFrame(new JFrame("日志查看工具[京东商城专用版]by Yankun"));
        getjFrame().add(getMainPanel());
        getjFrame().setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getjFrame().setSize(Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height - 10);
        getjFrame().setExtendedState(Frame.MAXIMIZED_BOTH);
        //日志输出路径框不可手动输入
        getOutputPathTXT().setEditable(false);

        getMainJplitPane().setDividerLocation(540);

        //构建一个JDesktopPane
        setjDesktopPane(new JDesktopPane());

        //放到显示Panel里
        getViewInfoPane().add(getjDesktopPane());

        //设置jDesktopPane背景色
        getjDesktopPane().setBackground(new Color(51, 51, 51));

        //设置时间格式
        try {
            getStartTime().setModel(new SpinnerDateModel(new SimpleDateFormat("HH:mm").parse("00:00"), null, null, Calendar.MINUTE));

            JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(getStartTime(), "HH:mm");
            getStartTime().setEditor(dateEditor);


            getEndTime().setModel(new SpinnerDateModel(new SimpleDateFormat("HH:mm").parse("00:00"), null, null, Calendar.MINUTE));
            JSpinner.DateEditor dateEditor2 = new JSpinner.DateEditor(getEndTime(), "HH:mm");
            getEndTime().setEditor(dateEditor2);

        } catch (ParseException e) {
            e.printStackTrace();
        }


        //创建弹出菜单
        setjPopupMenu(new JPopupMenu());
        JMenuItem ipMenuItem = new JMenuItem("移除");
        //注册移除事件
        ipMenuItem.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                JList jList = (JList) getjPopupMenu().getInvoker();
                if (!jList.isSelectionEmpty()) {
                    int index = jList.getSelectedIndex();
                    if (index == -1 || index >= jList.getModel().getSize())
                        return;

                    //移除选中数据项
                    DefaultListModel listMode = (DefaultListModel) jList.getModel();
                    listMode.remove(index);
                    jList.setModel(listMode);

                }
            }
        });
        getjPopupMenu().add(ipMenuItem);

        //设置控制按钮状态
        switchControlButton(Switch.DEFAULT);

        getjFrame().addWindowListener(new WindowAdapter() {
            public void windowClosing(final WindowEvent event) {
                //如果不为空 则先执行stop
                if (viewerController != null) {
                    viewerController.stop();
                }

                if(viewerListener!=null){
                    viewerListener.stop();
                }

                System.exit(0);
            }
        });

        getjFrame().setVisible(true);

    }


    /**
     * 按制按钮状态
     *
     * @param status
     */
    private void switchControlButton(Switch status) {
        switch (status) {
            case DEFAULT:
                getExecButton().setEnabled(true);
                getPauseButton().setEnabled(false);
                getStopButton().setEnabled(false);
                break;
            case ON:
                getExecButton().setEnabled(false);
                getPauseButton().setEnabled(true);
                getStopButton().setEnabled(true);
                break;
            case PAUSE:
                getExecButton().setEnabled(true);
                getPauseButton().setEnabled(false);
                getStopButton().setEnabled(true);
                break;
            case OFF:
                getExecButton().setEnabled(true);
                getPauseButton().setEnabled(false);
                getStopButton().setEnabled(false);
                break;
        }
    }

    /**
     * 设置过滤关键字
     *
     * @param keyWord
     */
    private void setFilterKeyWord(String keyWord) {
        //关键字过滤
        Filter.setKeyWord(keyWord);
        getBottomInfoLable().setText("过滤关键字：" + keyWord);
    }


    /**
     * 显示提示信息
     *
     * @param message
     */
    public void showMessageDialog(String message) {
        JOptionPane.showMessageDialog(getjFrame(), message, "  提示信息  ", JOptionPane.INFORMATION_MESSAGE);
    }



    /**
     * 显示调试信息
     * @param message
     */
    public synchronized  void showDebugInfo(String message){
        String text =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS").format(new Date())+" - " + message +"\n" + getDebugJTestArea().getText();
        getDebugJTestArea().setText(text);
    }

    public static void main(String[] args) {
        new ViewerAction().show();
    }

}