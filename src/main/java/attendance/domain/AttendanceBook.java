package attendance.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttendanceBook {

    private final Map<Crew, AttendanceLog> attendanceRecord;

    public AttendanceBook(Map<Crew, AttendanceLog> attendanceRecord) {
        this.attendanceRecord = attendanceRecord;
    }

    public Attendance registerAttendance(Crew crew, LocalDateTime newAttendanceDateTime) {
        validateCrewExistance(crew);
        AttendanceLog attendanceLog = attendanceRecord.get(crew);

        return attendanceLog.registerAttendance(newAttendanceDateTime);
    }

    public List<Attendance> modifyAttendance(Crew crew, LocalDateTime newALocalDateTime) {
        validateCrewExistance(crew);
        AttendanceLog attendanceLog = attendanceRecord.get(crew);
        return attendanceLog.modifyAttendanceRecord(newALocalDateTime);
    }


    public List<Attendance> checkAttendancesRecord(Crew crew) {
        validateCrewExistance(crew);
        AttendanceLog attendanceLog = attendanceRecord.get(crew);
        return attendanceLog.checkAttendancesRecord();
    }

    public AttendanceStatus checkAttendanceCrewStatus(Crew crew) {
        AttendanceLog attendanceLog = attendanceRecord.get(crew);
        int attendanceCount = attendanceLog.countAttendanceStatus(Subject.ATTENDANCE);
        int lateCount = attendanceLog.countAttendanceStatus(Subject.LATE);
        int absentCount = attendanceLog.countAttendanceStatus(Subject.ABSENT);
        return new AttendanceStatus(attendanceCount, lateCount, absentCount);
    }

    public Map<Crew, AttendanceStatus> checkExpelledCrews() {
        Map<Crew, AttendanceStatus> crewAttendanceStatuses = new HashMap<>();

        for (Map.Entry<Crew, AttendanceLog> entry : attendanceRecord.entrySet()) {
            AttendanceLog attendanceLog = entry.getValue();
            int attendanceCount = attendanceLog.countAttendanceStatus(Subject.ATTENDANCE);
            int lateCount = attendanceLog.countAttendanceStatus(Subject.LATE);
            int absentCount = attendanceLog.countAttendanceStatus(Subject.ABSENT);
package attendance.domain;

import java.time.LocalDateTime;
import java.util.*;

            public class AttendanceBook {

                private final Map<Crew, AttendanceLog> attendanceRecord;

                public AttendanceBook(Map<Crew, AttendanceLog> attendanceRecord) {
                    this.attendanceRecord = attendanceRecord;
                }

                public Attendance registerAttendance(Crew crew, LocalDateTime newAttendanceDateTime) {
                    validateCrewExistance(crew);
                    AttendanceLog attendanceLog = attendanceRecord.get(crew);

                    return attendanceLog.registerAttendance(newAttendanceDateTime);
                }

                public List<Attendance> modifyAttendance(Crew crew, LocalDateTime newALocalDateTime) {
                    validateCrewExistance(crew);
                    AttendanceLog attendanceLog = attendanceRecord.get(crew);
                    return attendanceLog.modifyAttendanceRecord(newALocalDateTime);
                }

                public List<Attendance> checkAttendancesRecord(Crew crew) {
                    validateCrewExistance(crew);
                    AttendanceLog attendanceLog = attendanceRecord.get(crew);
                    return attendanceLog.checkAttendancesRecord();
                }

                public AttendanceStatus checkAttendanceCrewStatus(Crew crew) {
                    AttendanceLog attendanceLog = attendanceRecord.get(crew);
                    int attendanceCount = attendanceLog.countAttendanceStatus(Subject.ATTENDANCE);
                    int lateCount = attendanceLog.countAttendanceStatus(Subject.LATE);
                    int absentCount = attendanceLog.countAttendanceStatus(Subject.ABSENT);
                    return new AttendanceStatus(attendanceCount, lateCount, absentCount);
                }

                public Map<Crew, AttendanceStatus> checkExpelledCrews() {
                    Map<Crew, AttendanceStatus> crewAttendanceStatuses = createAttendanceStatuses();
                    List<Map.Entry<Crew, AttendanceStatus>> sortedEntries = sortAttendanceStatuses(crewAttendanceStatuses);
                    return convertToMap(sortedEntries);
                }

                private Map<Crew, AttendanceStatus> createAttendanceStatuses() {
                    Map<Crew, AttendanceStatus> crewAttendanceStatuses = new HashMap<>();
                    for (Map.Entry<Crew, AttendanceLog> entry : attendanceRecord.entrySet()) {
                        AttendanceStatus attendanceStatus = createAttendanceStatus(entry.getValue());
                        crewAttendanceStatuses.put(entry.getKey(), attendanceStatus);
                    }
                    return crewAttendanceStatuses;
                }

                private AttendanceStatus createAttendanceStatus(AttendanceLog attendanceLog) {
                    int attendanceCount = attendanceLog.countAttendanceStatus(Subject.ATTENDANCE);
                    int lateCount = attendanceLog.countAttendanceStatus(Subject.LATE);
                    int absentCount = attendanceLog.countAttendanceStatus(Subject.ABSENT);
                    return new AttendanceStatus(attendanceCount, lateCount, absentCount);
                }

                private List<Map.Entry<Crew, AttendanceStatus>> sortAttendanceStatuses(Map<Crew, AttendanceStatus> crewAttendanceStatuses) {
                    List<Map.Entry<Crew, AttendanceStatus>> sortedEntries = new ArrayList<>(crewAttendanceStatuses.entrySet());
                    sortedEntries.sort(this::compareAttendanceStatus);
                    return sortedEntries;
                }

                private int compareAttendanceStatus(Map.Entry<Crew, AttendanceStatus> entry1, Map.Entry<Crew, AttendanceStatus> entry2) {
                    AttendanceStatus status1 = entry1.getValue();
                    AttendanceStatus status2 = entry2.getValue();

                    int statusComparison = -status1.getSubjectStatus().compareTo(status2.getSubjectStatus());
                    if (statusComparison != 0) return statusComparison;

                    int totalComparison = Integer.compare(
                        (status2.getLateCount() + status2.getAbsentCount()),
                        (status1.getLateCount() + status1.getAbsentCount())
                    );
                    if (totalComparison != 0) return totalComparison;

                    int absentComparison = Integer.compare(status2.getAbsentCount(), status1.getAbsentCount());
                    if (absentComparison != 0) return absentComparison;

                    return entry1.getKey().getName().compareTo(entry2.getKey().getName());
                }

                private Map<Crew, AttendanceStatus> convertToMap(List<Map.Entry<Crew, AttendanceStatus>> sortedEntries) {
                    Map<Crew, AttendanceStatus> sortedMap = new LinkedHashMap<>();
                    for (Map.Entry<Crew, AttendanceStatus> entry : sortedEntries) {
                        sortedMap.put(entry.getKey(), entry.getValue());
                    }
                    return sortedMap;
                }

                private void validateCrewExistance(Crew crew) {
                    if (!attendanceRecord.containsKey(crew)) {
                        throw new IllegalArgumentException("등록되지 않는 크루입니다");
                    }
                }
            }
