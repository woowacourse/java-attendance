package domain;

import fixture.CrewRecordsFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class CrewRecordsTest {
    @DisplayName("입력 받은 닉네임과 LocalDateTime으로 새 출석 기록을 저장할 수 있다.")
    @Test
    void addRecordTest() {
        // given
        CrewRecords crewRecords = CrewRecordsFixture.createEmptyCrewRecords("브리");

        // when
        Crew crew = new Crew("브리");
        AttendanceRecord record = new AttendanceRecord(LocalDateTime.parse("2024-12-03T13:00"));

        // then
        assertDoesNotThrow(() -> crewRecords.addRecord(crew, record));
    }

    @DisplayName("존재하지 않는 크루의 출석 기록을 등록하려고 할 경우 예외가 발생한다.")
    @Test
    void addRecordCrewExceptionTest() {
        // given
        CrewRecords crewRecords = CrewRecordsFixture.createEmptyCrewRecords("브리");

        // when
        Crew crew = new Crew("솔라");
        AttendanceRecord record = new AttendanceRecord(LocalDateTime.parse("2024-12-03T13:00"));

        // then
        assertThatThrownBy(() -> crewRecords.addRecord(crew, record)).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("이미 기록이 있는 날짜에 새 출석 기록을 등록하려고 할 경우 예외가 발생한다.")
    @Test
    void addRecordExceptionTest() {
        // given
        CrewRecords crewRecords = CrewRecordsFixture.createSingleCrewRecord("저스틴", "2024-12-02T13:35");

        // when
        Crew crew = new Crew("저스틴");
        AttendanceRecord record = new AttendanceRecord(LocalDateTime.parse("2024-12-02T13:00"));

        // then
        assertThatThrownBy(() -> crewRecords.addRecord(crew, record)).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("입력 받은 날짜의 출석 시간을 수정할 수 있다.")
    @Test
    void updateRecordTest() {
        // given
        CrewRecords crewRecords = CrewRecordsFixture.createSingleCrewRecord("저스틴", "2024-12-02T13:35");
        Crew crew = new Crew("저스틴");
        AttendanceRecord oldRecord = new AttendanceRecord(LocalDateTime.parse("2024-12-02T13:35"));
        LocalDate oldDate = LocalDate.of(2024, 12, 2);
        LocalTime newTime = LocalTime.of(13, 30);

        // when
        crewRecords.updateRecord(crew, oldDate, newTime);
        AttendanceRecord newRecord = crewRecords.getRecordOnDate(crew, oldDate);

        // then
        assertAll(
                () -> assertThat(oldRecord.getAttendanceStatus()).isEqualTo(AttendanceStatus.ABSENT),
                () -> assertThat(newRecord.getAttendanceStatus()).isEqualTo(AttendanceStatus.TARDY)
        );
    }

    @DisplayName("존재하지 않는 크루의 출석 기록을 수정하려고 할 경우 예외가 발생한다.")
    @Test
    void updateRecordExceptionTest() {
        // given
        CrewRecords crewRecords = CrewRecordsFixture.createSingleCrewRecord("저스틴", "2024-12-02T13:35");

        // when
        Crew crew = new Crew("브리");
        LocalDate oldDate = LocalDate.of(2024, 12, 2);
        LocalTime newTime = LocalTime.of(13, 30);

        // then
        assertThatThrownBy(() -> crewRecords.updateRecord(crew, oldDate, newTime)).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("입력 받은 날짜에 해당하는 출석 기록 객체를 반환한다.")
    @Test
    void getRecordOnDateTest() {
        // given
        CrewRecords crewRecords = CrewRecordsFixture.createSingleCrewRecord("솔라", "2024-12-02T13:00");
        Crew crew = new Crew("솔라");
        LocalDateTime dateTime = LocalDateTime.parse("2024-12-02T13:00");

        // when
        AttendanceRecord expectedValue = new AttendanceRecord(dateTime);
        AttendanceRecord actualValue = crewRecords.getRecordOnDate(crew, dateTime.toLocalDate());

        // then
        assertThat(actualValue).isEqualTo(expectedValue);
    }

    @DisplayName("입력한 닉네임에 대한 출석 기록이 없을 경우 예외를 발생시킨다.")
    @Test
    void getCrewRecordsExceptionTest() {
        // given
        CrewRecords crewRecords = CrewRecordsFixture.createSingleCrewRecord("솔라", "2024-12-02T13:00");
        LocalDate date = LocalDate.of(2024, 12, 2);

        // when
        Crew excludedCrew = new Crew("네오");

        // then
        assertThatThrownBy(() -> crewRecords.getRecordOnDate(excludedCrew, date)).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("입력 받은 크루의 전체 출석 기록을 반환한다.")
    @Test
    void getAttendanceRecordsOfTest() {
        // given
        CrewRecords crewRecords = CrewRecordsFixture.createSingleCrewRecord("솔라", "2024-12-02T13:00", "2024-12-03T10:00", "2024-12-04T10:10");
        Crew crew = new Crew("솔라");

        // when
        AttendanceRecords attendanceRecords = crewRecords.getAttendanceRecordsOf(crew);

        // then
        assertAll(
                () -> assertThat(attendanceRecords.getRecordOnDate(LocalDate.of(2024, 12, 2)))
                        .isEqualTo(new AttendanceRecord(LocalDateTime.parse("2024-12-02T13:00"))),
                () -> assertThat(attendanceRecords.getRecordOnDate(LocalDate.of(2024, 12, 3)))
                        .isEqualTo(new AttendanceRecord(LocalDateTime.parse("2024-12-03T10:00"))),
                () -> assertThat(attendanceRecords.getRecordOnDate(LocalDate.of(2024, 12, 4)))
                        .isEqualTo(new AttendanceRecord(LocalDateTime.parse("2024-12-04T10:10")))
        );
    }

    @DisplayName("입력 받은 크루의 제적 상태를 반환한다.")
    @ParameterizedTest
    @MethodSource("warningStatusTestArgs")
    void getWarningStatusTest(WarningStatus expectedValue, String[] dateTimes) {
        // given
        CrewRecords crewRecords = CrewRecordsFixture.createSingleCrewRecord("네오", dateTimes);
        Crew crew = new Crew("네오");

        // when
        WarningStatus actualValue = crewRecords.getWarningStatus(crew);

        // then
        assertThat(actualValue).isEqualTo(expectedValue);
    }

    @DisplayName("제적 대상자인 크루들을 제적 위험 순서대로 반환한다.")
    @Test
    void getWarnedCrewsTest() {
        // given
        CrewRecords crewRecords = CrewRecordsFixture.createEmptyCrewRecords("네오", "저스틴", "솔라", "브리");
        Crew neo = new Crew("네오");
        Crew justin = new Crew("저스틴");

        // when
        crewRecords.addRecord(justin, new AttendanceRecord(LocalDate.of(2024, 12, 2)));
        crewRecords.addRecord(justin, new AttendanceRecord(LocalDate.of(2024, 12, 3)));
        crewRecords.addRecord(justin, new AttendanceRecord(LocalDate.of(2024, 12, 4)));
        crewRecords.addRecord(neo, new AttendanceRecord(LocalDate.of(2024, 12, 2)));
        crewRecords.addRecord(neo, new AttendanceRecord(LocalDate.of(2024, 12, 3)));
        List<WarnedCrew> actualValue = crewRecords.getWarnedCrews();

        // then
        assertThat(actualValue.stream().map(WarnedCrew::name)).containsExactly("저스틴", "네오");
    }

    @DisplayName("입력 받은 크루의 지각 횟수를 반환한다.")
    @Test
    void getTardyCountTest() {
        // given
        CrewRecords crewRecords = CrewRecordsFixture.createSingleCrewRecord("솔라", "2024-12-02T13:00", "2024-12-03T10:20", "2024-12-04T10:10");
        Crew crew = new Crew("솔라");

        // when
        int expectedValue = 2;
        int actualValue = crewRecords.getTardyCount(crew);

        // then
        assertThat(actualValue).isEqualTo(expectedValue);
    }

    @DisplayName("입력 받은 크루의 결석 횟수를 반환한다.")
    @Test
    void getAbsentCountTest() {
        // given
        CrewRecords crewRecords = CrewRecordsFixture.createSingleCrewRecord("네오", "2024-12-02T13:40", "2024-12-03T10:31", "2024-12-04T11:00");
        Crew crew = new Crew("네오");

        // when
        int expectedValue = 3;
        int actualValue = crewRecords.getAbsentCount(crew);

        // then
        assertThat(actualValue).isEqualTo(expectedValue);
    }

    static Stream<Arguments> warningStatusTestArgs() {
        return Stream.of(
                Arguments.of(WarningStatus.WARN, new String[]{"2024-12-02T14:00",
                        "2024-12-03T10:10",
                        "2024-12-04T10:10",
                        "2024-12-05T10:10"}),
                Arguments.of(WarningStatus.COUNSEL, new String[]{"2024-12-02T14:00",
                        "2024-12-03T14:00",
                        "2024-12-04T14:00"}),
                Arguments.of(WarningStatus.EXPEL, new String[]{"2024-12-02T14:00",
                        "2024-12-03T14:00",
                        "2024-12-04T14:00",
                        "2024-12-05T14:00",
                        "2024-12-06T14:00",
                        "2024-12-09T14:00",})
        );
    }
}
