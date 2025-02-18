import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StudentRepository {
    List<Student> students = new ArrayList<>();

    public boolean notExistStudent(String studentName) {
        return false;
    }

    public Student findStudentByName(String name) {
        for (Student student : students){
            if (student.name.equals(name)){
                return student;
            }
        }
        return null;
    }

    public void addStudent(Student student) {
        students.add(student);
    }
}
