package domain;

import domain.attendance.Attendance;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
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

        @DisplayName("이미 존재하는 크루원을 넣으면 에러가 발샐")
        @Test
        void duplicatedCrew(){
            CrewGroup crewGroup = new CrewGroup();

            crewGroup.addCrew("가이온");

            assertThatThrownBy(() -> crewGroup.addCrew("가이온")).isInstanceOf(IllegalArgumentException.class);
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
        @Test
        void findMissingCrewByName(){
            assertThatThrownBy(() -> crewGroup.findByName("네오")).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    class sortTest{
        @DisplayName("정렬된 Attendance Test")
        @Test
        void getSortedAttendanceTest(){
            CrewGroup crewGroup = new CrewGroup();
            crewGroup.addCrew("가이온");
            crewGroup.addCrew("돔푸");
            crewGroup.addCrew("리사");
            crewGroup.addCrew("네오");


            Attendance attendance1 = crewGroup.findByName("가이온").getAttendanceRecord();
            attendance1.addAttendance(LocalDateTime.of(2024,12,2,12,0));
            attendance1.addAttendance(LocalDateTime.of(2024,12,3,12,0));
            attendance1.addAttendance(LocalDateTime.of(2024,12,4,12,0));
            attendance1.addAttendance(LocalDateTime.of(2024,12,5,12,0));
            attendance1.addAttendance(LocalDateTime.of(2024,12,6,13,0));

            Attendance attendance2 = crewGroup.findByName("돔푸").getAttendanceRecord();
            attendance2.addAttendance(LocalDateTime.of(2024,12,2,12,0));
            attendance2.addAttendance(LocalDateTime.of(2024,12,3,12,0));
            attendance2.addAttendance(LocalDateTime.of(2024,12,4,12,0));
            attendance2.addAttendance(LocalDateTime.of(2024,12,5,12,0));
            attendance2.addAttendance(LocalDateTime.of(2024,12,6,13,6));

            Attendance attendance3 = crewGroup.findByName("리사").getAttendanceRecord();
            attendance3.addAttendance(LocalDateTime.of(2024,12,2,12,0));
            attendance3.addAttendance(LocalDateTime.of(2024,12,3,12,0));
            attendance3.addAttendance(LocalDateTime.of(2024,12,4,12,0));
            attendance3.addAttendance(LocalDateTime.of(2024,12,5,10,0));
            attendance3.addAttendance(LocalDateTime.of(2024,12,6,13,0));

            Attendance attendance4 = crewGroup.findByName("네오").getAttendanceRecord();
            attendance4.addAttendance(LocalDateTime.of(2024,12,2,12,0));
            attendance4.addAttendance(LocalDateTime.of(2024,12,3,12,0));
            attendance4.addAttendance(LocalDateTime.of(2024,12,4,12,0));
            attendance4.addAttendance(LocalDateTime.of(2024,12,5,10,0));
            attendance4.addAttendance(LocalDateTime.of(2024,12,6,13,0));

            List<Crew> sortedGroup = crewGroup.getSortedWarningCrews();
            assertThat(sortedGroup.getFirst().getName()).isEqualTo("가이온");
            assertThat(sortedGroup.get(1).getName()).isEqualTo("돔푸");
            assertThat(sortedGroup.get(2).getName()).isEqualTo("네오");
            assertThat(sortedGroup.get(3).getName()).isEqualTo("리사");
        }
    }
}
