package util;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class FileManagerTest {


    @Test
    @DisplayName("csv 파일 전체를 읽어온다")
    void readTest1() {
        //given
        final String fileName = "attendances.csv";
        final int expectedSize = 42;
        final String expectedHeader = "nickname,datetime";

        //when
        final List<String> data = FileManager.readFileLines(fileName);

        //then
        assertThat(data)
                .isNotEmpty()
                .hasSize(expectedSize)
                .startsWith(expectedHeader);
    }
}
