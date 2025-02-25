import domain.Attendance;
import domain.AttendanceSheet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import policy.FileReaderPolicy;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class FileReaderPolicyTest {
    private static final String FILE_PATH = "src/main/resources/attendances.csv";

    FileReaderPolicy fileReaderPolicy;

    @BeforeEach
    void setUp() {
        fileReaderPolicy = new FileReaderPolicy(FILE_PATH);
    }

    @Test
    @DisplayName("지정된 위치의 파일이 아니면 예외가 발생한다")
    public void validateFileReaderPolicyTest() {
        assertThatThrownBy(() -> new FileReaderPolicy("attendances.csv"))
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("파일을 읽어들일 수 있다")
    public void readLinesTest() {
        assertThat(fileReaderPolicy.readLines())
                .isInstanceOf(List.class);
    }

    @Test
    @DisplayName("쉼표에 따라 나눈 문자열의 개수가 차이가 나면 예외가 발생한다")
    public void validateFileFormatTest() {
        //given
        String[] splitLine = {"링크"};

        //when-then
        assertThatThrownBy(() -> fileReaderPolicy.validateSplitLineFormat(splitLine))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("날짜와 시간 형식이 맞지 않으면 예외가 발생한다")
    public void validateAttendanceDateTimeFormatTest() {
        //given
        String attendanceDateTime = "2024:12:13 09:11";

        //when-then
        assertThatThrownBy(() -> fileReaderPolicy.parseAttendanceDateTime(attendanceDateTime))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("파일 한 줄을 읽어들여 출석부에 기록할 수 있다")
    public void createAttendanceTest() {
        //given
        String attendanceInfo = "쿠키,2024-12-13 10:08";

        //when-then
        assertThat(fileReaderPolicy.createAttendance(attendanceInfo))
                .isInstanceOf(Attendance.class);
    }

    @Test
    @DisplayName("파일 전체를 읽어들여 출석부에 기록할 수 있다")
    public void createAttendancesTest() {
        //given
        List<String> attendancesInfo = List.of(
                "쿠키,2024-12-13 10:08",
                "빙봉,2024-12-13 10:07"
                );

        //when-then
        assertThat(fileReaderPolicy.createAttendances(attendancesInfo))
                .isInstanceOf(AttendanceSheet.class);
    }

}
