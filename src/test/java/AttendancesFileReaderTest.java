import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.Test;

class AttendancesFileReaderTest {

    @Test
    void 전체_파일_읽기() {
        AttendancesFileReader attendancesFileReader = new AttendancesFileReader();
        assertThat(attendancesFileReader.read()).isEqualTo("쿠키,2024-12-13 10:08\n"
                + "빙봉,2024-12-13 10:07\n"
                + "빙티,2024-12-13 10:07\n"
                + "이든,2024-12-13 10:07\n");
    }

    @Test
    void 입력_받은_파일_내용을_가공한다() {
        AttendancesFileParser attendancesFileParser = new AttendancesFileParser();
        Crews crews = attendancesFileParser.init();
        assertThat(crews.size()).isEqualTo(4);
    }
}
