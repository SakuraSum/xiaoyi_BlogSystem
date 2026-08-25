package UI;

import TOOL.BackToMainMenuItem;
import DatabaseConnection.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class DeveloperJFrame {
    // 窗口
    static JFrame jf;

    // 菜单栏
    static JMenuBar menuBar;

    // 构造方法
    public DeveloperJFrame(){
        // 初始化窗口
        initJFrame();

        // 初始化菜单栏
        initMenuBar();

        // 在开发者介绍界面中心放置开发者的信息
        developerInfo();

        // 设置窗口可见
        jf.setVisible(true);
    }

    // 初始化窗口
    public static void initJFrame(){
        // 创建窗口
        jf = new JFrame();
        jf.setTitle("开发者介绍");
        jf.setSize(800,600);
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    // 初始化菜单栏
    public static void initMenuBar() {
        // 创建菜单栏
        menuBar = new JMenuBar();

        // 创建菜单项
        BackToMainMenuItem backToMainMenuItem = new BackToMainMenuItem(jf);

        // 添加菜单项到菜单栏
        menuBar.add(backToMainMenuItem);

        // 设置窗口的菜单栏
        jf.setJMenuBar(menuBar);
    }

    // 在开发者介绍界面中心放置开发者的信息
    // 从DatabaseConnection的developerJSearch方法获得开发者的信息
    public static void developerInfo(){
        List<String[]> developers = DatabaseConnection.developerJSearch();
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        for (String[] developer : developers) {
            JLabel nameLabel = new JLabel("开发者: " + developer[0]);

            // 创建一个 ImageIcon 对象，使用图片的本地路径
            ImageIcon imageIcon = new ImageIcon(developer[1]);
            // 创建一个 JLabel 对象，并将 ImageIcon 对象设置为其图标
            JLabel photoLabel = new JLabel(imageIcon);

            JLabel profileLabel = new JLabel("简介: " + developer[2]);
            JLabel phoneNumberLabel = new JLabel("联系电话: " + developer[3]);
            centerPanel.add(nameLabel);
            centerPanel.add(photoLabel);
            centerPanel.add(profileLabel);
            centerPanel.add(phoneNumberLabel);
        }
        jf.add(centerPanel, BorderLayout.CENTER);
    }
}