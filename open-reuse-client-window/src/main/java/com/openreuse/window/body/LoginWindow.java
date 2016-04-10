package com.openreuse.window.body;

import com.openresure.client.ClientAgent;
import com.openresure.client.config.ConfigManager;
import com.openresure.client.listener.MessageListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Jasmine on 16/3/28.
 */

public class LoginWindow extends JFrame implements ActionListener {
    public JLabel userNameLabel, passwordLabel, serverLabel;
    public JTextField userNameField, serverField;
    public JPasswordField passwordField;
    public JFrame jFrame;
    public JPanel jPanel;
    public JButton loginButton, registerButton;
    public SendMessageWindow sendMessageWindow;
    MessageListener messageListener;

    public LoginWindow(){
        addComponents();
        loginButton.addActionListener(this);
        registerButton.addActionListener(this);
    }
    public void addComponents(){
        jFrame = new JFrame("Login");

        jPanel = new JPanel();
        jPanel.setLayout(null);

        userNameLabel = new JLabel("Username");
        passwordLabel = new JLabel("Password");
        serverLabel = new JLabel("Server Address");
        userNameField = new JTextField();
        passwordField = new JPasswordField();
        serverField = new JTextField();

        loginButton = new JButton("Login");
        registerButton = new JButton("Register");

        userNameLabel.setBounds(20,20,110,20);
        userNameField.setBounds(130,20,200,20);

        passwordField.setBounds(130,60,200,20);
        passwordLabel.setBounds(20,60,110,20);

        serverLabel.setBounds(20,100,110,20);
        serverField.setBounds(130,100,200,20);

        loginButton.setBounds(70,140,100,20);
        registerButton.setBounds(190,140,100,20);

        jPanel.add(userNameLabel);
        jPanel.add(userNameField);

        jPanel.add(passwordLabel);
        jPanel.add(passwordField);

        jPanel.add(serverField);
        jPanel.add(serverLabel);

        jPanel.add(loginButton);
        jPanel.add(registerButton);

        Container container = jFrame.getContentPane();
        container.add(jPanel);

        int screen_width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int screen_height = Toolkit.getDefaultToolkit().getScreenSize().height;
        jFrame.setLocation((screen_width - jFrame.getWidth()) / 2,
                (screen_height - jFrame.getHeight()) / 2);
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setSize(360,210);
    }


    public void loginSuccessDialog(){
        JOptionPane.showMessageDialog(this,"Login Successfully");
        this.setVisible(false);
        sendMessageWindow = new SendMessageWindow(userNameField.getText(),serverField.getText());
    }

    public void loginFailedDialog(){
        JOptionPane.showMessageDialog(this,"Login Failed!","Login Failed",JOptionPane.OK_OPTION);
        userNameField.setText("");
        passwordField.setText("");
        serverField.setText("");
    }

    public void noRegisterDialog(){
        JOptionPane.showMessageDialog(this,"You haven't register yet.","Login Failed",JOptionPane.OK_OPTION);
        this.setVisible(false);
        new RegisterWindow();
    }

    public void actionPerformed(ActionEvent e){
        String username = userNameField.getText();
        char[] szPasswd = passwordField.getPassword();
        String serverIP = serverField.getText();
        Pattern pattern = Pattern.compile("[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}");
        Matcher matcher = pattern.matcher(serverIP);
        boolean matched = matcher.matches();
        if(!matched){
            JOptionPane.showMessageDialog(this, "Invalid Server IP", "Invalid Server IP", JOptionPane.OK_OPTION);
            return;
        }
        ConfigManager.getInstance().setCurrentServerAddr(serverIP);
        if(e.getActionCommand().equals("Login")){
//            this.registerListener( new ValidateLoginListener());
            boolean success = ClientAgent.loginValidate(serverIP, username, new String(szPasswd));
            this.dispose();
            if(success) loginSuccessDialog();
            else loginFailedDialog();
        }else if(e.getActionCommand().equals("Register")){
            System.out.print("Register");
            this.dispose();
            new RegisterWindow();
        }else {

        }
    }


    public void registerListener( MessageListener messageListener){
        this.messageListener = messageListener;
    }

    public static void main(String[] args) {
        new LoginWindow();
    }

}

