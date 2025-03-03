package domain;

import domain.dateTime.AttendanceDateTime;
import domain.record.AttendanceRecord;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class AttendanceRecordTest {


    @DisplayName("출석 기록 객체가 정상적으로 생성된다")
    @Test
    void validAttendanceRecord() {
        // given
        final AttendanceDateTime attendanceDateTime = AttendanceDateTime.from("2024-12-11 10:00");

        // when
        // then
        Assertions.assertThatCode(() -> new AttendanceRecord(attendanceDateTime))
                .doesNotThrowAnyException();
    }

    @DisplayName("출석 기록 객체가 생성시 출석 상태도 시간에 따라 정해진다.")
    @ParameterizedTest
    @MethodSource("methodSources")
    void validAttendanceStatus(final AttendanceDateTime attendanceDateTime,
                               final AttendanceStatus expectedAttendanceStatus) {
        // given
        // when
        // then
        final AttendanceRecord attendanceRecord = new AttendanceRecord(attendanceDateTime);
        final AttendanceStatus attendanceStatus = attendanceRecord.getAttendanceStatus();

        Assertions.assertThat(attendanceStatus).isEqualTo(expectedAttendanceStatus);
    }

    private static Stream<Arguments> methodSources() {
        return Stream.of(
                Arguments.arguments(AttendanceDateTime.from("2024-12-09 13:05"), AttendanceStatus.PRESENT),
                Arguments.arguments(AttendanceDateTime.from("2024-12-11 10:06"), AttendanceStatus.LATE),
                Arguments.arguments(AttendanceDateTime.from("2024-12-12 10:30"), AttendanceStatus.LATE),
                Arguments.arguments(AttendanceDateTime.from("2024-12-13 10:31"), AttendanceStatus.ABSENT)
        );
    }
}
