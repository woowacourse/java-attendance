package attendance.domain;

import attendance.exception.CustomException;
import attendance.exception.ErrorMessage;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttendanceBook {
    private final Map<Crew, AttendanceRecord> attendanceBook;
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

    private AttendanceRecord putDefaultValue(LocalDateTime now) {
        List<AttendanceTime> attendanceTimes = new ArrayList<>();
        for (int i = 1; i < now.getDayOfMonth(); i++) {
            LocalDateTime dateTime = now.withDayOfMonth(i).withHour(DEFAULT_VALUE).withMinute(DEFAULT_VALUE);
            excludeWeekend(dateTime, attendanceTimes);
        }
        return new AttendanceRecord(attendanceTimes);
    }

    private void excludeWeekend(LocalDateTime dateTime, List<AttendanceTime> attendanceTimes) {
        if (!EnumSet.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY).contains(dateTime.getDayOfWeek())) {
            attendanceTimes.add(new AttendanceTime(dateTime));
        }
    }

    public AttendanceTime registerAttendance(Crew inputCrewName, LocalDateTime inputTime) {
        Crew crew = findRegisteredCrew(inputCrewName.getName());
        AttendanceRecord attendanceRecord = attendanceBook.get(crew);
        AttendanceRecord newAttendanceRecord = attendanceRecord.registerAttendance(inputTime);
        attendanceBook.put(crew, newAttendanceRecord);
        return newAttendanceRecord.findAttendanceRecord(inputTime);
    }

    public AttendanceTime findBeforeAttendanceRecord(Crew inputCrewName, LocalDateTime inputTime) {
        Crew crew = findRegisteredCrew(inputCrewName.getName());
        AttendanceRecord attendanceRecord = attendanceBook.get(crew);
        return attendanceRecord.findAttendanceRecord(inputTime);
    }

    public AttendanceTime modifyAttendance(Crew inputCrewName, LocalDateTime inputTime) {
        Crew crew = findRegisteredCrew(inputCrewName.getName());
        AttendanceRecord attendanceRecord = attendanceBook.get(crew);
        AttendanceRecord updatedAttendanceRecord = attendanceRecord.modifyAttendanceTime(inputTime);
        attendanceBook.put(crew, updatedAttendanceRecord);
        return updatedAttendanceRecord.findAttendanceRecord(inputTime);
    }

    public AttendanceRecord findAttendanceRecord(Crew inputCrewName) {
        Crew crew = findRegisteredCrew(inputCrewName.getName());
        return attendanceBook.get(crew);
    }

    public List<RiskCrew> findPenaltyCrews() {
        List<RiskCrew> riskCrews = new ArrayList<>();
        for (Crew crew : attendanceBook.keySet()) {
            AttendanceRecord attendanceRecord = attendanceBook.get(crew);
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

    public Map<Crew, AttendanceRecord> getAttendanceBook() {
        return Collections.unmodifiableMap(attendanceBook);
    }

}
