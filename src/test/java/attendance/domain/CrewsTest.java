package attendance.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

public class CrewsTest {

    @DisplayName("크루 추가")
    @Test
    public void 크루_추가() {
        //given
        List<Crew> originalList = new ArrayList<>();
        originalList.add(new Crew("우가"));
        originalList.add(new Crew("범블비"));

        assertDoesNotThrow(() -> new Crews(originalList));
    }

    @Test
    public void 원본_리스트_수정시_내부_리스트_영향_없음() {
        //given
        List<Crew> originalList = new ArrayList<>();
        originalList.add(new Crew("우가"));
        originalList.add(new Crew("범블비"));

        Crews crews = new Crews(originalList);

        //when
        originalList.add(new Crew("제프리"));

        //then
        assertEquals(2, crews.getCrews().size());
    }

    @Test
    public void 내부_리스트_수정_불가능_확인() {
        //given
        List<Crew> list = new ArrayList<>();
        list.add(new Crew("우가"));

        Crews crews = new Crews(list);

        //when & then
        assertThrows(UnsupportedOperationException.class, () -> {
            crews.getCrews().add(new Crew("범블비"));
        });
    }

    //TODO : findCrew() 해서 반환 검사
    @Test
    public void 닉네임_일치_크루_반환() {
        //given
        List<Crew> originalCrews = new ArrayList<>();
        Crew crew = new Crew("우가");
        originalCrews.add(crew);

        Crews crews = new Crews(originalCrews);

        //when & then
        Assertions.assertThat(crews.findCrew("우가")).isEqualTo(crew);

    }
}
