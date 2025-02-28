package domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class AttendanceRecordsTest {

    @Nested
    @DisplayName("예외가 발생하지 않는 테스트")
    class Success {

        @Test
        @DisplayName("닉네임과 날짜(연월일)가 일치하는 출석 기록이 있으면 true를 반환한다.")
        void exists_test_true() {
            // given
            String nickname = "name";
            Crew crew = new Crew(nickname);
            LocalDate checkedDate = LocalDate.of(2025, 2, 3);
            LocalTime time = LocalTime.of(13, 0);
            AttendanceRecord attendanceRecord = AttendanceRecord.of(crew, checkedDate, time);

            AttendanceRecords attendanceRecords = new AttendanceRecords(new ArrayList<>());
            attendanceRecords.add(attendanceRecord);

            // when & then
            Assertions.assertThat(attendanceRecords.exists(crew, checkedDate)).isTrue();
        }

        @Test
        @DisplayName("닉네임과 날짜가 일치하는 출석 기록이 없으면 false를 반환한다.")
        void exists_test_false() {
            // given
            String nickname = "name";
            Crew crew = new Crew(nickname);
            LocalDate checkedDate = LocalDate.of(2025, 2, 3);
            LocalTime time = LocalTime.of(13, 0);
            AttendanceRecord attendanceRecord = AttendanceRecord.of(crew, checkedDate, time);

            AttendanceRecords attendanceRecords = new AttendanceRecords(new ArrayList<>());
            attendanceRecords.add(attendanceRecord);

            LocalDate uncheckedDate = LocalDate.of(2025, 2, 4);

            // when & then
            Assertions.assertThat(attendanceRecords.exists(crew, uncheckedDate)).isFalse();
        }

        @Test
        @DisplayName("출석 기록을 추가한다")
        void add_test() {
            // given
            String nickname = "name";
            Crew crew = new Crew(nickname);
            LocalDate checkedDate = LocalDate.of(2025, 2, 3);
            LocalTime time = LocalTime.of(13, 0);
            AttendanceRecord attendanceRecord = AttendanceRecord.of(crew, checkedDate, time);

            AttendanceRecords attendanceRecords = new AttendanceRecords(new ArrayList<>());
            attendanceRecords.add(attendanceRecord);

            // when & then
            Assertions.assertThat(attendanceRecords.exists(crew, checkedDate)).isTrue();
        }

        @Test
        @DisplayName("닉네임과 날짜가 일치하는 출석 기록을 반환한다")
        void find_test() {
            // given
            String nickname = "name";
            Crew crew = new Crew(nickname);
            LocalDate checkedDate = LocalDate.of(2025, 2, 3);
            LocalTime time = LocalTime.of(13, 0);
            AttendanceRecord attendanceRecord = AttendanceRecord.of(crew, checkedDate, time);

            AttendanceRecords attendanceRecords = new AttendanceRecords(new ArrayList<>());
            attendanceRecords.add(attendanceRecord);

            // when & then
            AttendanceRecord found = attendanceRecords.find(crew, checkedDate);
            Assertions.assertThat(attendanceRecord).isEqualTo(found);
        }

        @Test
        @DisplayName("출석 기록을 덮어쓴다")
        void overwriteAttendanceRecord_test() {
            // given
            String nickname = "name";
            Crew crew = new Crew(nickname);
            LocalDate date = LocalDate.of(2025, 2, 3);
            LocalTime beforeTime = LocalTime.of(13, 0);
            LocalTime afterTime = LocalTime.of(13, 2);
            AttendanceRecord before = AttendanceRecord.of(crew, date, beforeTime);
            AttendanceRecord after = AttendanceRecord.of(crew, date, afterTime);

            AttendanceRecords attendanceRecords = new AttendanceRecords(List.of(before));
            attendanceRecords.overwriteAttendanceRecord(after);

            // when & then
            SoftAssertions.assertSoftly(softAssertions -> {
                AttendanceRecord found = attendanceRecords.find(crew, date);
                softAssertions.assertThat(found.getCrew()).isEqualTo(after.getCrew());
                softAssertions.assertThat(found.getDateTime()).isEqualTo(after.getDateTime());
                softAssertions.assertThat(found.getStatus()).isEqualTo(after.getStatus());
            });
        }
    }

    @Nested
    @DisplayName("예외 테스트")
    class Fail {
        @Test
        @DisplayName("닉네임과 날짜가 같은 출석 기록이 존재하는데 추가하려고 하는 경우 예외가 발생한다")
        void add_test_exception() {
            // given
            String nickname = "name";
            Crew crew = new Crew(nickname);
            LocalDate checkedDate = LocalDate.of(2025, 2, 3);
            LocalTime time = LocalTime.of(13, 0);
            AttendanceRecord attendanceRecord = AttendanceRecord.of(crew, checkedDate, time);

            AttendanceRecords attendanceRecords = new AttendanceRecords(new ArrayList<>());
            attendanceRecords.add(attendanceRecord);

            // when & then
            Assertions.assertThat(attendanceRecords.exists(crew, checkedDate)).isTrue();
        }
    }
}