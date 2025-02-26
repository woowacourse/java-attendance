package model;

import java.util.List;

public class Students {

    List<Student> studentRepository;

    public Students(List<Student> studentRepository) {
        this.studentRepository = studentRepository;
    }

    public boolean isExistStudent(String name) {
        return studentRepository.stream()
                .anyMatch(student -> student.getName().equals(name));
    }

    public Student findStudentByName(String name) {
        for (Student student : studentRepository) {
            if (student.getName().equals(name)) {
                return student;
            }
        }
        throw new IllegalArgumentException("[ERROR] 존재하지 않는 학생의 이름입니다.");
    }

    public void updateEveryStudentNoInformationInFile(TodayDate todayDate) {
        for (Student student : studentRepository) {
            student.updateNoInformationInFile(todayDate.getTodayDateTIme());
        }
    }

    public List<Student> getStudentRepository() {
        return studentRepository;
    }
}
