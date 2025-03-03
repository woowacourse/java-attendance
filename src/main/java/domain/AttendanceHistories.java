package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class AttendanceHistories {
    private static final double TARDY_TO_ABSENT = 3;
    private static final double REVERSE_ORDER = -1;
    private final Map<Crew, AttendanceDateTimes> attendanceHistories;

    public AttendanceHistories(Map<Crew, AttendanceDateTimes> attendanceHistoryData) {
        this.attendanceHistories = attendanceHistoryData;
    }

    public AttendanceStatus addAttendanceHistory(Crew crew, LocalDateTime attendanceDateTime) {
        LocalDate attendanceDate = attendanceDateTime.toLocalDate();
        validateCrewPresence(crew);
        AttendanceDateTimes attendanceDateTimes = attendanceHistories.get(crew);
        validateDuplicateAttendance(attendanceDateTimes, attendanceDate);
        attendanceHistories.put(crew, attendanceHistories.get(crew).add(attendanceDateTime));
        return AttendanceStatus.of(attendanceDateTime);
    }

    public AttendanceDateTime replaceAttendanceHistory(Crew crew, LocalDateTime newAttendanceDateTime) {
        validateCrewPresence(crew);
        AttendanceDateTimes attendanceDateTimes = attendanceHistories.get(crew);
        AttendanceDateTime oldAttendanceDateTime = attendanceDateTimes.remove(newAttendanceDateTime.toLocalDate());
        attendanceDateTimes.add(newAttendanceDateTime);
        return oldAttendanceDateTime;
    }

    public AttendanceDateTimes getAttendanceDateTimes(Crew crew) {
        validateCrewPresence(crew);
        return attendanceHistories.get(crew);
    }

    public int getPresentCount(Crew crew, LocalDate lastDate) {
        validateCrewPresence(crew);
        AttendanceDateTimes attendanceDateTimes = attendanceHistories.get(crew);
        return attendanceDateTimes.getPresentCount(lastDate);
    }

    public int getTardyCount(Crew crew, LocalDate lastDate) {
        validateCrewPresence(crew);
        AttendanceDateTimes attendanceDateTimes = attendanceHistories.get(crew);
        return attendanceDateTimes.getTardyCount(lastDate);
    }

    public int getAbsentCount(Crew crew, LocalDate lastDate) {
        validateCrewPresence(crew);
        AttendanceDateTimes attendanceDateTimes = attendanceHistories.get(crew);
        return attendanceDateTimes.getAbsentCount(lastDate);
    }

    public DisciplinaryStatus getDisciplinaryStatusOf(Crew crew, LocalDate lastDate) {
        int tardyCount = getTardyCount(crew, lastDate);
        int absentCount = getAbsentCount(crew, lastDate);
        return DisciplinaryStatus.of(tardyCount, absentCount);
    }

    public List<Crew> getDisciplinedCrews(LocalDate lastDate) {
        List<Crew> crews = new ArrayList<>(attendanceHistories.keySet().stream()
                .filter(crew -> getDisciplinaryStatusOf(crew, lastDate) != DisciplinaryStatus.NONE)
                .toList());
        return sortDisciplinedCrews(crews, lastDate);
    }

    private List<Crew> sortDisciplinedCrews(List<Crew> crews, LocalDate lastDate) {
        sortCrewsByName(crews);
        sortCrewsByAttendanceCount(crews, lastDate);
        sortCrewsByDisciplinaryStatus(crews, lastDate);
        return crews;
    }

    private void sortCrewsByName(List<Crew> crews) {
        crews.sort(Comparator.comparing(Crew::nickname));
    }

    private void sortCrewsByAttendanceCount(List<Crew> crews, LocalDate lastDate) {
        crews.sort(Comparator.comparing(crew -> {
            int absentCount = getAbsentCount(crew, lastDate);
            int tardyCount = getTardyCount(crew, lastDate);
            absentCount += (int) (tardyCount / TARDY_TO_ABSENT);
            return (absentCount + tardyCount % TARDY_TO_ABSENT) * REVERSE_ORDER;
        }));
    }

    private void sortCrewsByDisciplinaryStatus(List<Crew> crews, LocalDate lastDate) {
        crews.sort(Comparator.comparing(crew -> {
            DisciplinaryStatus status = getDisciplinaryStatusOf(crew, lastDate);
            return status.ordinal() * REVERSE_ORDER;
        }));
    }

    private void validateCrewPresence(Crew crew) {
        if (!attendanceHistories.containsKey(crew)) {
            throw new IllegalArgumentException("[ERROR] 등록되지 않은 닉네임입니다.");
        }
    }

    private void validateDuplicateAttendance(AttendanceDateTimes attendanceDateTimes, LocalDate attendanceDate) {
        if (attendanceDateTimes.contains(attendanceDate)) {
            throw new IllegalArgumentException("[ERROR] 이미 출석을 확인하였습니다. 필요한 경우 수정 기능을 이용해 주세요.");
        }
    }
}
