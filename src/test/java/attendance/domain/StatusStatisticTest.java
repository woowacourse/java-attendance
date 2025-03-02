package attendance.domain;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StatusStatisticTest {

    @Test
    @DisplayName("출석 상태 통계를 갱신한다.")
    void test_updateStatusStatistic() {
        var nickname = new Nickname("이든");
        var status = new StatusStatistics(nickname);
        Map<LocalDate, Attendance> attendances = createStandardAttendanceMap();
        status.update(attendances);

        assertAll(
            () -> assertThat(status.getCount(AttendanceStatus.ATTENDANCE)).isEqualTo(1),
            () -> assertThat(status.getCount(AttendanceStatus.LATE)).isEqualTo(1),
            () -> assertThat(status.getCount(AttendanceStatus.ABSENCE)).isEqualTo(1)
        );
    }

    @Test
    @DisplayName("출석 상태 통계에 대한 가중치를 반환한다.")
    void test_returnWeightOfStatusStatistics() {
        var nickname = new Nickname("이든");
        var status = new StatusStatistics(nickname);
        Map<LocalDate, Attendance> attendances = createStandardAttendanceMap();
        status.update(attendances);

        assertThat(status.getWeight()).isEqualTo(1);
    }

    @Test
    @DisplayName("출석 상태 통계는 제적 상태로 비교해 정렬할 수 있다.")
    void test_compareLevelOfStatusStatistics() {
        var nickname = new Nickname("이든");

        var noneSanctionStatus = createStatusWithAttendance(nickname, 0);
        var waringStatusStatics = createStatusWithAttendance(nickname, 2);
        var meetingStatusStatics = createStatusWithAttendance(nickname, 3);
        var disMissStatusStatics = createStatusWithAttendance(nickname, 6);

        assertAll(
            () -> assertThat(noneSanctionStatus.compareTo(waringStatusStatics)).isLessThan(0),
            () -> assertThat(waringStatusStatics.compareTo(meetingStatusStatics)).isLessThan(0),
            () -> assertThat(meetingStatusStatics.compareTo(disMissStatusStatics)).isLessThan(0)
        );
    }

    @Test
    @DisplayName("제적 상태가 같을 경우, 가중치로 비교해 정렬할 수 있다.")
    void test_compareWeightOfStatusStatistics() {
        var nickname = new Nickname("이든");

        Map<LocalDate, Attendance> attendances = createStandardAttendanceMap();
        var status = new StatusStatistics(nickname);
        status.update(attendances);

        var late = LocalDateTime.of(2024, 12, 17, 10, 10);
        attendances.put(late.toLocalDate(), new Attendance(late));
        var statusMoreWeight = new StatusStatistics(nickname);
        statusMoreWeight.update(attendances);

        assertThat(status.compareTo(statusMoreWeight)).isLessThan(0);
    }

    @Test
    @DisplayName("가중치가 같을 경우, 이름 비교해 정렬할 수 있다.")
    void test_compareNameOfStatusStatistics() {
        Map<LocalDate, Attendance> attendances = createStandardAttendanceMap();
        var status = new StatusStatistics(new Nickname("나"));
        status.update(attendances);

        var statusFastName = new StatusStatistics(new Nickname("가"));
        statusFastName.update(attendances);

        assertThat(status.compareTo(statusFastName)).isLessThan(0);
    }

    private StatusStatistics createStatusWithAttendance(Nickname nickname, int attendanceCount) {
        var status = new StatusStatistics(nickname);
        Map<LocalDate, Attendance> attendances = new HashMap<>();
        for (int i = 0; i < attendanceCount; i++) {
            LocalDateTime dateTime = LocalDateTime.of(2024, 12, 16 + i, 15, 0);
            attendances.put(dateTime.toLocalDate(), new Attendance(dateTime));
        }
        status.update(attendances);
        return status;
    }

    private Map<LocalDate, Attendance> createStandardAttendanceMap() {
        var attendance = LocalDateTime.of(2024, 12, 10, 10, 0);
        var late = LocalDateTime.of(2024, 12, 11, 10, 10);
        var absence = LocalDateTime.of(2024, 12, 13, 10, 35);

        Map<LocalDate, Attendance> attendances = new HashMap<>();
        attendances.put(attendance.toLocalDate(), new Attendance(attendance));
        attendances.put(late.toLocalDate(), new Attendance(late));
        attendances.put(absence.toLocalDate(), new Attendance(absence));
        return attendances;
    }
}
