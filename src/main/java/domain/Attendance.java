package domain;

import dto.AbsenceResultDto;
import dto.AttendanceResultDto;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Attendance {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final String TODAY_FORMAT = "2024-12-%02d %s";

    private final Map<Crew, List<LocalDateTime>> attendances;

    public Attendance(final Map<Crew, List<LocalDateTime>> attendanceMap) {
        this.attendances = attendanceMap;
    }

    public Crew getCrewByName(String name) {
        return attendances.keySet()
                .stream()
                .filter(crew -> crew.isSame(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 크루 입니다."));
    }

    public void save(final Crew crew, final String schoolStartTime, final int todayDay) {
        List<LocalDateTime> localDateTimes = attendances.get(crew);

        String today = String.format(TODAY_FORMAT, todayDay, schoolStartTime);
        LocalDateTime todayLocalDateTime = parseToLocalDateTime(today);

        validateDuplicateSave(todayDay, localDateTimes);
        localDateTimes.add(todayLocalDateTime);
        attendances.put(crew, localDateTimes);
    }

    public LocalDateTime update(final Crew crew, final String updateTime, final int date) {
        Calender.validateHolyDay(date);

        List<LocalDateTime> localDateTimes = attendances.get(crew);
        int attendanceRecordIndex;
        LocalDateTime beforeLocalDateTime = null;
        for (attendanceRecordIndex = 0; attendanceRecordIndex < localDateTimes.size(); attendanceRecordIndex++) {
            LocalDateTime localDateTime = localDateTimes.get(attendanceRecordIndex);
            int dayOfMonth = localDateTime.getDayOfMonth();
            if (dayOfMonth == date) {
                beforeLocalDateTime = localDateTime;
                break;
            }
        }

        String today = String.format(TODAY_FORMAT, date, updateTime);
        LocalDateTime todayLocalDateTime = parseToLocalDateTime(today);

        localDateTimes.set(attendanceRecordIndex, todayLocalDateTime);

        return beforeLocalDateTime;
    }

    public List<AttendanceResultDto> readRecord(final Crew crew, int todayDay) {
        List<LocalDateTime> localDateTimes = attendances.get(crew); //해당 크루의 출석 기록
        localDateTimes.sort(Comparator.comparing((LocalDateTime::getDayOfMonth)));

        List<AttendanceResultDto> attendanceResultDtos = new ArrayList<>();

        int idx = 0;
        for (int dayIndex = 1; dayIndex < todayDay; dayIndex++) {
            if (Calender.findBy(dayIndex).getDescription().equals("공휴일")) {
                continue;
            }

            LocalDateTime localDateTime = null;
            try {
                localDateTime = localDateTimes.get(idx);
            } catch (IndexOutOfBoundsException e) {
                checkAbsence(dayIndex, attendanceResultDtos);
                continue;
            }

            int dayOfMonth = localDateTime.getDayOfMonth();

            if (dayIndex == dayOfMonth) {
                AttendanceState state = AttendanceState.findStateBy(localDateTime.toLocalTime(), dayOfMonth);
                AttendanceResultDto attendanceResultDto = new AttendanceResultDto(localDateTime, state);
                attendanceResultDtos.add(attendanceResultDto);
                idx++;
                continue;
            }
            checkAbsence(dayIndex, attendanceResultDtos);
        }
        return attendanceResultDtos;
    }

    public Map<Crew, AbsenceResultDto> getAbsence(final int todayDay) {
        Map<Crew, AbsenceResultDto> absenceMap = new HashMap<>();
        for (Crew crew : attendances.keySet()) {
            List<AttendanceResultDto> attendanceResultDtos = readRecord(crew, todayDay);

            AbsenceHistory absenceHistory = new AbsenceHistory(attendanceResultDtos);
            AbsenceResultDto absenceResultDto = absenceHistory.calculate();

            absenceMap.put(crew, absenceResultDto);
        }

        return absenceMap;
    }

    public Map<Crew, List<LocalDateTime>> getAttendances() {
        return attendances;
    }

    private void validateDuplicateSave(final int todayDay, final List<LocalDateTime> localDateTimes) {
        for (LocalDateTime localDateTime : localDateTimes) {
            int dayOfMonth = localDateTime.getDayOfMonth();

            if (dayOfMonth == todayDay) {
                throw new IllegalArgumentException("이미 출석한 크루입니다.");
            }
        }
    }

    private void checkAbsence(final int dayIndex, final List<AttendanceResultDto> attendanceResultDtos) {
        AttendanceState state = AttendanceState.ABSENCE;
        LocalDateTime newLocalDateTime = LocalDateTime.of(2024, 12, dayIndex, 0, 0);
        AttendanceResultDto attendanceResultDto = new AttendanceResultDto(newLocalDateTime, state);
        attendanceResultDtos.add(attendanceResultDto);
    }

    private LocalDateTime parseToLocalDateTime(final String today) {
        return LocalDateTime.parse(today, DATE_TIME_FORMATTER);
    }
}
