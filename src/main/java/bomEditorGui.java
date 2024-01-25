import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class bomEditorGui {

    public static void main(String[] args) {

        JFrame frame = new JFrame("Bom Editor Beta version");
        // Setting the width and height of frame
        frame.setSize(550, 300);
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

        status.setBounds(10,80,80,25);
        panel.add(status);

        JLabel operations = new JLabel("Operation list");

        operations.setBounds(10,120,80,25);
        panel.add(operations);

        JLabel smdOperations = new JLabel("SMD operations");

        smdOperations.setBounds(10,160,110,25);
        panel.add(smdOperations);

        JTextField userText = new JTextField(20);
        userText.setBounds(130,20,165,25);
        panel.add(userText);

        JTextField userText2 = new JTextField(20);
        userText2.setBounds(130,80,365,25);
        panel.add(userText2);

        JTextField userText3 = new JTextField(20);
        userText3.setBounds(130,120,365,25);
        panel.add(userText3);

        JTextField userText4 = new JTextField(20);
        userText4.setBounds(130,160,365,25);
        panel.add(userText4);

        JButton button = new JButton("Edit");
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String bomName = userText.getText();
                //BomEditor bomEditor = new BomEditor(bomName);
                BomEditor bomResult = new BomResult().bomEditor(bomName);
                String message = bomResult.getMessage();
                String operations = bomResult.getOperations();
                String smdOperations = bomResult.getSmdOperations();
                //String smdOperations = "asdad";
                userText2.setText(message);
                userText3.setText(operations);
                userText4.setText(smdOperations);
            }
        };

        button.addActionListener(actionListener);
        userText.addActionListener(actionListener);
        button.setBounds(300, 20, 80, 25);
        panel.add(button);
    }



    }

