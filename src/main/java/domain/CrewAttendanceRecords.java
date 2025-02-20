package domain;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class CrewAttendanceRecords {
    private static final int HEADER_ROW = 1;
    private static final int CREW_INDEX = 0;
    private static final int RECORD_INDEX = 1;

    private final Map<Crew, AttendanceRecords> crewAttendanceRecords = new HashMap<>();

    public CrewAttendanceRecords(String path, DateGenerator dateGenerator) {
        List<String> rows = readContent(path).stream().skip(HEADER_ROW).toList();
        for (String row : rows) {
            Crew crew = new Crew(row.split(",")[CREW_INDEX]);
            AttendanceRecord attendanceRecord = new AttendanceRecord(row.split(",")[RECORD_INDEX]);
            AttendanceRecords existingRecords = this.crewAttendanceRecords.getOrDefault(crew, new AttendanceRecords());
            existingRecords.addRecord(attendanceRecord);
            this.crewAttendanceRecords.put(crew, existingRecords);
        }
        crewAttendanceRecords.values().forEach(attendanceRecord -> attendanceRecord.fillAbsences(dateGenerator));
    }

    private List<String> readContent(String path) {
        if (path.isEmpty()) {
            throw new IllegalStateException("");
        }
        return getStrings(path);
    }

    private List<String> getStrings(String path) {
        try {
            InputStream inputStream = CrewAttendanceRecords.class.getResourceAsStream(path);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            return reader.lines().toList();
        } catch (NullPointerException e) {
            throw new IllegalStateException("");
        }
    }

    public boolean hasCrew(Crew crew) {
        return this.crewAttendanceRecords.containsKey(crew);
    }

    public boolean hasRecord(Crew crew, LocalDate date) {
        if (!hasCrew(crew)) {
            return false;
        }
        AttendanceRecords records = this.crewAttendanceRecords.get(crew);
        return records.hasRecordOfDate(date);
    }

    public AttendanceRecord updateAttendanceRecord(Crew crew, AttendanceRecord newAttendanceRecord) {
        validateCrewPresence(crew);
        AttendanceRecords records = crewAttendanceRecords.get(crew);
        records.addRecord(newAttendanceRecord);
        return records.removeRecord(newAttendanceRecord.getDate());
    }

    public int getPresentCount(Crew crew) {
        validateCrewPresence(crew);
        return crewAttendanceRecords.get(crew).getPresentCount();
    }

    public int getTardyCount(Crew crew) {
        validateCrewPresence(crew);
        return crewAttendanceRecords.get(crew).getTardyCount();
    }

    public int getAbsentCount(Crew crew) {
        validateCrewPresence(crew);
        return crewAttendanceRecords.get(crew).getAbsentCount();
    }

    private void validateCrewPresence(Crew crew) {
        if (!hasCrew(crew)) {
            throw new IllegalArgumentException("[ERROR] 등록되지 않은 닉네임입니다.");
        }
    }

    public AttendanceRecord checkIn(Crew crew, LocalTime time, DateGenerator dateGenerator) {
        validateCrewPresence(crew);
        AttendanceRecords attendanceRecords = crewAttendanceRecords.get(crew);
        validatePresence(attendanceRecords, dateGenerator);
        AttendanceRecord attendanceRecord = new AttendanceRecord(time, dateGenerator);
        attendanceRecords.addRecord(attendanceRecord);
        return attendanceRecord;
    }

    private void validatePresence(AttendanceRecords attendanceRecords, DateGenerator dateGenerator) {
        if (attendanceRecords.hasRecordOfDate(dateGenerator.generate())) {
            throw new IllegalArgumentException("[ERROR] 이미 출석을 확인하였습니다. 필요한 경우 수정 기능을 이용해 주세요.");
        }
    }

    public List<AttendanceRecord> getSortedRecords(Crew crew) {
        return crewAttendanceRecords.get(crew).getSortedRecords();
    }

    public List<Crew> getWarnedCrews() {
        List<Crew> warnedCrews = new ArrayList<>();
        for (Map.Entry<Crew, AttendanceRecords> recordsEntry : crewAttendanceRecords.entrySet()) {
            Crew crew = recordsEntry.getKey();
            AttendanceRecords attendanceRecords = recordsEntry.getValue();
            int absentCount = attendanceRecords.getAbsentCount();
            int tardyCount = attendanceRecords.getTardyCount();
            DisciplinaryStatus status = DisciplinaryStatus.getStatus(absentCount, tardyCount);
            if (status != DisciplinaryStatus.NONE) {
                warnedCrews.add(crew);
            }
        }
        return sortWarnedCrews(warnedCrews);
    }

    private List<Crew> sortWarnedCrews(List<Crew> crews) {
        crews.sort(Comparator.comparing(Crew::name));
        crews.sort(Comparator.comparing(crew -> {
            int absentCount = crewAttendanceRecords.get(crew).getAbsentCount();
            int tardyCount = crewAttendanceRecords.get(crew).getTardyCount();
            absentCount += (tardyCount / 3);
            return (absentCount + tardyCount % 3) * -1;
        }));
        crews.sort(Comparator.comparing(crew -> {
            AttendanceRecords attendanceRecords = crewAttendanceRecords.get(crew);
            int absentCount = attendanceRecords.getAbsentCount();
            int tardyCount = attendanceRecords.getTardyCount();
            DisciplinaryStatus status = DisciplinaryStatus.getStatus(absentCount, tardyCount);
            return status.ordinal() * -1;
        }));
        return crews;
    }
}
