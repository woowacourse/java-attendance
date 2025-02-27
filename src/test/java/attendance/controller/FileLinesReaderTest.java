package attendance.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class FileLinesReaderTest {

    @Test
    void 파일의_첫_번째_줄을_제외한_모든_줄을_읽어온다() {
        assertThat(FileLinesReader.readLinesWithoutFirstLine("src/test/resources/", "test.csv"))
                .containsExactly("2", "3");
    }

}
