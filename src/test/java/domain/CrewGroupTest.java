package domain;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class CrewGroupTest {
    @Nested
    class CrewFindTest{
        @DisplayName("중복되는 이름은 허용되지 않는다.")
        @Test
        void test() {
            List<String> crews = List.of("수양", "수양");
            assertThatThrownBy(() -> CrewGroup.from(crews))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("중복된 이름의 크루는 존재할 수 없습니다.");
        }

        @DisplayName("크루 이름으로 크루목록에서 조회한다")
        @Test
        void test2() {
            String crewName = "가이온";
            List<String> crews = List.of("수양", crewName);
            CrewGroup crewGroup = CrewGroup.from(crews);

            Crew crew = crewGroup.findCrew(crewName);

            assertThat(crew).isInstanceOf(Crew.class);
            assertThat(crew.getName()).isEqualTo(crewName);
        }

        @DisplayName("등록되지 않은 크루찾기")
        @Test
        void test3(){
            List<String> crews = List.of("가이온","가이온1","가이온2");
            CrewGroup crewGroup = CrewGroup.from(crews);

            assertThatThrownBy(() -> crewGroup.findCrew("수양")).
                    isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    class CrewSortTes{
        @DisplayName("이름순으로 정렬 되어 있는지 확인 테스트")
        @ParameterizedTest
        @ValueSource(ints = {0,1,2,3})
        void sortTest(int val){
            List<String> crews = List.of("가이온0","가이온1","가이온2","가이온3");
            CrewGroup crewGroup = CrewGroup.from(crews);

            List<Crew> crewList = crewGroup.sortedAttendanceWarning();
            assertThat(crewList.get(val).getName()).isEqualTo("가이온" + val);
        }
    }
}
