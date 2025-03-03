package attendance.domain;

import attendance.util.DateGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class AttendanceRecordsTest {

    private static final DateGenerator dateGenerator = new TestDateGenerator();

    @Test
    @DisplayName("여러 출결 상황을 받아 정렬해 생성한다")
    void 여러_출결_상황을_받아_정렬해_생성한다() {
        // given
        LocalDate nowDate = dateGenerator.generate();

        List<String> nicknames = List.of("비타", "레오", "듀이", "꾹이", "몽이");

        List<List<Attendance>> attendances = List.of(
                List.of(
                        Attendance.fromDateTime(LocalDateTime.of(nowDate, LocalTime.MAX)),
                        Attendance.fromDateTime(LocalDateTime.of(nowDate.minusDays(1), LocalTime.MAX)),
                        Attendance.fromDateTime(LocalDateTime.of(nowDate.minusDays(2), LocalTime.MAX))
                ),
                List.of(
                        Attendance.fromDateTime(LocalDateTime.of(nowDate, LocalTime.MAX)),
                        Attendance.fromDateTime(LocalDateTime.of(nowDate.minusDays(1), LocalTime.MAX)),
                        Attendance.fromDateTime(LocalDateTime.of(nowDate.minusDays(2), LocalTime.MAX))
                ),
                List.of(
                        Attendance.fromDateTime(LocalDateTime.of(nowDate, LocalTime.MAX)),
                        Attendance.fromDateTime(LocalDateTime.of(nowDate.minusDays(1), LocalTime.MAX)),
                        Attendance.fromDateTime(LocalDateTime.of(nowDate.minusDays(2), LocalTime.MAX)),
                        Attendance.fromDateTime(LocalDateTime.of(nowDate.minusDays(5), LocalTime.MIDNIGHT))
                ),
                List.of(
                        Attendance.fromDateTime(LocalDateTime.of(nowDate, LocalTime.MAX)),
                        Attendance.fromDateTime(LocalDateTime.of(nowDate.minusDays(1), LocalTime.MAX)),
                        Attendance.fromDateTime(LocalDateTime.of(nowDate.minusDays(2), LocalTime.MAX)),
                        Attendance.fromDateTime(LocalDateTime.of(nowDate.minusDays(5), LocalTime.MAX))
                ),
                List.of(
                        Attendance.fromDateTime(LocalDateTime.of(nowDate, LocalTime.MAX)),
                        Attendance.fromDateTime(LocalDateTime.of(nowDate.minusDays(1), LocalTime.MAX)),
                        Attendance.fromDateTime(LocalDateTime.of(nowDate.minusDays(2), LocalTime.MAX)),
                        Attendance.fromDateTime(LocalDateTime.of(nowDate.minusDays(5), LocalTime.MAX)),
                        Attendance.fromDateTime(LocalDateTime.of(nowDate.minusDays(6), LocalTime.MAX)),
                        Attendance.fromDateTime(LocalDateTime.of(nowDate.minusDays(7), LocalTime.MAX)),
                        Attendance.fromDateTime(LocalDateTime.of(nowDate.minusDays(8), LocalTime.MAX))
                )
        );

        List<AttendanceRecord> attendanceRecords = List.of(
                AttendanceRecord.fromNicknameAndAttendances(nicknames.get(0), attendances.get(0)),
                AttendanceRecord.fromNicknameAndAttendances(nicknames.get(1), attendances.get(1)),
                AttendanceRecord.fromNicknameAndAttendances(nicknames.get(2), attendances.get(2)),
                AttendanceRecord.fromNicknameAndAttendances(nicknames.get(3), attendances.get(3)),
                AttendanceRecord.fromNicknameAndAttendances(nicknames.get(4), attendances.get(4))
        );

        AttendanceRecords records = new AttendanceRecords(attendanceRecords);

        // when
        List<AttendanceRecord> result = records.records();

        // then
        assertAll(
                () -> assertThat(result.get(0).getNickname()).isEqualTo("몽이"),
                () -> assertThat(result.get(1).getNickname()).isEqualTo("꾹이"),
                () -> assertThat(result.get(2).getNickname()).isEqualTo("듀이"),
                () -> assertThat(result.get(3).getNickname()).isEqualTo("레오"),
                () -> assertThat(result.get(4).getNickname()).isEqualTo("비타")
        );
    }

    private static class TestDateGenerator implements DateGenerator {

        @Override
        public LocalDate generate() {
            return LocalDate.of(2025, 3, 19);
        }
    }
}
