package view;

import domain.CrewGroup;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;


class FileInputViewTest {
    @DisplayName("파일 입력 테스트")
    @Test
    void FilesTest() {
        try {
            CrewGroup crews = FileInputView.loadInitFileData();

            Assertions.assertThat(crews.has("빙봉")).isTrue();
            Assertions.assertThat(crews.has("빙티")).isTrue();
            Assertions.assertThat(crews.has("이든")).isTrue();
            Assertions.assertThat(crews.has("가이온")).isFalse();

            Assertions.assertThat(crews.findByName("빙봉").getAttendanceRecord().getAbsenceCount()).isEqualTo(1);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
