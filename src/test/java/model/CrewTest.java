package model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CrewTest {

    @Test
    @DisplayName("String 입력값으로 올바른 Crew 객체를 생성하는 지 성공 테스트")
    void ofSuccess() {

        // given
        final String nicknameInput = "칼리";

        // when
        final Crew crew = Crew.of(nicknameInput);
        final String expected = crew.getNickname().getValue();

        // then
        Assertions.assertThat(expected).isEqualTo(nicknameInput);
    }

    @Test
    @DisplayName("같은 이름의 Crew라면 같은 객체로 판단하는 지 테스트")
    void testEquals() {

        // given
        final String nickname1 = "칼리";
        final String nickname2 = "칼리";
        final Set<Crew> crews = new HashSet<>();

        // when
        final Crew crew1 = Crew.of(nickname1);
        final Crew crew2 = Crew.of(nickname2);
        crews.add(crew1);
        crews.add(crew2);

        // then
        assertAll(
                () -> Assertions.assertThat(crew1).isEqualTo(crew2),
                () -> Assertions.assertThat(crews.size()).isEqualTo(1)
        );
    }
}