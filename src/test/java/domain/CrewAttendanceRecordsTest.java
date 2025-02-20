package domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class CrewAttendanceRecordsTest {
    @Test
    @DisplayName("출석 기록 존재 여부를 반환한다.")
    void hasRecordTest() {
        CrewAttendanceRecords crewAttendanceRecords = new CrewAttendanceRecords("/attendances.csv", () -> LocalDate.of(2024, 12, 13));
        Crew crew = new Crew("쿠키");
        LocalDate date = LocalDate.of(2024, 12, 13);
        boolean hasRecord = crewAttendanceRecords.hasRecord(crew, date);

        assertThat(hasRecord).isTrue();
    }

    @Test
    @DisplayName("수정하려는 크루의 닉네임, 날짜, 시간을 입력 받아서 출석 기록을 갱신한다.")
    void updateAttendanceRecordTest() {
        CrewAttendanceRecords crewAttendanceRecords = new CrewAttendanceRecords("/attendances.csv", () -> LocalDate.of(2024, 12, 13));
        Crew crew = new Crew("빙티");
        LocalDate date = LocalDate.of(2024, 12, 3);
        LocalTime time = LocalTime.of(9, 58);
        AttendanceRecord newAttendanceRecord = AttendanceRecord.of(date, time);
        AttendanceRecord oldAttendanceRecord = crewAttendanceRecords.updateAttendanceRecord(crew, newAttendanceRecord);

        assertThat(oldAttendanceRecord).isEqualTo(AttendanceRecord.parse("2024-12-03 10:07"));
    }

    @Test
    @DisplayName("수정할 때 기록이 없는 닉네임을 입력하면 예외가 발생한다.")
    void updateAttendanceRecordExceptionTest() {
        CrewAttendanceRecords crewAttendanceRecords = new CrewAttendanceRecords("/attendances.csv", () -> LocalDate.of(2024, 12, 13));
        Crew crew = new Crew("포비");
        LocalDate date = LocalDate.of(2024, 12, 3);
        LocalTime time = LocalTime.of(9, 58);
        AttendanceRecord newAttendanceRecord = AttendanceRecord.of(date, time);

        assertThatThrownBy(() -> crewAttendanceRecords.updateAttendanceRecord(crew, newAttendanceRecord))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 등록되지 않은 닉네임입니다.");
    }

    @Test
    @DisplayName("입력 받은 크루의 총 출석 횟수를 반환한다.")
    void getPresentCountTest() {
        CrewAttendanceRecords crewAttendanceRecords = new CrewAttendanceRecords("/attendances.csv", () -> LocalDate.of(2024, 12, 13));
        Crew crew = new Crew("빙티");
        assertThat(crewAttendanceRecords.getAttendanceCount(crew, Attendance.PRESENT)).isEqualTo(3);
    }

    @Test
    @DisplayName("입력 받은 크루의 총 지각 횟수를 반환한다.")
    void getTardyCountTest() {
        CrewAttendanceRecords crewAttendanceRecords = new CrewAttendanceRecords("/attendances.csv", () -> LocalDate.of(2024, 12, 13));
        Crew crew = new Crew("빙티");
        assertThat(crewAttendanceRecords.getAttendanceCount(crew, Attendance.TARDY)).isEqualTo(4);
    }

    @Test
    @DisplayName("입력 받은 크루의 총 결석 횟수를 반환한다.")
    void getAbsentCountTest() {
        CrewAttendanceRecords crewAttendanceRecords = new CrewAttendanceRecords("/attendances.csv", () -> LocalDate.of(2024, 12, 13));
        Crew crew = new Crew("빙티");
        assertThat(crewAttendanceRecords.getAttendanceCount(crew, Attendance.ABSENT)).isEqualTo(3);
    }

    @Test
    @DisplayName("출석 횟수 기록을 확인할 때 기록이 없는 닉네임을 입력하면 예외가 발생한다.")
    void getAttendanceCountExceptionTest() {
        CrewAttendanceRecords crewAttendanceRecords = new CrewAttendanceRecords("/attendances.csv", () -> LocalDate.of(2024, 12, 13));
        Crew crew = new Crew("포비");
        assertAll(
                () -> assertThatThrownBy(() -> crewAttendanceRecords.getAttendanceCount(crew, Attendance.PRESENT)).isInstanceOf(IllegalArgumentException.class).hasMessage("[ERROR] 등록되지 않은 닉네임입니다."),
                () -> assertThatThrownBy(() -> crewAttendanceRecords.getAttendanceCount(crew, Attendance.TARDY)).isInstanceOf(IllegalArgumentException.class).hasMessage("[ERROR] 등록되지 않은 닉네임입니다."),
                () -> assertThatThrownBy(() -> crewAttendanceRecords.getAttendanceCount(crew, Attendance.ABSENT)).isInstanceOf(IllegalArgumentException.class).hasMessage("[ERROR] 등록되지 않은 닉네임입니다.")
        );
    }

    @Test
    @DisplayName("닉네임과 등교 시간을 입력하면 출석할 수 있다.")
    void checkInTest() {
        CrewAttendanceRecords crewAttendanceRecords = new CrewAttendanceRecords("/attendances.csv", () -> LocalDate.of(2024, 12, 13));
        Crew crew = new Crew("짱수");
        LocalTime time = LocalTime.of(10, 0);
        AttendanceRecord attendanceRecord = crewAttendanceRecords.checkIn(crew, time, () -> LocalDate.of(2024, 12, 13));

        assertAll(
                () -> assertThat(attendanceRecord).isEqualTo(AttendanceRecord.parse("2024-12-13 10:00")),
                () -> assertThat(crewAttendanceRecords.hasRecord(crew, LocalDate.of(2024, 12, 13))).isTrue()
        );
    }

    @Test
    @DisplayName("출석할 때 기록이 없는 닉네임을 입력하면 예외가 발생한다.")
    void checkInExceptionTest() {
        CrewAttendanceRecords crewAttendanceRecords = new CrewAttendanceRecords("/attendances.csv", () -> LocalDate.of(2024, 12, 13));
        Crew crew = new Crew("포비");
        LocalTime time = LocalTime.of(10, 0);
        assertThatThrownBy(() -> crewAttendanceRecords.checkIn(crew, time, () -> LocalDate.of(2024, 12, 13)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 등록되지 않은 닉네임입니다.");
    }

    @Test
    @DisplayName("이미 출석을 하였는데 다시 출석 확인을 하는 경우 예외를 발생시킨다.")
    void validatePresenceTest() {
        CrewAttendanceRecords crewAttendanceRecords = new CrewAttendanceRecords("/attendances.csv", () -> LocalDate.of(2024, 12, 13));
        Crew crew = new Crew("쿠키");
        LocalTime time = LocalTime.of(10, 0);
        assertThatThrownBy(() -> crewAttendanceRecords.checkIn(crew, time, () -> LocalDate.of(2024, 12, 13)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 이미 출석을 확인하였습니다. 필요한 경우 수정 기능을 이용해 주세요.");
    }

    @Test
    @DisplayName("입력 받은 크루의 출결 기록을 날짜순으로 정렬해서 반환한다.")
    void getSortedRecordsTest() {
        CrewAttendanceRecords crewAttendanceRecords = new CrewAttendanceRecords("/attendances.csv", () -> LocalDate.of(2024, 12, 13));
        Crew crew = new Crew("빙티");
        List<AttendanceRecord> actualRecords = crewAttendanceRecords.getSortedRecords(crew);
        List<AttendanceRecord> expectedRecords = List.of(
                AttendanceRecord.parse("2024-12-02 13:00"),
                AttendanceRecord.parse("2024-12-03 10:07"),
                AttendanceRecord.parse("2024-12-04 10:02"),
                AttendanceRecord.parse("2024-12-05 10:06"),
                AttendanceRecord.parse("2024-12-06 10:01"),
                AttendanceRecord.asAbsent(LocalDate.of(2024, 12, 9)),
                AttendanceRecord.parse("2024-12-10 10:08"),
                AttendanceRecord.asAbsent(LocalDate.of(2024, 12, 11)),
                AttendanceRecord.asAbsent(LocalDate.of(2024, 12, 12)),
                AttendanceRecord.parse("2024-12-13 10:07"));

        assertThat(actualRecords).isEqualTo(expectedRecords);
    }

    @Test
    @DisplayName("제적 위험자 리스트를 반환한다.")
    void getWarnedCrewsTest() {
        CrewAttendanceRecords crewAttendanceRecords = new CrewAttendanceRecords("/attendances.csv", () -> LocalDate.of(2024, 12, 13));
        List<Crew> actualCrews = crewAttendanceRecords.getWarnedCrews();
        List<Crew> expected = List.of(new Crew("빙티"), new Crew("이든"), new Crew("빙봉"), new Crew("쿠키"));

        assertThat(actualCrews).isEqualTo(expected);
    }
}
