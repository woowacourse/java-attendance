package service;

import domain.AttendanceBook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.AttendanceRepository;
import repository.AttendanceRepositoryImpl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class AttendanceStoreTest {
    AttendanceRepository attendanceRepository = new AttendanceRepositoryImpl();
    AttendanceStoreService attendanceStoreService = new AttendanceStoreService(attendanceRepository);

    Map<String, AttendanceBook> attendanceBooks = new HashMap<>();

    @BeforeEach
    void setup() {
        AttendanceBook 쿠키_출석부 = new AttendanceBook(2024, 12);
        쿠키_출석부.create(LocalDate.of(2024, 12, 13), LocalTime.of(10, 8));

        AttendanceBook 빙봉_출석부 = new AttendanceBook(2024, 12);
        빙봉_출석부.create(LocalDate.of(2024, 12, 13), LocalTime.of(10, 7));

        AttendanceBook 빙티_출석부 = new AttendanceBook(2024, 12);
        빙티_출석부.create(LocalDate.of(2024, 12, 13), LocalTime.of(10, 7));

        AttendanceBook 이든_출석부 = new AttendanceBook(2024, 12);
        이든_출석부.create(LocalDate.of(2024, 12, 13), LocalTime.of(10, 7));

        attendanceBooks.put("쿠키", 쿠키_출석부);
        attendanceBooks.put("빙봉", 빙봉_출석부);
        attendanceBooks.put("빙티", 빙티_출석부);
        attendanceBooks.put("이든", 이든_출석부);
    }

    @DisplayName("csv 파일에 알맞는 출석 정보를 저장할 수 있다.")
    @Test
    void test1() {
        // given

        // when
        attendanceStoreService.save("src/main/resources/test_attendances.csv");

        // then
        for (String name : attendanceBooks.keySet()) {
            AttendanceBook expected = attendanceBooks.get(name);
            assertThat(attendanceRepository.findByCrewName(name)).isEqualTo(expected);
        }
    }
}
