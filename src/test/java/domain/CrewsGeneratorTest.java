package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CrewsGeneratorTest {

    @Test
    @DisplayName("csv파일을 읽어 크루 출석 기록 초기화 한다")
    void test1() {
        //given
        //when
        final Map<String, Crew> crews = CrewsGenerator.generate();

        //then
        assertThat(crews).isNotEmpty();

    }
}
