package first_program;

import javax.swing.*;

public class inputTextfield {
    public static void main(String[] args) {
        String text;
        text= JOptionPane.showInputDialog("enter text");
        JOptionPane.showMessageDialog(null, "you input text:\n "+text);
    }
}
