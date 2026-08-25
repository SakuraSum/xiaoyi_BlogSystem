package TOOL;

import UI.LoginJFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BackToLoginMenuItem extends JMenuItem {
    public BackToLoginMenuItem(JFrame currentFrame) {
        super("返回登录界面");

        // 给菜单项添加监听器
        this.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 打开登录界面
                new LoginJFrame();

                // 关闭当前窗口
                currentFrame.dispose();
            }
        });
    }
}
