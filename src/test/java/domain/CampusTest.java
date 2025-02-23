package domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class CampusTest {

    @Test
    void 캠퍼스_열린_날짜_테스트() {
        assertDoesNotThrow(() -> Campus.validateCampusOpenDate(LocalDate.of(2024, 12, 2)));
    }

    @Test
    void 캠퍼스_닫힌_날짜_예외() {
        assertThatThrownBy(() -> Campus.validateCampusOpenDate(LocalDate.of(2024, 12, 25)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 캠퍼스_열린_시간_테스트() {
        assertDoesNotThrow(() -> Campus.validateOpenHours(LocalDateTime.of(2024, 12, 2, 10,30)));
    }

    @Test
    void 캠퍼스_닫힌_시간_예외() {
        assertThatThrownBy(() -> Campus.validateOpenHours(LocalDateTime.of(2024, 12, 2, 23, 30)))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
