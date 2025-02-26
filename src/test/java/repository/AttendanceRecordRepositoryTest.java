package repository;

import domain.AttendanceRecord;
import domain.AttendanceStatus;
import java.time.LocalDate;
import java.time.LocalTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceRecordRepositoryTest {

    @AfterEach
    void clearRepository() {
        AttendanceRecordRepository.clear();
    }

    @Test
    @DisplayName("날짜(월일)에 크루의 출석 기록이 있으면 true를 반환한다")
    void existsTest_true() {
        // given
        String nickname = "name";
        LocalDate checkedDate = LocalDate.of(2025, 2, 3);
        LocalTime time = LocalTime.of(13, 0);
        AttendanceStatus status = AttendanceStatus.of(checkedDate, time);
        AttendanceRecordRepository.add(new AttendanceRecord("name", checkedDate, time, status));

        // when & then
        Assertions.assertThat(AttendanceRecordRepository.exists(nickname, checkedDate)).isTrue();
    }

    @Test
    @DisplayName("날짜(월일)에 크루의 출석 기록이 없으면 false를 반환한다")
    void existsTest_false() {
        // given
        String nickname = "name";
        LocalDate checkedDate = LocalDate.of(2025, 2, 3);
        LocalTime time = LocalTime.of(13, 0);
        AttendanceStatus status = AttendanceStatus.of(checkedDate, time);
        AttendanceRecordRepository.add(new AttendanceRecord("name", checkedDate, time, status));

        LocalDate uncheckedDate = LocalDate.of(2025, 2, 4);

        // when & then
        Assertions.assertThat(AttendanceRecordRepository.exists(nickname, uncheckedDate)).isFalse();
    }
}