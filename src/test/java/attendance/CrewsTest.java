package attendance;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

public class CrewsTest {

    @Test
    @DisplayName("크루의 닉네임이 들어오면, 크루를 추가하고, Crew를 리턴한다.")
    void addCrewByNicknameTest1() {
        Crews crews = new Crews();
        assertThat(crews.addCrew("모루")).isInstanceOf(Crew.class);
    }

    @Test
    @DisplayName("이미 추가된 크루면 예외")
    void addCrewByNicknameTest2() {
        Crews crews = new Crews();
        crews.addCrew("모루");
        assertThatThrownBy(() -> crews.addCrew("모루")).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("이미 존재하는 크루일 경우, 닉네임을 통해 크루를 찾는다")
    void findCrewByNicknameTest1() {
        Crews crews = new Crews();
        crews.addCrew("모루");
        assertThat(crews.findCrewByNickname("모루")).hasFieldOrPropertyWithValue("nickname", "모루");
    }

    @Test
    @DisplayName("찾으려는 크루가 없을 경우 예외")
    void findCrewByNicknameTest2() {
        Crews crews = new Crews();
        crews.addCrew("모루");
        assertThatThrownBy(() -> crews.findCrewByNickname("히포")).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("크루의 닉네임과 출석 시간이 들어오면 크루의 출석 기록에 추가하고, Attendance를 리턴 - 출석 기록 확인")
    void addCrewAttendanceTest1() {
        Crews crews = new Crews();
        Crew crew = crews.addCrew("모루");

        assertThat(crew.addAttendance(LocalDateTime.of(2024,12,11,9,58)))
                .hasFieldOrPropertyWithValue("attendanceDateTime", LocalDateTime.of(2024,12,11,9,58));
    }

    @Test
    @DisplayName("크루의 닉네임과 출석 시간이 들어오면 크루의 출석 기록에 추가하고, Attendance를 리턴 - 출석 상태 확인-출석")
    void addCrewAttendanceTest2() {
        Crews crews = new Crews();
        Crew crew = crews.addCrew("모루");

        assertThat(crew.addAttendance(LocalDateTime.of(2024,12,11,9,58)))
                .hasFieldOrPropertyWithValue("attendanceStatus", "출석");
    }

    @Test
    @DisplayName("크루의 닉네임과 출석 시간이 들어오면 크루의 출석 기록에 추가하고, Attendance를 리턴 - 출석 상태 확인-지각")
    void addCrewAttendanceTest3() {
        Crews crews = new Crews();
        Crew crew = crews.addCrew("모루");

        assertThat(crew.addAttendance(LocalDateTime.of(2024,12,11,10,6)))
                .hasFieldOrPropertyWithValue("attendanceStatus", "지각");
    }

    @Test
    @DisplayName("크루의 닉네임과 출석 시간이 들어오면 크루의 출석 기록에 추가하고, Attendance를 리턴 - 출석 상태 확인-결석")
    void addCrewAttendanceTest4() {
        Crews crews = new Crews();
        Crew crew = crews.addCrew("모루");

        assertThat(crew.addAttendance(LocalDateTime.of(2024,12,11,10,36)))
                .hasFieldOrPropertyWithValue("attendanceStatus", "결석");
    }

    @Test
    @DisplayName("크루의 닉네임으로 출석 기록을 찾고, 그 기록에 출석 추가")
    void addCrewAttendanceTest5() {
        Crews crews = new Crews();
        crews.addCrew("모루");

        Crew crew = crews.findCrewByNickname("모루");
        assertThat(crew.addAttendance(LocalDateTime.of(2024,12,11,10,36)))
                .hasFieldOrPropertyWithValue("attendanceStatus", "결석");
    }

    @Test
    @DisplayName("크루의 닉네임으로 출석 기록을 가져온다.")
    void findCrewAttendanceByNicknameTest1() {
        Crews crews = new Crews();
        Crew crew = crews.addCrew("모루");
        crew.addAttendance(LocalDateTime.of(2024,12,11,10,36));
        crew.addAttendance(LocalDateTime.of(2024,12,12,10,6));

        assertThat(crews.findCrewAttendanceByNickname("모루").size()).isEqualTo(2);
    }

    @Test
    @DisplayName("크루의 닉네임으로 출석 기록을 찾을 수 없으면 예외")
    void findCrewAttendanceByNicknameTest2() {
        Crews crews = new Crews();
        Crew crew = crews.addCrew("모루");
        crew.addAttendance(LocalDateTime.of(2024,12,11,10,36));
        crew.addAttendance(LocalDateTime.of(2024,12,12,10,6));

        assertThatThrownBy(() -> crews.findCrewAttendanceByNickname("히포").size()).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("날짜에 맞는 출석 기록 찾기")
    void findAttendanceByDateTest1() {
        Crews crews = new Crews();
        Crew crew = crews.addCrew("모루");
        crew.addAttendance(LocalDateTime.of(2024,12,11,10,36));
        crew.addAttendance(LocalDateTime.of(2024,12,12,10,6));

        assertThat(crew.findAttendanceByDate(LocalDate.of(2024,12,11)))
                .hasFieldOrPropertyWithValue("attendanceDateTime", LocalDateTime.of(2024,12,11,10,36));
    }

    @Test
    @DisplayName("해당하는 날짜의 출석 기록이 없으면 예외")
    void findAttendanceByDateTest2() {
        Crews crews = new Crews();
        Crew crew = crews.addCrew("모루");
        crew.addAttendance(LocalDateTime.of(2024,12,11,10,36));
        crew.addAttendance(LocalDateTime.of(2024,12,12,10,6));

        assertThatThrownBy(() -> crew.findAttendanceByDate(LocalDate.of(2024,12,13)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("날짜로 출석 기록을 찾아 수정")
    void updateAttendanceTest1() {
        Crews crews = new Crews();
        Crew crew = crews.addCrew("모루");
        crew.addAttendance(LocalDateTime.of(2024,12,11,10,36));
        crew.addAttendance(LocalDateTime.of(2024,12,12,10,6));

        Attendance updatedAttendance = crew.updateAttendance(LocalDate.of(2024, 12, 11), LocalTime.of(10, 0));


        assertSoftly(softly -> {
            assertThat(updatedAttendance)
                    .hasFieldOrPropertyWithValue("attendanceDateTime", LocalDateTime.of(2024,12,11,10,0));
            assertThat(updatedAttendance)
                    .hasFieldOrPropertyWithValue("attendanceStatus", "출석");

            Crew foundCrew = crews.findCrewByNickname("모루");
            assertThat(foundCrew.findAttendanceByDate(LocalDate.of(2024,12,11)))
                    .hasFieldOrPropertyWithValue("attendanceDateTime", LocalDateTime.of(2024,12,11,10,0));
        });
    }

    @Test
    @DisplayName("수정 전과 수정 후의 Attendance는 달라야 한다.")
    void updateAttendanceTest2() {
        Crews crews = new Crews();
        Crew crew = crews.addCrew("모루");
        crew.addAttendance(LocalDateTime.of(2024,12,11,10,36));
        crew.addAttendance(LocalDateTime.of(2024,12,12,10,6));

        LocalDate updateDate = LocalDate.of(2024,12,11);
        Attendance beforeUpdate = crew.findAttendanceByDate(updateDate);

        Attendance afterUpdate = crew.updateAttendance(LocalDate.of(2024, 12, 11), LocalTime.of(10, 0));
        assertThat(beforeUpdate).isNotEqualTo(afterUpdate);
    }
}
