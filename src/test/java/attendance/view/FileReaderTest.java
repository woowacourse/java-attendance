package attendance.view;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileReaderTest {

    private FileLineReader reader;

    @BeforeEach
    void setUp() {
        reader = new FileLineReader();
    }

    @Test
    void 파일경로와_이름을_받으면_모든_줄을_읽어온다() {
        reader = new FileLineReader();

        List<String> allLines = reader.readAllLines("src/test/java/resources/", "test.csv");

        assertThat(allLines).contains(
                "nickname,datetime",
                "쿠키,2024-12-13 10:08"
        );
    }

    @Test
    void 존재하지_않은_경로나_이름을_받으면_줄을_읽어올_수_없다() {
        reader = new FileLineReader();

        assertThatThrownBy(() -> reader.readAllLines("a/", "a.csv"))
                .isInstanceOf(IllegalArgumentException.class);
    }

}
