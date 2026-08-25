package UI;
//jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);// 结束进程
//jf.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); // 销毁当前窗口
import TOOL.BackToMainMenuItem;
import  DatabaseConnection.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AnnouncementFrame extends JFrame {
    // 窗口
    static JFrame jf;

    // 区域块
    static JPanel jpTop;// 顶部 - 按钮
    static JPanel jpCenter;// 中心 - 展示公告

    // 菜单栏
    static JMenuBar menuBar;

    // 构造方法
    public AnnouncementFrame(){
        // 初始化窗口
        initJFrame();

        // 初始化区域块
        initJPanel();

        // 初始化菜单栏
        initMenuBar();

        // 设置窗口可见
        jf.setVisible(true);
    }

    // 初始化窗口
    public static void initJFrame(){
        // 创建窗口
        jf = new JFrame();
        jf.setTitle("系统公告");
        jf.setSize(1000,800);
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        // 设置内容面板透明
        jf.setContentPane(new JPanel() {
            @Override
            public boolean isOptimizedDrawingEnabled() {
                return false;
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                setOpaque(false);
            }
        });
        jf.getContentPane().setLayout(null);
    }

    // 初始化区域块
    public static void initJPanel(){
        // 创建区域块
        jpTop = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                setOpaque(false);
            }
        };
        jpCenter = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                setOpaque(false);
            }
        };

        // 设置布局管理器
        // 所有的组件都将垂直排列
        jpCenter.setLayout(new BoxLayout(jpCenter, BoxLayout.Y_AXIS));

        // 在顶部区域块中添加"系统公告"标签
        JLabel titleLabel = new JLabel("系统公告", JLabel.CENTER);
        titleLabel.setFont(new Font("宋体", Font.BOLD, 36));  // 设置字体大小和样式
        jpTop.add(titleLabel);

        // 在中心区块中展示公告
        List<String[]> announcements = DatabaseConnection.announcementSearch();
        for (String[] announcement : announcements) {
            JLabel announcementLabel = new JLabel
                    ("<html><b>" + announcement[0] +
                            "</b><br>" + announcement[1] +
                            "<br><i>" + announcement[2] +
                            "</i></html>");
            jpCenter.add(announcementLabel);
        }

        // 把区域块添加到窗口
        jpTop.setBounds(0, 0, 1000, 100);
        jpCenter.setBounds(0, 100, 1000, 700);
        jf.getContentPane().add(jpTop);
        jf.getContentPane().add(jpCenter);
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
}