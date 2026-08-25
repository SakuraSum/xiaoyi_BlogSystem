package UI;
// 关闭后结束进程

/*
在DatabaseConnection类里定义一个与登录相关的方法，然后在登录类里调用该方法
获取两个文本框的文字
检验是否为空（两者都不能为空），只要有一个为空，弹出“账号和密码都不能为空！”
如果都不为空，则先在db_blog数据库中的tb_user数据表中的user_number字段查找是否有对应账号，没有对应账号的话，弹出“账号不存在！”
如果有对应账号，则根据db_blog数据库中的tb_user数据表中的user_number字段找到对应user_password字段比较是否密码相同，弹出“密码错误”
如果以上情况不存在，销毁当前登陆窗口，则进入博客系统主页面
* */
import  DatabaseConnection.DatabaseConnection;
import TOOL.SetBackground;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginJFrame {
    // 窗口
    static JFrame jf;

    // 菜单栏
    static JMenuBar menuBar;

    //输入文本框
    static JTextField accountTextField;// 账号文本框
    static JTextField passwordTextField;// 密码文本框

    // 构造方法
    public LoginJFrame(){
        // 初始化窗口
        initJFrame();

        // 初始化背景图片
        initBackground();

        // 初始化菜单栏
        initMenuBar();

        // 初始化文本输入框和标签
        initTextFieldsAndLabels();

        // 初始化按钮
        initButton();

        // 设置窗口可见
        jf.setVisible(true);
    }

    // 初始化窗口
    public static void initJFrame(){
        // 创建窗口
        jf = new JFrame();
        jf.setTitle("登陆界面");
        jf.setSize(1000,800);
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jf.setLayout(null); // 设置布局为null
    }

    // 初始化菜单栏
    public static void initMenuBar() {
        // 创建菜单栏
        menuBar = new JMenuBar();

        // 创建“注册”菜单项
        JMenuItem registerMenuItem = new JMenuItem("注册");
        registerMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 打开注册页面
                new RegisterFrame();

                // 关闭当前窗口
                jf.dispose();
            }
        });

        // 将“注册”菜单项添加到菜单栏
        menuBar.add(registerMenuItem);

        // 将菜单栏设置为窗口的菜单栏
        jf.setJMenuBar(menuBar);
    }

    // 初始化文本输入框和标签
    public static void initTextFieldsAndLabels(){
        // 创建账号标签
        JLabel accountLabel = new JLabel("账号");
        accountLabel.setBounds(400, 300, 200, 30);

        // 创建账号文本输入框
        accountTextField = new JTextField();
        accountTextField.setBounds(400, 330, 200, 30);

        // 创建密码标签
        JLabel passwordLabel = new JLabel("密码");
        passwordLabel.setBounds(400, 380, 200, 30);

        // 创建密码文本输入框
        passwordTextField = new JTextField();
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
        JButton confirmButton = new JButton("确认登录");
        confirmButton.setBounds(400, 500, 200, 30);

        // 添加按钮点击事件处理器
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userNumber = accountTextField.getText();
                String password = passwordTextField.getText();
                if (userNumber.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "账号和密码都不能为空！");
                } else {
                    boolean success = DatabaseConnection.loginUser(userNumber, password);
                    if (success) {

                        // 销毁当前窗口
                        jf.dispose();
                        // 进入博客系统主页面
                        new SystemMainJFrame();
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