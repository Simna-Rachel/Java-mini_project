import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Ecometer {

    private JFrame frame;
    private JPanel loginPanel;
    private JPanel inputPanel;

    public Ecometer() {
        frame = new JFrame("Carbon Emission Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(550, 600);

        initLoginPanel();
        initInputPanel();

        frame.add(loginPanel);
        frame.setVisible(true);
    }

    private void initLoginPanel() {
        loginPanel = new JPanel();
        loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));
        loginPanel.setBorder(new EmptyBorder(30, 50, 30, 50));
        loginPanel.setBackground(new Color(240, 248, 255)); // light blue

        JLabel title = new JLabel("Login");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel userLabel = new JLabel("Username:");
        JTextField userField = new JTextField(15);

        JLabel passLabel = new JLabel("Password:");
        JPasswordField passField = new JPasswordField(15);

        JButton loginButton = new JButton("Login");
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.setBackground(new Color(135, 206, 250));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);

        loginPanel.add(title);
        loginPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        loginPanel.add(userLabel);
        loginPanel.add(userField);
        loginPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        loginPanel.add(passLabel);
        loginPanel.add(passField);
        loginPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        loginPanel.add(loginButton);

        loginButton.addActionListener(e -> {
            String username = userField.getText();
            String password = new String(passField.getPassword());

            if (username.equals("admin") && password.equals("1234")) {
                frame.getContentPane().removeAll();
                frame.add(inputPanel);
                frame.revalidate();
                frame.repaint();
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid login!");
            }
        });
    }

    private void initInputPanel() {
        inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.setBorder(new EmptyBorder(20, 50, 20, 50));
        inputPanel.setBackground(new Color(245, 255, 250));

        JLabel title = new JLabel("Monthly Carbon Calculator");
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField carField = new JTextField(10);
        JTextField electricityField = new JTextField(10);
        JTextField meatField = new JTextField(10);
        JTextField flightsField = new JTextField(10);

        JLabel carLabel = new JLabel("Car travel (km/month):");
        JLabel electricityLabel = new JLabel("Electricity usage (kWh/month):");
        JLabel meatLabel = new JLabel("Meat meals/month:");
        JLabel flightsLabel = new JLabel("Flights/month:");

        JButton calcButton = new JButton("Calculate Emission");
        calcButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        calcButton.setBackground(new Color(60, 179, 113));
        calcButton.setForeground(Color.WHITE);
        calcButton.setFocusPainted(false);

        JLabel resultLabel = new JLabel("");
        resultLabel.setFont(new Font("Arial", Font.BOLD, 16));
        JLabel adviceLabel = new JLabel("<html></html>");
        adviceLabel.setFont(new Font("Arial", Font.ITALIC, 14));

        addInputRow(inputPanel, carLabel, carField);
        addInputRow(inputPanel, electricityLabel, electricityField);
        addInputRow(inputPanel, meatLabel, meatField);
        addInputRow(inputPanel, flightsLabel, flightsField);

        inputPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        inputPanel.add(calcButton);
        inputPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        inputPanel.add(resultLabel);
        inputPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        inputPanel.add(adviceLabel);

        calcButton.addActionListener(e -> {
            try {
                double carKm = Double.parseDouble(carField.getText());
                double electricity = Double.parseDouble(electricityField.getText());
                double meatMeals = Double.parseDouble(meatField.getText());
                double flights = Double.parseDouble(flightsField.getText());

                double carFactor = 0.21;
                double electricityFactor = 0.5;
                double meatFactor = 5.0;
                double flightFactor = 250.0;

                double totalEmission = carKm * carFactor
                        + electricity * electricityFactor
                        + meatMeals * meatFactor
                        + flights * flightFactor;

                resultLabel.setText(String.format("Total Emission: %.2f kg CO₂", totalEmission));

                String advice;
                Color adviceColor;

                if (totalEmission < 250) {
                    advice = "<html>🌱 Excellent! Your monthly footprint is very low.<br>" +
                            "🌟 You're leading by example. Keep up your amazing eco-conscious lifestyle!<br>" +
                            "💡 Tip: Share your sustainability practices with friends and family to inspire change.</html>";
                    adviceColor = new Color(0, 128, 0);
                } else if (totalEmission < 400) {
                    advice = "<html>👍 Good job! Your monthly footprint is low.<br>" +
                            "✅ You're doing better than most people!<br>" +
                            "💪 Keep making mindful choices, and consider exploring even greener options.</html>";
                    adviceColor = new Color(255, 165, 0);
                } else if (totalEmission < 600) {
                    advice = "<html>⚖ Balanced. You’re doing okay, but small improvements can help.<br>" +
                            "🌿 You're on the right track. Try small tweaks like energy-saving bulbs or reducing short car trips.</html>";
                    adviceColor = new Color(255, 140, 0);
                } else if (totalEmission < 800) {
                    advice = "<html>⚠ High footprint. Consider making the following changes:<br>" +
                            "- Reduce unnecessary car trips or carpool<br>" +
                            "- Unplug electronics when not in use<br>" +
                            "- Use energy-efficient appliances<br>" +
                            "- Try reducing red meat consumption<br>" +
                            "- Combine errands into one trip to save fuel</html>";
                    adviceColor = new Color(220, 20, 60);
                } else if (totalEmission < 1000) {
                    advice = "<html>🚨 Very high footprint! Significant changes are needed:<br>" +
                            "- Consider switching to an electric vehicle or using public transport<br>" +
                            "- Install solar panels or choose renewable electricity providers<br>" +
                            "- Reduce meat consumption, especially red meat<br>" +
                            "- Avoid short flights and use trains or buses when possible<br>" +
                            "- Audit your home for energy leaks and insulate properly</html>";
                    adviceColor = new Color(178, 34, 34);
                } else {
                    advice = "<html>🔥 Extreme footprint! Urgent action needed!<br>" +
                            "🌍 You may be among the top carbon emitters globally.<br>" +
                            "Suggestions:<br>" +
                            "- Stop or severely reduce flying (use virtual meetings or trains)<br>" +
                            "- Transition to a fully plant-based diet<br>" +
                            "- Sell or replace fuel-inefficient vehicles<br>" +
                            "- Conduct a full home energy audit and retrofit<br>" +
                            "- Offset your emissions by supporting reforestation or clean energy projects<br>" +
                            "- Consider lifestyle downsizing if feasible</html>";
                    adviceColor = new Color(139, 0, 0);
                }

                adviceLabel.setText(advice);
                adviceLabel.setForeground(adviceColor);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Please enter valid numbers for all fields.");
            }
        });
    }

    private void addInputRow(JPanel panel, JLabel label, JTextField field) {
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
        panel.add(label);
        panel.add(field);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
    }

    public static void main(String[] args) {
        new Ecometer();
    }
}