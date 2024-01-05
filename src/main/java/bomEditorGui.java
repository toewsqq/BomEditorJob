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

        panel.setLayout(null);


        JLabel jobNameLabel = new JLabel("Job Name");

        jobNameLabel.setBounds(10,20,80,25);
        panel.add(jobNameLabel);

        JLabel status = new JLabel("Status");

        status.setBounds(10,120,80,25);
        panel.add(status);

        JTextField userText = new JTextField(20);
        userText.setBounds(100,20,165,25);
        panel.add(userText);

        JTextField userText2 = new JTextField(20);
        userText2.setBounds(100,120,165,25);
        panel.add(userText2);

        JButton button = new JButton("Create");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String bomName = userText.getText();
                BomEditor bomEditor = new BomEditor();
                //bomEditor.ReadAllExcel(bomName);
                String message = bomEditor.ReadAllExcel(bomName);
                userText2.setText(message);
            }
        });

        button.setBounds(280, 20, 80, 25);
        panel.add(button);
    }



    }

