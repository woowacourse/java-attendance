import static org.assertj.core.api.Assertions.assertThat;

import domain.AttendCount;
import domain.AttendReader;
import domain.AttendanceBook;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class AttendReaderTest {

    @Test
    @DisplayName("잘못된 경로의 파일을 읽으면 예외를 던진다")
    void throwExceptionWhenWrongPath() {
        //given
        String wrongPath = "wrong_path.csv";
        AttendReader attendReader = new AttendReader(wrongPath);

        //when & then
        Assertions.assertThatThrownBy(attendReader::loadAttendanceBook);
    }

    @Test
    @DisplayName("잘못된 형식의 이름일 경우 예외를 던진다")
    void throwExceptionWhenWrongNameFormat() {
        //given
        String path = "wrong_name_format.csv";
        AttendReader attendReader = new AttendReader(path);

        //when & then
        Assertions.assertThatThrownBy(attendReader::loadAttendanceBook);
    }

    @ParameterizedTest
    @ValueSource(strings = {"empty_date_time.csv",
            "wrong_date_time_format.csv",
            "wrong_date_format.csv",
            "wrong_time_format.csv"})
    @DisplayName("잘못된 형식의 날짜일 경우 예외를 던진다")
    void throwExceptionWhenWrongDateTimeFormat(String path) {
        //given
        AttendReader attendReader = new AttendReader(path);

        //when & then
        Assertions.assertThatThrownBy(attendReader::loadAttendanceBook);
    }

    @Test
    @DisplayName("csv 파일 데이터를 기반으로 출석 기록을 저장한다")
    void loadAttendByCsv() {
        //given
        String path = "attendances.csv";
        AttendReader attendReader = new AttendReader(path);

        //when
        AttendanceBook attendanceBook = attendReader.loadAttendanceBook();

        //then
        List<String> names = List.of("빙티", "가나");
        List<AttendCount> attendCounts = names.stream().map(attendanceBook::countAttend).toList();
        List<AttendCount> expectedCounts = List.of(
                new AttendCount(3, 0, 6),
                new AttendCount(1, 1, 7));
        assertThat(attendCounts).containsExactlyElementsOf(expectedCounts);
    }
}
