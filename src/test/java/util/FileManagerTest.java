package util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class FileManagerTest {


    @Test
    @DisplayName("resources에 위치한 파일의 정보를 읽어 온다.")
    void test1() {
        //given
        final String fileName1 = "attendances.csv";
        final String fileName2 = "empty";

        //when
        final List<String> lines = FileManager.readFileLines(fileName1);

        //then
        assertAll(
                () -> assertThat(lines).isNotEmpty(),
                () -> assertThat(lines.getFirst()).isEqualTo("nickname,datetime"),
                () -> assertThatIllegalStateException().isThrownBy(() -> FileManager.readFileLines(fileName2))
        );
    }

}
