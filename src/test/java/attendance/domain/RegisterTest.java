package attendance.domain;

import attendance.domain.constant.AttendanceStatus;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

class RegisterTest {

    private Register register;
    private Crew crew;
    private LocalDate currentDate;

    @BeforeEach
    void setUp() {
        // Test setup - 현재 날짜와 Crew 객체를 생성
        currentDate = LocalDate.of(2025, 2, 21);
        Crew crew1 = Crew.from("제프리");
        Crew crew2 = Crew.from("부기");
        crew = Crew.from("우가");
        Crews crews = new Crews(Set.of(crew, crew1, crew2));
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

    @DisplayName("2월 1일부터 21일까지 평일 수는 총 15일이라, 출석기록은 총 15개 있어야 하는지 검증하는 테스트 입니다.")
    @Test
    void 출석기록_계산_테스트() {
        //given
        LocalDateTime firstTime = LocalDateTime.of(currentDate, java.time.LocalTime.of(9, 59));
        register.modifyInfo(crew, firstTime);

        //when
        AttendanceRegistry result = register.checkAttendanceHistory(crew);

        //then
        assertThat(result.getDateInfos().size()).isEqualTo(15);
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
    void 위험군_팀_조회_테스트() {
        //given
        Map<Crew, List<LocalDateTime>> attendanceTimes = new HashMap<>();

        attendanceTimes.put(crew, List.of(LocalDateTime.of(2025,2,3,12,59),
                LocalDateTime.of(2025,2,4,10,31),
                LocalDateTime.of(2025, 2, 5, 10, 31)));
        register.fromCrewAttendanceTimeFile(attendanceTimes);

        //when
        Map<Crew, List<Integer>> riskCrews = register.findAllExpertRiskCrews();

        //then
        assertThat(riskCrews).containsKey(crew);
    }

}
