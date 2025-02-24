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
}