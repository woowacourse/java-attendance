package domain;

import static org.assertj.core.api.Assertions.assertThat;

import infrastructure.file.AttendanceFileReader;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import org.junit.jupiter.api.Test;

public class DangerousStatusTest {

    @Test
    void 제적_대상자인지_확인한다() {
        AttendanceRecord attendanceRecord = new AttendanceRecord(() -> LocalDate.of(2024, 12, 9));

        DangerousStatus dangerousStatus = DangerousStatus.of(
                attendanceRecord.countStatus(AttendanceStatus.LATE),
                attendanceRecord.countStatus(AttendanceStatus.ABSENCE));

        assertThat(dangerousStatus).isEqualTo(DangerousStatus.DISMISSAL);

        DangerousStatus dangerousStatus1 = DangerousStatus.of(3, 5);
        assertThat(dangerousStatus1).isEqualTo(DangerousStatus.DISMISSAL);
    }

    @Test
    void 면담_대상자인지_확인한다() {
        AttendanceRecord attendanceRecord = new AttendanceRecord(() -> LocalDate.of(2024, 12, 4));

        DangerousStatus dangerousStatus = DangerousStatus.of(
                attendanceRecord.countStatus(AttendanceStatus.LATE),
                attendanceRecord.countStatus(AttendanceStatus.ABSENCE));

        assertThat(dangerousStatus).isEqualTo(DangerousStatus.INTERVIEW);
    }

    @Test
    void 경고_대상자인지_확인한다() {
        AttendanceRecord attendanceRecord = new AttendanceRecord(() -> LocalDate.of(2024, 12, 3));

        DangerousStatus dangerousStatus = DangerousStatus.of(
                attendanceRecord.countStatus(AttendanceStatus.LATE),
                attendanceRecord.countStatus(AttendanceStatus.ABSENCE));

        assertThat(dangerousStatus).isEqualTo(DangerousStatus.WARNING);
    }

    @Test
    void 모범인지_확인한다() {
        AttendanceRecord attendanceRecord = new AttendanceRecord(() -> LocalDate.of(2024, 12, 2));

        DangerousStatus dangerousStatus = DangerousStatus.of(
                attendanceRecord.countStatus(AttendanceStatus.LATE),
                attendanceRecord.countStatus(AttendanceStatus.ABSENCE));

        assertThat(dangerousStatus).isEqualTo(DangerousStatus.GOOD);
    }

    @Test
    void 제적_위험자_조회_정렬_테스트() {
        AttendanceManager attendanceManager = new AttendanceManager(() -> LocalDate.of(2024, 12, 14));
        AttendanceFileReader attendanceFileReader = new AttendanceFileReader();
        attendanceFileReader.readFiles(attendanceManager);

        List<Crew> crews = attendanceManager.getCrews();

        Comparator<Crew> comparator = (crew1, crew2) -> {
            int i = crew2.getAttendanceRecord().totalAbsenceCount() - crew1.getAttendanceRecord().totalAbsenceCount();
            if (i == 0) {
                int j = crew2.getAttendanceRecord().countStatus(AttendanceStatus.LATE)
                        % DangerousStatus.LATE_TO_ABSENCE_RATE
                        - crew1.getAttendanceRecord().countStatus(AttendanceStatus.LATE)
                        % DangerousStatus.LATE_TO_ABSENCE_RATE;
                if (j == 0) {
                    return crew1.getNickname().compareTo(crew2.getNickname());
                }
                return j;
            }
            return i;
        };

        crews.sort(comparator);
        crews.forEach(crew -> System.out.println(crew.getNickname()));
    }


}
