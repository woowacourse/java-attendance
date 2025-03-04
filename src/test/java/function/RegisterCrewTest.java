package function;

import static org.assertj.core.api.Assertions.assertThat;

import domain.AttendanceBook;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.CrewRegistrationService;
import utils.CsvReader;
import utils.ParsingUtils;

public class RegisterCrewTest {
    @Test
    @DisplayName("제공된 파일로부터 출석 기록을 읽어온다.")
    void Read_Existed_Attendance_Records_From_File() {
        List<String> existedRecords =
                CsvReader.readExistedRecords("src/test/java/resources/test.csv");

        assertThat(existedRecords.getFirst()).isEqualTo("nickname,datetime");
        assertThat(existedRecords.get(1)).isEqualTo("쿠키,2024-12-13 10:08");
    }

    @Test
    @DisplayName("읽어온 출석 기록을 크루 이름, 시간 로그 데이터로 분리한다.")
    void Parse_Existed_Attendance_Records_To_Name_And_TimeLog() {
        String existedRecord = "빙봉,2024-12-13 10:07";
        List<String> parsedRecord = ParsingUtils.parseRecordToNameAndDate(existedRecord);

        assertThat(parsedRecord.getFirst()).isEqualTo("빙봉");
        assertThat(parsedRecord.get(1)).isEqualTo("2024-12-13 10:07");
    }

    @Test
    @DisplayName("시간 로그 데이터를 날짜와 시간 데이터로 분리한다.")
    void Parse_TimeLog_To_Date_And_Time() {
        String date = "2024-12-13 10:07";
        List<String> parsedDate = ParsingUtils.parseTimeLogToDateAndTime(date);

        assertThat(parsedDate.getFirst()).isEqualTo("2024-12-13");
        assertThat(parsedDate.get(1)).isEqualTo("10:07");
    }

    @Test
    @DisplayName("분리한 데이터를 바탕으로 크루 등록을 진행한다.")
    void Register_Crew() {
        String name = "빙봉";
        String date = "2024-12-13";
        String time = "10:07";

        AttendanceBook attendanceBook = new AttendanceBook();
        attendanceBook.registerCrew(name, LocalDate.parse(date), LocalTime.parse(time));

        assertThat(attendanceBook.checkCrewExisted(name)).isEqualTo(true);
    }

    @Test
    @DisplayName("csv 파일을 읽어서 크루 등록을 진행한다.")
    void Register_Crews() {
        CrewRegistrationService crewRegistrationService = new CrewRegistrationService();

        AttendanceBook attendanceBook = crewRegistrationService.registerCrews("src/test/java/resources/test.csv");

        assertThat(attendanceBook.checkCrewExisted("쿠키")).isEqualTo(true);
    }
}