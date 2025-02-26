package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import dto.AttendanceRecordDTO;
import dto.CheckAttendanceResponse;
import dto.GetAttendanceRecordsResponse;
import dto.ModifyAttendanceResponse;
import java.time.LocalDate;
import java.time.LocalTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.Parser;

public class CrewTest {

    private Crew crew;

    @BeforeEach
    void setUp() {
        crew = new Crew("pobi");
    }

    @Test
    @DisplayName("닉네임과 등교 시간을 입력하면 출석할 수 있다.")
    void crewTest() {
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();

        assertThat(crew.checkAttendance(date, time))
                .extracting(CheckAttendanceResponse::date, CheckAttendanceResponse::time)
                .containsExactly(Parser.parseDateInKorean(date), Parser.parseTimeToString(time));
    }

    @Test
    @DisplayName("이미 출석한 경우, 다시 출석할 수 없으며 수정 기능을 이용하도록 안내한다.")
    void crewTest2() {
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();

        crew.checkAttendance(date, time);

        assertThatThrownBy(() -> crew.checkAttendance(date, time))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorCode.ATTENDANCE_DATE_DUPLICATED.getMessage());
    }

    @Test
    @DisplayName("출석 확인을 수정하려면 닉네임, 수정하려는 날짜, 등교 시간을 입력하여 기록을 수정할 수 있다.")
    void crewTest3() {
        LocalDate date = LocalDate.now();
        LocalTime originalTime = LocalTime.now();
        LocalTime modifiedTime = LocalTime.now().minusHours(1);

        crew.checkAttendance(date, originalTime);

        assertThat(crew.modifyAttendance(date, modifiedTime))
                .extracting(ModifyAttendanceResponse::date,
                        ModifyAttendanceResponse::originalTime,
                        ModifyAttendanceResponse::modifiedTime)
                .containsExactly(Parser.parseDateInKorean(date), Parser.parseTimeToString(originalTime),
                        Parser.parseTimeToString(modifiedTime));
    }

    @Test
    @DisplayName("닉네임을 입력하면 전날까지의 크루 출석 기록을 확인할 수 있다.")
    void crewTest4() {
        LocalDate date = LocalDate.of(2024, 12, 3);
        LocalTime time = LocalTime.now();

        AttendanceRecordDTO expectedContainedDTO = new AttendanceRecordDTO(
                Parser.parseDateInKorean(date),
                Parser.parseTimeToString(time),
                AttendanceStatus.findMessageByAttendDateAndTime(date, time));

        crew.checkAttendance(date, time);
        GetAttendanceRecordsResponse attendanceRecords = crew.getAttendanceRecords();
        
        Assertions.assertThat(attendanceRecords.attendanceRecordDTOs())
                .contains(expectedContainedDTO);
    }
}
