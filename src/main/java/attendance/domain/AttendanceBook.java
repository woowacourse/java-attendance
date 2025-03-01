package attendance.domain;

import attendance.exception.CustomException;
import attendance.exception.ErrorMessage;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttendanceBook {
    private final Map<Crew, List<AttendanceRecord>> attendanceBook;
    private static final int DEFAULT_VALUE = 0;

    public AttendanceBook(Crews crews, LocalDateTime now) {
        this.attendanceBook = new HashMap<>();
        initializeAttendanceBook(crews, now);
    }

    private void initializeAttendanceBook(Crews crews, LocalDateTime now) {
        for (Crew crew : crews.getCrews()) {
            attendanceBook.put(crew, putDefaultValue(now));
        }
    }

    private List<AttendanceRecord> putDefaultValue(LocalDateTime now) {
        List<AttendanceTime> attendanceTimes = new ArrayList<>();
        for (int i = 1; i < now.getDayOfMonth(); i++) {
            LocalDateTime dateTime = now.withDayOfMonth(i).withHour(DEFAULT_VALUE).withMinute(DEFAULT_VALUE);
            excludeWeekend(dateTime, attendanceTimes);
        }
        AttendanceRecord attendanceRecord = new AttendanceRecord(attendanceTimes);
        return Collections.singletonList(attendanceRecord);
    }

    private void excludeWeekend(LocalDateTime dateTime, List<AttendanceTime> attendanceTimes) {
        if (dateTime.getDayOfWeek() != DayOfWeek.SATURDAY && dateTime.getDayOfWeek() != DayOfWeek.SUNDAY) {
            attendanceTimes.add(new AttendanceTime(dateTime));
        }
    }

    public AttendanceTime registerAttendance(String inputCrewName, LocalDateTime inputTime) {
        Crew crew = findRegisteredCrew(inputCrewName);
        AttendanceRecord attendanceRecord = attendanceBook.get(crew).getLast();
        return attendanceRecord.registerAttendance(inputTime);
    }

    public AttendanceTime findBeforeAttendanceRecord(String inputCrewName, LocalDateTime inputTime) {
        Crew crew = findRegisteredCrew(inputCrewName);
        AttendanceRecord attendanceRecord = attendanceBook.get(crew).getLast();
        return attendanceRecord.findAttendanceRecord(inputTime);
    }

    public AttendanceTime modifyAttendance(String inputCrewName, LocalDateTime inputTime) {
        Crew crew = findRegisteredCrew(inputCrewName);
        AttendanceRecord attendanceRecord = attendanceBook.get(crew).getLast();
        AttendanceRecord updatedAttendanceRecord = attendanceRecord.modifyAttendanceTime(inputTime);
        attendanceBook.put(crew, List.of(updatedAttendanceRecord));
        return updatedAttendanceRecord.findAttendanceRecord(inputTime);
    }

    public AttendanceRecord findAttendanceRecord(String inputCrewName) {
        Crew crew = findRegisteredCrew(inputCrewName);
        return attendanceBook.get(crew).getLast();
    }

    public List<RiskCrew> findPenaltyCrews() {
        List<RiskCrew> riskCrews = new ArrayList<>();
        for (Crew crew : attendanceBook.keySet()) {
            AttendanceRecord attendanceRecord = attendanceBook.get(crew).getLast();
            PenaltyType penaltyType = attendanceRecord.checkPenaltyStatus();
            findRiskCrews(crew, penaltyType, attendanceRecord, riskCrews);
        }
        return sortRiskCrews(riskCrews);
    }

    private void findRiskCrews(Crew crew, PenaltyType penaltyType, AttendanceRecord attendanceRecord,
                               List<RiskCrew> riskCrews) {
        if (!penaltyType.equals(PenaltyType.NONE)) {
            int lateCounts = attendanceRecord.checkLateCounts();
            int absenceCounts = attendanceRecord.checkAbsenceCounts();
            riskCrews.add(new RiskCrew(crew.getName(), absenceCounts, lateCounts, penaltyType));
        }
    }

    private List<RiskCrew> sortRiskCrews(List<RiskCrew> riskCrews) {
        riskCrews.sort((r1, r2) -> {
            int absenceComparison = Integer.compare(r2.getAbsenceCount(), r1.getAbsenceCount());
            if (absenceComparison == 0) {
                int lateComparison = Integer.compare(r2.getLateCount(), r1.getLateCount());
                if (lateComparison == 0) {
                    return r1.getName().compareTo(r2.getName());
                }
                return lateComparison;
            }
            return absenceComparison;
        });

        return riskCrews;
    }

    private Crew findRegisteredCrew(String inputCrewName) {
        return attendanceBook.keySet()
                .stream()
                .filter(crew -> crew.getName().equals(inputCrewName))
                .findFirst()
                .orElseThrow(() -> CustomException.from(ErrorMessage.NOT_FIND_CREW));
    }

    public Map<Crew, List<AttendanceRecord>> getAttendanceBook() {
        return attendanceBook;
    }

}
