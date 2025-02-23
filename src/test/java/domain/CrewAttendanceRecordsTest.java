package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CrewAttendanceRecordsTest {
    private static final LocalDate SYSTEM_DATE_FOR_TEST = LocalDate.of(2024, 12, 13);
    CrewAttendanceRecords crewAttendanceRecords = new CrewAttendanceRecords(new CsvParsingGenerator(),
            SYSTEM_DATE_FOR_TEST);

    @Test
    @DisplayName("출석 기록 존재 여부를 반환한다.")
    void hasRecordTest() {
        // given
        Crew crew = new Crew("쿠키");
        LocalDate date = LocalDate.of(2024, 12, 13);
        // when
        boolean hasRecord = crewAttendanceRecords.hasRecord(crew, date);
        // then
        assertThat(hasRecord).isTrue();
    }

    @Test
    @DisplayName("수정하려는 크루의 닉네임, 날짜, 시간을 입력 받아서 출석 기록을 갱신한다.")
    void updateAttendanceRecordTest() {
        // given
        Crew crew = new Crew("빙티");
        LocalDate date = LocalDate.of(2024, 12, 3);
        LocalTime time = LocalTime.of(9, 58);
        AttendanceRecord newAttendanceRecord = AttendanceRecord.of(date, time);
        // when
        AttendanceRecord oldAttendanceRecord = crewAttendanceRecords.updateAttendanceRecord(crew, newAttendanceRecord);
        // then
        assertThat(oldAttendanceRecord).isEqualTo(AttendanceRecord.of(date, LocalTime.of(10, 7)));
    }

    @Test
    @DisplayName("수정할 때 기록이 없는 닉네임을 입력하면 예외가 발생한다.")
    void updateAttendanceRecordExceptionTest() {
        // given
        Crew crew = new Crew("포비");
        LocalDate date = LocalDate.of(2024, 12, 3);
        LocalTime time = LocalTime.of(9, 58);
        AttendanceRecord newAttendanceRecord = AttendanceRecord.of(date, time);
        // when & then
        assertThatThrownBy(() -> crewAttendanceRecords.updateAttendanceRecord(crew, newAttendanceRecord))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 등록되지 않은 닉네임입니다.\n");
    }

    @Test
    @DisplayName("입력 받은 크루의 총 출석 횟수를 반환한다.")
    void getPresentCountTest() {
        // given
        Crew crew = new Crew("빙티");
        // when
        int presentCount = crewAttendanceRecords.getAttendanceCount(crew, Attendance.PRESENT);
        // then
        assertThat(presentCount).isEqualTo(3);
    }

    @Test
    @DisplayName("입력 받은 크루의 총 지각 횟수를 반환한다.")
    void getTardyCountTest() {
        // given
        Crew crew = new Crew("빙티");
        // when
        int tardyCount = crewAttendanceRecords.getAttendanceCount(crew, Attendance.TARDY);
        // then
        assertThat(tardyCount).isEqualTo(4);
    }

    @Test
    @DisplayName("입력 받은 크루의 총 결석 횟수를 반환한다.")
    void getAbsentCountTest() {
        // given
        Crew crew = new Crew("빙티");
        // when
        int absentCount = crewAttendanceRecords.getAttendanceCount(crew, Attendance.ABSENT);
        // then
        assertThat(absentCount).isEqualTo(3);
    }

    @Test
    @DisplayName("출석 횟수 기록을 확인할 때 기록이 없는 닉네임을 입력하면 예외가 발생한다.")
    void getAttendanceCountExceptionTest() {
        // given
        Crew crew = new Crew("포비");
        // when & then
        assertAll(
                () -> assertThatThrownBy(
                        () -> crewAttendanceRecords.getAttendanceCount(crew, Attendance.PRESENT)).isInstanceOf(
                        IllegalArgumentException.class).hasMessage("[ERROR] 등록되지 않은 닉네임입니다.\n"),
                () -> assertThatThrownBy(
                        () -> crewAttendanceRecords.getAttendanceCount(crew, Attendance.TARDY)).isInstanceOf(
                        IllegalArgumentException.class).hasMessage("[ERROR] 등록되지 않은 닉네임입니다.\n"),
                () -> assertThatThrownBy(
                        () -> crewAttendanceRecords.getAttendanceCount(crew, Attendance.ABSENT)).isInstanceOf(
                        IllegalArgumentException.class).hasMessage("[ERROR] 등록되지 않은 닉네임입니다.\n")
        );
    }

    @Test
    @DisplayName("닉네임과 등교 시간을 입력하면 출석할 수 있다.")
    void checkInTest() {
        // given
        Crew crew = new Crew("짱수");
        LocalTime time = LocalTime.of(10, 0);
        // when
        AttendanceRecord attendanceRecord = crewAttendanceRecords.checkIn(crew, time, SYSTEM_DATE_FOR_TEST);
        // then
        assertAll(
                () -> assertThat(attendanceRecord).isEqualTo(
                        AttendanceRecord.of(LocalDate.of(2024, 12, 13), LocalTime.of(10, 0))),
                () -> assertThat(crewAttendanceRecords.hasRecord(crew, LocalDate.of(2024, 12, 13))).isTrue()
        );
    }

    @Test
    @DisplayName("출석할 때 기록이 없는 닉네임을 입력하면 예외가 발생한다.")
    void checkInExceptionTest() {
        // given
        Crew crew = new Crew("포비");
        LocalTime time = LocalTime.of(10, 0);
        // when & then
        assertThatThrownBy(() -> crewAttendanceRecords.checkIn(crew, time, SYSTEM_DATE_FOR_TEST))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 등록되지 않은 닉네임입니다.\n");
    }

    @Test
    @DisplayName("이미 출석을 하였는데 다시 출석 확인을 하는 경우 예외를 발생시킨다.")
    void validatePresenceTest() {
        // given
        Crew crew = new Crew("쿠키");
        LocalTime time = LocalTime.of(10, 0);
        // when & then
        assertThatThrownBy(() -> crewAttendanceRecords.checkIn(crew, time, SYSTEM_DATE_FOR_TEST))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 이미 출석을 확인하였습니다. 필요한 경우 수정 기능을 이용해 주세요.\n");
    }

    @Test
    @DisplayName("입력 받은 크루의 출결 기록을 날짜순으로 정렬해서 반환한다.")
    void getSortedRecordsTest() {
        // given
        Crew crew = new Crew("빙티");
        // when
        List<AttendanceRecord> actualRecords = crewAttendanceRecords.getSortedRecords(crew);
        // then
        List<AttendanceRecord> expectedRecords = List.of(
                AttendanceRecord.of(LocalDate.of(2024, 12, 2), LocalTime.of(13, 0)),
                AttendanceRecord.of(LocalDate.of(2024, 12, 3), LocalTime.of(10, 7)),
                AttendanceRecord.of(LocalDate.of(2024, 12, 4), LocalTime.of(10, 2)),
                AttendanceRecord.of(LocalDate.of(2024, 12, 5), LocalTime.of(10, 6)),
                AttendanceRecord.of(LocalDate.of(2024, 12, 6), LocalTime.of(10, 1)),
                AttendanceRecord.asAbsent(LocalDate.of(2024, 12, 9)),
                AttendanceRecord.of(LocalDate.of(2024, 12, 10), LocalTime.of(10, 8)),
                AttendanceRecord.asAbsent(LocalDate.of(2024, 12, 11)),
                AttendanceRecord.asAbsent(LocalDate.of(2024, 12, 12)),
                AttendanceRecord.of(LocalDate.of(2024, 12, 13), LocalTime.of(10, 7)));
        assertThat(actualRecords).isEqualTo(expectedRecords);
    }

    @Test
    @DisplayName("제적 위험자 리스트를 반환한다.")
    void getWarnedCrewsTest() {
        // given & when
        List<Crew> actualCrews = crewAttendanceRecords.getWarnedCrews();
        // then
        List<Crew> expected = List.of(new Crew("빙티"), new Crew("이든"), new Crew("빙봉"), new Crew("쿠키"));
        assertThat(actualCrews).isEqualTo(expected);
    }
}
