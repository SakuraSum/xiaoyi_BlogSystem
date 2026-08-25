package DatabaseConnection;
/**
 * 预编译
 * 预编译是数据库执行 SQL 语句前的一种处理方式。
 * 在预编译阶段，SQL 语句的结构会被分析并优化，生成一个执行计划。
 * 这个执行计划可以被多次重用，避免了每次执行时都重新解析和优化 SQL 语句，从而提高了执行效率。
 *
 * 同时，预编译的 SQL 语句通常使用参数代替直接的值，
 * 这样在执行时只需要传入具体的参数值，而不需要修改 SQL 语句本身。
 * 这种方式可以防止 SQL 注入攻击，因为参数值在传入时会被适当地处理，防止了恶意代码的执行。
 *
 * 总的来说，预编译可以提高 SQL 语句的执行效率，增强代码的可读性，以及提高应用的安全性。
 */

/**
 * prepareStatement
 * `prepareStatement` 是 Java 中用于执行 SQL 语句的一种方法，它属于 `java.sql.Connection` 接口。
 * 这个方法接收一个 SQL 语句作为输入，然后返回一个 `java.sql.PreparedStatement` 对象。
 *
 * 使用 `prepareStatement` 方法的主要优点是可以创建预编译的 SQL 语句，
 * 这意味着 SQL 语句在执行前已经在数据库中被编译并优化过，这可以提高执行效率。
 *
 * 此外，`prepareStatement` 还支持参数化的 SQL 语句，
 * 也就是说，你可以在 SQL 语句中使用问号（?）作为占位符，
 * 然后在执行时通过 `PreparedStatement` 对象的 `set` 方法来设置具体的参数值。
 * 这种方式可以提高代码的可读性，并且可以防止 SQL 注入攻击，因为参数值在设置时会被适当地处理。
 */

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseConnection {
    private static String url="jdbc:mysql://localhost:3306/db_blog?useSSL=true&serverTimezone=UTC";
    private static String user="root";
    private static String password="123456";

    // 添加静态变量存储当前登录用户的账号
    public static String loggedUserNumber = "123";

    // 数据库连接
    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection( url,user,password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    // 登录查询语句
    public static boolean loginUser(String username, String password) {
        String sql = "SELECT * FROM tb_user WHERE user_number = ?";
        // 创建一个 PreparedStatement 对象来发送参数化的 SQL 语句到数据库
        try (Connection conn = getConnection()) {
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

                // 通过 PreparedStatement 对象的 setString 方法设置 SQL 语句中的参数，
                // 第一个参数是参数的索引（从1开始），第二个参数是参数的值
                pstmt.setString(1, username);

                // 执行 SQL 查询，并获取结果集
                ResultSet rs = pstmt.executeQuery();
                if (!rs.next()) {
                    JOptionPane.showMessageDialog(null, "账号不存在！");
                    return false;
                } else if (!rs.getString("user_password").equals(password)) {
                    JOptionPane.showMessageDialog(null, "密码错误！");
                    return false;
                } else {
                    // 登录成功后设置当前用户账号
                    loggedUserNumber = username;
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 注册插入语句
    public static boolean registerUser(String username, String password) {
        String sql = "SELECT * FROM tb_user WHERE user_number = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                JOptionPane.showMessageDialog(null, "该账号已经存在，请写一个新的账号。");
                return false;
            } else {
                sql = "INSERT INTO tb_user (user_number, user_password) VALUES (?, ?)";
                try (PreparedStatement pstmt2 = conn.prepareStatement(sql)) {
                    pstmt2.setString(1, username);
                    pstmt2.setString(2, password);
                    pstmt2.executeUpdate();
                }

                // 添加到tb_userInfo表
                sql = "INSERT INTO tb_userInfo (user_number, userInfo_name) VALUES (?, ?)";
                try (PreparedStatement pstmt3 = conn.prepareStatement(sql)) {
                    pstmt3.setString(1, username);
                    pstmt3.setString(2, username);
                    pstmt3.executeUpdate();
                }

                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 添加获取用户账号的方法
    public static List<String[]> getUserJFrame(String userNumber) {
        List<String[]> userInfo = new ArrayList<>();
        String sql = "SELECT user_password, user_phoneNumber, user_static FROM tb_user WHERE user_number = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userNumber);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String[] user = new String[3];
                user[0] = rs.getString("user_password");
                user[1] = rs.getString("user_phoneNumber");
                user[2] = rs.getString("user_static");
                userInfo.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userInfo;
    }

    // 添加获取用户个人信息的方法
    public static List<String[]> getUserInfo(String userNumber) {
        List<String[]> userInfo = new ArrayList<>();
        String sql = "SELECT userInfo_name, userInfo_gander, userInfo_profile FROM tb_userInfo WHERE user_number = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userNumber);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String[] user = new String[3];
                user[0] = rs.getString("userInfo_name");
                user[1] = rs.getString("userInfo_gander");
                user[2] = rs.getString("userInfo_profile");
                userInfo.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userInfo;
    }

    // 添加获取用户博客信息的方法
    public static List<String[]> getUserBlog(String userNumber) {
        List<String[]> userBlogs = new ArrayList<>();
        String sql = "SELECT blog_number, blog_title, blog_text, blog_insertTime FROM tb_blog WHERE user_number = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userNumber);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String[] blog = new String[5];
                blog[0] = rs.getString("blog_number");
                blog[1] = rs.getString("blog_title");
                blog[2] = rs.getString("blog_text");
                blog[3] = rs.getString("blog_insertTime");

                // 获取博客标签
                String labelSql = "SELECT label_name FROM tb_label WHERE label_number IN (SELECT label_number FROM tb_relation_blog_label WHERE blog_number = ?)";
                try (PreparedStatement labelPstmt = conn.prepareStatement(labelSql)) {
                    labelPstmt.setString(1, blog[0]);
                    ResultSet labelRs = labelPstmt.executeQuery();
                    if (labelRs.next()) {
                        blog[4] = labelRs.getString("label_name");
                    } else {
                        blog[4] = "无标签"; // 如果没有找到标签，设置为 "无标签"
                    }
                }

                userBlogs.add(blog);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userBlogs;
    }

    /** 系统公告查询语句
     * 在DatabaseConnection类创建一个方法 announcementSearch
    // 系统公告查询语句
    // db_blog数据库
    // tb_announcement数据表
    // 查询字段：announcement_title announcement_text announcement_date
    // 返回List集合
    // 在AnnouncementFrame的中心区块依次展示公告*/
    public static List<String[]> announcementSearch() {
        // 创建一个新的ArrayList来存储公告信息
        List<String[]> announcements = new ArrayList<>();

        // 定义SQL查询语句，从tb_announcement表中选择公告的标题 内容 日期
        String sql = "SELECT announcement_title, announcement_text, announcement_date FROM tb_announcement";

        // 使用try-with-resources语句创建数据库连接和PreparedStatement，这样在try代码块结束后它们都会自动关闭
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // 执行SQL查询并获取结果集
            ResultSet rs = pstmt.executeQuery();

            // 遍历结果集
            while (rs.next()) {
                // 对于每一行结果，创建一个新的字符串数组来存储公告的标题 内容 日期
                String[] announcement = new String[3];
                announcement[0] = rs.getString("announcement_title");
                announcement[1] = rs.getString("announcement_text");
                announcement[2] = rs.getString("announcement_date");

                // 将新的公告添加到公告列表中
                announcements.add(announcement);
            }
        } catch (SQLException e) {
            // 如果在执行SQL查询或处理结果集时发生错误，打印相关报错
            e.printStackTrace();
        }
        // 返回公告列表
        return announcements;
    }

    /** 开发者信息查询语句
    // db_blog数据库
    // tb_developer数据表
    // 查询字段:developer_name developer_photo developer_profile developer_phoneNumber
    // 返回List集合
    // 在DeveloperJFrame的中心依次展示开发者介绍*/
    public static List<String[]> developerJSearch(){
        // 创建一个新的ArrayList来存储开发者信息
        List<String[]> developers = new ArrayList<>();

        // 定义SQL查询语句，从tb_developer表中选择开发者的姓名、照片、简介和电话号码
        String sql = "SELECT developer_name, developer_photo, developer_profile, developer_phoneNumber FROM tb_developer";

        // 使用try-with-resources语句创建数据库连接和PreparedStatement，这样在try代码块结束后它们都会自动关闭
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // 执行SQL查询并获取结果集
            ResultSet rs = pstmt.executeQuery();

            // 遍历结果集
            while (rs.next()) {
                // 对于每一行结果，创建一个新的字符串数组来存储开发者的姓名、照片、简介和电话号码
                String[] developer = new String[4];
                developer[0] = rs.getString("developer_name");
                developer[1] = rs.getString("developer_photo");
                developer[2] = rs.getString("developer_profile");
                developer[3] = rs.getString("developer_phoneNumber");

                // 将新的开发者添加到开发者列表中
                developers.add(developer);
            }
        } catch (SQLException e) {
            // 如果在执行SQL查询或处理结果集时发生错误，打印相关报错
            e.printStackTrace();
        }

        // 返回开发者列表
        return developers;
    }



}

