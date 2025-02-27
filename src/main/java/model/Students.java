package model;

import java.util.List;

public class Students {

    private final List<Student> students;

    public Students(List<Student> studentRepository) {
        this.students = studentRepository;
    }

    public boolean isExistStudent(String name) {
        return students.stream()
                .anyMatch(student -> student.getName().equals(name));
    }

    public Student findStudentByName(String name) {
        for (Student student : students) {
            if (student.getName().equals(name)) {
                return student;
            }
        }
        throw new IllegalArgumentException("[ERROR] 존재하지 않는 학생의 이름입니다.");
    }

    public void updateEveryStudentNoInformationInFile(TodayDate todayDate) {
        for (Student student : students) {
            student.updateNoInformationInFile(todayDate.getTodayDateTIme());
        }
    }

    public List<Student> getStudents() {
        return students;
    }
}
