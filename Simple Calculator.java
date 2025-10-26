import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Calculator extends JFrame implements ActionListener {

    // Components
    private JTextField display;
    private JPanel panel;
    private String[] buttons = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", ".", "=", "+"
    };
    private JButton[] btn = new JButton[buttons.length];
    private JButton clearBtn;

    // Variables for operations
    private String operator = "";
    private double num1 = 0, num2 = 0, result = 0;
    private boolean startNew = true;

    public Calculator() {
        // Frame settings
        setTitle("Simple Calculator");
        setSize(300, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Display field
        display = new JTextField("0");
        display.setFont(new Font("Arial", Font.BOLD, 24));
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setEditable(false);
        add(display, BorderLayout.NORTH);

        // Button panel
        panel = new JPanel();
        panel.setLayout(new GridLayout(4, 4, 10, 10));
        for (int i = 0; i < buttons.length; i++) {
            btn[i] = new JButton(buttons[i]);
            btn[i].setFont(new Font("Arial", Font.BOLD, 20));
            btn[i].addActionListener(this);
            panel.add(btn[i]);
        }
        add(panel, BorderLayout.CENTER);

        // Clear button
        clearBtn = new JButton("C");
        clearBtn.setFont(new Font("Arial", Font.BOLD, 20));
        clearBtn.addActionListener(this);
        add(clearBtn, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (command.charAt(0) >= '0' && command.charAt(0) <= '9' || command.equals(".")) {
            if (startNew) {
                display.setText(command);
                startNew = false;
            } else {
                display.setText(display.getText() + command);
            }
        } else if (command.equals("C")) {
            display.setText("0");
            num1 = num2 = result = 0;
            operator = "";
            startNew = true;
        } else if (command.equals("=")) {
            num2 = Double.parseDouble(display.getText());
            switch (operator) {
                case "+": result = num1 + num2; break;
                case "-": result = num1 - num2; break;
                case "*": result = num1 * num2; break;
                case "/": 
                    if (num2 != 0) result = num1 / num2;
                    else display.setText("Error");
                    break;
            }
            display.setText(String.valueOf(result));
            startNew = true;
        } else { // operator pressed
            operator = command;
            num1 = Double.parseDouble(display.getText());
            startNew = true;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Calculator().setVisible(true);
        });
    }
}
