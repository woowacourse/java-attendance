package domain.crew;

import static controller.AttendanceCommandController.REFERENCE_DAY;
import static controller.AttendanceCommandController.REFERENCE_MONTH;
import static controller.AttendanceCommandController.REFERENCE_YEAR;

import domain.DisciplinaryStatus;
import domain.dateTime.AttendanceDateTime;
import domain.record.AttendanceRecord;
import domain.record.AttendanceRecords;
import domain.record.AttendanceStatusCounts;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import util.CsvReader;
import util.DateUtil;

public class CrewGenerator {

    public Crews generate(final String filePath) {
        final List<String[]> crews = CsvReader.readFile(filePath);
        final Map<Nickname, TreeSet<AttendanceRecord>> crewData = generateCrewData(crews);
        final List<Crew> crewGroup = new ArrayList<>();
        crewData.forEach((nickname, attendanceRecordGroup) -> {
            final AttendanceRecords attendanceRecords = new AttendanceRecords(attendanceRecordGroup);
            processAbsence(attendanceRecords);
            final Crew crew = generateCrew(nickname, attendanceRecords);
            crewGroup.add(crew);
        });
        return new Crews(crewGroup);
    }

    private static Map<Nickname, TreeSet<AttendanceRecord>> generateCrewData(final List<String[]> crews) {
        final Map<Nickname, TreeSet<AttendanceRecord>> crewData = new HashMap<>();
        for (final String[] crew : crews) {
            final String inputNickname = crew[0];
            final String inputDateTime = crew[1];
            final Nickname nickname = new Nickname(inputNickname);
            final AttendanceDateTime attendanceDateTime = AttendanceDateTime.from(inputDateTime);
            final AttendanceRecord attendanceRecord = new AttendanceRecord(attendanceDateTime);
            crewData.computeIfAbsent(nickname, k -> new TreeSet<>()).add(attendanceRecord);
        }
        return crewData;
    }

    private static void processAbsence(final AttendanceRecords attendanceRecords) {
        final List<Integer> validDays = DateUtil.calculateValidDays(
                REFERENCE_YEAR,
                REFERENCE_MONTH,
                REFERENCE_DAY - 1
        );
        validDays.forEach(day -> {
            final LocalDate localDate = LocalDate.of(REFERENCE_YEAR, REFERENCE_MONTH, day);
            final AttendanceRecord attendanceRecord = attendanceRecords.findByDate(localDate);

            if (attendanceRecord == null) {
                final AttendanceDateTime attendanceDateTime = AttendanceDateTime.createAbsence(localDate);
                attendanceRecords.add(new AttendanceRecord(attendanceDateTime));
            }
        });
    }

    private static Crew generateCrew(final Nickname nickname, final AttendanceRecords attendanceRecords) {
        final AttendanceStatusCounts attendanceStatusCounts = attendanceRecords.getAttendanceStatusCounts();
        final int absence = attendanceStatusCounts.getAbsence();
        final int late = attendanceStatusCounts.getLate();
        final DisciplinaryStatus disciplinaryStatus = DisciplinaryStatus.findByAbsenceAndLatenessCount(absence,
                late);
        return new Crew(nickname, attendanceRecords, disciplinaryStatus);
    }
}
