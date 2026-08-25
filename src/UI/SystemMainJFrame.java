package UI;
// 关闭后结束进程
import DatabaseConnection.DatabaseConnection;
import TOOL.BackToLoginMenuItem;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SystemMainJFrame {
    // 窗口
    static JFrame jf;

    // 菜单栏
    static JMenuBar menuBar;

    // 区域块
    static JPanel jpTop;// 顶部 - 大标题
    static JPanel jpLeft;// 左侧 - 按钮
    static JPanel jpCenter;// 中心 - 放一张图(为了好看)

    // 构造方法
    public SystemMainJFrame(){
        // 初始化窗口
        initJFrame();

        // 初始化菜单栏
        initMenuBar();

        // 初始化区域块
        initJPanel();

        // 初始化按钮(左区块)
        initJButton();

        // 设置窗口可见
        jf.setVisible(true);

        // 获取当前登录用户的信息
        String nowUserNumber = DatabaseConnection.loggedUserNumber;
        System.out.println("当前登录用户的账号: " + nowUserNumber);
    }

    // 初始化窗口
    public static void initJFrame(){
        // 创建窗口
        jf = new JFrame();
        jf.setTitle("博客系统主页面");
        jf.setSize(1000,800);
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
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

    // 初始化区域块
    public static void initJPanel(){
        // 创建3个区域块
        jpTop = new JPanel();
        jpLeft = new JPanel();
        jpCenter = new JPanel();

        jpTop.setPreferredSize(new Dimension(1000,100));
        jpLeft.setPreferredSize(new Dimension(200,800));

        // 控制左区块组件从上往下排序
        jpLeft.setLayout(new BoxLayout(jpLeft, BoxLayout.Y_AXIS));

        // 在顶部区域块中添加"博客系统主页"标签
        JLabel titleLabel = new JLabel("博客系统主页", JLabel.CENTER);
        titleLabel.setFont(new Font("宋体", Font.BOLD, 36));  // 设置字体大小和样式
        jpTop.add(titleLabel);

        // 在中心区域块中添加图片
        ImageIcon imageIcon = new ImageIcon("background.jpg");
        JLabel imageLabel = new JLabel(imageIcon);
        jpCenter.add(imageLabel);

        // 给区域块添加边框
        jpTop.setBorder(new javax.swing.border.LineBorder(Color.gray, 1));
        jpLeft.setBorder(new javax.swing.border.LineBorder(Color.gray, 1));
        jpCenter.setBorder(new javax.swing.border.LineBorder(Color.gray, 1));

        // 把三个区域块添加到窗口
        jf.add(jpTop, BorderLayout.NORTH);
        jf.add(jpLeft, BorderLayout.WEST);
        jf.add(jpCenter, BorderLayout.CENTER);
    }

    // 初始化按钮(左区块)
    public static void initJButton(){
        JButton userButton = new JButton("个人中心");
        JButton announcementButton = new JButton("系统公告");
        JButton developerButton = new JButton("开发者介绍");

        userButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new UserJFrame();
                jf.dispose();
            }
        });

        announcementButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AnnouncementFrame();
                jf.dispose();
            }
        });

        developerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DeveloperJFrame();
                jf.dispose();
            }
        });

        // 添加按钮和间隙
        jpLeft.add(userButton);
        jpLeft.add(Box.createVerticalStrut(10));  // 创建10像素的间隙
        jpLeft.add(announcementButton);
        jpLeft.add(Box.createVerticalStrut(10));  // 创建10像素的间隙
        jpLeft.add(developerButton);
    }


}
