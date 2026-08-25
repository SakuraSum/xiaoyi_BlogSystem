package UI;

import DatabaseConnection.DatabaseConnection;
import TOOL.BackToMainMenuItem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class UserJFrame {
    // 窗口
    static JFrame jf;

    // 区域块
    static JPanel jpTop;// 顶部 - 大标题
    static JPanel jpLeft;// 左侧 - 按钮：个人信息/个人账户
    static JPanel jpCenter;// 中心 - 展示个人信息/个人账户

    // 菜单栏
    static JMenuBar menuBar;

    // 构造方法
    public UserJFrame(){
        // 初始化窗口
        initJFrame();

        // 获取当前登录用户的信息
        String currentUserNumber = DatabaseConnection.loggedUserNumber;
        System.out.println("当前登录用户的账号: " + currentUserNumber);

        // 初始化区域块
        initJPanel();

        // 在中心区域展示用户信息
        JLabel userInfoLabel = new JLabel("当前用户账号: " + currentUserNumber, JLabel.CENTER);
        userInfoLabel.setFont(new Font("宋体", Font.BOLD, 36));  // 设置字体大小和样式
        jpTop.add(userInfoLabel);

        // 初始化菜单栏
        initMenuBar();

        // 初始化按钮(左区块)
        initJButton();

        // 设置窗口可见
        jf.setVisible(true);
    }

    // 初始化窗口
    public static void initJFrame(){
        // 创建窗口
        jf = new JFrame();
        jf.setTitle("用户个人中心");
        jf.setSize(1000,800);
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
        JButton manageUserButton = new JButton("用户账号管理");
        JButton userInfoButton = new JButton("用户个人信息");
        JButton userBlogsButton = new JButton("用户所有博客");

        // 按钮监听
        /**
         * //在DatabaseConnection中，创建一个方法
 *         //方法名为getUserJFrame
 *         // 通过当前的账号获取密码、绑定手机号和账号状态
 *         //db_blog数据库
 *         //tb_user数据表
 *         //需要获取的字段：user_password user_phoneNumber user_static
 *         //用List集合获取以上字段
 *         //随后在点击UserJFrame内的“用户账号管理”按钮后
 *         //就利用监听获取DatabaseConnection中getUserJFrame方法返回的字段
 *         //然后用表格形式将获取的到的字段显现在中心区块上
 *         //表格第一列为密码、绑定手机号和账号状态
 *         //表格第二列为第一列对应信息
         */
        manageUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 获取当前登录用户的信息
                String nowUserNumber = DatabaseConnection.loggedUserNumber;
                List<String[]> userInfo = DatabaseConnection.getUserJFrame(nowUserNumber);

                // 清空中心区域
                jpCenter.removeAll();

                // 创建表格数据
                String[] columnNames = {"账号："+nowUserNumber, "内容"};
                String[][] data = {
                        {"密码", userInfo.get(0)[0]},
                        {"绑定手机号", userInfo.get(0)[1]},
                        {"账号状态", userInfo.get(0)[2]}
                };

                // 创建表格
                JTable table = new JTable(data, columnNames);
                JScrollPane scrollPane = new JScrollPane(table);
                jpCenter.add(scrollPane);

                // 重新绘制中心区域
                jpCenter.revalidate();
                jpCenter.repaint();
            }
        });

        /**
         * //在DatabaseConnection中，创建一个方法
 *         //方法名为 getUserInfo
 *         // 通过当前的账号获取 昵称 性别 个人简介
 *         //db_blog数据库
 *         //tb_userInfo数据表
 *         //需要获取的字段：userInfo_name userInfo_gander userInfo_profile
 *         //用List集合获取以上字段
 *         //随后在点击UserJFrame内的“用户个人信息”按钮后
 *         //就利用监听获取DatabaseConnection中getUserInfo方法返回的字段
 *         //然后用表格形式将获取的到的字段显现在中心区块上
 *         //表格第一列为昵称 性别 个人简介
 *         //表格第二列为第一列对应信息
         */
        userInfoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 获取当前登录用户的信息
                String nowUserNumber = DatabaseConnection.loggedUserNumber;
                List<String[]> userInfo = DatabaseConnection.getUserInfo(nowUserNumber);

                // 清空中心区域
                jpCenter.removeAll();

                // 创建表格数据
                String[] columnNames = {"信息类型", "信息内容"};
                String[][] data = {
                        {"昵称", userInfo.get(0)[0]},
                        {"性别", userInfo.get(0)[1]},
                        {"个人简介", userInfo.get(0)[2]}
                };

                // 创建表格
                JTable table = new JTable(data, columnNames);
                JScrollPane scrollPane = new JScrollPane(table);
                jpCenter.add(scrollPane);

                // 重新绘制中心区域
                jpCenter.revalidate();
                jpCenter.repaint();
            }
        });

        /**
         * //在DatabaseConnection中，创建一个方法
 *         //方法名为 getUserBlog
 *         // 通过当前的账号获取 博客编号 博客标题 博客正文 博客发布日期 博客标签
 *         //db_blog数据库
 *         //tb_blog 数据表
 *         //需要获取的字段：blog_number blog_title blog_text blog_insertTime
         //用List集合获取以上字段
         注：接下来用blog_number找到标签
         首先，用 blog_number在 tb_relation_blog_label 中 找到对应的 label_number
         再用label_number 在 tb_label 中获取到 label_name
         最后再把这个 label_name 放入到 集合末尾
 *         //随后在点击UserJFrame内的“用户所有博客”按钮后
 *         //就利用监听获取DatabaseConnection中 getUserBlog 方法返回的字段
 *         //然后用表格形式将获取的到的字段显现在中心区块上
 *         //表格第一列为博客编号 博客标题 博客正文 博客发布日期 博客标签
 *         //表格第二列为第一列对应信息
         */
        userBlogsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 获取当前登录用户的信息
                String nowUserNumber = DatabaseConnection.loggedUserNumber;
                List<String[]> userBlogs = DatabaseConnection.getUserBlog(nowUserNumber);

                // 清空中心区域
                jpCenter.removeAll();

                // 创建表格数据
                String[] columnNames = {"博客编号", "博客标题", "博客正文", "博客发布日期", "博客标签"};
                String[][] data = new String[userBlogs.size()][5];
                for (int i = 0; i < userBlogs.size(); i++) {
                    data[i][0] = userBlogs.get(i)[0];
                    data[i][1] = userBlogs.get(i)[1];
                    data[i][2] = userBlogs.get(i)[2];
                    data[i][3] = userBlogs.get(i)[3];
                    data[i][4] = userBlogs.get(i)[4];
                }

                // 创建表格
                JTable table = new JTable(data, columnNames);
                JScrollPane scrollPane = new JScrollPane(table);
                jpCenter.add(scrollPane);

                // 重新绘制中心区域
                jpCenter.revalidate();
                jpCenter.repaint();
            }
        });

        // 添加按钮和间隙
        jpLeft.add(manageUserButton);
        jpLeft.add(Box.createVerticalStrut(10));  // 创建10像素的间隙
        jpLeft.add(userInfoButton);
        jpLeft.add(Box.createVerticalStrut(10));  // 创建10像素的间隙
        jpLeft.add(userBlogsButton);
    }

}
