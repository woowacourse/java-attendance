import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.AbsenceLevel;
import domain.AttendanceHistory;
import domain.AttendanceResult;
import domain.Attendances;
import domain.Crew;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceHistoryTest {

    private AttendanceHistory attendanceHistory;
    private Crew crew;

    @BeforeEach
    void setUp() {
        Map<Crew, Attendances> attendanceMap = new HashMap<>();
        crew = new Crew("벡터");
        attendanceMap.put(crew, new Attendances(new ArrayList<>()));
        attendanceHistory = new AttendanceHistory(attendanceMap);
    }

    @Test
    @DisplayName("닉네임과 시간을 통해 기록 - 출석")
    void attendanceTest() {
        LocalDateTime attendanceTime = LocalDateTime.of(2024, 12, 3, 10, 0);
        AttendanceResult attendanceResult = attendanceHistory.checkAttendance(crew, attendanceTime);
        assertThat(attendanceResult).isEqualTo(AttendanceResult.ATTENDANCE);
    }

    @Test
    @DisplayName("닉네임과 시간을 통해 기록 - 지각")
    void lateTest() {
        LocalDateTime attendanceTime = LocalDateTime.of(2024, 12, 3, 10, 6);
        AttendanceResult attendanceResult = attendanceHistory.checkAttendance(crew, attendanceTime);
        assertThat(attendanceResult).isEqualTo(AttendanceResult.LATE);
    }

    @Test
    @DisplayName("닉네임과 시간을 통해 기록 - 결석")
    void absentTest() {
        LocalDateTime attendanceTime = LocalDateTime.of(2024, 12, 3, 10, 35);
        AttendanceResult attendanceResult = attendanceHistory.checkAttendance(crew, attendanceTime);
        assertThat(attendanceResult).isEqualTo(AttendanceResult.ABSENT);
    }

    @Test
    @DisplayName("닉네임과 시간을 통해 기록(월요일) - 출석")
    void mondayAttendanceTest() {
        LocalDateTime attendanceTime = LocalDateTime.of(2024, 12, 2, 13, 0);
        AttendanceResult attendanceResult = attendanceHistory.checkAttendance(crew, attendanceTime);
        assertThat(attendanceResult).isEqualTo(AttendanceResult.ATTENDANCE);
    }

    @Test
    @DisplayName("닉네임과 시간을 통해 기록(월요일) - 지각")
    void mondayLateTest() {
        LocalDateTime attendanceTime = LocalDateTime.of(2024, 12, 2, 13, 10);
        AttendanceResult attendanceResult = attendanceHistory.checkAttendance(crew, attendanceTime);
        assertThat(attendanceResult).isEqualTo(AttendanceResult.LATE);
    }

    @Test
    @DisplayName("닉네임과 시간을 통해 기록(월요일) - 결석")
    void mondayAbsentTest() {
        LocalDateTime attendanceTime = LocalDateTime.of(2024, 12, 2, 13, 35);
        AttendanceResult attendanceResult = attendanceHistory.checkAttendance(crew, attendanceTime);
        assertThat(attendanceResult).isEqualTo(AttendanceResult.ABSENT);
    }

    @Test
    @DisplayName("주말 출석 예외 처리")
    void weekendTest() {
        LocalDateTime attendanceTime = LocalDateTime.of(2024, 12, 7, 10, 0);
        assertThatThrownBy(() -> attendanceHistory.checkAttendance(crew, attendanceTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
    }

    @Test
    @DisplayName("크리스마스 예외처리")
    void holidayTest() {
        LocalDateTime attendanceTime = LocalDateTime.of(2024, 12, 25, 10, 0);
        assertThatThrownBy(() -> attendanceHistory.checkAttendance(crew, attendanceTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
    }

    @Test
    @DisplayName("운영시간 외 출석 - 이른 시간")
    void operatingTimeFastTest() {
        LocalDateTime attendanceTime = LocalDateTime.of(2024, 12, 3, 7, 0);
        assertThatThrownBy(() -> attendanceHistory.checkAttendance(crew, attendanceTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
    }

    @Test
    @DisplayName("운영시간 외 출석 - 늦은 시간")
    void operatingTimeLateTest() {
        LocalDateTime attendanceTime = LocalDateTime.of(2024, 12, 3, 23, 5);
        assertThatThrownBy(() -> attendanceHistory.checkAttendance(crew, attendanceTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
    }

    @Test
    @DisplayName("중복 출석 예외 처리")
    void duplicateAttendanceTest() {
        LocalDateTime attendanceTime = LocalDateTime.of(2024, 12, 3, 10, 0);
        attendanceHistory.checkAttendance(crew, attendanceTime);
        assertThatThrownBy(() -> attendanceHistory.checkAttendance(crew, attendanceTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
    }

    @Test
    @DisplayName("출석 기록 수정 ")
    void editAttendanceTest() {
        Crew crew = new Crew("벡터");
        LocalDateTime firstAttendanceTime = LocalDateTime.of(2024, 2, 21, 9, 0);
        attendanceHistory.checkAttendance(crew, firstAttendanceTime);
        LocalDateTime newAttendanceTime = LocalDateTime.of(2024, 2, 21, 10, 0);
        LocalDateTime oldAttendanceTime = attendanceHistory.editAttendance(crew, newAttendanceTime);
        assertThat(oldAttendanceTime).isEqualTo(firstAttendanceTime);
    }


    @Test
    @DisplayName("출석 기록에 이름이 없을 경우")
    void noEditNameTest() {
        Crew invalidName = new Crew("제프");
        LocalDateTime attendanceTime = LocalDateTime.of(2024, 2, 20, 10, 0);
        assertThatThrownBy(() -> attendanceHistory.editAttendance(invalidName, attendanceTime))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("존재 x인 날짜 수정 시도")
    void noAttendanceDayTest() {
        Crew crew = new Crew("벡터");
        Map<Crew, Attendances> attendanceMap = new HashMap<>();
        attendanceMap.put(crew, new Attendances(new ArrayList<>()));
        attendanceHistory = new AttendanceHistory(attendanceMap);
        attendanceHistory.checkAttendance(crew, LocalDateTime.of(2024, 12, 19, 10, 0));
        LocalDateTime dateTime = LocalDateTime.of(2024, 2, 20, 10, 0);
        assertThatThrownBy(() -> attendanceHistory.editAttendance(crew, dateTime))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("이름을 통해 출석 기록  자체를 확인")
    void getCrewAttendanceHistory() {
        LocalDateTime attendanceTime = LocalDateTime.of(2024, 12, 20, 10, 0);
        attendanceHistory.checkAttendance(crew, attendanceTime);
        Attendances actualAttendanceDateTimes = attendanceHistory.getAttendances(crew);
        assertThat(actualAttendanceDateTimes).isNotNull();
        assertThat(actualAttendanceDateTimes.haveAttendanceDate(LocalDate.from(attendanceTime))).isTrue();
    }

    @Test
    @DisplayName("출석 횟수를 확인")
    void getAttendanceCountTest() {
        LocalDateTime attendanceTime = LocalDateTime.of(2024, 12, 2, 10, 0);
        LocalDateTime attendanceTime1 = LocalDateTime.of(2024, 12, 3, 10, 0);
        LocalDateTime attendanceTime2 = LocalDateTime.of(2024, 12, 4, 10, 0);
        LocalDateTime attendanceTime3 = LocalDateTime.of(2024, 12, 5, 10, 15);
        LocalDateTime attendanceTime4 = LocalDateTime.of(2024, 12, 6, 11, 0);
        LocalDateTime attendanceTime5 = LocalDateTime.of(2024, 12, 9, 13, 0);
        LocalDateTime attendanceTime6 = LocalDateTime.of(2024, 12, 10, 10, 0);
        attendanceHistory.checkAttendance(crew, attendanceTime);
        attendanceHistory.checkAttendance(crew, attendanceTime1);
        attendanceHistory.checkAttendance(crew, attendanceTime2);
        attendanceHistory.checkAttendance(crew, attendanceTime3);
        attendanceHistory.checkAttendance(crew, attendanceTime4);
        attendanceHistory.checkAttendance(crew, attendanceTime5);
        attendanceHistory.checkAttendance(crew, attendanceTime6);

        LocalDate standardDate = LocalDate.of(2024, 12, 10);
        int presentCount = attendanceHistory.getAttendanceCount(crew, standardDate);
        assertThat(presentCount).isEqualTo(4);
    }

    @Test
    @DisplayName("지각 횟수를 확인")
    void getLateCountTest() {
        LocalDateTime attendanceTime = LocalDateTime.of(2024, 12, 2, 10, 0);
        LocalDateTime attendanceTime1 = LocalDateTime.of(2024, 12, 3, 10, 0);
        LocalDateTime attendanceTime2 = LocalDateTime.of(2024, 12, 4, 10, 0);
        LocalDateTime attendanceTime3 = LocalDateTime.of(2024, 12, 5, 10, 15);
        LocalDateTime attendanceTime4 = LocalDateTime.of(2024, 12, 6, 11, 0);
        LocalDateTime attendanceTime5 = LocalDateTime.of(2024, 12, 9, 13, 6);
        LocalDateTime attendanceTime6 = LocalDateTime.of(2024, 12, 10, 10, 0);
        attendanceHistory.checkAttendance(crew, attendanceTime);
        attendanceHistory.checkAttendance(crew, attendanceTime1);
        attendanceHistory.checkAttendance(crew, attendanceTime2);
        attendanceHistory.checkAttendance(crew, attendanceTime3);
        attendanceHistory.checkAttendance(crew, attendanceTime4);
        attendanceHistory.checkAttendance(crew, attendanceTime5);
        attendanceHistory.checkAttendance(crew, attendanceTime6);

        LocalDate standardDate = LocalDate.of(2024, 12, 10);
        int presentCount = attendanceHistory.getLateCount(crew, standardDate);
        assertThat(presentCount).isEqualTo(2);
    }

    @Test
    @DisplayName("결석 횟수를 확인")
    void getAbsentCountTest() {
        LocalDateTime attendanceTime = LocalDateTime.of(2024, 12, 2, 10, 0);
        LocalDateTime attendanceTime1 = LocalDateTime.of(2024, 12, 3, 10, 0);
        LocalDateTime attendanceTime2 = LocalDateTime.of(2024, 12, 4, 10, 0);
        LocalDateTime attendanceTime3 = LocalDateTime.of(2024, 12, 5, 10, 15);
        LocalDateTime attendanceTime4 = LocalDateTime.of(2024, 12, 6, 11, 0);
        LocalDateTime attendanceTime5 = LocalDateTime.of(2024, 12, 9, 13, 6);
        LocalDateTime attendanceTime6 = LocalDateTime.of(2024, 12, 10, 10, 0);
        attendanceHistory.checkAttendance(crew, attendanceTime);
        attendanceHistory.checkAttendance(crew, attendanceTime1);
        attendanceHistory.checkAttendance(crew, attendanceTime2);
        attendanceHistory.checkAttendance(crew, attendanceTime3);
        attendanceHistory.checkAttendance(crew, attendanceTime4);
        attendanceHistory.checkAttendance(crew, attendanceTime5);
        attendanceHistory.checkAttendance(crew, attendanceTime6);

        LocalDate standardDate = LocalDate.of(2024, 12, 10);
        int presentCount = attendanceHistory.getAbsentCount(crew, standardDate);
        assertThat(presentCount).isEqualTo(1);
    }

    @Test
    @DisplayName("기록 없으면 결석으로 취급해서 결석 횟수를 확인")
    void getRealAbsentCountTest() {
        LocalDateTime attendanceTime = LocalDateTime.of(2024, 12, 2, 10, 0);
        LocalDateTime attendanceTime3 = LocalDateTime.of(2024, 12, 5, 10, 15);
        LocalDateTime attendanceTime4 = LocalDateTime.of(2024, 12, 6, 11, 0);
        LocalDateTime attendanceTime5 = LocalDateTime.of(2024, 12, 9, 13, 6);
        LocalDateTime attendanceTime6 = LocalDateTime.of(2024, 12, 10, 10, 0);
        attendanceHistory.checkAttendance(crew, attendanceTime);
        attendanceHistory.checkAttendance(crew, attendanceTime3);
        attendanceHistory.checkAttendance(crew, attendanceTime4);
        attendanceHistory.checkAttendance(crew, attendanceTime5);
        attendanceHistory.checkAttendance(crew, attendanceTime6);
        LocalDate standardDate = LocalDate.of(2024, 12, 10);
        int presentCount = attendanceHistory.getAbsentCount(crew, standardDate);
        assertThat(presentCount).isEqualTo(3);
    }

    @Test
    @DisplayName("이름으로 경고 대상자인지 판별")
    void WarningTest() {
        LocalDateTime attendanceTime = LocalDateTime.of(2024, 12, 2, 10, 0);
        LocalDateTime attendanceTime2 = LocalDateTime.of(2024, 12, 4, 10, 0);
        LocalDateTime attendanceTime3 = LocalDateTime.of(2024, 12, 5, 10, 15);
        LocalDateTime attendanceTime4 = LocalDateTime.of(2024, 12, 6, 11, 0);
        LocalDateTime attendanceTime5 = LocalDateTime.of(2024, 12, 9, 13, 6);
        LocalDateTime attendanceTime6 = LocalDateTime.of(2024, 12, 10, 10, 0);
        attendanceHistory.checkAttendance(crew, attendanceTime);
        attendanceHistory.checkAttendance(crew, attendanceTime2);
        attendanceHistory.checkAttendance(crew, attendanceTime3);
        attendanceHistory.checkAttendance(crew, attendanceTime4);
        attendanceHistory.checkAttendance(crew, attendanceTime5);
        attendanceHistory.checkAttendance(crew, attendanceTime6);
        LocalDate standardDate = LocalDate.of(2024, 12, 10);
        AbsenceLevel absenceLevel = attendanceHistory.getAbsenceLevel(crew, standardDate);
        assertThat(absenceLevel).isEqualTo(AbsenceLevel.WARNING);
    }

    @Test
    @DisplayName("이름으로 면담 대상자인지 판별")
    void MeetingTest() {
        LocalDateTime attendanceTime = LocalDateTime.of(2024, 12, 2, 10, 0);
        LocalDateTime attendanceTime3 = LocalDateTime.of(2024, 12, 5, 10, 15);
        LocalDateTime attendanceTime4 = LocalDateTime.of(2024, 12, 6, 11, 0);
        LocalDateTime attendanceTime5 = LocalDateTime.of(2024, 12, 9, 13, 6);
        LocalDateTime attendanceTime6 = LocalDateTime.of(2024, 12, 10, 10, 0);
        attendanceHistory.checkAttendance(crew, attendanceTime);
        attendanceHistory.checkAttendance(crew, attendanceTime3);
        attendanceHistory.checkAttendance(crew, attendanceTime4);
        attendanceHistory.checkAttendance(crew, attendanceTime5);
        attendanceHistory.checkAttendance(crew, attendanceTime6);
        LocalDate standardDate = LocalDate.of(2024, 12, 10);
        AbsenceLevel absenceLevel = attendanceHistory.getAbsenceLevel(crew, standardDate);
        assertThat(absenceLevel).isEqualTo(AbsenceLevel.MEETING);
    }

    @Test
    @DisplayName("이름으로 제적 대상자인지 판별")
    void GetOutTest() {
        LocalDateTime attendanceTime4 = LocalDateTime.of(2024, 12, 6, 11, 0);
        LocalDateTime attendanceTime5 = LocalDateTime.of(2024, 12, 9, 13, 6);
        LocalDateTime attendanceTime6 = LocalDateTime.of(2024, 12, 10, 10, 0);
        attendanceHistory.checkAttendance(crew, attendanceTime4);
        attendanceHistory.checkAttendance(crew, attendanceTime5);
        attendanceHistory.checkAttendance(crew, attendanceTime6);
        LocalDate standardDate = LocalDate.of(2024, 12, 10);
        AbsenceLevel absenceLevel = attendanceHistory.getAbsenceLevel(crew, standardDate);
        assertThat(absenceLevel).isEqualTo(AbsenceLevel.GET_OUT);
    }

}


