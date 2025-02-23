package domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CrewAttendanceRecords {
    private final Map<Crew, AttendanceRecords> crewAttendanceRecords;

    public CrewAttendanceRecords(CrewAttendanceRecordsGenerator generator, DateGenerator dateGenerator) {
        this.crewAttendanceRecords = generator.generate(dateGenerator);
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

    public void updateAttendanceRecord(Crew crew, AttendanceRecord oldRecord, AttendanceRecord newRecord) {
        validateCrewPresence(crew);
        AttendanceRecords records = crewAttendanceRecords.get(crew);
        records.updateRecord(oldRecord, newRecord);
    }

    public AttendanceRecord getRecordAtDate(Crew crew, LocalDate date) {
        validateCrewPresence(crew);
        AttendanceRecords attendanceRecords = crewAttendanceRecords.get(crew);
        return attendanceRecords.getRecordAtDate(date);
    }

    public int getTardyCount(Crew crew) {
        validateCrewPresence(crew);
        return crewAttendanceRecords.get(crew).getTardyCount();
    }

    public int getAbsentCount(Crew crew) {
        validateCrewPresence(crew);
        return crewAttendanceRecords.get(crew).getAbsentCount();
    }

    public int getAttendanceCount(Crew crew, Attendance attendance) {
        validateCrewPresence(crew);
        return crewAttendanceRecords.get(crew).getAttendanceCount(attendance);
    }

    public AttendanceRecord checkIn(Crew crew, LocalTime time, DateGenerator dateGenerator) {
        validateCrewPresence(crew);
        AttendanceRecords attendanceRecords = crewAttendanceRecords.get(crew);
        validatePresence(attendanceRecords, dateGenerator);
        AttendanceRecord attendanceRecord = AttendanceRecord.checkIn(time, dateGenerator);
        attendanceRecords.addRecord(attendanceRecord);
        return attendanceRecord;
    }

    public TreeSet<AttendanceRecord> getAttendanceRecords(Crew crew) {
        return crewAttendanceRecords.get(crew).getAttendanceRecords();
    }

    public List<Crew> getWarnedCrews() {
        List<Crew> expelledCrews = getCrewsByDisciplinaryStatus(DisciplinaryStatus.EXPELLED);
        List<Crew> oneOnOneCrews = getCrewsByDisciplinaryStatus(DisciplinaryStatus.ONE_ON_ONE);
        List<Crew> warningCrews = getCrewsByDisciplinaryStatus(DisciplinaryStatus.WARNING);
        return Stream.of(expelledCrews, oneOnOneCrews, warningCrews)
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    private List<Crew> getCrewsByDisciplinaryStatus(DisciplinaryStatus targetStatus) {
        List<Crew> warnedCrews = new ArrayList<>();
        for (Map.Entry<Crew, AttendanceRecords> recordsEntry : crewAttendanceRecords.entrySet()) {
            Crew crew = recordsEntry.getKey();
            AttendanceRecords attendanceRecords = recordsEntry.getValue();
            DisciplinaryStatus status = attendanceRecords.getDisciplinaryStatus();
            if (status == targetStatus) {
                warnedCrews.add(crew);
            }
        }
        return sortWarnedCrews(warnedCrews);
    }

    private List<Crew> sortWarnedCrews(List<Crew> crews) {
        crews.sort(Comparator.comparing((Crew crew) -> {
            int absentCount = crewAttendanceRecords.get(crew).getAbsentCount();
            int tardyCount = crewAttendanceRecords.get(crew).getTardyCount();
            int convertedAbsencesAndTardies = DisciplinaryStatus.getConvertedAbsencesAndTardies(absentCount, tardyCount);
            return convertedAbsencesAndTardies * -1;
        }).thenComparing(Crew::name));
        return crews;
    }

    private void validatePresence(AttendanceRecords attendanceRecords, DateGenerator dateGenerator) {
        if (attendanceRecords.hasRecordOfDate(dateGenerator.generate())) {
            throw new IllegalArgumentException("[ERROR] 이미 출석을 확인하였습니다. 필요한 경우 수정 기능을 이용해 주세요.\n");
        }
    }

    private void validateCrewPresence(Crew crew) {
        if (!hasCrew(crew)) {
            throw new IllegalArgumentException("[ERROR] 등록되지 않은 닉네임입니다.\n");
        }
    }
}
