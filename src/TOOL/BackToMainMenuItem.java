package TOOL;
import UI.SystemMainJFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BackToMainMenuItem extends JMenuItem {
    // 构造方法
    public BackToMainMenuItem(JFrame currentFrame) {
        super("返回博客系统主界面");

        // 给菜单项添加监听器
        this.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 打开博客系统主界面
                new SystemMainJFrame();

                // 关闭当前窗口
                currentFrame.dispose();
            }
        });
    }
}