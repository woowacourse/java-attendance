package function;

import domain.Crew;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.CsvReader;
import utils.ParsingUtils;

public class RegisterCrewTest {
    @Test
    @DisplayName("제공된 파일로부터 출석 기록을 읽어온다.")
    void read_Existed_Attendance_Records_From_File() {
        List<String> existedRecords =
                CsvReader.readExistedRecords("src/main/resources/attendances.csv");

    }

    @Test
    @DisplayName("읽어온 출석 기록을 크루 이름, 시간 로그 데이터로 분리한다.")
    void parse_Existed_Attendance_Records_To_Name_And_Date() {
        String existedRecord = "빙봉,2024-12-13 10:07";
        List<String> parsedRecord = ParsingUtils.parseRecordToNameAndDate(existedRecord);
    }

    @Test
    @DisplayName("시간 로그 데이터를 날짜와 시간 데이터로 분리한다.")
    void parse_Date_To_Day_And_Time() {
        String date = "2024-12-13 10:07";
        List<String> parsedDate = ParsingUtils.parseDateToDayAndTime(date);
    }

    @Test
    @DisplayName("분리한 데이터를 바탕으로 사용자 등록을 진행한다.")
    void register_Crew() {
        String name = "빙봉";
        String date = "2024-12-13";
        String time = "10:07";

        Crew crew = new Crew(name, LocalDate.parse(date), LocalTime.parse(time));
    }
}