package util;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Queue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class FileManagerTest {


    @Test
    @DisplayName("csv 파일 전체를 읽어온다")
    void readTest1() throws URISyntaxException, IOException {
        //given
        final String path = "src/main/resources/";
        final String fileName = "attendance.csv";

        //when
        final Queue<String> data = FileManger.readFileLines(fileName);

        //then
        assertThat(data).isNotEmpty();

    }

    private class FileManger {

        public static Queue<String> readFileLines(final String fileName) {
            return null;
        }
    }
}
