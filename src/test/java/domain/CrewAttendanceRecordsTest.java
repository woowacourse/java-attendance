package domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class CrewAttendanceRecordsTest {
    @Test
    @DisplayName("객체 생성 시 출석 기록이 없는 날짜가 결석으로 기록되었는지 확인한다.")
    void instanceTest() {
        CrewAttendanceRecords crewAttendanceRecords = new CrewAttendanceRecords(new CsvParsingGenerator(), LocalDate.of(2024, 12, 13));
        Crew crew = new Crew("쿠키");
        LocalDate missingDate = LocalDate.of(2024, 12, 12);
        AttendanceRecord expectedRecord = AttendanceRecord.asAbsent(missingDate);

        assertThat(crewAttendanceRecords.getRecordAtDate(crew, missingDate)).isEqualTo(expectedRecord);
    }

    @Test
    @DisplayName("입력 받은 날짜에 존재하는 기록을 반환한다.")
    void getRecordAtDateTest() {
        CrewAttendanceRecords crewAttendanceRecords = new CrewAttendanceRecords(new CsvParsingGenerator(), LocalDate.of(2024, 12, 13));
        Crew crew = new Crew("쿠키");
        LocalDate date = LocalDate.of(2024, 12, 13);
        AttendanceRecord actualRecord = AttendanceRecord.parse("2024-12-13 10:08");
        AttendanceRecord expectedRecord = crewAttendanceRecords.getRecordAtDate(crew, date);

        assertThat(actualRecord).isEqualTo(expectedRecord);
    }

    @Test
    @DisplayName("수정하려는 크루의 닉네임, 날짜, 시간을 입력 받아서 출석 기록을 갱신한다.")
    void updateAttendanceRecordTest() {
        CrewAttendanceRecords crewAttendanceRecords = new CrewAttendanceRecords(new CsvParsingGenerator(), LocalDate.of(2024, 12, 13));
        Crew crew = new Crew("빙티");
        LocalDate date = LocalDate.of(2024, 12, 3);
        LocalTime time = LocalTime.of(9, 58);
        AttendanceRecord newAttendanceRecord = AttendanceRecord.of(date, time);
        AttendanceRecord oldAttendanceRecord = crewAttendanceRecords.getRecordAtDate(crew, date);

        crewAttendanceRecords.updateAttendanceRecord(crew, oldAttendanceRecord, newAttendanceRecord);

        assertThat(crewAttendanceRecords.getRecordAtDate(crew, date)).isEqualTo(newAttendanceRecord);
    }

    @Test
    @DisplayName("기록이 없는 닉네임의 출석 기록을 가져오려 하면 예외가 발생한다.")
    void getRecordAtDateExceptionTest() {
        CrewAttendanceRecords crewAttendanceRecords = new CrewAttendanceRecords(new CsvParsingGenerator(), LocalDate.of(2024, 12, 13));
        Crew crew = new Crew("포비");
        LocalDate date = LocalDate.of(2024, 12, 3);

        assertThatThrownBy(() -> crewAttendanceRecords.getRecordAtDate(crew, date))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 등록되지 않은 닉네임입니다.\n");
    }

    @Test
    @DisplayName("입력 받은 크루의 총 출석 횟수를 반환한다.")
    void getPresentCountTest() {
        CrewAttendanceRecords crewAttendanceRecords = new CrewAttendanceRecords(new CsvParsingGenerator(), LocalDate.of(2024, 12, 13));
        Crew crew = new Crew("빙티");
        assertThat(crewAttendanceRecords.getAttendanceCount(crew, Attendance.PRESENT)).isEqualTo(3);
    }

    @Test
    @DisplayName("입력 받은 크루의 총 지각 횟수를 반환한다.")
    void getTardyCountTest() {
        CrewAttendanceRecords crewAttendanceRecords = new CrewAttendanceRecords(new CsvParsingGenerator(), LocalDate.of(2024, 12, 13));
        Crew crew = new Crew("빙티");
        assertThat(crewAttendanceRecords.getAttendanceCount(crew, Attendance.TARDY)).isEqualTo(4);
    }

    @Test
    @DisplayName("입력 받은 크루의 총 결석 횟수를 반환한다.")
    void getAbsentCountTest() {
        CrewAttendanceRecords crewAttendanceRecords = new CrewAttendanceRecords(new CsvParsingGenerator(), LocalDate.of(2024, 12, 13));
        Crew crew = new Crew("빙티");
        assertThat(crewAttendanceRecords.getAttendanceCount(crew, Attendance.ABSENT)).isEqualTo(3);
    }

    @Test
    @DisplayName("출석 횟수 기록을 확인할 때 기록이 없는 닉네임을 입력하면 예외가 발생한다.")
    void getAttendanceCountExceptionTest() {
        CrewAttendanceRecords crewAttendanceRecords = new CrewAttendanceRecords(new CsvParsingGenerator(), LocalDate.of(2024, 12, 13));
        Crew crew = new Crew("포비");
        assertAll(
                () -> assertThatThrownBy(() -> crewAttendanceRecords.getAttendanceCount(crew, Attendance.PRESENT)).isInstanceOf(IllegalArgumentException.class).hasMessage("[ERROR] 등록되지 않은 닉네임입니다.\n"),
                () -> assertThatThrownBy(() -> crewAttendanceRecords.getAttendanceCount(crew, Attendance.TARDY)).isInstanceOf(IllegalArgumentException.class).hasMessage("[ERROR] 등록되지 않은 닉네임입니다.\n"),
                () -> assertThatThrownBy(() -> crewAttendanceRecords.getAttendanceCount(crew, Attendance.ABSENT)).isInstanceOf(IllegalArgumentException.class).hasMessage("[ERROR] 등록되지 않은 닉네임입니다.\n")
        );
    }

    @Test
    @DisplayName("닉네임과 등교 시간을 입력하면 출석할 수 있다.")
    void checkInTest() {
        CrewAttendanceRecords crewAttendanceRecords = new CrewAttendanceRecords(new CsvParsingGenerator(), LocalDate.of(2024, 12, 13));
        Crew crew = new Crew("짱수");
        LocalTime time = LocalTime.of(10, 0);
        AttendanceRecord attendanceRecord = crewAttendanceRecords.checkIn(crew, time, LocalDate.of(2024, 12, 13));

        assertAll(
                () -> assertThat(attendanceRecord).isEqualTo(AttendanceRecord.parse("2024-12-13 10:00")),
                () -> assertThat(crewAttendanceRecords.getRecordAtDate(crew, LocalDate.of(2024, 12, 13))).isEqualTo(attendanceRecord)
        );
    }

    @Test
    @DisplayName("출석할 때 기록이 없는 닉네임을 입력하면 예외가 발생한다.")
    void checkInExceptionTest() {
        CrewAttendanceRecords crewAttendanceRecords = new CrewAttendanceRecords(new CsvParsingGenerator(), LocalDate.of(2024, 12, 13));
        Crew crew = new Crew("포비");
        LocalTime time = LocalTime.of(10, 0);
        assertThatThrownBy(() -> crewAttendanceRecords.checkIn(crew, time, LocalDate.of(2024, 12, 13)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 등록되지 않은 닉네임입니다.\n");
    }

    @Test
    @DisplayName("이미 출석을 하였는데 다시 출석 확인을 하는 경우 예외를 발생시킨다.")
    void validatePresenceTest() {
        CrewAttendanceRecords crewAttendanceRecords = new CrewAttendanceRecords(new CsvParsingGenerator(), LocalDate.of(2024, 12, 13));
        Crew crew = new Crew("쿠키");
        LocalTime time = LocalTime.of(10, 0);
        assertThatThrownBy(() -> crewAttendanceRecords.checkIn(crew, time, LocalDate.of(2024, 12, 13)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 이미 출석을 확인하였습니다. 필요한 경우 수정 기능을 이용해 주세요.\n");
    }

    @Test
    @DisplayName("제적 위험자 리스트를 제적, 면담, 경고 대상 순으로 반환한다.")
    void getWarnedCrewsTest() {
        class TestCrewAttendanceRecordsGenerator implements CrewAttendanceRecordsGenerator {
            @Override
            public Map<Crew, AttendanceRecords> generate(LocalDate currentDate) {
                Crew crew1 = new Crew("포비");
                AttendanceRecords crew1Records = new AttendanceRecords();
                crew1Records.addRecord(AttendanceRecord.parse("2024-12-02 13:00"));
                crew1Records.addRecord(AttendanceRecord.parse("2024-12-03 10:00"));
                crew1Records.addRecord(AttendanceRecord.parse("2024-12-04 14:00"));
                crew1Records.addRecord(AttendanceRecord.parse("2024-12-05 14:00"));
                crew1Records.addRecord(AttendanceRecord.parse("2024-12-06 10:00"));
                crew1Records.addRecord(AttendanceRecord.parse("2024-12-09 13:10"));

                Crew crew2 = new Crew("네오");
                AttendanceRecords crew2Records = new AttendanceRecords();
                crew2Records.addRecord(AttendanceRecord.parse("2024-12-02 14:00"));
                crew2Records.addRecord(AttendanceRecord.parse("2024-12-03 14:00"));
                crew2Records.addRecord(AttendanceRecord.parse("2024-12-04 14:00"));
                crew2Records.addRecord(AttendanceRecord.parse("2024-12-05 14:00"));
                crew2Records.addRecord(AttendanceRecord.parse("2024-12-06 14:00"));
                crew2Records.addRecord(AttendanceRecord.parse("2024-12-09 14:00"));

                Crew crew3 = new Crew("솔라");
                AttendanceRecords crew3Records = new AttendanceRecords();
                crew3Records.addRecord(AttendanceRecord.parse("2024-12-02 14:00"));
                crew3Records.addRecord(AttendanceRecord.parse("2024-12-03 10:10"));
                crew3Records.addRecord(AttendanceRecord.parse("2024-12-04 14:00"));
                crew3Records.addRecord(AttendanceRecord.parse("2024-12-05 14:00"));
                crew3Records.addRecord(AttendanceRecord.parse("2024-12-06 14:00"));
                crew3Records.addRecord(AttendanceRecord.parse("2024-12-09 13:00"));

                return Map.of(crew1, crew1Records, crew2, crew2Records, crew3, crew3Records);
            }
        }

        CrewAttendanceRecords crewAttendanceRecords = new CrewAttendanceRecords(new TestCrewAttendanceRecordsGenerator(), LocalDate.of(2024, 12, 14));
        List<Crew> actualCrews = crewAttendanceRecords.getWarnedCrews();
        List<Crew> expectedCrews = List.of(new Crew("네오"), new Crew("솔라"), new Crew("포비"));

        assertThat(actualCrews).isEqualTo(expectedCrews);
    }
}
