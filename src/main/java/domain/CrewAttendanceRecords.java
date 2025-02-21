package domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class CrewAttendanceRecords {
    private static final int REVERSE_ORDER = -1;
    private static final int TARDY_TO_ABSENT = 3;

    private final Map<Crew, AttendanceRecords> crewAttendanceRecords;

    public CrewAttendanceRecords(CrewAttendanceRecordsGenerator generator, LocalDate today) {
        this.crewAttendanceRecords = generator.generate(today);
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

    public AttendanceRecord checkIn(Crew crew, LocalTime time, LocalDate today) {
        validateCrewPresence(crew);
        AttendanceRecords attendanceRecords = crewAttendanceRecords.get(crew);
        validatePresence(attendanceRecords, today);
        AttendanceRecord attendanceRecord = AttendanceRecord.checkIn(time, today);
        attendanceRecords.addRecord(attendanceRecord);
        return attendanceRecord;
    }

    public List<AttendanceRecord> getSortedRecords(Crew crew) {
        return crewAttendanceRecords.get(crew).getSortedRecords();
    }

    public List<Crew> getWarnedCrews() {
        List<Crew> warnedCrews = new ArrayList<>();
        for (Map.Entry<Crew, AttendanceRecords> recordsEntry : crewAttendanceRecords.entrySet()) {
            Crew crew = recordsEntry.getKey();
            AttendanceRecords attendanceRecords = recordsEntry.getValue();
            DisciplinaryStatus status = attendanceRecords.getDisciplinaryStatus();
            if (status != DisciplinaryStatus.NONE) {
                warnedCrews.add(crew);
            }
        }
        return sortWarnedCrews(warnedCrews);
    }

    private List<Crew> sortWarnedCrews(List<Crew> crews) {
        sortCrewsByName(crews);
        sortCrewsByAttendanceCount(crews);
        sortCrewsByDisciplinaryStatus(crews);
        return crews;
    }

    private void sortCrewsByName(List<Crew> crews) {
        crews.sort(Comparator.comparing(Crew::name));
    }

    private void sortCrewsByAttendanceCount(List<Crew> crews) {
        crews.sort(Comparator.comparing(crew -> {
            int absentCount = crewAttendanceRecords.get(crew).getAbsentCount();
            int tardyCount = crewAttendanceRecords.get(crew).getTardyCount();
            absentCount += (tardyCount / TARDY_TO_ABSENT);
            return (absentCount + tardyCount % TARDY_TO_ABSENT) * REVERSE_ORDER;
        }));
    }

    private void sortCrewsByDisciplinaryStatus(List<Crew> crews) {
        crews.sort(Comparator.comparing(crew -> {
            AttendanceRecords attendanceRecords = crewAttendanceRecords.get(crew);
            DisciplinaryStatus status = attendanceRecords.getDisciplinaryStatus();
            return status.ordinal() * REVERSE_ORDER;
        }));
    }

    private void validatePresence(AttendanceRecords attendanceRecords, LocalDate date) {
        if (attendanceRecords.hasRecordOfDate(date)) {
            throw new IllegalArgumentException("[ERROR] 이미 출석을 확인하였습니다. 필요한 경우 수정 기능을 이용해 주세요.\n");
        }
    }

    private void validateCrewPresence(Crew crew) {
        if (!hasCrew(crew)) {
            throw new IllegalArgumentException("[ERROR] 등록되지 않은 닉네임입니다.\n");
        }
    }
}
