package attendance.util;

import attendance.domain.Attendance;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayName("파일 데이터 불러오기")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class FileLoaderTest {

    @Test
    void 문자열_데이터를_Attendance_객체로_변환한다() {
        List<String> datas = List.of(
                "쿠키,2024-12-13 10:08",
                "빙봉,2024-12-13 10:07",
                "빙티,2024-12-13 10:07",
                "이든,2024-12-13 10:07"
        );

        List<Attendance> attendances = FileLoader.loadAll(datas);

        Assertions.assertThat(attendances)
                .hasSize(4)
                .hasOnlyElementsOfType(Attendance.class);
    }
}
