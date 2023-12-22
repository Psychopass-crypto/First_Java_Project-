import javax.swing.*;
import java.awt.*;
import java.sql.*;



public class LoginGUI extends Component {

    private JFrame frame;
    private JPanel panel;
    private JTextField userText;
    private JPasswordField passText;

    public LoginGUI() {


        Font uiFont = new Font("Serif", Font.PLAIN, 18);


        frame = new JFrame("Login");
        frame.setSize(350, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        panel = new JPanel();
        panel.setBackground(new Color(240, 240, 240));
        panel.setLayout(null);


        JLabel userLabel = new JLabel("Username");
        userLabel.setBounds(20, 20, 80, 25);
        userLabel.setFont(uiFont);

        JLabel passLabel = new JLabel("Password");
        passLabel.setBounds(20, 60, 80, 25);
        passLabel.setFont(uiFont);


        userText = new JTextField();
        userText.setBounds(120, 20, 200, 25);
        userText.setFont(uiFont);

        passText = new JPasswordField();
        passText.setBounds(120, 60, 200, 25);
        passText.setFont(uiFont);


        JButton loginButton = new JButton("Login");
        loginButton.setBounds(130, 100, 80, 25);
        loginButton.setBackground(Color.BLUE);
        loginButton.setForeground(Color.BLACK);
        loginButton.setFont(uiFont);


        loginButton.addActionListener(e -> {
            if (isSuccessfulLogin()) {
                frame.dispose();
            }
        });


        panel.add(userLabel);
        panel.add(userText);
        panel.add(passLabel);
        panel.add(passText);
        panel.add(loginButton);

        // Set panel to frame
        frame.add(panel);
        frame.setVisible(true);
    }


    public boolean isSuccessfulLogin() {
        String username = "admin";
        String password = "admin";

        boolean successfulLogin = false;
        if (userText.getText().equals(username) && new String(passText.getPassword()).equals(password)) {
            successfulLogin = true;
            JOptionPane.showMessageDialog(this,
                    "Login successful!",
                    "Success", JOptionPane.INFORMATION_MESSAGE);

            // Establish SQLite connection
            String url = "jdbc:sqlite:/path/to/login.sqlite";
            try (Connection conn = DriverManager.getConnection(url)) {
                System.out.println("Connection to SQLite database has been established.");

                // Check credentials in Admin table
                String sql = "SELECT COUNT(*) FROM studentss WHERE Username = ? AND Password = ?";
                try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                    preparedStatement.setString(1, username);
                    preparedStatement.setString(2, password);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    if (resultSet.next() && resultSet.getInt(1) > 0) {
                        System.out.println("Credentials are valid.");
                        // Perform actions for valid credentials
                    } else {
                        System.out.println("Invalid credentials.");
                        // Handle invalid credentials
                    }
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

            StudentManagementApp mainWindow = new StudentManagementApp();
            mainWindow.setVisible(true);

            frame.dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Invalid username or password.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }

        return successfulLogin;
    }


    public void setVisible(boolean visible) {
    }

    public static void main(String[] args) {
        new LoginGUI();
    }
}

