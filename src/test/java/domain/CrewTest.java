package domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CrewTest {

    @Test
    @DisplayName("크루 생성 시 이름 유효성 검사")
    public void validateName() {
        //given
        String userName = "가나다라마";
        List<LocalDateTime> histories = new ArrayList<>();
        //when & then
        assertThatThrownBy(() -> new Crew(userName, histories, LocalDate.of(2024, 12, 19)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("이름은 네 글자 이하여야 합니다.");
    }
}
