import com.almasb.fxgl.core.collection.Array;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.*;

public class StudentManagementApp extends JFrame {


    private final JTextField idField;
    private final JTextField nameField;
    private final JTextField ageField;
    private final String DATABASE_URL = "jdbc:sqlite:identifier.sqlite";
    private final String DATABASE_USERNAME = "your_username";
    private final String DATABASE_PASSWORD = "your_password";


    public StudentManagementApp() {
        setTitle("Student management application");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setFocusable(false);


        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        inputPanel.setBackground(Color.BLACK);

        JLabel idLabel = new JLabel("Student ID:");
        idLabel.setForeground(Color.WHITE);
        idField = new JTextField(10);
        inputPanel.add(idLabel);
        inputPanel.add(idField);

        JLabel nameLabel = new JLabel("Student Name:");
        nameLabel.setForeground(Color.WHITE);
        nameField = new JTextField(15);
        inputPanel.add(nameLabel);
        inputPanel.add(nameField);

        JLabel ageLabel = new JLabel("Student Age:");
        ageLabel.setForeground(Color.WHITE);
        ageField = new JTextField(5);
        inputPanel.add(ageLabel);
        inputPanel.add(ageField);


        JPanel buttonPanel = getjPanel();

        JTextArea displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setBackground(Color.BLACK);
        displayArea.setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(displayArea);

        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(inputPanel, BorderLayout.NORTH);
        contentPane.add(buttonPanel, BorderLayout.CENTER);
        contentPane.add(scrollPane, BorderLayout.SOUTH);
    }

    @NotNull
    private JPanel getjPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        buttonPanel.setBackground(Color.BLACK);

        JButton addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addStudent();
            }
        });

        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteStudent();
            }
        });

        JButton modifyButton = new JButton("Modify");
        modifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modifyStudent();
            }
        });

        JButton displayButton = new JButton("Display");
        displayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayStudents();
            }
        });

        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(modifyButton);
        buttonPanel.add(displayButton);
        return buttonPanel;
    }


    public void addStudent() {
        String id = idField.getText();
        String name = nameField.getText();
        String ageStr = ageField.getText();

        if (id.isEmpty() || name.isEmpty() || ageStr.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter student ID, name, and age.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int age;
        try {
            age = Integer.parseInt(ageStr);
            if (age < 0 || age > 50) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Please enter a valid age (between 0 and 50).",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String databasePath = "jdbc:sqlite:identifier.sqlite";
        try (Connection conn = DriverManager.getConnection(databasePath)) {

            String sql = "INSERT INTO students (id, name, age) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, id);
                stmt.setString(2, name);
                stmt.setInt(3, age);

                stmt.executeUpdate();
            }

            // Add the new student to the ArrayList
            Array<Student> studentsList = new Array<>();
            studentsList.add(new Student(id, name, age));

            // Clear the input fields
            clearFields();

            // Display a success message
            JOptionPane.showMessageDialog(this,
                    "Student added successfully!",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            // Display an error message
            JOptionPane.showMessageDialog(this,
                    "Error: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    public void deleteStudent() {
        String id = idField.getText();

        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter student ID.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String deleteQuery = "DELETE FROM students WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(deleteQuery)) {

            pstmt.setString(1, id);
            int deletedRows = pstmt.executeUpdate();

            if (deletedRows > 0) {
                JOptionPane.showMessageDialog(this,
                        "Student deleted successfully!",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this,
                        "No student found with ID " + id,
                        "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                    "Error: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void modifyStudent() {
        String id = idField.getText();
        String name = nameField.getText();
        String ageStr = ageField.getText();

        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter student ID.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String updateQuery = "UPDATE students SET name = ?, age = ? WHERE id = ?";
        String studentDBManager = "jdbc:sqlite:identifier.sqlite";
        try (Connection conn = DriverManager.getConnection(studentDBManager, DATABASE_USERNAME, DATABASE_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {

            pstmt.setString(1, name);
            int age = Integer.parseInt(ageStr);
            pstmt.setInt(2, age);
            pstmt.setString(3, id);

            int updatedRows = pstmt.executeUpdate();

            if (updatedRows > 0) {
                JOptionPane.showMessageDialog(this,
                        "Student modified successfully!",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this,
                        "No student found with ID " + id,
                        "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                    "Error: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }




    public void displayStudents() {
        String query = "SELECT id, name, age FROM students";
        try (Connection conn = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet resultSet = stmt.executeQuery(query)) {

            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("ID");
            model.addColumn("Name");
            model.addColumn("Age");

            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");

                model.addRow(new Object[]{id, name, age});
            }

            JTable table = new JTable(model);
            JScrollPane scrollPane = new JScrollPane(table);

            JFrame frame = new JFrame("Students List");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.add(scrollPane);
            frame.pack();
            frame.setVisible(true);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                    "Error: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }



    public void clearFields() {
        idField.setText("");
        nameField.setText("");
        ageField.setText("");
    }




    public class Student {
        private String id;
        private String name;
        private int age;

        public Student(String id, String name, int age) {
            this.id = id;
            this.name = name;
            this.age = age;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "ID: " + id + ", Name: " + name + ", Age: " + age;
        }
    }


    public static void main(String[] args) {
        LoginGUI loginGUI = new LoginGUI();
        loginGUI.setVisible(true);

        if (loginGUI.isSuccessfulLogin()) {
            StudentManagementApp app = new StudentManagementApp();
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    StudentDBManager databaseManager = new StudentDBManager();
                    StudentManagementApp app = new StudentManagementApp();
                    app.setVisible(true);
                }
            });
        }


    }
}