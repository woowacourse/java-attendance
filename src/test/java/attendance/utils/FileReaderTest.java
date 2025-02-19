package attendance.utils;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FileReaderTest {


    @DisplayName("크루원의 닉네임과 출석일시를 가져온다.")
    @Test
    void 크루원_닉네임과_출석일시를_가져온다() {

        //given
        String filePath = "src/main/resources/attendances.csv";

        //when
        List<String> attendances = FileReader.parseToFile(filePath);

        //then
        assertThat(attendances.size()).isEqualTo(43);
    }

}