package util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.Attendance;
import domain.Crew;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FileManagerTest {

    @DisplayName("csv파일을 읽어와 Attendance 객체를 생성할 수 있다.")
    @Test
    void getReadFile() {
        //given
        String filePath = "src/main/resources/attendances.csv";

        //when
        Attendance attendance = FileManager.readFile(filePath);

        //then
        Crew crew = Crew.from("이든");

        assertThat(attendance).isNotNull();
        assertThat(attendance.getAttendances())
                .containsKey(crew)
                .isNotEmpty();
    }

    @DisplayName("파일 경로가 잘못된 경우 예외를 발생한다.")
    @Test
    void invalidFilePath() {
        //given
        String filPath = "invalidFilePath";

        //when & then
        assertThatThrownBy(() -> FileManager.readFile(filPath))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("잘못된 파일 입니다.");
    }

}
