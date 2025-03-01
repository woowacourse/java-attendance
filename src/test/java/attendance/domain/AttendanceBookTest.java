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
                new AttendanceDate(2024, 12, 3));

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
