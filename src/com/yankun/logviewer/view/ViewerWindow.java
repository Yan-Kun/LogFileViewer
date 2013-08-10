package com.yankun.logviewer.view;

import javax.swing.*;

/**
 * Created with IntelliJ IDEA.
 * User: yankun
 * Date: 13-1-4
 * Time: 下午3:13
 * To change this template use File | Settings | File Templates.
 */
public abstract class ViewerWindow {
    private JFrame jFrame;

    private JPanel mainPanel;
    private JPanel viewInfoPane;


    private JTextField domainTxT;

    private JTextField ipTXT;
    private JList ipList;
    private JButton addIpButton;

    private JTextField logFileTXT;
    private JList logFileList;
    private JButton addLogFile;

    //桌面Panel
    private JDesktopPane jDesktopPane;
    private JTextField keyWordTXT;
    private JButton filterKeyWord;

    private JTextField outputPathTXT;

    private JButton execButton;
    private JButton loadButton;
    private JButton clearButton;
    private JButton saveButton;
    private JButton stopButton;

    private JButton outputButton;
    private JButton pauseButton;
    private JRadioButton tailModelRadio;
    private JRadioButton viewerModelRadio;
    private JButton cenDieButton;
    private JButton duiDieButton;
    private JButton bingPaiButton;
    private JButton aboutButton;
    private JPanel leftPanel;
    private JSplitPane splitPanel;
    private JLabel bottomInfoLable;
    private JTextArea debugJTestArea;
    private JSplitPane mainJplitPane;
    private JToolBar jToolBar;
    private JSpinner startTime;
    private JSpinner endTime;
    private JPopupMenu jPopupMenu;


    public ViewerWindow() {

    }

    public JFrame getjFrame() {
        return jFrame;
    }

    public void setjFrame(JFrame jFrame) {
        this.jFrame = jFrame;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public void setMainPanel(JPanel mainPanel) {
        this.mainPanel = mainPanel;
    }

    public JPanel getViewInfoPane() {
        return viewInfoPane;
    }

    public void setViewInfoPane(JPanel viewInfoPane) {
        this.viewInfoPane = viewInfoPane;
    }

    public JTextField getDomainTxT() {
        return domainTxT;
    }

    public void setDomainTxT(JTextField domainTxT) {
        this.domainTxT = domainTxT;
    }

    public JTextField getIpTXT() {
        return ipTXT;
    }

    public void setIpTXT(JTextField ipTXT) {
        this.ipTXT = ipTXT;
    }

    public JList getIpList() {
        return ipList;
    }

    public void setIpList(JList ipList) {
        this.ipList = ipList;
    }

    public JButton getAddIpButton() {
        return addIpButton;
    }

    public void setAddIpButton(JButton addIpButton) {
        this.addIpButton = addIpButton;
    }

    public JTextField getLogFileTXT() {
        return logFileTXT;
    }

    public void setLogFileTXT(JTextField logFileTXT) {
        this.logFileTXT = logFileTXT;
    }

    public JList getLogFileList() {
        return logFileList;
    }

    public void setLogFileList(JList logFileList) {
        this.logFileList = logFileList;
    }

    public JButton getAddLogFile() {
        return addLogFile;
    }

    public void setAddLogFile(JButton addLogFile) {
        this.addLogFile = addLogFile;
    }

    public JDesktopPane getjDesktopPane() {
        return jDesktopPane;
    }

    public void setjDesktopPane(JDesktopPane jDesktopPane) {
        this.jDesktopPane = jDesktopPane;
    }

    public JTextField getKeyWordTXT() {
        return keyWordTXT;
    }

    public void setKeyWordTXT(JTextField keyWordTXT) {
        this.keyWordTXT = keyWordTXT;
    }

    public JButton getFilterKeyWord() {
        return filterKeyWord;
    }

    public void setFilterKeyWord(JButton filterKeyWord) {
        this.filterKeyWord = filterKeyWord;
    }

    public JTextField getOutputPathTXT() {
        return outputPathTXT;
    }

    public void setOutputPathTXT(JTextField outputPathTXT) {
        this.outputPathTXT = outputPathTXT;
    }

    public JButton getExecButton() {
        return execButton;
    }

    public void setExecButton(JButton execButton) {
        this.execButton = execButton;
    }

    public JButton getLoadButton() {
        return loadButton;
    }

    public void setLoadButton(JButton loadButton) {
        this.loadButton = loadButton;
    }

    public JButton getClearButton() {
        return clearButton;
    }

    public void setClearButton(JButton clearButton) {
        this.clearButton = clearButton;
    }

    public JButton getSaveButton() {
        return saveButton;
    }

    public void setSaveButton(JButton saveButton) {
        this.saveButton = saveButton;
    }

    public JButton getStopButton() {
        return stopButton;
    }

    public void setStopButton(JButton stopButton) {
        this.stopButton = stopButton;
    }

    public JButton getOutputButton() {
        return outputButton;
    }

    public void setOutputButton(JButton outputButton) {
        this.outputButton = outputButton;
    }

    public JButton getPauseButton() {
        return pauseButton;
    }

    public void setPauseButton(JButton pauseButton) {
        this.pauseButton = pauseButton;
    }

    public JSpinner getStartTime() {
        return startTime;
    }

    public void setStartTime(JSpinner startTime) {
        this.startTime = startTime;
    }

    public JSpinner getEndTime() {
        return endTime;
    }

    public void setEndTime(JSpinner endTime) {
        this.endTime = endTime;
    }

    public JRadioButton getTailModelRadio() {
        return tailModelRadio;
    }

    public void setTailModelRadio(JRadioButton tailModelRadio) {
        this.tailModelRadio = tailModelRadio;
    }

    public JRadioButton getViewerModelRadio() {
        return viewerModelRadio;
    }

    public void setViewerModelRadio(JRadioButton viewerModelRadio) {
        this.viewerModelRadio = viewerModelRadio;
    }

    public JButton getCenDieButton() {
        return cenDieButton;
    }

    public void setCenDieButton(JButton cenDieButton) {
        this.cenDieButton = cenDieButton;
    }

    public JButton getDuiDieButton() {
        return duiDieButton;
    }

    public void setDuiDieButton(JButton duiDieButton) {
        this.duiDieButton = duiDieButton;
    }

    public JButton getBingPaiButton() {
        return bingPaiButton;
    }

    public void setBingPaiButton(JButton bingPaiButton) {
        this.bingPaiButton = bingPaiButton;
    }

    public JButton getAboutButton() {
        return aboutButton;
    }

    public void setAboutButton(JButton aboutButton) {
        this.aboutButton = aboutButton;
    }

    public JPanel getLeftPanel() {
        return leftPanel;
    }

    public void setLeftPanel(JPanel leftPanel) {
        this.leftPanel = leftPanel;
    }

    public JSplitPane getSplitPanel() {
        return splitPanel;
    }

    public void setSplitPanel(JSplitPane splitPanel) {
        this.splitPanel = splitPanel;
    }

    public JLabel getBottomInfoLable() {
        return bottomInfoLable;
    }

    public void setBottomInfoLable(JLabel bottomInfoLable) {
        this.bottomInfoLable = bottomInfoLable;
    }

    public JPopupMenu getjPopupMenu() {
        return jPopupMenu;
    }

    public void setjPopupMenu(JPopupMenu jPopupMenu) {
        this.jPopupMenu = jPopupMenu;
    }

    public void setDebugJTestArea(JTextArea debugJTestArea) {
        this.debugJTestArea = debugJTestArea;
    }

    public JTextArea getDebugJTestArea() {
        return debugJTestArea;
    }

    public JSplitPane getMainJplitPane() {
        return mainJplitPane;
    }

    public void setMainJplitPane(JSplitPane mainJplitPane) {
        this.mainJplitPane = mainJplitPane;
    }

    public JToolBar getjToolBar() {
        return jToolBar;
    }

    public void setjToolBar(JToolBar jToolBar) {
        this.jToolBar = jToolBar;
    }
}
