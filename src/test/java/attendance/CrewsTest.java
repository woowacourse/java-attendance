package attendance;

import attendance.domain.*;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

public class CrewsTest {

    @Nested
    @DisplayName("Crews에 Crew 추가 테스트")
    class AddCrewTest {
        @Test
        @DisplayName("크루의 닉네임이 들어오면, 크루를 추가하고, Crew를 리턴한다.")
        void addByNicknameTest1() {
            Crews crews = new Crews();
            assertThat(crews.create("모루")).isInstanceOf(Crew.class);
        }

        @Test
        @DisplayName("이미 추가된 크루면 예외")
        void addByNicknameTest2() {
            Crews crews = new Crews();
            crews.create("모루");
            assertThatThrownBy(() -> crews.create("모루")).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    @DisplayName("닉네임으로 크루 찾기 테스트")
    class FindCrewByNicknameTest {
        @Test
        @DisplayName("이미 존재하는 크루일 경우, 닉네임을 통해 크루를 찾는다")
        void findCrewByNicknameTest1() {
            Crews crews = new Crews();
            crews.create("모루");
            assertThat(crews.findCrewByNickname("모루")).hasFieldOrPropertyWithValue("nickname", "모루");
        }

        @Test
        @DisplayName("찾으려는 크루가 없을 경우 예외")
        void findCrewByNicknameTest2() {
            Crews crews = new Crews();
            crews.create("모루");
            assertThatThrownBy(() -> crews.findCrewByNickname("히포")).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    @DisplayName("출석 추가 테스트")
    class AddAttendanceTest {
        @Test
        @DisplayName("크루의 닉네임과 출석 시간이 들어오면 크루의 출석 기록에 추가하고, Attendance를 리턴 - 출석 기록 확인")
        void addAttendanceTest1() {
            Crews crews = new Crews();
            Crew crew = crews.create("모루");

            assertThat(crew.addAttendance(LocalDateTime.of(2024, 12, 11, 9, 58)))
                    .hasFieldOrPropertyWithValue("dateTime", LocalDateTime.of(2024, 12, 11, 9, 58));
        }

        @Test
        @DisplayName("크루의 닉네임과 출석 시간이 들어오면 크루의 출석 기록에 추가하고, Attendance를 리턴 - 출석 상태 확인-출석")
        void addAttendanceTest2() {
            Crews crews = new Crews();
            Crew crew = crews.create("모루");

            assertThat(crew.addAttendance(LocalDateTime.of(2024, 12, 11, 9, 58)))
                    .hasFieldOrPropertyWithValue("status", AttendanceStatus.ATTEND);
        }

        @Test
        @DisplayName("크루의 닉네임과 출석 시간이 들어오면 크루의 출석 기록에 추가하고, Attendance를 리턴 - 출석 상태 확인-지각")
        void addAttendanceTest3() {
            Crews crews = new Crews();
            Crew crew = crews.create("모루");

            assertThat(crew.addAttendance(LocalDateTime.of(2024, 12, 11, 10, 6)))
                    .hasFieldOrPropertyWithValue("status", AttendanceStatus.LATE);
        }

        @Test
        @DisplayName("크루의 닉네임과 출석 시간이 들어오면 크루의 출석 기록에 추가하고, Attendance를 리턴 - 출석 상태 확인-결석")
        void addAttendanceTest4() {
            Crews crews = new Crews();
            Crew crew = crews.create("모루");

            assertThat(crew.addAttendance(LocalDateTime.of(2024, 12, 11, 10, 36)))
                    .hasFieldOrPropertyWithValue("status", AttendanceStatus.ABSENCE);
        }

        @Test
        @DisplayName("크루의 닉네임으로 출석 기록을 찾고, 그 기록에 출석 추가")
        void addAttendanceTest5() {
            Crews crews = new Crews();
            crews.create("모루");

            Crew crew = crews.findCrewByNickname("모루");
            assertThat(crew.addAttendance(LocalDateTime.of(2024, 12, 11, 10, 36)))
                    .hasFieldOrPropertyWithValue("status", AttendanceStatus.ABSENCE);
        }
    }

    @Nested
    @DisplayName("크루의 닉네임으로 출석 기록 찾기 테스트")
    class FindCrewAttendanceByNicknameTest {
        @Test
        @DisplayName("크루의 닉네임으로 출석 기록을 가져온다.")
        void findCrewAttendanceByNicknameTest1() {
            Crews crews = new Crews();
            Crew crew = crews.create("모루");
            crew.addAttendance(LocalDateTime.of(2024, 12, 11, 10, 36));
            crew.addAttendance(LocalDateTime.of(2024, 12, 12, 10, 6));

            assertThat(crews.findCrewAttendanceByNickname("모루").size()).isEqualTo(2);
        }

        @Test
        @DisplayName("크루의 닉네임으로 출석 기록을 찾을 수 없으면 예외")
        void findCrewAttendanceByNicknameTest2() {
            Crews crews = new Crews();
            Crew crew = crews.create("모루");
            crew.addAttendance(LocalDateTime.of(2024, 12, 11, 10, 36));
            crew.addAttendance(LocalDateTime.of(2024, 12, 12, 10, 6));

            assertThatThrownBy(() -> crews.findCrewAttendanceByNickname("히포").size()).isInstanceOf(
                    IllegalArgumentException.class);
        }
    }

    @Nested
    @DisplayName("날짜로 출석 찾기 테스트")
    class FindCrewAttendanceByDateTest {
        @Test
        @DisplayName("날짜에 맞는 출석 기록 찾기")
        void findAttendanceByDateTest1() {
            Crews crews = new Crews();
            Crew crew = crews.create("모루");
            crew.addAttendance(LocalDateTime.of(2024, 12, 11, 10, 36));
            crew.addAttendance(LocalDateTime.of(2024, 12, 12, 10, 6));

            assertThat(crew.findAttendanceByDate(LocalDate.of(2024, 12, 11)))
                    .hasFieldOrPropertyWithValue("dateTime", LocalDateTime.of(2024, 12, 11, 10, 36));
        }

        @Test
        @DisplayName("해당하는 날짜의 출석 기록이 없으면 예외")
        void findAttendanceByDateTest2() {
            Crews crews = new Crews();
            Crew crew = crews.create("모루");
            crew.addAttendance(LocalDateTime.of(2024, 12, 11, 10, 36));
            crew.addAttendance(LocalDateTime.of(2024, 12, 12, 10, 6));

            assertThatThrownBy(() -> crew.findAttendanceByDate(LocalDate.of(2024, 12, 13)))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    @DisplayName("출석 기록 수정 테스트")
    class UpdateAttendanceTest {
        @Test
        @DisplayName("날짜로 출석 기록을 찾아 수정")
        void updateAttendanceTest1() {
            Crews crews = new Crews();
            Crew crew = crews.create("모루");
            crew.addAttendance(LocalDateTime.of(2024, 12, 11, 10, 36));
            crew.addAttendance(LocalDateTime.of(2024, 12, 12, 10, 6));

            Attendance updatedAttendance = crew.updateAttendance(LocalDate.of(2024, 12, 11), LocalTime.of(10, 0));

            assertSoftly(softly -> {
                assertThat(updatedAttendance)
                        .hasFieldOrPropertyWithValue("dateTime", LocalDateTime.of(2024, 12, 11, 10, 0));
                assertThat(updatedAttendance)
                        .hasFieldOrPropertyWithValue("status", AttendanceStatus.ATTEND);

                Crew foundCrew = crews.findCrewByNickname("모루");
                assertThat(foundCrew.findAttendanceByDate(LocalDate.of(2024, 12, 11)))
                        .hasFieldOrPropertyWithValue("dateTime", LocalDateTime.of(2024, 12, 11, 10, 0));
            });
        }

        @Test
        @DisplayName("수정 전과 수정 후의 Attendance는 달라야 한다.")
        void updateAttendanceTest2() {
            Crews crews = new Crews();
            Crew crew = crews.create("모루");
            crew.addAttendance(LocalDateTime.of(2024, 12, 11, 10, 36));
            crew.addAttendance(LocalDateTime.of(2024, 12, 12, 10, 6));

            LocalDate updateDate = LocalDate.of(2024, 12, 11);
            Attendance beforeUpdate = crew.findAttendanceByDate(updateDate);

            Attendance afterUpdate = crew.updateAttendance(LocalDate.of(2024, 12, 11), LocalTime.of(10, 0));
            assertThat(beforeUpdate).isNotEqualTo(afterUpdate);
        }
    }

    @Nested
    @DisplayName("크루별 전날까지의 출석 확인 테스트")
    class GetAttendanceUntilYesterdayTest {
        @Test
        @DisplayName("크루를 찾아 전날까지의 출석 기록 가져오기")
        void getAttendanceUntilYesterdayTest1() {
            Crews crews = new Crews();
            Crew crew = crews.create("모루");
            crew.addAttendance(LocalDateTime.of(2024, 12, 11, 10, 36));
            crew.addAttendance(LocalDateTime.of(2024, 12, 12, 10, 6));

            LocalDate today = LocalDate.of(2024, 12, 13);
            assertThat(crews.getCrewAttendancesUtilYesterday(today, "모루").size()).isEqualTo(9);
        }

        @Test
        @DisplayName("전날까지의 출석 기록에서 Attendance가 제대로 들어있는지 확인")
        void getAttendanceUntilYesterdayTest2() {
            Crews crews = new Crews();
            Crew crew = crews.create("모루");
            crew.addAttendance(LocalDateTime.of(2024, 12, 11, 10, 36));
            crew.addAttendance(LocalDateTime.of(2024, 12, 12, 10, 6));
            crew.addAttendance(LocalDateTime.of(2024, 12, 13, 10, 6));

            LocalDate today = LocalDate.of(2024, 12, 13);
            assertSoftly(softly -> {
                assertThat(crews.getCrewAttendancesUtilYesterday(today, "모루")).contains(
                        new Attendance(LocalDateTime.of(2024, 12, 11, 10, 36)));
                assertThat(crews.getCrewAttendancesUtilYesterday(today, "모루")).contains(
                        new Attendance(LocalDateTime.of(2024, 12, 12, 10, 6)));

                assertThat(crews.getCrewAttendancesUtilYesterday(today, "모루")).doesNotContain(
                        new Attendance(LocalDateTime.of(2024, 12, 13, 10, 6)));
            });
        }

        @Test
        @DisplayName("출석 기록이 없으면 해당 날짜에 0시 0분을 리턴")
        void getAttendanceUntilYesterdayTest3() {
            Crews crews = new Crews();
            Crew crew = crews.create("모루");
            crew.addAttendance(LocalDateTime.of(2024, 12, 11, 10, 36));
            crew.addAttendance(LocalDateTime.of(2024, 12, 12, 10, 6));
            crew.addAttendance(LocalDateTime.of(2024, 12, 13, 10, 6));

            LocalDate today = LocalDate.of(2024, 12, 13);

            List<Attendance> result = crews.getCrewAttendancesUtilYesterday(today, "모루");
            assertSoftly(softly -> {
                assertThat(result).contains(new Attendance(LocalDateTime.of(LocalDate.of(2024, 12, 2), LocalTime.MIN)));
                assertThat(result).contains(new Attendance(LocalDateTime.of(2024, 12, 11, 10, 36)));
                assertThat(result).contains(new Attendance(LocalDateTime.of(2024, 12, 12, 10, 6)));
                assertThat(result).doesNotContain(new Attendance(LocalDateTime.of(2024, 12, 13, 10, 6)));
            });
        }
    }

    @Nested
    @DisplayName("제적 위험자 확인 테스트")
    class FindWarningCrewsTest {
        @Test
        @DisplayName("전날까지의 출석 기록을 바탕으로 제적 대상자 찾기")
        void findWarningCrewsTest1() {
            Crews crews = new Crews();
            Crew crew = crews.create("모루");
            crew.addAttendance(LocalDateTime.of(2024, 12, 11, 11, 6));
            crew.addAttendance(LocalDateTime.of(2024, 12, 12, 11, 6));
            crew.addAttendance(LocalDateTime.of(2024, 12, 13, 11, 6));
            crew.addAttendance(LocalDateTime.of(2024, 12, 16, 14, 6));
            crew.addAttendance(LocalDateTime.of(2024, 12, 17, 11, 6));

            assertThat(crews.findWarningExpulsionCrews()).asInstanceOf(InstanceOfAssertFactories.MAP).containsEntry(AbsenceRule.EXPULSION, List.of(crew));
        }

        @Test
        @DisplayName("전날까지의 출석 기록을 바탕으로 면담 대상자 찾기")
        void findWarningCrewsTest2() {
            Crews crews = new Crews();
            Crew crew = crews.create("모루");
            crew.addAttendance(LocalDateTime.of(2024, 12, 11, 11, 6));
            crew.addAttendance(LocalDateTime.of(2024, 12, 12, 11, 6));
            crew.addAttendance(LocalDateTime.of(2024, 12, 13, 11, 6));

            assertThat(crews.findWarningExpulsionCrews()).asInstanceOf(InstanceOfAssertFactories.MAP).containsEntry(AbsenceRule.COUNSELING, List.of(crew));
        }

        @Test
        @DisplayName("전날까지의 출석 기록을 바탕으로 경고 대상자 찾기")
        void findWarningCrewsTest3() {
            Crews crews = new Crews();
            Crew crew = crews.create("모루");
            crew.addAttendance(LocalDateTime.of(2024, 12, 11, 11, 6));
            crew.addAttendance(LocalDateTime.of(2024, 12, 12, 11, 6));

            assertThat(crews.findWarningExpulsionCrews()).asInstanceOf(InstanceOfAssertFactories.MAP).containsEntry(AbsenceRule.WARNING, List.of(crew));
        }

        @Test
        @DisplayName("출석 상태로 내림차순 정렬하고, 이름으로 오름차순 정렬해야 한다.")
        void findWarningCrewsTest5() {
            Crews crews = new Crews();

            Crew crew1 = crews.create("빙티"); //결석 3회, 지각 4회
            crew1.addAttendance(LocalDateTime.of(2024, 12, 9, 13, 6));
            crew1.addAttendance(LocalDateTime.of(2024, 12, 10, 11, 6));
            crew1.addAttendance(LocalDateTime.of(2024, 12, 11, 11, 6));
            crew1.addAttendance(LocalDateTime.of(2024, 12, 12, 11, 6));
            crew1.addAttendance(LocalDateTime.of(2024, 12, 13, 10, 6));
            crew1.addAttendance(LocalDateTime.of(2024, 12, 16, 13, 6));
            crew1.addAttendance(LocalDateTime.of(2024, 12, 17, 10, 6));

            Crew crew2 = crews.create("이든"); //결석 2회, 지각 5회
            crew2.addAttendance(LocalDateTime.of(2024, 12, 9, 13, 6));
            crew2.addAttendance(LocalDateTime.of(2024, 12, 10, 10, 6));
            crew2.addAttendance(LocalDateTime.of(2024, 12, 11, 10, 6));
            crew2.addAttendance(LocalDateTime.of(2024, 12, 12, 10, 6));
            crew2.addAttendance(LocalDateTime.of(2024, 12, 13, 10, 6));
            crew2.addAttendance(LocalDateTime.of(2024, 12, 16, 14, 6));
            crew2.addAttendance(LocalDateTime.of(2024, 12, 13, 11, 6));

            Crew crew3 = crews.create("빙봉"); //결석 1회, 지각 6회
            crew3.addAttendance(LocalDateTime.of(2024, 12, 9, 13, 6));
            crew3.addAttendance(LocalDateTime.of(2024, 12, 10, 10, 6));
            crew3.addAttendance(LocalDateTime.of(2024, 12, 11, 10, 6));
            crew3.addAttendance(LocalDateTime.of(2024, 12, 12, 10, 6));
            crew3.addAttendance(LocalDateTime.of(2024, 12, 13, 10, 6));
            crew3.addAttendance(LocalDateTime.of(2024, 12, 16, 13, 6));
            crew3.addAttendance(LocalDateTime.of(2024, 12, 13, 11, 6));

            Crew crew4 = crews.create("쿠키"); //결석 2회, 지각 3회
            crew4.addAttendance(LocalDateTime.of(2024, 12, 9, 13, 6));
            crew4.addAttendance(LocalDateTime.of(2024, 12, 10, 10, 6));
            crew4.addAttendance(LocalDateTime.of(2024, 12, 11, 10, 6));
            crew4.addAttendance(LocalDateTime.of(2024, 12, 12, 11, 6));
            crew4.addAttendance(LocalDateTime.of(2024, 12, 13, 11, 6));

            List<Crew> warningCrews = crews.findWarningExpulsionCrews().get(AbsenceRule.COUNSELING);
            assertThat(warningCrews).isSortedAccordingTo(Crew::compareTo);
        }
    }
}
