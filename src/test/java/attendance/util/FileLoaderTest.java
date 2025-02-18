package attendance.util;

import attendance.domain.Attendance;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class FileLoaderTest {

    @DisplayName("Attendance 생성 성공")
    @Test
    void test1(){
        String data = "쿠키,2024-12-13 10:08";

        Assertions.assertThat(FileLoader.load(data))
                .isNotNull()
                .isInstanceOf(Attendance.class);
    }
}
