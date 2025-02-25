package repository;

import constant.AttendanceStatus;
import domain.AttendanceRecord;
import java.time.LocalDate;
import java.time.LocalTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceRecordRepositoryTest {

    @BeforeEach
    void initRepository() {
        AttendanceRecordRepository.clear();
    }

    @Test
    @DisplayName("날짜(월일)에 크루의 출석 기록이 있으면 true를 반환한다")
    void existsTest() {
        // given
        String nickname = "name";
        LocalDate monday = LocalDate.of(2025, 2, 3);
        LocalTime time = LocalTime.of(13, 0);
        AttendanceStatus status = AttendanceStatus.of(monday, time);
        AttendanceRecordRepository.add(new AttendanceRecord("name", monday, time, status));

        // when & then
        Assertions.assertThat(AttendanceRecordRepository.exists(nickname, monday)).isTrue();
    }
}