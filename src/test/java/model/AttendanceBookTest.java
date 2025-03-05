package model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import dto.DismissalCrewDto;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import model.policy.EducationTimePolicy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceBookTest {

    @DisplayName("크루 닉네임을 통해 출석 기록을 가져온다")
    @Test
    void getCrewAttendanceHistoryTest() {
        AttendanceHistory attendanceHistory = new AttendanceHistory();
        AttendanceBook attendanceBook = new AttendanceBook(Map.of(
                "짱수", attendanceHistory
        ));
        assertThat(attendanceBook.getAttendanceHistoryByNickname("짱수")).isEqualTo(attendanceHistory);
    }

    @DisplayName("존재하지 않는 크루라면 예외가 발생한다")
    @Test
    void notExistCrewTest() {
        AttendanceHistory attendanceHistory = new AttendanceHistory();
        AttendanceBook attendanceBook = new AttendanceBook(Map.of(
                "짱수", attendanceHistory
        ));
        assertThatThrownBy(() -> attendanceBook.getAttendanceHistoryByNickname("호떡"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 등록되지 않은 닉네임입니다.");
    }

    // 제적 위험자 조회 결과
//- 빙티: 결석 3회, 지각 4회 (면담)
//- 이든: 결석 2회, 지각 5회 (면담)
//- 빙봉: 결석 1회, 지각 6회 (면담)
//- 쿠키: 결석 2회, 지각 3회 (면담)
//- 짱수: 결석 0회, 지각 6회 (경고)
    @DisplayName("모든 제적 위험자 정보를 가져온다")
    @Test
    void getAllDismissalCrewByOrderTest() {
        AttendanceHistory bingteeHistory = AttendanceHistoryFactory.make(Stream.of(
                makeDateTimeByAttendanceType(2, AttendanceType.ABSENT),
                makeDateTimeByAttendanceType(3, AttendanceType.ABSENT),
                makeDateTimeByAttendanceType(4, AttendanceType.ABSENT),
                makeDateTimeByAttendanceType(5, AttendanceType.LATE),
                makeDateTimeByAttendanceType(6, AttendanceType.LATE),
                makeDateTimeByAttendanceType(9, AttendanceType.LATE),
                makeDateTimeByAttendanceType(10, AttendanceType.LATE)
        ));
        AttendanceHistory edenHistory = AttendanceHistoryFactory.make(Stream.of(
                makeDateTimeByAttendanceType(2, AttendanceType.ABSENT),
                makeDateTimeByAttendanceType(3, AttendanceType.ABSENT),
                makeDateTimeByAttendanceType(4, AttendanceType.LATE),
                makeDateTimeByAttendanceType(5, AttendanceType.LATE),
                makeDateTimeByAttendanceType(6, AttendanceType.LATE),
                makeDateTimeByAttendanceType(9, AttendanceType.LATE),
                makeDateTimeByAttendanceType(10, AttendanceType.LATE)
        ));
        AttendanceHistory bingbongHistory = AttendanceHistoryFactory.make(Stream.of(
                makeDateTimeByAttendanceType(2, AttendanceType.ABSENT),
                makeDateTimeByAttendanceType(3, AttendanceType.LATE),
                makeDateTimeByAttendanceType(4, AttendanceType.LATE),
                makeDateTimeByAttendanceType(5, AttendanceType.LATE),
                makeDateTimeByAttendanceType(6, AttendanceType.LATE),
                makeDateTimeByAttendanceType(9, AttendanceType.LATE),
                makeDateTimeByAttendanceType(10, AttendanceType.LATE)
        ));
        AttendanceHistory cookieHistory = AttendanceHistoryFactory.make(Stream.of(
                makeDateTimeByAttendanceType(2, AttendanceType.ABSENT),
                makeDateTimeByAttendanceType(3, AttendanceType.ABSENT),
                makeDateTimeByAttendanceType(4, AttendanceType.LATE),
                makeDateTimeByAttendanceType(5, AttendanceType.LATE),
                makeDateTimeByAttendanceType(6, AttendanceType.LATE),
                makeDateTimeByAttendanceType(9, AttendanceType.NONE),
                makeDateTimeByAttendanceType(10, AttendanceType.NONE)
        ));
        AttendanceHistory zzangsuHistory = AttendanceHistoryFactory.make(Stream.of(
                makeDateTimeByAttendanceType(2, AttendanceType.LATE),
                makeDateTimeByAttendanceType(3, AttendanceType.LATE),
                makeDateTimeByAttendanceType(4, AttendanceType.LATE),
                makeDateTimeByAttendanceType(5, AttendanceType.LATE),
                makeDateTimeByAttendanceType(6, AttendanceType.LATE),
                makeDateTimeByAttendanceType(9, AttendanceType.LATE),
                makeDateTimeByAttendanceType(10, AttendanceType.NONE)
        ));
        AttendanceBook attendanceBook = new AttendanceBook(Map.of(
                "빙봉", bingbongHistory,
                "빙티", bingteeHistory,
                "쿠키", cookieHistory,
                "짱수", zzangsuHistory,
                "이든", edenHistory
        ));

        LocalDate baseDate = LocalDate.of(2024, 12, 11);

        List<DismissalCrewDto> expected = List.of(
                new DismissalCrewDto("빙티", makeTypeCountResult(3, 4, 0), DismissalType.COUNSEL),
                new DismissalCrewDto("이든", makeTypeCountResult(2, 5, 0), DismissalType.COUNSEL),
                new DismissalCrewDto("빙봉", makeTypeCountResult(1, 6, 0), DismissalType.COUNSEL),
                new DismissalCrewDto("쿠키", makeTypeCountResult(2, 3, 2), DismissalType.COUNSEL),
                new DismissalCrewDto("짱수", makeTypeCountResult(0, 6, 1), DismissalType.WARNING)
        );

        assertThat(attendanceBook.getAllDismissalCrewByOrder(baseDate)).containsExactlyElementsOf(expected);
    }

    private static LocalDateTime makeDateTimeByAttendanceType(final int day, final AttendanceType type) {
        LocalDate localDate = LocalDate.of(2024, 12, day);
        LocalTime startTime = EducationTimePolicy.getStartTime(localDate.getDayOfWeek());
        LocalTime lateTime = startTime.plus(type.getThreshold()).plus(Duration.ofMinutes(1));
        return LocalDateTime.of(localDate, lateTime);
    }

    private static Map<AttendanceType, Integer> makeTypeCountResult(
            final int absentCount,
            final int lateCount,
            final int noneCount
    ) {
        return Map.of(
                AttendanceType.ABSENT, absentCount,
                AttendanceType.LATE, lateCount,
                AttendanceType.NONE, noneCount
        );
    }
}
