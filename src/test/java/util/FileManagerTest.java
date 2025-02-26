package util;

import static org.assertj.core.api.Assertions.assertThat;

import domain.Attendance;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FileManagerTest {

    @DisplayName("csv파일을 가져올 수 있다.")
    @Test
    void getReadFile() {
        // given
        Attendance attendance = FileManager.readFile();

        // when & then
        assertThat(attendance).isNotNull();
    }

}
