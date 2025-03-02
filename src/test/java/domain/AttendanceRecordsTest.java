package domain;

import fixture.AttendanceRecordsGenerator;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
            Assertions.assertThat(attendanceRecords.existsByCrewAndDate(crew, checkedDate)).isTrue();
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
                AttendanceRecord found = attendanceRecords.getOneByCrewAndDate(crew, date);
                softAssertions.assertThat(found.getCrew()).isEqualTo(after.getCrew());
                softAssertions.assertThat(found.getDateTime()).isEqualTo(after.getDateTime());
                softAssertions.assertThat(found.getStatus()).isEqualTo(after.getStatus());
            });
        }


        @Test
        @DisplayName("닉네임과 날짜(연월일)가 일치하는 출석 기록이 있으면 true를 반환한다.")
        void existsByCrewAndDate_test_true() {
            // given
            String nickname = "name";
            Crew crew = new Crew(nickname);
            LocalDate checkedDate = LocalDate.of(2025, 2, 3);
            LocalTime time = LocalTime.of(13, 0);
            AttendanceRecord attendanceRecord = AttendanceRecord.of(crew, checkedDate, time);
            AttendanceRecords attendanceRecords = new AttendanceRecords(List.of(attendanceRecord));

            // when & then
            Assertions.assertThat(attendanceRecords.existsByCrewAndDate(crew, checkedDate)).isTrue();
        }

        @Test
        @DisplayName("닉네임과 날짜가 일치하는 출석 기록이 없으면 false를 반환한다.")
        void existsByCrewAndDate_test_false() {
            // given
            String nickname = "name";
            Crew crew = new Crew(nickname);
            LocalDate checkedDate = LocalDate.of(2025, 2, 3);
            LocalTime time = LocalTime.of(13, 0);
            AttendanceRecord attendanceRecord = AttendanceRecord.of(crew, checkedDate, time);
            AttendanceRecords attendanceRecords = new AttendanceRecords(List.of(attendanceRecord));

            LocalDate uncheckedDate = LocalDate.of(2025, 2, 4);

            // when & then
            Assertions.assertThat(attendanceRecords.existsByCrewAndDate(crew, uncheckedDate)).isFalse();
        }

        @Test
        @DisplayName("닉네임과 날짜가 일치하는 출석 기록을 반환한다")
        void getOneByCrewAndDate_test() {
            // given
            String nickname = "name";
            Crew crew = new Crew(nickname);
            LocalDate checkedDate = LocalDate.of(2025, 2, 3);
            LocalTime time = LocalTime.of(13, 0);
            AttendanceRecord attendanceRecord = AttendanceRecord.of(crew, checkedDate, time);
            AttendanceRecords attendanceRecords = new AttendanceRecords(List.of(attendanceRecord));

            // when & then
            AttendanceRecord found = attendanceRecords.getOneByCrewAndDate(crew, checkedDate);
            Assertions.assertThat(attendanceRecord).isEqualTo(found);
        }

        @Test
        @DisplayName("닉네임과 날짜가 일치하는 출석 기록을 반환한다. 기록이 없으면 빈 출석 기록을 반환한다.")
        void findOneByCrewAndDate_test_empty() {
            // given
            String nickname = "name";
            Crew crew = new Crew(nickname);

            LocalDate checkedDate = LocalDate.of(2025, 2, 3);
            LocalDate unCheckedDate = LocalDate.of(2025, 2, 4);
            LocalTime time = LocalTime.of(13, 0);
            AttendanceRecord attendanceRecord = AttendanceRecord.of(crew, checkedDate, time);
            AttendanceRecords attendanceRecords = new AttendanceRecords(List.of(attendanceRecord));

            // when & then
            AbstractAttendanceRecord found = attendanceRecords.findOneByCrewAndDate(crew, unCheckedDate);
            Assertions.assertThat(found instanceof EmptyAttendanceRecord).isTrue();
        }

        @Test
        @DisplayName("닉네임과 날짜가 일치하는 출석 기록을 반환한다. 기록이 있으면 출석 기록을 반환한다.")
        void findOneByCrewAndDate_test() {
            // given
            String nickname = "name";
            Crew crew = new Crew(nickname);

            LocalDate checkedDate = LocalDate.of(2025, 2, 3);
            LocalTime time = LocalTime.of(13, 0);
            AttendanceRecord attendanceRecord = AttendanceRecord.of(crew, checkedDate, time);
            AttendanceRecords attendanceRecords = new AttendanceRecords(List.of(attendanceRecord));

            // when & then
            AbstractAttendanceRecord found = attendanceRecords.findOneByCrewAndDate(crew, checkedDate);
            Assertions.assertThat(found instanceof AttendanceRecord).isTrue();
        }

        @Test
        @DisplayName("해당 크루의 두 날짜 사이의 출성 상태 통계를 반환한다")
        void getAttendanceStatistics() {
            LocalDate from = LocalDate.of(2025, 2, 2);
            LocalDate to = LocalDate.of(2025, 2, 25);
            Crew crew = new Crew("루키");
            int lateCount = 5;
            int absentCount = 3;

            AttendanceRecords attendanceRecords = new AttendanceRecords(AttendanceRecordsGenerator.generate(from, to,
                    crew, lateCount, absentCount));
            Map<AttendanceStatus, Integer> statusCount = attendanceRecords.calculateAttendanceStatusCount(crew, from,
                    to);

            SoftAssertions.assertSoftly(softAssertions -> {
                softAssertions.assertThat(statusCount.get(AttendanceStatus.LATE)).isEqualTo(lateCount);
                softAssertions.assertThat(statusCount.get(AttendanceStatus.ABSENT)).isEqualTo(absentCount);
            });
        }
    }

    @Nested
    @DisplayName("예외 테스트")
    class Fail {
        @Test
        @DisplayName("닉네임과 날짜가 같은 출석 기록이 존재하는데 또 추가하려고 하는 경우 예외가 발생한다")
        void add_test_exception() {
            // given
            String nickname = "name";
            Crew crew = new Crew(nickname);
            LocalDate checkedDate = LocalDate.of(2025, 2, 3);
            LocalTime time = LocalTime.of(13, 0);
            AttendanceRecord attendanceRecord = AttendanceRecord.of(crew, checkedDate, time);
            AttendanceRecords attendanceRecords = new AttendanceRecords(List.of(attendanceRecord));

            // when & then
            Assertions.assertThatThrownBy(() -> {
                attendanceRecords.add(attendanceRecord);
            }).isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("닉네임과 날짜가 일치하는 출석 기록을 반환한다. 없으면 예외가 발생한다.")
        void getOneByCrewAndDate_test_exception() {
            // given
            String nickname = "name";
            Crew crew = new Crew(nickname);
            LocalDate checkedDate = LocalDate.of(2025, 2, 3);
            LocalTime time = LocalTime.of(13, 0);
            AttendanceRecord attendanceRecord = AttendanceRecord.of(crew, checkedDate, time);
            AttendanceRecords attendanceRecords = new AttendanceRecords(List.of(attendanceRecord));

            // when & then
            Assertions.assertThatThrownBy(() -> {
                attendanceRecords.getOneByCrewAndDate(new Crew("noname"), checkedDate);
            }).isInstanceOf(IllegalArgumentException.class);

        }
    }
}