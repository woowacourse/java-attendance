package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class AttendanceBookTest {

    @Nested
    class ValidCases {

        @Test
        void 출석일시를_저장한다() {
            // given
            Map<AttendanceDate, AttendanceTime> attendanceDateTimes = new HashMap<>();
            AttendanceRecord attendanceRecord = new AttendanceRecord(
                attendanceDateTimes);
            AttendanceBook attendanceBook = new AttendanceBook(new Crew("머피"),
                attendanceRecord);

            AttendanceDate attendanceDate = new AttendanceDate(2024, 12, 3);
            AttendanceTime attendanceTime = new AttendanceTime(10, 0);
            final AttendanceDateTime attendanceDateTime = new AttendanceDateTime(
                attendanceDate, attendanceTime);
            // when
            attendanceBook.save(attendanceDateTime);

            // then
            assertThat(attendanceBook.retrieveByDate(attendanceDate)).isEqualTo(
                attendanceDateTime);
        }

        @Test
        void 출석일시를_수정한다() {
            // given
            Map<AttendanceDate, AttendanceTime> attendanceDateTimes = new HashMap<>();
            AttendanceDate attendanceDate = new AttendanceDate(2024, 12, 3);
            AttendanceTime attendanceTime = new AttendanceTime(10, 0);
            attendanceDateTimes.put(attendanceDate, attendanceTime);
            AttendanceRecord attendanceRecord = new AttendanceRecord(
                attendanceDateTimes);
            AttendanceBook attendanceBook = new AttendanceBook(new Crew("머피"),
                attendanceRecord);

            AttendanceTime modifiedAttendanceTime = new AttendanceTime(11, 0);
            AttendanceDateTime modifiedAttendanceDateTime = new AttendanceDateTime(
                attendanceDate, modifiedAttendanceTime);

            // when
            attendanceBook.modify(modifiedAttendanceDateTime);

            // then
            assertThat(attendanceBook.retrieveByDate(attendanceDate)).isEqualTo(
                modifiedAttendanceDateTime);
        }

        @Test
        void 출석일시를_찾는다() {
            // given
            Map<AttendanceDate, AttendanceTime> attendanceDateTimes = new HashMap<>();
            AttendanceDate attendanceDate = new AttendanceDate(2024, 12, 3);
            AttendanceTime attendanceTime = new AttendanceTime(10, 0);
            attendanceDateTimes.put(attendanceDate, attendanceTime);

            AttendanceRecord attendanceRecord = new AttendanceRecord(
                attendanceDateTimes);
            AttendanceBook attendanceBook = new AttendanceBook(new Crew("머피"),
                attendanceRecord);

            // when
            AttendanceDateTime foundAttendanceDateTime = attendanceBook.retrieveByDate(
                attendanceDate);

            // then
            assertThat(foundAttendanceDateTime).isEqualTo(
                new AttendanceDateTime(attendanceDate, attendanceTime));
        }

        @Test
        void 출석일시를_오름차순_정렬하여_반환한다() {
            // given
            Map<AttendanceDate, AttendanceTime> attendanceDateTimes = new HashMap<>();

            AttendanceDate secondAttendanceDate = new AttendanceDate(
                2024, 12, 3);
            AttendanceTime secondAttendanceTime = new AttendanceTime(
                10, 0);
            attendanceDateTimes.put(secondAttendanceDate,
                secondAttendanceTime);

            AttendanceDate firstAttendanceDate = new AttendanceDate(
                2024, 12, 2);
            AttendanceTime firstAttendanceTime = new AttendanceTime(
                13, 0);
            attendanceDateTimes.put(firstAttendanceDate,
                firstAttendanceTime);

            AttendanceRecord attendanceRecord = new AttendanceRecord(
                attendanceDateTimes);
            AttendanceBook attendanceBook = new AttendanceBook(new Crew("John"),
                attendanceRecord);

            // when
            List<AttendanceDateTime> foundAttendanceDateTimes = attendanceBook.retrieveOrderByDateTimeUntilDate(
                new AttendanceDate(2024, 12, 4));

            // then
            assertThat(foundAttendanceDateTimes).containsExactly(
                new AttendanceDateTime(
                    firstAttendanceDate, firstAttendanceTime),
                new AttendanceDateTime(
                    secondAttendanceDate, secondAttendanceTime)
            );
        }
    }

    @Nested
    class InvalidCases {

        @Test
        void 출석부는_크루_또는_출석기록을_가지지않는다면_기록하지않는다() {
            // when & then
            assertThatThrownBy(() -> new AttendanceBook(null, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("출석부는 크루와 출석 기록을 가지고 있어야 합니다.");
        }
    }
}
