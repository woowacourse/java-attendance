package domain.crew.comparator;

import static org.assertj.core.api.Assertions.assertThat;

import domain.attendance.comparator.AttendanceTimesComparator;
import domain.crew.Crew;
import domain.crew.CrewAttendance;
import domain.testdata.AttendanceTestData.AttendanceTimesData;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CrewAttendanceComparatorTest {

    @Test
    @DisplayName("CrewAttedance에 대한 정렬 기준 확인")
    void compareCrewAttendanceTest() {
        // given
        CrewAttendance counselingCrewAttendance = CrewAttendance.of(
                Crew.of("차니"), AttendanceTimesData.createCounselingAttendanceTimes()
        );
        CrewAttendance warnedCrewAttendance = CrewAttendance.of(
                Crew.of("포비"), AttendanceTimesData.createWarnedAttendanceTimes()
        );
        CrewAttendance samePenaltyWarnedCrewAttendance = CrewAttendance.of(
                Crew.of("가나"), AttendanceTimesData.createWarnedAttendanceTimes()
        );
        CrewAttendanceComparator crewAttendanceComparator = new CrewAttendanceComparator(
                new AttendanceTimesComparator(LocalDate.of(2024, 12, 31))
        );

        // when
        int negative = crewAttendanceComparator.compare(warnedCrewAttendance, counselingCrewAttendance);
        int positive = crewAttendanceComparator.compare(counselingCrewAttendance, warnedCrewAttendance);
        int negative1 = crewAttendanceComparator.compare(samePenaltyWarnedCrewAttendance, warnedCrewAttendance);

        // then
        assertThat(negative).isLessThan(0);
        assertThat(positive).isGreaterThan(0);
        assertThat(negative1).isLessThan(0);
    }
}
