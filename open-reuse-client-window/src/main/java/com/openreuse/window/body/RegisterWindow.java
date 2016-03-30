package com.openreuse.window.body;

/**
 * Created by Jasmine on 16/3/28.
 */
import com.openresure.client.ClientAgent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterWindow extends JFrame implements ActionListener {
    public static final int WIDTH = 400;
    public static final int HEIGHT = 240;

    JTextField userNameField;
    JPasswordField passwordField;
    JPasswordField confirmPasswordField;
    JFrame jFrame;
    boolean isEqual = true;
    public RegisterWindow(){
        jFrame = new JFrame("Register");
        Container container = jFrame.getContentPane();
        JPanel panel = new JPanel();
        panel.setLayout(null);



        JLabel userNameLabel = new JLabel("User Name");
        userNameLabel.setBounds(20,20,100,20);
        userNameField = new JTextField(15);
        userNameField.setBounds(150,20,200,20);
        panel.add(userNameLabel);
        panel.add(userNameField);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(20,60,100,20);
        passwordField = new JPasswordField(15);
        passwordField.setBounds(150,60,200,20);
        panel.add(passwordLabel);
        panel.add(passwordField);


        JLabel confirmLabel = new JLabel("Confirm Password");
        confirmLabel.setBounds(20,100,140,20);
        confirmPasswordField = new JPasswordField(15);
        confirmPasswordField.setBounds(150,100,200,20);
        panel.add(confirmLabel);
        panel.add(confirmPasswordField);

        JButton registerButton = new JButton("Register");
        registerButton.setBounds(30,160,100,20);
        JButton clearButton = new JButton("Clear");
        clearButton.setBounds(145,160,100,20);
        JButton returnButton = new JButton("Return");
        returnButton.setBounds(260,160,100,20);
        panel.add(registerButton);
        panel.add(clearButton);
        panel.add(returnButton);
        registerButton.addActionListener(this);

        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jFrame.dispose();
                new LoginWindow();
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userNameField.setText("");
                passwordField.setText("");
                confirmPasswordField.setText("");

            }
        });

        container.add(panel);
        int screen_width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int screen_height = Toolkit.getDefaultToolkit().getScreenSize().height;
        jFrame.setLocation((screen_width - jFrame.getWidth()) / 2,
                (screen_height - jFrame.getHeight()) / 2);
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setSize(WIDTH,HEIGHT);
    }

    public void actionPerformed(ActionEvent e) {
        String userName = userNameField.getText();
        char[] password = passwordField.getPassword();
        char[] passwordConfirmed = confirmPasswordField.getPassword();
        if (password.length != passwordConfirmed.length){
            isEqual = false;
        }else{
            for(int i = 0; i<password.length; i++){
                if(password[i] != passwordConfirmed[i]){
                    isEqual = false;
                }
            }
        }

        if(isEqual){
//            this.registerListener(new ValidateRegisterListener());
            boolean success = ClientAgent.registerValidate(userName, new String(password));
            if(success){
                JOptionPane.showMessageDialog(this, "Register Successfully","Register", JOptionPane.OK_OPTION);
                jFrame.dispose();
                new LoginWindow();
            }else{
                JOptionPane.showMessageDialog(this, "Register failure", "Server decline", JOptionPane.OK_OPTION);
                jFrame.dispose();
                new LoginWindow();
            }
        }else{
            JOptionPane.showMessageDialog(this,"Password confirmed error!","Register Again",JOptionPane.OK_OPTION);
        }
        System.out.println("UserName"+userName);
        System.out.println("Password" + new String(password));
        System.out.println("PasswordConfirmed" + new String(passwordConfirmed));


    }


}
