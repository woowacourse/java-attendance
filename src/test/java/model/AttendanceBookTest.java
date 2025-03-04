package model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.Map;
import java.util.TreeSet;

class AttendanceBookTest {

    @Test
    @DisplayName("TreeSet 자료형에 맞게 자동 sort 되어 있는지 테스트")
    void addSortFunction() {

        // given
        final AttendanceDateTime attendanceDateTime1 = AttendanceDateTime.of("2025-02-27 10:00");
        final AttendanceDateTime attendanceDateTime2 = AttendanceDateTime.of("2025-02-28 10:00");

        final Attendance attendance1 = Attendance.of(attendanceDateTime1);
        final Attendance attendance2 = Attendance.of(attendanceDateTime2);

        // when
        final TreeSet<Attendance> attendances = new TreeSet<>();
        attendances.add(attendance2);
        attendances.add(attendance1);
        final AttendanceBook attendanceBook = new AttendanceBook(attendances);
        final TreeSet<Attendance> result = attendanceBook.getAttendances();

        // then
        Assertions.assertThat(result).containsExactly(attendance1, attendance2);
    }

    @ParameterizedTest
    @DisplayName("같은 일자의 출석이 들어왔을 때 true를 반환하는 지")
    @ValueSource(strings = {"2024-12-16 10:00", "2024-12-16 12:00"})
    void isSameByDateSuccess(final String dateTimeInput) {

        // given
        final AttendanceDateTime descAttendanceDateTime = AttendanceDateTime.of(dateTimeInput);
        final Attendance descAttendance = Attendance.of(descAttendanceDateTime);

        final AttendanceDateTime srcAttendanceDateTime =  AttendanceDateTime.of("2024-12-16 10:00");
        final Attendance srcAttendance = Attendance.of(srcAttendanceDateTime);
        final TreeSet<Attendance> attendances = new TreeSet<>();
        attendances.add(srcAttendance);
        final AttendanceBook attendanceBook = new AttendanceBook(attendances);

        // when
        // then
        Assertions.assertThat(attendanceBook.isSameByDate(descAttendance)).isTrue();
    }

    @ParameterizedTest
    @DisplayName("다른 일자의 출석이 들어왔을 때 false를 반환하는 지")
    @ValueSource(strings = {"2024-12-16 10:00", "2024-12-17 12:00"})
    void isSameByDateFailure(final String dateTimeInput) {

        // given
        final AttendanceDateTime descAttendanceDateTime = AttendanceDateTime.of(dateTimeInput);
        final Attendance descAttendance = Attendance.of(descAttendanceDateTime);

        final AttendanceDateTime srcAttendanceDateTime =  AttendanceDateTime.of("2024-12-18 10:00");
        final Attendance srcAttendance = Attendance.of(srcAttendanceDateTime);
        final TreeSet<Attendance> attendances = new TreeSet<>();
        attendances.add(srcAttendance);
        final AttendanceBook attendanceBook = new AttendanceBook(attendances);

        // when
        // then
        Assertions.assertThat(attendanceBook.isSameByDate(descAttendance)).isFalse();
    }

    @Test
    @DisplayName("출석 기록이 존재할 때 잘 찾는 지")
    void findByDayOfMonthSuccess() {

        // given
        final AttendanceDateTime srcAttendanceDateTime1 = AttendanceDateTime.of("2024-12-16 10:00");
        final Attendance srcAttendance1 = Attendance.of(srcAttendanceDateTime1);
        final AttendanceDateTime descAttendanceDateTime2 = AttendanceDateTime.of("2024-12-17 10:00");
        final Attendance srcAttendance2 = Attendance.of(descAttendanceDateTime2);
        final TreeSet<Attendance> attendances = new TreeSet<>();
        attendances.add(srcAttendance1);
        attendances.add(srcAttendance2);
        final AttendanceBook attendanceBook = new AttendanceBook(attendances);

        // when
        final Attendance findAttendance = attendanceBook.findByDayOfMonth(DayOfMonth.of("16"));

        // then
        Assertions.assertThat(findAttendance).isEqualTo(srcAttendance1);
    }

    @Test
    @DisplayName("출석 기록이 존재하지 않을 때 예외 처리되는 지")
    void findByDayOfMonthFailure() {

        // given
        final AttendanceDateTime srcAttendanceDateTime1 = AttendanceDateTime.of("2024-12-16 10:00");
        final Attendance srcAttendance1 = Attendance.of(srcAttendanceDateTime1);
        final AttendanceDateTime descAttendanceDateTime2 = AttendanceDateTime.of("2024-12-17 10:00");
        final Attendance srcAttendance2 = Attendance.of(descAttendanceDateTime2);
        final TreeSet<Attendance> attendances = new TreeSet<>();
        attendances.add(srcAttendance1);
        attendances.add(srcAttendance2);
        final AttendanceBook attendanceBook = new AttendanceBook(attendances);

        // when
        // then
        Assertions.assertThatThrownBy(
                () -> attendanceBook.findByDayOfMonth(DayOfMonth.of("18"))
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("업데이트 기능이 잘 작동하는 지")
    void update() {

        // given
        final AttendanceDateTime srcAttendanceDateTime1 = AttendanceDateTime.of("2024-12-16 10:00");
        final Attendance srcAttendance1 = Attendance.of(srcAttendanceDateTime1);
        final AttendanceDateTime descAttendanceDateTime2 = AttendanceDateTime.of("2024-12-17 10:00");
        final Attendance srcAttendance2 = Attendance.of(descAttendanceDateTime2);
        final TreeSet<Attendance> attendances = new TreeSet<>();
        attendances.add(srcAttendance1);
        attendances.add(srcAttendance2);
        final AttendanceBook attendanceBook = new AttendanceBook(attendances);

        final AttendanceDateTime newAttendanceDateTime = AttendanceDateTime.of("2024-12-18 10:00");
        final Attendance newAttendance = Attendance.of(newAttendanceDateTime);
        // when
        attendanceBook.update(srcAttendance1, newAttendance);

        // then
        Assertions.assertThat(attendanceBook.isSameByDate(srcAttendance1)).isFalse();
        Assertions.assertThat(attendanceBook.isSameByDate(newAttendance)).isTrue();
    }

    @Test
    @DisplayName("존재하지 않는 출석 기록, 결석으로 추가")
    void updateRecordFromTo() {

        // given
        final AttendanceDateTime srcAttendanceDateTime1 = AttendanceDateTime.of("2024-12-16 10:00");
        final Attendance srcAttendance1 = Attendance.of(srcAttendanceDateTime1);
        final AttendanceDateTime descAttendanceDateTime2 = AttendanceDateTime.of("2024-12-17 10:00");
        final Attendance srcAttendance2 = Attendance.of(descAttendanceDateTime2);
        final TreeSet<Attendance> attendances = new TreeSet<>();
        attendances.add(srcAttendance1);
        attendances.add(srcAttendance2);
        final AttendanceBook attendanceBook = new AttendanceBook(attendances);

        // when
        attendanceBook.updateRecordFromTo(17, 20);

        // then
        final List<AttendanceStatus> statuses = attendanceBook.getStatuses();
        final Map<AttendanceStatus, Integer> statusCounts = AttendanceStatus.countStatus(statuses);
        final AttendanceCountsDto countsDto = new AttendanceCountsDto(statusCounts);
        Assertions.assertThat(attendanceBook.getAttendances().size()).isEqualTo(5);
        org.junit.jupiter.api.Assertions.assertAll(
                () -> Assertions.assertThat(countsDto.map().get(AttendanceStatus.ATTENDANCE)).isEqualTo(2),
                () -> Assertions.assertThat(countsDto.map().get(AttendanceStatus.TARDINESS)).isEqualTo(0),
                () -> Assertions.assertThat(countsDto.map().get(AttendanceStatus.ABSENCE)).isEqualTo(3)
        );
    }
}