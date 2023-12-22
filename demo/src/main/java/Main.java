import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            StudentDBManager databaseManager = new StudentDBManager();
            StudentManagementApp app = new StudentManagementApp();
            createMenu(app);
        });
    }

    private static void createMenu(StudentManagementApp app) {
        JMenuBar menuBar = new JMenuBar();

        JMenu studentMenu = new JMenu("Student");
        JMenuItem addStudentItem = new JMenuItem("Add Student");
        JMenuItem deleteStudentItem = new JMenuItem("Delete Student");
        JMenuItem modifyStudentItem = new JMenuItem("Modify Student");
        JMenuItem displayStudentItem = new JMenuItem("Display Students");

        addStudentItem.addActionListener(e -> app.addStudent()); // Connect the menu action to the method
        deleteStudentItem.addActionListener(e -> app.deleteStudent()); // Connect the menu action to the method
        modifyStudentItem.addActionListener(e -> app.modifyStudent()); // Connect the menu action to the method
        displayStudentItem.addActionListener(e -> app.displayStudents()); // Connect the menu action to the method

        studentMenu.add(addStudentItem);
        studentMenu.add(deleteStudentItem);
        studentMenu.add(modifyStudentItem);
        studentMenu.add(displayStudentItem);

        menuBar.add(studentMenu);

        app.setJMenuBar(menuBar);
    }
}

