import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RegisterCrewTest {
    @Test
    @DisplayName("제공된 파일로부터 출석 기록을 읽어온다.")
    void read_Existed_Attendance_Records_From_File() {
        List<String> existedRecords =
                CrewRegistration.readExistedRecords("src/main/resources/attendances.csv");

    }

    @Test
    @DisplayName("읽어온 출석 기록을 크루 이름, 시간 로그 데이터로 분리한다.")
    void parse_Existed_Attendance_Records_To_Name_And_Date() {
        String existedRecord = "빙봉,2024-12-13 10:07";
        List<String> parsedRecord = CrewRegistration.parseRecordToNameAndDate(existedRecord);
    }

    @Test
    @DisplayName("시간 로그 데이터를 날짜와 시간 데이터로 분리한다.")
    void parse_Date_To_Day_And_Time() {
        String date = "2024-12-13 10:07";
        List<String> parsedDate = CrewRegistration.parseDateToDayAndTime(date);
    }
}