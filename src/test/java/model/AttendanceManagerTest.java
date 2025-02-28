package model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

class AttendanceManagerTest {

    @Test
    @DisplayName("Nickname 객체로 AttendanceBook 찾는 메서드가 잘 작동하는 지 테스트")
    void findByNicknameSuccess() {

        // given
        final Crew crew1 = Crew.of("칼리");
        final Crew crew2 = Crew.of("피케이");
        final Crew crew3 = Crew.of("새로이");

        final AttendanceBook attendanceBook = new AttendanceBook(new TreeSet<>());

        final Map<Crew, AttendanceBook> attendanceBookMap = new HashMap<>();
        attendanceBookMap.put(crew1, attendanceBook);
        attendanceBookMap.put(crew2, attendanceBook);
        attendanceBookMap.put(crew3, attendanceBook);
        final AttendanceManager attendanceManager = new AttendanceManager(attendanceBookMap);

        final Nickname nicknameSrc = new Nickname("칼리");

        // when
        final Crew findCrew = attendanceManager.findByNickname(nicknameSrc);

        // then
        Assertions.assertThat(crew1.getNickname()).isEqualTo(findCrew.getNickname());
    }

    @Test
    @DisplayName("존재하지 않는 Nickname 객체로 AttendanceBook 찾는 메서드가 예외 처리를 하는 지")
    void findByNicknameFailure() {

        // given
        final Crew crew1 = Crew.of("칼리");
        final Crew crew2 = Crew.of("피케이");
        final Crew crew3 = Crew.of("새로이");

        final AttendanceBook attendanceBook = new AttendanceBook(new TreeSet<>());

        final Map<Crew, AttendanceBook> attendanceBookMap = new HashMap<>();
        attendanceBookMap.put(crew1, attendanceBook);
        attendanceBookMap.put(crew2, attendanceBook);
        attendanceBookMap.put(crew3, attendanceBook);
        final AttendanceManager attendanceManager = new AttendanceManager(attendanceBookMap);

        final Nickname nicknameSrc = new Nickname("매트");

        // when
        // then
        Assertions.assertThatThrownBy(
                () -> attendanceManager.findByNickname(nicknameSrc)
        ).isInstanceOf(IllegalArgumentException.class);
    }
}