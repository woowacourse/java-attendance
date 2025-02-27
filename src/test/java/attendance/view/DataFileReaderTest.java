package attendance.view;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DataFileReaderTest {
    @DisplayName("파일 내용 줄단위 변환")
    @Test
    void test1(){
        Assertions.assertThat(new DataFileReader().read())
                .hasSize(41);
    }
}
