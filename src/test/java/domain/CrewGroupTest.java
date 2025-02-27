package domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class CrewGroupTest {
    @Nested
    class addCrew{
        @DisplayName("크루원 넣기")
        @Test
        void addCrew(){
            CrewGroup crewGroup = new CrewGroup();
            crewGroup.addCrew("가이온");
            assertThat(crewGroup.findByName("가이온")).isInstanceOf(Crew.class);
        }
    }

    @Nested
    class findCrew{
        CrewGroup crewGroup;

        @BeforeEach
        void setCrewGroup(){
            crewGroup = new CrewGroup();
            Stream.of("가이온","가이온1","가이온2","가이온3")
                    .forEach(name -> crewGroup.addCrew(name));
        }

        @DisplayName("크루원 찾기")
        @Test
        void findCrewByName(){
            assertAll(
                    () -> assertThat(crewGroup.findByName("가이온")).isInstanceOf(Crew.class),
                    () -> assertThat(crewGroup.findByName("가이온1")).isInstanceOf(Crew.class),
                    () -> assertThat(crewGroup.findByName("가이온2")).isInstanceOf(Crew.class),
                    () -> assertThat(crewGroup.findByName("가이온3")).isInstanceOf(Crew.class)
            );
        }

        @DisplayName("존재하지 않는 크루원을 조회하면 에러가 발생")
        void findMissingCrewByName(){
            assertThatThrownBy(() -> crewGroup.findByName("네오")).isInstanceOf(IllegalArgumentException.class);
        }
    }
}