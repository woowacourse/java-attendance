package domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class SystemDateProviderTest {
    @Test
    @DisplayName("현재 날짜를 정상적으로 반환")
    void SystemDateProvideTest() {
        //given
        LocalDate now = LocalDate.now();
        //when
        LocalDate providedDate = SystemDateProvider.now();
        //then
        assertThat(providedDate).isEqualTo(now);
    }
}