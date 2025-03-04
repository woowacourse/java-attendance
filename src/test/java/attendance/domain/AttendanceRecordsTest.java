package attendance.domain;

import attendance.util.DateGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("출결 상황 종합 테스트")
class AttendanceRecordsTest {

    private static final DateGenerator dateGenerator = new TestDateGenerator();

    @Test
    @DisplayName("여러 출결 상황을 받아 정렬해 생성한다")
    void 여러_출결_상황을_받아_정렬해_생성한다() {
        // given
        LocalDate nowDate = dateGenerator.generate();

        List<String> nicknames = List.of("비타", "레오", "듀이", "꾹이", "몽이");
        List<String> excepted = List.of("몽이", "꾹이", "듀이", "레오", "비타");

        AttendanceRecords attendanceRecords = createInitRecords(nowDate, nicknames);

        // when
        List<AttendanceRecord> records = attendanceRecords.getRecords();

        List<String> result = records.stream()
                .map(AttendanceRecord::getNickname)
                .toList();

        // then
        assertThat(result)
                .containsExactlyElementsOf(excepted);
    }

    private static AttendanceRecords createInitRecords(final LocalDate nowDate, final List<String> nicknames) {
        List<List<Attendance>> attendances = List.of(
                List.of( // 결석 2회 비타
                        Attendance.createFromDateTime(LocalDateTime.of(nowDate.minusDays(1), LocalTime.MAX)),
                        Attendance.createFromDateTime(LocalDateTime.of(nowDate.minusDays(2), LocalTime.MAX))
                ),
                List.of( // 결석 2회 레오
                        Attendance.createFromDateTime(LocalDateTime.of(nowDate.minusDays(1), LocalTime.MAX)),
                        Attendance.createFromDateTime(LocalDateTime.of(nowDate.minusDays(2), LocalTime.MAX))
                ),
                List.of( // 결석 2회 지각 1회 듀이
                        Attendance.createFromDateTime(LocalDateTime.of(nowDate.minusDays(1), LocalTime.MAX)),
                        Attendance.createFromDateTime(LocalDateTime.of(nowDate.minusDays(2), LocalTime.MAX)),
                        Attendance.createFromDateTime(LocalDateTime.of(nowDate.minusDays(5), LocalTime.MIDNIGHT))
                ),
                List.of( // 결석 3회 꾹이
                        Attendance.createFromDateTime(LocalDateTime.of(nowDate.minusDays(1), LocalTime.MAX)),
                        Attendance.createFromDateTime(LocalDateTime.of(nowDate.minusDays(2), LocalTime.MAX)),
                        Attendance.createFromDateTime(LocalDateTime.of(nowDate.minusDays(5), LocalTime.MAX))
                ),
                List.of( // 결석 6회 몽이
                        Attendance.createFromDateTime(LocalDateTime.of(nowDate.minusDays(1), LocalTime.MAX)),
                        Attendance.createFromDateTime(LocalDateTime.of(nowDate.minusDays(2), LocalTime.MAX)),
                        Attendance.createFromDateTime(LocalDateTime.of(nowDate.minusDays(5), LocalTime.MAX)),
                        Attendance.createFromDateTime(LocalDateTime.of(nowDate.minusDays(6), LocalTime.MAX)),
                        Attendance.createFromDateTime(LocalDateTime.of(nowDate.minusDays(7), LocalTime.MAX)),
                        Attendance.createFromDateTime(LocalDateTime.of(nowDate.minusDays(8), LocalTime.MAX))
                )
        );

        List<AttendanceRecord> attendanceRecords = List.of(
                AttendanceRecord.fromNicknameAndAttendances(nicknames.get(0), attendances.get(0)),
                AttendanceRecord.fromNicknameAndAttendances(nicknames.get(1), attendances.get(1)),
                AttendanceRecord.fromNicknameAndAttendances(nicknames.get(2), attendances.get(2)),
                AttendanceRecord.fromNicknameAndAttendances(nicknames.get(3), attendances.get(3)),
                AttendanceRecord.fromNicknameAndAttendances(nicknames.get(4), attendances.get(4))
        );

        return new AttendanceRecords(attendanceRecords);
    }

    private static class TestDateGenerator implements DateGenerator {

        @Override
        public LocalDate generate() {
            return LocalDate.of(2025, 3, 19);
        }
    }
}
