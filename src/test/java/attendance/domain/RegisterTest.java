package attendance.domain;

import attendance.domain.constant.AttendanceStatus;
import attendance.exception.CustomException;
import attendance.exception.ErrorMessage;
import java.time.LocalTime;
import java.util.Map.Entry;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

class RegisterTest {

    private Register register;
    private Crew crew;
    private Crew crew1;
    private Crew crew2;
    private Crew crew3;
    private LocalDate currentDate;

    @BeforeEach
    void setUp() {
        currentDate = LocalDate.of(2025, 2, 5);
        crew = Crew.from("우가");
        crew1 = Crew.from("제프리");
        crew2 = Crew.from("부기");
        crew3 = Crew.from("범블비");
        Crews crews = new Crews(Set.of(crew, crew1, crew2, crew3));
        register = new Register(crews, currentDate);
    }

    @Test
    void 기존_출석시간_수정_테스트() {
        //given
        LocalDateTime originalTime = LocalDateTime.of(currentDate, java.time.LocalTime.of(9, 59));
        register.modifyInfo(crew, originalTime);

        //when
        LocalDateTime modifiedTime = LocalDateTime.of(currentDate, java.time.LocalTime.of(10, 6));
        AttendanceChecker result = register.modifyInfo(crew, modifiedTime);

        //then
        assertThat(result.getAttendanceStatus()).isEqualTo(AttendanceStatus.LATE.getName());
    }

    @Test
    void 출석정보_조회_테스트() {
        //given
        LocalDateTime time = LocalDateTime.of(currentDate, java.time.LocalTime.of(9, 59));
        register.modifyInfo(crew, time);

        //when
        AttendanceChecker result = register.findInfo(crew, time);

        //then
        assertThat(result.getAttendanceStatus()).isEqualTo(AttendanceStatus.ATTENDANCE.getName());
    }

    @DisplayName("2월 1일부터 5일까지 평일 수는 총 3일이라, 출석기록은 총 3개 있어야 하는지 검증하는 테스트 입니다.")
    @Test
    void 출석기록_계산_테스트() {
        //when
        AttendanceRegistry result = register.checkAttendanceHistory(crew);

        //then
        assertThat(result.getDateInfos().size()).isEqualTo(3);
    }

    @Test
    void 출석_시간_파일_처리_테스트() {
        //given
        Map<Crew, List<LocalDateTime>> attendanceTimes = new HashMap<>();
        attendanceTimes.put(crew, List.of(LocalDateTime.of(currentDate, LocalTime.of(10, 6))));

        //when
        register.fromCrewAttendanceTimeFile(attendanceTimes);

        //then
        AttendanceChecker firstCheck = register.findInfo(crew, attendanceTimes.get(crew).get(0));

        assertThat(firstCheck.getLocalDateTime()).isEqualTo(attendanceTimes.get(crew).get(0));
    }

    @Test
    void 캠퍼스_운영_오픈_출석_시간_검증() {
        //given
        LocalDateTime localDateTime = LocalDateTime.of(2025, 2, 25, 7,59);

        //when & then
        Assertions.assertThatThrownBy(() -> register.modifyInfo(crew, localDateTime))
                        .isInstanceOf(CustomException.class)
                                .hasMessage(ErrorMessage.CAMPUS_NOT_OPERATION.getMessage());
    }

    @Test
    void 캠퍼스_운영_마감_출석_시간_검증() {
        //given
        LocalDateTime localDateTime = LocalDateTime.of(2025, 2, 25, 23,0);

        //when & then
        Assertions.assertThatThrownBy(() -> register.modifyInfo(crew, localDateTime))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorMessage.CAMPUS_NOT_OPERATION.getMessage());
    }

    @DisplayName("crew와, crew3의 결석 횟수 + 지각 횟수가 똑같을 때 닉네임순으로 정렬이 되어서 crew3가 crew보다 먼저 오고, crew2가 결석 횟수가 가장 많아서 제일 먼저 온다")
    @Test
    void 위험군_팀_조회_및_닉네임_결석횟수_정렬_테스트() {
        //given
        Map<Crew, List<LocalDateTime>> attendanceTimes = new HashMap<>();

        attendanceTimes.put(crew, List.of(LocalDateTime.of(2025,2,3,12,59),
                LocalDateTime.of(2025,2,4,10,31), //출석 1, 결석2
                LocalDateTime.of(2025, 2, 5, 10, 31)));
        attendanceTimes.put(crew1, List.of(LocalDateTime.of(2025,2,3,12,59),
                LocalDateTime.of(2025,2,4,9,59), //출석 2,
                LocalDateTime.of(2025, 2, 5, 10, 31)));
        attendanceTimes.put(crew2, List.of(LocalDateTime.of(2025,2,3,13,59),
                LocalDateTime.of(2025,2,4,10,59), //결석 3,
                LocalDateTime.of(2025, 2, 5, 11, 0)));
        attendanceTimes.put(crew3, List.of(LocalDateTime.of(2025,2,3,13,31),
                LocalDateTime.of(2025,2,4,10,31), //결석 2, 출석 1
                LocalDateTime.of(2025, 2, 5, 9, 59)));
        register.fromCrewAttendanceTimeFile(attendanceTimes);

        //when
        List<CrewRisk> riskCrews = register.findAllExpertRiskCrews();
        List<Crew> orderedCrews = new ArrayList<>();
        for (CrewRisk crewRisk : riskCrews) {
            orderedCrews.add(crewRisk.getCrew());
        }
        assertAll(
                () -> assertThat(orderedCrews.size()).isEqualTo(3),
                () -> assertThat(orderedCrews.getFirst()).isEqualTo(crew2),
                () -> assertThat(orderedCrews.get(1)).isEqualTo(crew3),
                () -> assertThat(orderedCrews.getLast()).isEqualTo(crew)
        );
    }

}
