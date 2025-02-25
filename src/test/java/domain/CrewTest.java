package domain;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class CrewTest {
    
    @Test
    void 크루_이름이_2글자_미만_4글자_초과이지_못한다() {
        // given & when & then
        assertThrows(IllegalArgumentException.class, () -> new Crew("a"));
        assertThrows(IllegalArgumentException.class, () -> new Crew("abcde"));
    }
}
