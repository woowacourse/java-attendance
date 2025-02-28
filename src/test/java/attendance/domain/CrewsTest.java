package attendance.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashSet;
import java.util.Set;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

public class CrewsTest {

    @DisplayName("크루 추가")
    @Test
    public void 크루_추가() {
        //given
        Set<Crew> originalCrews = new HashSet<>();
        originalCrews.add(new Crew("우가"));
        originalCrews.add(new Crew("범블비"));

        assertDoesNotThrow(() -> new Crews(originalCrews));
    }

    @Test
    public void 원본_리스트_수정시_내부_리스트_영향_없음() {
        //given
        Set<Crew> originalCrews = new HashSet<>();
        originalCrews.add(new Crew("우가"));
        originalCrews.add(new Crew("범블비"));

        Crews crews = new Crews(originalCrews);

        //when
        originalCrews.add(new Crew("제프리"));

        //then
        assertEquals(2, crews.getCrews().size());
    }

    @Test
    public void 내부_리스트_수정_불가능_확인() {
        //given
        Set<Crew> originalCrews = new HashSet<>();
        originalCrews.add(new Crew("우가"));

        Crews crews = new Crews(originalCrews);

        //when & then
        assertThrows(UnsupportedOperationException.class, () -> {
            crews.getCrews().add(new Crew("범블비"));
        });
    }

    @Test
    public void 닉네임_일치_크루_반환() {
        //given
        Set<Crew> originalCrews = new HashSet<>();
        Crew crew = new Crew("우가");
        originalCrews.add(crew);

        Crews crews = new Crews(originalCrews);

        //when & then
        Assertions.assertThat(crews.findCrew("우가")).isEqualTo(crew);
    }

}
