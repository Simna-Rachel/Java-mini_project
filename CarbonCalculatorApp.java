import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.*;
import java.util.*;

public class CarbonCalculatorApp {

    private JFrame frame;
    private JPanel loginPanel;
    private JPanel inputPanel;
    private String currentUser = null;

    // 🔹 Database connection details
    private static final String DB_URL = "jdbc:mysql://localhost:3306/Ecometer?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "treat";

    public CarbonCalculatorApp() {
        frame = new JFrame("Ecometer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(550, 600);

        initLoginPanel();
        initInputPanel();

        frame.add(loginPanel);
        frame.setVisible(true);
    }

    // Save emission data
    private boolean saveEmission(String username, String month, int year,
                                 double carKm, double electricityKwh, double meatMeals, double flights, double totalEmission) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                String schema = conn.getCatalog();
                String table = "user_emissions";

                // Check if table exists
                try (PreparedStatement psExist = conn.prepareStatement(
                        "SELECT TABLE_NAME FROM information_schema.TABLES WHERE TABLE_SCHEMA = ? AND TABLE_NAME = ?")) {
                    psExist.setString(1, schema);
                    psExist.setString(2, table);
                    try (ResultSet rs = psExist.executeQuery()) {
                        if (!rs.next()) {
                            JOptionPane.showMessageDialog(frame, "The table 'user_emissions' does not exist in the database. Please create it first.");
                            return false;
                        }
                    }
                }

                // Get columns
                Set<String> cols = new HashSet<>();
                try (PreparedStatement ps = conn.prepareStatement(
                        "SELECT COLUMN_NAME FROM information_schema.COLUMNS WHERE TABLE_SCHEMA = ? AND TABLE_NAME = ?")) {
                    ps.setString(1, schema);
                    ps.setString(2, table);
                    try (ResultSet rs = ps.executeQuery()) {
                        while (rs.next()) cols.add(rs.getString(1).toLowerCase());
                    }
                }

                boolean hasUserId = cols.contains("user_id");
                String usernameCol = null;
                for (String cand : new String[]{"user_name", "username", "user"}) if (cols.contains(cand)) { usernameCol = cand; break; }

                Integer resolvedUserId = null;
                if (hasUserId) {
                    try (PreparedStatement psUid = conn.prepareStatement("SELECT id FROM user_data WHERE user_name = ? LIMIT 1")) {
                        psUid.setString(1, username);
                        try (ResultSet rs = psUid.executeQuery()) {
                            if (rs.next()) resolvedUserId = rs.getInt(1);
                        }
                    }
                    if (resolvedUserId == null) {
                        JOptionPane.showMessageDialog(frame, "User not found in 'user_data' table.");
                        return false;
                    }
                }

                // Determine month parameter type
                Integer monthInt = null;
                try { monthInt = Integer.parseInt(month); } catch (NumberFormatException ignore) { }

                // Check for existing row for this user/month/year
                String existQuery;
                if (hasUserId) {
                    existQuery = "SELECT id FROM user_emissions WHERE year = ? AND month = ? AND user_id = ? LIMIT 1";
                } else if (usernameCol != null) {
                    existQuery = "SELECT id FROM user_emissions WHERE year = ? AND month = ? AND " + usernameCol + " = ? LIMIT 1";
                } else {
                    JOptionPane.showMessageDialog(frame, "Could not determine user column in 'user_emissions'.");
                    return false;
                }

                Integer existingId = null;
                try (PreparedStatement psEx = conn.prepareStatement(existQuery)) {
                    psEx.setInt(1, year);
                    if (monthInt != null) psEx.setInt(2, monthInt); else psEx.setString(2, month);
                    if (hasUserId) psEx.setInt(3, resolvedUserId); else psEx.setString(3, username);
                    try (ResultSet rs = psEx.executeQuery()) {
                        if (rs.next()) existingId = rs.getInt(1);
                    }
                }

                if (existingId != null) {
                    int choice = JOptionPane.showConfirmDialog(frame, "You already entered values for that month/year. Do you want to update them?", "Update existing data", JOptionPane.YES_NO_OPTION);
                    if (choice != JOptionPane.YES_OPTION) {
                        return false; // user chose not to update
                    }

                    // perform update
                    String upd = "UPDATE user_emissions SET car_km = ?, electricity_kwh = ?, meat_meals = ?, flights = ?, total_emission = ? WHERE id = ?";
                    try (PreparedStatement psUp = conn.prepareStatement(upd)) {
                        psUp.setDouble(1, carKm);
                        psUp.setDouble(2, electricityKwh);
                        psUp.setDouble(3, meatMeals);
                        psUp.setDouble(4, flights);
                        psUp.setDouble(5, totalEmission);
                        psUp.setInt(6, existingId);
                        psUp.executeUpdate();
                        return true;
                    }
                }

                // No existing row -> insert
                String insert = "INSERT INTO user_emissions (" + (hasUserId ? "user_id" : usernameCol) + ", car_km, electricity_kwh, meat_meals, flights, total_emission, month, year) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                try (PreparedStatement stmt = conn.prepareStatement(insert)) {
                    if (hasUserId) stmt.setInt(1, resolvedUserId); else stmt.setString(1, username);
                    stmt.setDouble(2, carKm);
                    stmt.setDouble(3, electricityKwh);
                    stmt.setDouble(4, meatMeals);
                    stmt.setDouble(5, flights);
                    stmt.setDouble(6, totalEmission);
                    if (monthInt != null) stmt.setInt(7, monthInt); else stmt.setString(7, month);
                    stmt.setInt(8, year);
                    stmt.executeUpdate();
                    return true;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Failed to save emission data: " + ex.getMessage());
            return false;
        }
    }

    private void initLoginPanel() {
        loginPanel = new JPanel();
        loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));
        loginPanel.setBorder(new EmptyBorder(30, 50, 30, 50));
        loginPanel.setBackground(new Color(240, 248, 255));

        JLabel title = new JLabel("Login");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel userLabel = new JLabel("Username:");
        JTextField userField = new JTextField(15);

        JLabel passLabel = new JLabel("Password:");
        JPasswordField passField = new JPasswordField(15);

        JButton loginButton = new JButton("Login");
        loginButton.setBackground(new Color(135, 206, 250));
        loginButton.setForeground(Color.WHITE);

        JButton registerButton = new JButton("Create Account");
        registerButton.setBackground(new Color(70, 130, 180));
        registerButton.setForeground(Color.WHITE);

        loginPanel.add(title);
        loginPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        loginPanel.add(userLabel);
        loginPanel.add(userField);
        loginPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        loginPanel.add(passLabel);
        loginPanel.add(passField);
        loginPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);
        loginPanel.add(buttonPanel);

        loginButton.addActionListener(e -> {
            String username = userField.getText().trim();
            String password = new String(passField.getPassword()).trim();

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter both username and password.");
                return;
            }

            if (checkLogin(username, password)) {
                currentUser = username;
                JOptionPane.showMessageDialog(frame, "Welcome, " + currentUser + "!");
                frame.getContentPane().removeAll();
                frame.add(inputPanel);
                frame.revalidate();
                frame.repaint();
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid username or password.");
            }
        });

        registerButton.addActionListener(e -> showCreateAccountDialog());
    }

    private boolean checkLogin(String username, String password) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                String query = "SELECT 1 FROM user_data WHERE user_name = ? AND password = ? LIMIT 1";
                try (PreparedStatement stmt = conn.prepareStatement(query)) {
                    stmt.setString(1, username);
                    stmt.setString(2, password);
                    ResultSet rs = stmt.executeQuery();
                    return rs.next();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
                                 
    private void showCreateAccountDialog() {
        JTextField newUser = new JTextField(12);
        JPasswordField newPass = new JPasswordField(12);

        JPanel p = new JPanel(new GridLayout(2, 2, 5, 5));
        p.add(new JLabel("Username:"));
        p.add(newUser);
        p.add(new JLabel("Password:"));
        p.add(newPass);

        int result = JOptionPane.showConfirmDialog(frame, p, "Create Account", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            createUser(newUser.getText().trim(), new String(newPass.getPassword()).trim());
        }
    }

    private boolean createUser(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Username and password cannot be empty.");
            return false;
        }
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                String insert = "INSERT INTO user_data (user_name, password) VALUES (?, ?)";
                try (PreparedStatement stmt = conn.prepareStatement(insert)) {
                    stmt.setString(1, username);
                    stmt.setString(2, password);
                    stmt.executeUpdate();
                    JOptionPane.showMessageDialog(frame, "Account created successfully!");
                    return true;
                }
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            JOptionPane.showMessageDialog(frame, "Username already exists.");
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error: " + e.getMessage());
            return false;
        }
    }

    private void initInputPanel() {
        inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.setBorder(new EmptyBorder(20, 50, 20, 50));
        inputPanel.setBackground(new Color(245, 255, 250));

        JLabel title = new JLabel("Ecometer");
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField carField = new JTextField(10);
        JTextField electricityField = new JTextField(10);
        JTextField meatField = new JTextField(10);
        JTextField flightsField = new JTextField(10);

        String[] months = {"January","February","March","April","May","June","July","August","September","October","November","December"};
        JComboBox<String> monthCombo = new JComboBox<>(months);
        JTextField yearField = new JTextField(6);
        yearField.setPreferredSize(new Dimension(80, 25));

        // Month + Year in one line
        JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        datePanel.setBackground(new Color(245, 255, 250));
        datePanel.add(new JLabel("Month:"));
        monthCombo.setPreferredSize(new Dimension(120, 25));
        datePanel.add(monthCombo);
        datePanel.add(new JLabel("Year:"));
        datePanel.add(yearField);

        JLabel resultLabel = new JLabel("");
        JLabel adviceLabel = new JLabel("");
        adviceLabel.setVerticalAlignment(SwingConstants.TOP);

        JButton calcButton = new JButton("Calculate Emission");
        JButton avgButton = new JButton("Yearly Average");
        calcButton.setBackground(new Color(60, 179, 113));
        calcButton.setForeground(Color.WHITE);
        avgButton.setBackground(new Color(30, 144, 255));
        avgButton.setForeground(Color.WHITE);

        inputPanel.add(title);
        addInputRow(inputPanel, "Car travel (km/month):", carField);
        addInputRow(inputPanel, "Electricity usage (kWh/month):", electricityField);
        addInputRow(inputPanel, "Meat meals/week:", meatField);
        addInputRow(inputPanel, "Flights/month:", flightsField);
        inputPanel.add(datePanel);

        JPanel buttons = new JPanel();
        buttons.setBackground(new Color(245, 255, 250));
        buttons.add(calcButton);
        buttons.add(avgButton);

        inputPanel.add(buttons);
        inputPanel.add(resultLabel);
        inputPanel.add(adviceLabel);

        calcButton.addActionListener(e -> {
            try {
                double car = Double.parseDouble(carField.getText());
                double elec = Double.parseDouble(electricityField.getText());
                double meat = Double.parseDouble(meatField.getText());
                double flights = Double.parseDouble(flightsField.getText());

                double totalEmission = car * 0.21 + elec * 0.5 + meat * 5 + flights * 250;

                resultLabel.setText(String.format("Total Emission: %.2f kg CO₂/month", totalEmission));

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

                int month = monthCombo.getSelectedIndex() + 1;
                String yearStr = yearField.getText().trim();
                if (yearStr.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please enter a year (e.g., 2025).");
                    return;
                }
                int year;
                try {
                    year = Integer.parseInt(yearStr);
                } catch (NumberFormatException nfe) {
                    JOptionPane.showMessageDialog(frame, "Please enter a valid numeric year (e.g., 2025).");
                    return;
                }
                int currentYear = java.time.Year.now().getValue();
                if (year < 1900 || year > currentYear + 1) {
                    JOptionPane.showMessageDialog(frame, "Please enter a realistic year between 1900 and " + (currentYear + 1) + ".");
                    return;
                }

                saveEmission(currentUser, String.valueOf(month), year, car, elec, meat, flights, totalEmission);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Please enter valid data.");
            }
        });

        avgButton.addActionListener(e -> showAverageWindow());
    }

    private void showAverageWindow() {
        JFrame avgFrame = new JFrame("Yearly Average - Ecometer");
        avgFrame.setSize(400, 300);
        avgFrame.setLocationRelativeTo(frame);
        avgFrame.setLayout(new BorderLayout());
        avgFrame.getContentPane().setBackground(new Color(240, 255, 240));

        JTextArea output = new JTextArea();
        output.setEditable(false);
        output.setFont(new Font("Monospaced", Font.PLAIN, 14));
        avgFrame.add(new JScrollPane(output), BorderLayout.CENTER);

        JTextField yearField = new JTextField(6);
        JButton calcBtn = new JButton("Calculate Average");
        JPanel top = new JPanel();
        top.setBackground(new Color(240, 255, 240));
        top.add(new JLabel("Enter Year:"));
        top.add(yearField);
        top.add(calcBtn);
        avgFrame.add(top, BorderLayout.NORTH);

        calcBtn.addActionListener(e -> {
            String ystr = yearField.getText().trim();
            if (ystr.isEmpty()) {
                JOptionPane.showMessageDialog(avgFrame, "Please enter a year (e.g., 2025).");
                return;
            }
            int year;
            try {
                year = Integer.parseInt(ystr);
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(avgFrame, "Please enter a valid numeric year (e.g., 2025).");
                return;
            }
            int currentYear = java.time.Year.now().getValue();
            if (year < 1900 || year > currentYear + 1) {
                JOptionPane.showMessageDialog(avgFrame, "Please enter a realistic year between 1900 and " + (currentYear + 1) + ".");
                return;
            }
            String result = getYearlyAverage(year);
            output.setText(result);
        });

        avgFrame.setVisible(true);
    }

    private String getYearlyAverage(int year) {
        StringBuilder sb = new StringBuilder();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                String q = """
                        SELECT month, total_emission
                        FROM user_emissions ue
                        JOIN user_data ud ON ue.user_id = ud.id
                        WHERE ud.user_name = ? AND ue.year = ?
                        """;
                try (PreparedStatement ps = conn.prepareStatement(q)) {
                    ps.setString(1, currentUser);
                    ps.setInt(2, year);
                    try (ResultSet rs = ps.executeQuery()) {
                        double sum = 0;
                        int count = 0;
                        while (rs.next()) {
                            int m = rs.getInt("month");
                            double val = rs.getDouble("total_emission");
                            sb.append(String.format("%-10s : %.2f kg CO₂%n",
                                    new java.text.DateFormatSymbols().getMonths()[m - 1], val));
                            sum += val;
                            count++;
                        }
                        if (count == 0) return "No records found for " + year;
                        double avg = sum / count;
                        sb.append("\n-----------------------------\n");
                        sb.append(String.format("Average Emission: %.2f kg CO₂", avg));
                    }
                }
            }
        } catch (Exception ex) {
            return "Error fetching data: " + ex.getMessage();
        }
        return sb.toString();
    }

    private void addInputRow(JPanel panel, String labelText, JTextField field) {
        JLabel label = new JLabel(labelText);
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
        panel.add(label);
        panel.add(field);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CarbonCalculatorApp::new);
    }
}
