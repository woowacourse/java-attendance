import domain.Attend;
import domain.AttendReader;
import domain.AttendanceBook;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendReaderTest {

    @Test
    @DisplayName("csv 파일을 기반으로 한 AttendanceBook 생성 테스트")
    void loadAttendanceBookTest() {
        //given
        String filePath = "attendances.csv";
        AttendReader attendReader = new AttendReader(filePath);
        String name = "빙티";
        AttendanceBook actual = new AttendanceBook();
        List<Attend> attends = List.of(Attend.of(LocalDate.of(2024, 12, 2), LocalTime.of(10, 5)),
                Attend.of(LocalDate.of(2024, 12, 3), LocalTime.of(8, 0)));
        actual.registerName(name);
        for (Attend attend : attends) {
            actual.attend(name, attend);
        }

        //when
        AttendanceBook expected = attendReader.loadAttendanceBook();

        //then
        List<Attend> expectedAttends = expected.getAttends(name);
        List<Attend> actualAttends = actual.getAttends(name);
        Assertions.assertThat(expectedAttends).containsExactlyElementsOf(actualAttends);
    }

    @Test
    @DisplayName("잘못된 csv 파일을 읽으면 예외 발생")
    void throwExceptionWhenReadWrongCsv() {
        String filePath = "attendances_format_fail.csv";
        AttendReader attendReader = new AttendReader(filePath);

        //when & then
        Assertions.assertThatThrownBy(attendReader::loadAttendanceBook).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("파일 행 구조가 잘못되었습니다.");
    }
}
