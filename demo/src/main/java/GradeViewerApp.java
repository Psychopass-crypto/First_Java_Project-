import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GradeViewerApp extends JFrame {
    private JTextField gradeTextField;
    private JTextArea displayTextArea;
    private JButton addButton;
    private JButton calculateButton;

    private int gradeCount;
    private double gradeTotal;

    public GradeViewerApp() {
        setTitle("Grade Viewer");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setupUI();
    }

    public static StudentManagementApp.Student findStudentById(String selectedId) {
        StudentManagementApp.Student grades = null;
        return grades;
    }

    private void setupUI() {
        gradeTextField = new JTextField(10);
        displayTextArea = new JTextArea(10, 30);
        displayTextArea.setEditable(false);

        addButton = new JButton("Add Grade");
        calculateButton = new JButton("Calculate Average");

        addButton.addActionListener(e -> addGrade());
        calculateButton.addActionListener(e -> calculateAverage());

        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Enter grade: "));
        inputPanel.add(gradeTextField);
        inputPanel.add(addButton);

        add(inputPanel, BorderLayout.NORTH);
        add(new JScrollPane(displayTextArea), BorderLayout.CENTER);
        add(calculateButton, BorderLayout.SOUTH);
    }

    private void addGrade() {
        try {
            double grade = Double.parseDouble(gradeTextField.getText());
            gradeTotal += grade;
            gradeCount++;
            displayTextArea.append("Grade added: " + grade + "\n");
            gradeTextField.setText("");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Invalid grade format!");
        }
    }

    private void calculateAverage() {
        if (gradeCount > 0) {
            double average = gradeTotal / gradeCount;
            displayTextArea.append("Average grade: " + average + "\n");
        } else {
            JOptionPane.showMessageDialog(null, "No grades have been added!");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new GradeViewerApp().setVisible(true);
        });
    }
}
