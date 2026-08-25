package UI;
/*
在DatabaseConnection类里定义一个与注册相关的方法，然后在注册类里调用该方法
获取2个文本框的文字
检验是否为空（2者都不能为空），只要有一个为空，弹出“账号和密码都不能为空！”
如果都不为空，则先在db_blog数据库中的tb_user数据表中的user_number字段查找是否存在相同账号，若存在，弹出“该账号已经存在，请写一个新的账号。”
如果没有对应账号，则把账号和密码插入到db_blog数据库中的tb_user数据表中的user_number字段和user_password字段
* */
import TOOL.BackToLoginMenuItem;
import  DatabaseConnection.DatabaseConnection;
import TOOL.SetBackground;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterFrame {
    // 窗口
    static JFrame jf;

    // 菜单栏
    static JMenuBar menuBar;

    //输入文本框
    static JTextField accountTextField;// 账号文本框
    static JTextField passwordTextField;// 密码文本框

    // 构造方法
    public RegisterFrame(){
        // 初始化窗口
        initJFrame();

        // 初始化背景图片
        initBackground();

        // 初始化文本输入框和标签
        initTextFieldsAndLabels();

        // 初始化按钮
        initButton();

        // 初始化菜单栏
        initMenuBar();

        // 设置窗口可见
        jf.setVisible(true);
    }

    // 初始化窗口
    public static void initJFrame(){
        // 创建窗口
        jf = new JFrame();
        jf.setTitle("注册界面");
        jf.setSize(1000,800);
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        jf.setLayout(null); // 设置布局为null
    }

    // 初始化菜单栏
    public static void initMenuBar() {
        // 创建菜单栏
        menuBar = new JMenuBar();

        // 创建“返回登录界面”菜单项
        BackToLoginMenuItem backToLoginMenuItem = new BackToLoginMenuItem(jf);

        // 将“返回登录界面”菜单项添加到菜单栏
        menuBar.add(backToLoginMenuItem);

        // 将菜单栏设置为窗口的菜单栏
        jf.setJMenuBar(menuBar);
    }

    // 初始化文本输入框和标签
    public static void initTextFieldsAndLabels(){
        // 创建账号标签
        JLabel accountLabel = new JLabel("账号");
        accountLabel.setBounds(400, 300, 200, 30);

        // 创建账号文本输入框
        accountTextField = new JTextField();// 账号文本框
        accountTextField.setBounds(400, 330, 200, 30);

        // 创建密码标签
        JLabel passwordLabel = new JLabel("密码");
        passwordLabel.setBounds(400, 380, 200, 30);

        // 创建密码文本输入框
        passwordTextField = new JTextField();// 密码文本框
        passwordTextField.setBounds(400, 410, 200, 30);

        // 将标签和文本输入框添加到窗口
        jf.add(accountLabel);
        jf.add(accountTextField);
        jf.add(passwordLabel);
        jf.add(passwordTextField);
    }

    // 初始化按钮
    public static void initButton(){
        // 创建确认按钮
        JButton confirmButton = new JButton("确认注册");
        confirmButton.setBounds(400, 500, 200, 30);

        // 添加按钮点击事件处理器
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("注册按钮被点击");
                String username = accountTextField.getText();
                String password = passwordTextField.getText();
                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "账号和密码都不能为空！");
                } else {
                    boolean success = DatabaseConnection.registerUser(username, password);
                    if (success) {
                        JOptionPane.showMessageDialog(null, "注册成功！");
                        System.out.println("注册成功");
                    }
                }
            }
        });

        // 将按钮添加到窗口
        jf.add(confirmButton);
    }

    // 初始化背景图片 initBackground
    public static void initBackground() {
        SetBackground background = new SetBackground("background.jpg");
        background.setBounds(0, 0, 1000, 800);
        jf.setContentPane(background);
        jf.getContentPane().setLayout(null); // 保持布局为null
    }
}