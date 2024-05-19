package first_program;

import javax.swing.*;

public class textAndTitle {
    public static void main(String[] args) {
        String text;
        text = JOptionPane.showInputDialog("enter text",JOptionPane.PLAIN_MESSAGE);
        String title ="message";
        JOptionPane.showMessageDialog(null, "you input text:\n "+text, title, JOptionPane.PLAIN_MESSAGE);

    }
}
