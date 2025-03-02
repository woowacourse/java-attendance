package domain;

import exception.AppException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.TreeMap;

import static org.assertj.core.api.Assertions.*;

class AttendanceBookTest {

    AttendanceBook attendanceBook;

    @BeforeEach
    void setUp() {
        Crew crew = Crew.of("조로");
        CheckInDate checkInDate = CheckInDate.of(2024, 12, 3);
        CheckInTime checkInTime = CheckInTime.of(10, 6);

        TreeMap<CheckInDate, CheckInTime> checkInMap = new TreeMap<>();
        checkInMap.put(checkInDate, checkInTime);

        CheckInHistory checkInHistory = CheckInHistory.of(checkInMap);

        TreeMap<Crew, CheckInHistory> attendance = new TreeMap<>();
        attendance.put(crew, checkInHistory);

        attendanceBook = AttendanceBook.of(attendance);
    }

    @Test
    @DisplayName("출석부에 특정 이름이 있으면 해당 크루의 체크인 기록들을 정상적으로 반환")
    void findCheckInHistoryTest() {
        //given
        String name = "조로";
        //when
        CheckInHistory foundHistory = attendanceBook.findHistoryByName(Crew.of(name));
        //then
        assertThat(foundHistory).isNotNull();
        assertThat(foundHistory.getCheckInCount()).isEqualTo(1);
    }

    @Test
    @DisplayName("출석부에 특정 이름이 없으면 예외를 발생")
    void noNameInAttendanceBookException() {
        //given
        String name = "차니";
        //when
        //then
        assertThatThrownBy(() -> attendanceBook.findHistoryByName(Crew.of(name)))
                .isInstanceOf(AppException.class)
                .hasMessageContaining(AppException.PREFIX);
    }

    @Test
    @DisplayName("특정 크루에 대해 체크인 수행")
    void checkInTest() {
        //given
        Crew crew = Crew.of("조로");
        CheckInDate checkInDate = CheckInDate.of(2024, 12, 4);
        CheckInTime checkInTime = CheckInTime.of(10, 0);
        CheckInHistory historyByName = attendanceBook.findHistoryByName(crew);
        //when
        //then
        assertThatCode(() -> attendanceBook.checkIn(historyByName, checkInDate, checkInTime))
                .doesNotThrowAnyException();
    }
}