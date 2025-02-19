package domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

class AttendanceTest {

    @Test
    @DisplayName("크루의 이름을 입력받아 해당 크루의 출석부인지 판단")
    void isSameNameTest() {
        String name = "조로";
        Attendance attendance = Attendance.of(name, List.of());
        assertThat(attendance.isSameName("조로")).isTrue();
    }

 /*   @Test
    @DisplayName("크루원의 결석이 2회 이상일 때 경고 대상자임을 반환")
    void warningCrewTest() {

    }

    @Test
    @DisplayName("크루원의 결석이 3회 이상일 때 면담 대상자임을 반환")
    void intervieweeCrewTest() {

    }

    @Test
    @DisplayName("크루원의 결석이 5회 초과일 때 제적 대상자임을 반환")
    void expulsionCrewTest() {

    }*/
}