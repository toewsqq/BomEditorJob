import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class bomEditorGui {

    public static void main(String[] args) {

//        BomEditor bomEditor = new BomEditor();
//        bomEditor.ReadAllExcel();

        JFrame frame = new JFrame("My First Swing Example");
        // Setting the width and height of frame
        frame.setSize(450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        /* Creating panel. This is same as a div tag in HTML
         * We can create several panels and add them to specific
         * positions in a JFrame. Inside panels we can add text
         * fields, buttons and other components.
         */
        JPanel panel = new JPanel();
        // adding panel to frame
        frame.add(panel);
        /* calling user defined method for adding components
         * to the panel.
         */
        placeComponents(panel);

        // Setting the frame visibility to true
        frame.setVisible(true);
    }

    private static void placeComponents(JPanel panel) {

        /* We will discuss about layouts in the later sections
         * of this tutorial. For now we are setting the layout
         * to null
         */
        panel.setLayout(null);

        // Creating JLabel
        JLabel jobNameLabel = new JLabel("Job Name");

        jobNameLabel.setBounds(10,20,80,25);
        panel.add(jobNameLabel);



        /* Creating text field where user is supposed to
         * enter user name.
         */
        JTextField userText = new JTextField(20);
        userText.setBounds(100,20,165,25);
        panel.add(userText);

        JTextField userText2 = new JTextField(20);
        userText2.setBounds(100,100,165,25);
        panel.add(userText2);
//
//        // Same process for password label and text field.
//        JLabel passwordLabel = new JLabel("Password");
//        passwordLabel.setBounds(10,50,80,25);
//        panel.add(passwordLabel);

        /*This is similar to text field but it hides the user
         * entered data and displays dots instead to protect
         * the password like we normally see on login screens.
         */
//        JPasswordField passwordText = new JPasswordField(20);
//        passwordText.setBounds(100,50,165,25);
//        panel.add(passwordText);

        // Creating login button
        JButton button = new JButton("Create");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String bomName = userText.getText();
                BomEditor bomEditor = new BomEditor();
                //bomEditor.ReadAllExcel(bomName);
                String message = bomEditor.ReadAllExcel(bomName);
                userText2.setText(message);
                //JOptionPane.showMessageDialog(null, "ReadAllExcel executed successfully.");
            }
        });
        button.setBounds(10, 80, 80, 25);
        panel.add(button);
    }



    }

