package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CrewsTest {


    @DisplayName("처벌 관련 데이터를 정렬해서 리턴한다")
    @Test
    void sortCrewsByDisciplinaryStatus() {
        // 결석 3 // 면담
        final List<String> secondCrewDateTimes = List.of("2024-12-03 11:00", "2024-12-04 11:00", "2024-12-05 12:00");
        final Crew secondCrew = generateCrew("카리나", secondCrewDateTimes);

        // 출석 1 결석 2 // 경고
        final List<String> firstCrewDateTimes = List.of("2024-12-03 10:00", "2024-12-04 11:00", "2024-12-05 12:00");
        final Crew firstCrew = generateCrew("장원영", firstCrewDateTimes);

        // 결석 4 // 면담
        final List<String> thirdCrewDateTimes = List.of("2024-12-03 12:00", "2024-12-04 11:00", "2024-12-05 12:00",
                "2024-12-06 12:00");
        final Crew thirdCrew = generateCrew("설윤", thirdCrewDateTimes);

        // 결석 4 // 면담
        final List<String> fourthCrewDateTimes = List.of("2024-12-03 12:00", "2024-12-04 11:00", "2024-12-05 12:00",
                "2024-12-06 12:00");
        final Crew fourthCrew = generateCrew("유나", fourthCrewDateTimes);
        final Crews crews = new Crews(List.of(fourthCrew, secondCrew, firstCrew, thirdCrew));
        final List<Crew> sortedDisciplinaryCrews = crews.findSortedDisciplinaryCrews();
        final List<Crew> expected = List.of(thirdCrew, fourthCrew, secondCrew, firstCrew);

        assertThat(sortedDisciplinaryCrews).containsExactlyElementsOf(expected);
    }

    private static Crew generateCrew(final String nickname, final List<String> inputDateTimes) {
        final Nickname nickname1 = new Nickname(nickname);
        final List<AttendanceRecord> attendanceRecordGroup = new ArrayList<>();

        for (final String inputDateTime : inputDateTimes) {
            final AttendanceDateTime dateTime = AttendanceDateTime.from(inputDateTime);
            final AttendanceRecord attendanceRecord = new AttendanceRecord(dateTime);
            attendanceRecordGroup.add(attendanceRecord);
        }
        final AttendanceRecords attendanceRecords = new AttendanceRecords(new TreeSet<>(attendanceRecordGroup));
        final AttendanceStatusCounts attendanceStatusCounts = attendanceRecords.getAttendanceStatusCounts();
        final int absence = attendanceStatusCounts.getAbsence();
        final int late = attendanceStatusCounts.getLate();
        final DisciplinaryStatus disciplinaryStatus = DisciplinaryStatus.findByAbsenceAndLatenessCount(absence,
                late);

        return new Crew(nickname1, attendanceRecords, disciplinaryStatus);
    }
}
