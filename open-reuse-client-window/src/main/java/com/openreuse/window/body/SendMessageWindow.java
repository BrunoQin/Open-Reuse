package com.openreuse.window.body;

/**
 * Created by Jasmine on 16/3/28.
 */

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class SendMessageWindow{

    private JFrame frame;
    private JList userList;
    private JTextArea textArea;
    private JTextField textField;
    private JTextField txt_hostIp;
    private JTextField txt_name;
    private JButton btn_start;
    private JButton btn_stop;
    private JButton btn_send;
    private JPanel northPanel;
    private JPanel southPanel;
    private JScrollPane rightScroll;
    private JScrollPane leftScroll;
    private JSplitPane centerSplit;
    private DefaultListModel listModel;
    private boolean isConnected = true;

    private UserLoginListener userLoginListener;


    // 执行发送
    public void send() {
        if (!isConnected) {
            JOptionPane.showMessageDialog(frame, "Connection Failed! Please Redo Login", "Error",
                    JOptionPane.ERROR_MESSAGE);
            new LoginWindow();
        }
        String message = textField.getText().trim();
        if (message == null || message.equals("")) {
            JOptionPane.showMessageDialog(frame, "Message cannot be null！", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        sendMessage(txt_name.getText() + ": " + message+"\n");
        textField.setText(null);
    }

    // 构造方法
    public SendMessageWindow(String username, String serverAddress) {
        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setForeground(Color.blue);
        textField = new JTextField();
        txt_hostIp = new JTextField(serverAddress);
        txt_hostIp.setEditable(false);
        txt_name = new JTextField(username);
        txt_name.setEditable(false);
        btn_start = new JButton("Connect");
        btn_stop = new JButton("Disconnect");
        btn_send = new JButton("Send");
        listModel = new DefaultListModel();
        userList = new JList(listModel);

        northPanel = new JPanel();
        northPanel.setLayout(new GridLayout(1, 6));

        northPanel.add(new JLabel("Server Address"));
        northPanel.add(txt_hostIp);
        northPanel.add(new JLabel(" User Name"));
        northPanel.add(txt_name);
        northPanel.add(btn_start);
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

        frame = new JFrame("Client");

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

        // 写消息的文本框中按回车键时事件
        textField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                send();
            }
        });

        // 单击发送按钮时事件
        btn_send.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                send();
            }
        });

        // 单击连接按钮时事件
        btn_start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new LoginWindow();
                frame.dispose();
            }
        });

        // 单击断开按钮时事件
        btn_stop.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Disconnect successfully!");
                new LoginWindow();
                frame.dispose();
            }
        });

        // 关闭窗口时事件
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                if (isConnected) {
                    closeConnection();// 关闭连接
                }
                System.exit(0);// 退出程序
            }
        });
    }

    public interface UserLoginListener{
        String updateUserList();
    }

    public void registerListener(UserLoginListener userLoginListener){
        this.userLoginListener = userLoginListener;
    }



    /**
     * 发送消息
     *
     * @param message
     */
    public void sendMessage(String message) {
        textArea.append(message);

    }

    public boolean closeConnection() {
        return true;
    }

    public static void main(String args[]){

    }

}