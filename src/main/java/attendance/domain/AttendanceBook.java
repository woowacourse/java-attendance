package attendance.domain;

import attendance.exception.CustomException;
import attendance.exception.ErrorMessage;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class AttendanceBook {
    private final Map<Crew, AttendanceRecord> attendanceBook;
    private static final int DEFAULT_VALUE = 0;

    public AttendanceBook(Crews crews, LocalDate now) {
        this.attendanceBook = new HashMap<>();
        initializeAttendanceBook(crews, now);
    }

    private void initializeAttendanceBook(Crews crews, LocalDate now) {
        for (Crew crew : crews.getCrews()) {
            attendanceBook.put(crew, putDefaultValue(now));
        }
    }

    private AttendanceRecord putDefaultValue(LocalDate now) {
        List<AttendanceTime> attendanceTimes = new ArrayList<>();
        for (int i = 1; i < now.getDayOfMonth(); i++) {
            LocalDate date = now.withDayOfMonth(i);
            if (!EnumSet.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY).contains(date.getDayOfWeek())) {
                attendanceTimes.add(new AttendanceTime(date));
            }
        }
        return new AttendanceRecord(attendanceTimes);
    }

    public AttendanceTime registerAttendance(Crew crewName, LocalDateTime attendanceTime) {
        CampusOperationTime.isOperation(attendanceTime.getHour());
        PublicHolidays.isPublicHolidays(attendanceTime.toLocalDate());
        Crew crew = findRegisteredCrew(crewName.getName());
        AttendanceRecord attendanceRecord = attendanceBook.get(crew);
        AttendanceRecord newAttendanceRecord = attendanceRecord.registerAttendance(attendanceTime);
        attendanceBook.put(crew, newAttendanceRecord);
        return newAttendanceRecord.findAttendanceRecord(attendanceTime);
    }


    public AttendanceTime findAttendanceRecord(Crew crewName, LocalDateTime recordedTime) {
        Crew crew = findRegisteredCrew(crewName.getName());
        AttendanceRecord attendanceRecord = attendanceBook.get(crew);
        return attendanceRecord.findAttendanceRecord(recordedTime);
    }

    public AttendanceTime modifyAttendance(Crew crewName, LocalDateTime modifyTime) {
        Crew crew = findRegisteredCrew(crewName.getName());
        AttendanceRecord attendanceRecord = attendanceBook.get(crew);
        AttendanceRecord updatedAttendanceRecord = attendanceRecord.modifyAttendanceTime(modifyTime);
        attendanceBook.put(crew, updatedAttendanceRecord);
        return updatedAttendanceRecord.findAttendanceRecord(modifyTime);
    }

    public AttendanceRecord findAttendanceRecord(Crew inputCrewName) {
        Crew crew = findRegisteredCrew(inputCrewName.getName());
        return attendanceBook.get(crew);
    }

    public List<RiskCrew> findPenaltyCrews() {
        return attendanceBook.entrySet()
                .stream()
                .map(entry -> RiskCrew.from(entry.getKey(), entry.getValue()))
                .filter(Objects::nonNull)
                .sorted(RiskCrew.RISK_COMPARATOR)
                .collect(Collectors.toList());
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
