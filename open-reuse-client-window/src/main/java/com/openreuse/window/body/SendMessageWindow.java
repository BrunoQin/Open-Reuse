package com.openreuse.window.body;

/**
 * Created by Jasmine on 16/3/28.
 */

import com.openresure.client.ClientAgent;
import com.openresure.client.service.MessageNotifyService;
import com.openreuse.common.message.MessageType;
import com.openreuse.window.listener.LoginListener;
import com.openreuse.window.listener.LogoutListener;
import com.openreuse.window.listener.PrintToWindowListener;
import com.openreuse.window.listener.RedoLoginListener;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SendMessageWindow{

    public JFrame frame;
    private JList userList;
    private JTextArea textArea;
    public JTextField textField;
    private JTextField txt_hostIp;
    private JTextField txt_name;
    public JButton btn_stop;
    public JButton btn_send;
    private JPanel northPanel;
    private JPanel southPanel;
    private JScrollPane rightScroll;
    private JScrollPane leftScroll;
    private JSplitPane centerSplit;
    public DefaultListModel listModel;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setListModel(String string){
        listModel.addElement(string);
    }

    public String getClient(int index){
        return listModel.getElementAt(index).toString();
    }

    public String getAllOnlineClients()
    {
        String result = "";
        for(int i = 0;i<listModel.getSize();i++){
            result = result+listModel.getElementAt(i).toString()+"\n";
        }
        return  result;
    }

    /**  **/
    private String username = "";

    // 执行发送
//    public void send() {
//        if (!isConnected) {
//            JOptionPane.showMessageDialog(frame, "Connection Failed! Please Redo Login", "Error",
//                    JOptionPane.ERROR_MESSAGE);
//            new LoginWindow();
//        }
//        String message = textField.getText().trim();
//        if (message == null || message.equals("")) {
//            JOptionPane.showMessageDialog(frame, "Message cannot be null！", "Error",
//                    JOptionPane.ERROR_MESSAGE);
//            return;
//        }
//        sendMessage(txt_name.getText() + ": " + message+"\n");
//        textField.setText(null);
//    }


    // 构造方法
    public SendMessageWindow(final String username, String serverAddress) {

        MessageNotifyService.getInstance().registerListener(MessageType.TEXT_MESSAGE,
                new PrintToWindowListener(this));
        MessageNotifyService.getInstance().registerListener(MessageType.REDO_LOGIN_MESSAGE,
                new RedoLoginListener(this));
        MessageNotifyService.getInstance().registerListener(MessageType.LOGIN_MESSAGE,
                new LoginListener(this));
        MessageNotifyService.getInstance().registerListener(MessageType.LOGOUT_MESSAGE,
                new LogoutListener(this));

        this.username = username;

        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setForeground(Color.blue);
        textField = new JTextField();
        txt_hostIp = new JTextField(serverAddress);
        txt_hostIp.setEditable(false);
        txt_name = new JTextField(username);
        txt_name.setEditable(false);

        btn_stop = new JButton("Disconnect");
        btn_send = new JButton("Send");
        listModel = new DefaultListModel();
        userList = new JList(listModel);

        northPanel = new JPanel();
        northPanel.setLayout(new GridLayout(1, 5));

        northPanel.add(new JLabel("Server Address"));
        northPanel.add(txt_hostIp);
        northPanel.add(new JLabel(" User Name"));
        northPanel.add(txt_name);
        northPanel.add(btn_stop);
        northPanel.setBorder(new TitledBorder("Server"));

        rightScroll = new JScrollPane(textArea);
        rightScroll.setBorder(new TitledBorder("Message"));
        leftScroll = new JScrollPane(userList);
        leftScroll.setBorder(new TitledBorder("Online Client"));
        southPanel = new JPanel(new BorderLayout());
        southPanel.add(textField, "Center");
        southPanel.add(btn_send, "East");
        southPanel.setBorder(new TitledBorder("Write Message"));

        centerSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftScroll,
                rightScroll);
        centerSplit.setDividerLocation(150);

        frame = new JFrame(getUsername());

        frame.setLayout(new BorderLayout());
        frame.add(northPanel, "North");
        frame.add(centerSplit, "Center");
        frame.add(southPanel, "South");
        frame.setSize(650, 400);
        int screen_width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int screen_height = Toolkit.getDefaultToolkit().getScreenSize().height;
        frame.setLocation((screen_width - frame.getWidth()) / 2+50,
                (screen_height - frame.getHeight()) / 2);
        frame.setVisible(true);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // 写消息的文本框中按回车键时事件
        textField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
               SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//Set Format
                String message = username+"  "+df.format(new Date())+"\n"+textField.getText()+"\n";
                ClientAgent.sendTextMessage(username, message);
                textField.setText("");
            }
        });

        // 单击发送按钮时事件
        btn_send.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//Set Format
                String message = username+"  "+df.format(new Date())+"\n"+textField.getText()+"\n";
                ClientAgent.sendTextMessage(username, message);
                textField.setText("");
            }
        });

        // 单击断开按钮时事件
        btn_stop.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Disconnect successfully!");
                new LoginWindow();
                frame.dispose();
                ClientAgent.logout(username);
            }
        });

    }


    /**
     * 发送消息
     *
     * @param message
     */
    public void sendMessage(String message) {
        textArea.append(message);
    }

    public static void main(String args[]){
        new SendMessageWindow("jinmin", "127.0.0.1");
    }

    public void somebodyLogin(String username){
        listModel.addElement(username);
    }

    public void somebodyLogout(String username){
        listModel.removeElement(username);
    }

}