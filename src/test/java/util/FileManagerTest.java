package util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.Attendances;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FileManagerTest {

    @DisplayName("csv파일을 읽어와 Attendance를 만들 수 있다.")
    @Test
    void readFile() {
        //given
        String filePath = "src/test/resources/attendances.csv";

        //when
        Attendances attendances = FileManager.readFile(filePath);

        //then
        assertThat(attendances.getAttendances())
                .isNotNull()
                .hasSize(5);
    }

    @DisplayName("파일의 구분자가 잘못되면 예외가 발생한다.")
    @Test
    void invalidDelimiter() {
        //given
        String filePath = "src/test/resources/invalidDelimiterAttendances.csv";

        //when //then
        assertThatThrownBy(() -> FileManager.readFile(filePath))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("파일의 구분자가 잘못되었습니다.");
    }

    @DisplayName("파일의 경로가 잘못되면 예외가 발생한다.")
    @Test
    void invalidFilePath() {
        //given
        String filePath = "src/test/resources/invalidFile.csv";

        //when //then
        assertThatThrownBy(() -> FileManager.readFile(filePath))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("잘못된 파일 입니다.");
    }

}
