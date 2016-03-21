package com.openreuse.window.body;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by kimmin on 3/21/16.
 */

public class LoginWindow extends JPanel {

    private static final long serialVersionUID=1L;

    static final int WIDTH=400;
    static final int HEIGHT=200;

    JFrame loginframe;

    public void add(Component c, GridBagConstraints constraints, int x, int y, int w, int h)
    {
        constraints.gridx=x;
        constraints.gridy=y;
        constraints.gridwidth=w;
        constraints.gridheight=h;
        add(c,constraints);
    }
    public LoginWindow()
    {
        loginframe=new JFrame("欢迎使用");
        loginframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GridBagLayout lay=new GridBagLayout();
        setLayout(lay);
        loginframe.add(this,BorderLayout.WEST);
        loginframe.setSize(WIDTH,HEIGHT);
        Toolkit kit=Toolkit.getDefaultToolkit();
        Dimension screenSize=kit.getScreenSize();
        int width=screenSize.width;
        int height=screenSize.height;
        int x=(width-WIDTH)/2;
        int y=(height-HEIGHT)/2;
        loginframe.setLocation(x,y);
        JButton ok=new JButton("登陆");
        JButton cancel=new JButton("放弃");
        JLabel title=new JLabel("欢迎使用");
        JLabel name=new JLabel("用户名");
        JLabel password=new JLabel("密码");
        final JTextField nameinput =new JTextField(8);
        final JTextField passwordinput =new JPasswordField(8);
        GridBagConstraints constraints=new GridBagConstraints();
        constraints.fill=GridBagConstraints.NONE;
        constraints.anchor=GridBagConstraints.EAST;
        constraints.weightx=3;
        constraints.weighty=4;
        add(title,constraints,0,0,2,1);
        add(name,constraints,0,1,1,1);
        add(password,constraints,0,2,1,1);
        add(nameinput,constraints,2,1,1,1);
        add(passwordinput,constraints,2,2,1,1);
        add(ok,constraints,0,3,1,1);
        add(cancel,constraints,2,3,1,1);
        loginframe.setResizable(false);
        loginframe.setVisible(true);

        ok.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent Event)
            {
                String nametext=nameinput.getText();
                String passwordtext=passwordinput.getText();
                String str=new String(passwordtext);
                boolean x=(nametext.equals("admin"));
                boolean y=(str.equals("123456"));
                boolean z=(x&&y);
                if(z)
                {
                    loginframe.dispose();
                }
                else
                {
                    nameinput.setText("");
                    passwordinput.setText("");
                }
            }
        });
        cancel.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent Event)
            {
                nameinput.setText("");
                passwordinput.setText("");
            }

        });

    }


 /*class windowsclose extends JPanel
 {
  JFrame jf;
  windowsclose()
  {
   jf=new JFrame("确认");
   jf.setVisible(true);
   jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   jf.setContentPane(this);
   JLabel l1=new JLabel("确认放弃么");
   JLabel l2=new JLabel();
   JButton b1=new JButton("是");
   JButton b2=new JButton("否");
   GridBagConstraints constraints1=new GridBagConstraints();
   constraints1.fill=GridBagConstraints.NONE;
   constraints1.anchor=GridBagConstraints.EAST;
   constraints1.weightx=3;
   constraints1.weighty=3;
   GridBagLayout layout=new GridBagLayout();
   setLayout(layout);
   add(l1,constraints1,1,0,1,1);
   add(b1,constraints1,0,1,1,1);
   add(b2,constraints1,2,1,1,1);
   add(l2,constraints1,1,1,1,1);
   jf.pack();
   b1.addActionListener(new ActionListener()
   {
    public void actionPerformed(ActionEvent Event)
    {
     jf.dispose();
    }
   });
   b1.addActionListener(new ActionListener()
   {
    public void actionPerformed(ActionEvent Event)
    {
     jf.dispose();
    }
   });
  }
  public void add(Component c,GridBagConstraints constraints,int x,int y,int w,int h)
  {
   constraints.gridx=x;
   constraints.gridy=y;
   constraints.gridwidth=w;
   constraints.gridheight=h;
   add(c,constraints);
  }
 }
 */


}
