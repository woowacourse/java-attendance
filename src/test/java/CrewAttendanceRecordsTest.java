import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

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
        AttendanceRecord newAttendanceRecord = new AttendanceRecord(date, time);
        AttendanceRecord oldAttendanceRecord = crewAttendanceRecords.updateAttendanceRecord(crew, newAttendanceRecord);

        assertThat(oldAttendanceRecord).isEqualTo(new AttendanceRecord("2024-12-03 10:07"));
    }

    @Test
    @DisplayName("수정할 때 기록이 없는 닉네임을 입력하면 예외가 발생한다.")
    void updateAttendanceRecordExceptionTest() {
        CrewAttendanceRecords crewAttendanceRecords = new CrewAttendanceRecords("/attendances.csv", () -> LocalDate.of(2024, 12, 13));
        Crew crew = new Crew("포비");
        LocalDate date = LocalDate.of(2024, 12, 3);
        LocalTime time = LocalTime.of(9, 58);
        AttendanceRecord newAttendanceRecord = new AttendanceRecord(date, time);

        assertThatThrownBy(() -> crewAttendanceRecords.updateAttendanceRecord(crew, newAttendanceRecord))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 등록되지 않은 닉네임입니다.");
    }

    @Test
    @DisplayName("입력 받은 크루의 총 출석 횟수를 반환한다.")
    void getPresentCountTest() {
        CrewAttendanceRecords crewAttendanceRecords = new CrewAttendanceRecords("/attendances.csv", () -> LocalDate.of(2024, 12, 13));
        Crew crew = new Crew("빙티");
        assertThat(crewAttendanceRecords.getPresentCount(crew)).isEqualTo(3);
    }

    @Test
    @DisplayName("입력 받은 크루의 총 지각 횟수를 반환한다.")
    void getTardyCountTest() {
        CrewAttendanceRecords crewAttendanceRecords = new CrewAttendanceRecords("/attendances.csv", () -> LocalDate.of(2024, 12, 13));
        Crew crew = new Crew("빙티");
        assertThat(crewAttendanceRecords.getTardyCount(crew)).isEqualTo(4);
    }

    @Test
    @DisplayName("입력 받은 크루의 총 결석 횟수를 반환한다.")
    void getAbsentCountTest() {
        CrewAttendanceRecords crewAttendanceRecords = new CrewAttendanceRecords("/attendances.csv", () -> LocalDate.of(2024, 12, 13));
        Crew crew = new Crew("빙티");
        assertThat(crewAttendanceRecords.getAbsentCount(crew)).isEqualTo(3);
    }

    @Test
    @DisplayName("출석 횟수 기록을 확인할 때 기록이 없는 닉네임을 입력하면 예외가 발생한다.")
    void getAttendanceCountExceptionTest() {
        CrewAttendanceRecords crewAttendanceRecords = new CrewAttendanceRecords("/attendances.csv", () -> LocalDate.of(2024, 12, 13));
        Crew crew = new Crew("포비");
        assertAll(
                () -> assertThatThrownBy(() -> crewAttendanceRecords.getPresentCount(crew)).isInstanceOf(IllegalArgumentException.class).hasMessage("[ERROR] 등록되지 않은 닉네임입니다."),
                () -> assertThatThrownBy(() -> crewAttendanceRecords.getTardyCount(crew)).isInstanceOf(IllegalArgumentException.class).hasMessage("[ERROR] 등록되지 않은 닉네임입니다."),
                () -> assertThatThrownBy(() -> crewAttendanceRecords.getAbsentCount(crew)).isInstanceOf(IllegalArgumentException.class).hasMessage("[ERROR] 등록되지 않은 닉네임입니다.")
        );
    }
}
