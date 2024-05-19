package first_program;

import javax.swing.*;

public class setTitleName {
    public static void main(String[] args) {
        String text;
        text = JOptionPane.showInputDialog("enter the title",JOptionPane.PLAIN_MESSAGE);
        String title =text;
        JOptionPane.showMessageDialog(null, text, title, JOptionPane.PLAIN_MESSAGE);
    }
}
