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

    //1. 출석등록
    public Attendance registerAttendance(Crew crew, LocalDateTime newAttendanceDateTime) {
        validateCrewExistance(crew);
        AttendanceLog attendanceLog = attendanceRecord.get(crew);

        return attendanceLog.registerAttendance(newAttendanceDateTime);
    }

    //2. 출석수정
    public List<Attendance> modifyAttendance(Crew crew, LocalDateTime newALocalDateTime) {
        validateCrewExistance(crew);
        AttendanceLog attendanceLog = attendanceRecord.get(crew);
        return attendanceLog.modifyAttendanceRecord(newALocalDateTime);
    }


    //3. 출석 확인
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

    // 4. 제적 위험자 확인
    public Map<Crew, AttendanceStatus> checkExpelledCrews() {
        Map<Crew, AttendanceStatus> crewAttendanceStatuses = new HashMap<>();

        // AttendanceLog에서 출석 상태 카운트하여 AttendanceStatus 객체 생성
        for (Map.Entry<Crew, AttendanceLog> entry : attendanceRecord.entrySet()) {
            AttendanceLog attendanceLog = entry.getValue();
            int attendanceCount = attendanceLog.countAttendanceStatus(Subject.ATTENDANCE);
            int lateCount = attendanceLog.countAttendanceStatus(Subject.LATE);
            int absentCount = attendanceLog.countAttendanceStatus(Subject.ABSENT);

            AttendanceStatus attendanceStatus = new AttendanceStatus(attendanceCount, lateCount, absentCount);
            crewAttendanceStatuses.put(entry.getKey(), attendanceStatus);
        }

        List<Map.Entry<Crew, AttendanceStatus>> sortedEntries = new ArrayList<>(crewAttendanceStatuses.entrySet());

        Collections.sort(sortedEntries, (entry1, entry2) -> {
            AttendanceStatus status1 = entry1.getValue();
            AttendanceStatus status2 = entry2.getValue();

            // 1. subjectStatus 우선순위 비교 (제적 -> 면담 -> 경고 -> 정상)
            if (!status1.getSubjectStatus().equals(status2.getSubjectStatus())) {
                return -status1.getSubjectStatus().compareTo(status2.getSubjectStatus());
            }

            // 2. lateCount + absentCount 기준 내림차순 정렬
            int total1 = status1.getLateCount() + status1.getAbsentCount();
            int total2 = status2.getLateCount() + status2.getAbsentCount();
            if (total1 != total2) {
                return Integer.compare(total2, total1);  // 내림차순
            }

            // 3. 만약 lateCount + absentCount이 동일하면, absentCount 기준 내림차순 정렬
            if (status1.getAbsentCount() != status2.getAbsentCount()) {
                return Integer.compare(status2.getAbsentCount(), status1.getAbsentCount());  // 내림차순
            }


            // 4. 동일하면 Crew 이름 기준 오름차순 정렬
            return entry1.getKey().getName().compareTo(entry2.getKey().getName());
        });

        Map<Crew, AttendanceStatus> sortedCrewAttendanceStatuses = new HashMap<>();
        for (Map.Entry<Crew, AttendanceStatus> entry : sortedEntries) {
            sortedCrewAttendanceStatuses.put(entry.getKey(), entry.getValue());
        }
        return sortedCrewAttendanceStatuses;
    }



    private void validateCrewExistance(Crew crew) {
        if (!attendanceRecord.containsKey(crew)) {
            throw new IllegalArgumentException("등록되지 않는 크루입니다");
        }
    }
}
