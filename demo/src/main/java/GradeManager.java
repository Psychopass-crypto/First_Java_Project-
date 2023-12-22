import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GradeManager<T extends GradeManager.GradeInterface> {
    private List<T> grades;

    public GradeManager() {
        grades = new ArrayList<>();
        // Add some sample grades
        grades.add((T) new Grade("Alice", "Math", 90.5));
        grades.add((T) new Grade("Alice", "Science", 85.0));
        grades.add((T) new Grade("Bob", "Math", 75.0));
        grades.add((T) new Grade("Bob", "Science", 80.0));
    }

    public List<T> getGradesForStudent(String studentName) {
        return grades.stream()
                .filter(grade -> grade.getStudentName().equals(studentName))
                .collect(Collectors.toList());
    }

    public void showGradesForStudent(String studentName) {
        List<T> studentGrades = getGradesForStudent(studentName);
        StudentGradeTable<T> gradeTable = new StudentGradeTable<>(studentName, studentGrades);
    }

    public interface GradeInterface {
        String getStudentName();

        String getSubject();

        double getScore();
    }

    public class Grade implements GradeInterface {
        private String studentName;
        private String subject;
        private double score;

        public Grade(String studentName, String subject, double score) {
            this.studentName = studentName;
            this.subject = subject;
            this.score = score;
        }

        @Override
        public String getStudentName() {
            return studentName;
        }

        @Override
        public String getSubject() {
            return subject;
        }

        @Override
        public double getScore() {
            return score;
        }
    }

    public class StudentGradeTable<T> {
        public StudentGradeTable(String studentName, List<T> studentGrades) {

        }


        public  static void main(String[] args) {
            GradeManager<GradeInterface> manager = new GradeManager<>();
            manager.showGradesForStudent("Alice");
        }
    }
}
