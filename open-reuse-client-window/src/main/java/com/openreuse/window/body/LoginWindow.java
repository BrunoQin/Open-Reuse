package com.openreuse.window.body;

import com.openresure.client.ClientAgent;
import com.openresure.client.listener.MessageListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Jasmine on 16/3/28.
 */

public class LoginWindow extends JFrame implements ActionListener {
    JLabel userNameLabel, passwordLabel, serverLabel;
    JTextField userNameField, serverField;
    JPasswordField passwordField;
    JFrame jFrame;
    JPanel jPanel;
    JButton loginButton, registerButton;
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
        JOptionPane.showMessageDialog(this,"Login Successfully","Login Successfully",JOptionPane.INFORMATION_MESSAGE);
        this.setVisible(false);
        new SendMessageWindow(userNameField.getText(),serverField.getText());
    }

    public void passwordErroDialog(){
        JOptionPane.showMessageDialog(this,"Login Failed","Password Error!",JOptionPane.OK_OPTION);
        userNameField.setText("");
        passwordField.setText("");
        serverField.setText("");
    }

    public void noRegisterDialog(){
        JOptionPane.showMessageDialog(this,"Login Failed","You haven't register yet.",JOptionPane.OK_OPTION);
        this.setVisible(false);
        new RegisterWindow();
    }

    public void actionPerformed(ActionEvent e){
        if(e.getActionCommand().equals("Login")){
//            this.registerListener( new ValidateLoginListener());
            String username = userNameField.getText();
            char[] szPasswd = passwordField.getPassword();
            String serverIP = serverField.getText();
            boolean success = ClientAgent.loginValidate(serverIP, username, new String(szPasswd));
            this.dispose();
            if(success) loginSuccessDialog();
            else passwordErroDialog();
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

