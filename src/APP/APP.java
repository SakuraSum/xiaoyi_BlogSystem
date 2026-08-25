package APP;

import UI.LoginJFrame;
//测试测试测试测试test
public class APP {
    /**
     * 登录界面(可跳转注册界面、博客系统主界面)
     * 注册界面【可返回登录界面】
     * 博客系统主界面(可跳转用户界面、公告界面、开发者介绍页面)【可返回登录界面】
     * 公告界面【可返回博客系统主界面】
     * 开发者介绍页面【可返回博客系统主界面】
     * 用户界面【可返回博客系统主界面】
     *
     */
    public static void main(String[] args) {
        new LoginJFrame();
    }
}
