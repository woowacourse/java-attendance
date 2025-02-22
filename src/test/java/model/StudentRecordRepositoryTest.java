package model;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.FileInput;

class StudentRecordRepositoryTest {

    StudentRecordRepository studentRecordRepository = FileInput.createStudentRepository();

    @Test
    @DisplayName("이름을 통해 존재하는 학생인지 판단하는 메서드 테스트")
    void test1() {
        Assertions.assertTrue(studentRecordRepository.isExistStudentByName("이든"));
    }

    @Test
    @DisplayName("이름과 출석 기록이 주어지면 벨류값에 저장하는 메서드 테스트")
    void test2() {
        studentRecordRepository.addRecord("말론", LocalDateTime.of(2024,12,12,0,0));
        Assertions.assertTrue(studentRecordRepository.getStudentRecord().get("말론")
                .getTimeRecords()
                .contains(LocalDateTime.of(2024,12,12,0,0)));
    }

    @Test
    @DisplayName("수정하고자 하는 일자의 기록 삭제 후, 새로운 기록 추가하는 메서드 테스트")
    void test3() {
        studentRecordRepository.modifyRecord("이든",LocalDateTime.of(2024,12,3,13,31));
        Assertions.assertTrue(studentRecordRepository.getStudentRecord().get("이든")
                .getTimeRecords()
                .contains(LocalDateTime.of(2024,12,3,13,31)));
    }

    @Test
    @DisplayName("수정하고자 하는 일자의 기록 삭제하는 메서드 테스트")
    void test4() {
        studentRecordRepository.modifyRecord("이든",LocalDateTime.of(2024,12,3,13,31));
        Assertions.assertFalse(studentRecordRepository.getStudentRecord().get("이든")
                .getTimeRecords()
                .contains(LocalDateTime.of(2024,12,3,10,6)));
    }
}