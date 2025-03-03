package domain.crew;

import domain.DisciplinaryStatus;
import domain.dateTime.AttendanceDateTime;
import domain.record.AttendanceRecord;
import domain.record.AttendanceRecords;
import domain.record.AttendanceStatusCounts;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import util.CsvReader;

public class CrewGenerator {

    public Crews generate(final String filePath) {
        final List<String[]> crews = CsvReader.readFile(filePath);
        final Map<Nickname, TreeSet<AttendanceRecord>> crewData = new HashMap<>();
        for (final String[] crew : crews) {
            final String inputNickname = crew[0];
            final String inputDateTime = crew[1];
            final Nickname nickname = new Nickname(inputNickname);
            final AttendanceDateTime attendanceDateTime = AttendanceDateTime.from(inputDateTime);
            final AttendanceRecord attendanceRecord = new AttendanceRecord(attendanceDateTime);
            crewData.computeIfAbsent(nickname, k -> new TreeSet<>()).add(attendanceRecord);
        }

        final List<Crew> crewGroup = new ArrayList<>();
        crewData.forEach((nickname, attendanceRecordGroup) -> {
            final AttendanceRecords attendanceRecords = new AttendanceRecords(attendanceRecordGroup);
            final AttendanceStatusCounts attendanceStatusCounts = attendanceRecords.getAttendanceStatusCounts();
            final int absence = attendanceStatusCounts.getAbsence();
            final int late = attendanceStatusCounts.getLate();
            final Crew crew = new Crew(nickname, attendanceRecords,
                    DisciplinaryStatus.findByAbsenceAndLatenessCount(absence, late));
            crewGroup.add(crew);
        });

        return new Crews(crewGroup);
    }
}
