package domain;

import dto.AbsenceResultDto;
import dto.AttendanceResultDto;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import util.DateTimeUtil;

public class Attendance {

    private final Map<Crew, List<LocalDateTime>> attendanceMap;

    public Attendance(final Map<Crew, List<LocalDateTime>> attendanceMap) {
        this.attendanceMap = attendanceMap;
    }

    public Crew getCrewByName(String name) {
        return attendanceMap.keySet()
                .stream()
                .filter(crew -> crew.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 크루 입니다."));
    }

    public Map<Crew, List<LocalDateTime>> getAttendanceMap() {
        return attendanceMap;
    }

    public void save(final Crew crew, final String schoolStartTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        List<LocalDateTime> localDateTimes = attendanceMap.get(crew);

        String today = String.format("2024-12-%02d %s", DateTimeUtil.getMonthBy(LocalDate.now()), schoolStartTime);
        LocalDateTime todayLocalDateTime = LocalDateTime.parse(today, formatter);

        validateDuplicateSave(24, localDateTimes);
//        validateDuplicateSave(DateTimeUtil.getDateBy(LocalDate.now()), localDateTimes);
        localDateTimes.add(todayLocalDateTime);
        attendanceMap.put(crew, localDateTimes);
    }

    private void validateDuplicateSave(final int todayDay, final List<LocalDateTime> localDateTimes) {
        for (LocalDateTime localDateTime : localDateTimes) {
            int dayOfMonth = DateTimeUtil.getDateBy(localDateTime.toLocalDate());
//            int dayOfMonth = localDateTime.getDayOfMonth();

            if (dayOfMonth == todayDay) {
                throw new IllegalArgumentException("이미 출석한 크루입니다.");
            }
        }
    }

    public LocalDateTime update(final Crew crew, final String updateTime, final int date) {
        Calender.validateHolyDay(date);

        List<LocalDateTime> localDateTimes = attendanceMap.get(crew);
        int i;
        LocalDateTime beforeLocalDateTime = null;
        for (i = 0; i < localDateTimes.size(); i++) {
            LocalDateTime localDateTime = localDateTimes.get(i);
            int dayOfMonth = DateTimeUtil.getDateBy(localDateTime.toLocalDate());
//            int dayOfMonth = localDateTime.getDayOfMonth();
            if (dayOfMonth == date) {
                beforeLocalDateTime = localDateTime;
                break;
            }
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        String today = String.format("2024-12-%02d %s", date, updateTime);
        LocalDateTime todayLocalDateTime = LocalDateTime.parse(today, formatter);

        localDateTimes.set(i, todayLocalDateTime);

        return beforeLocalDateTime;
    }

    public List<AttendanceResultDto> readRecord(final Crew crew) {
        List<LocalDateTime> localDateTimes = attendanceMap.get(crew);
        localDateTimes.sort(Comparator.comparing((l -> DateTimeUtil.getDateBy(l.toLocalDate()))));

        List<AttendanceResultDto> attendanceResultDtos = new ArrayList<>();

        int idx = 0;
        for (int dayIndex = 1; dayIndex < DateTimeUtil.getDateBy(LocalDate.now()); dayIndex++) {
            if (Calender.findBy(dayIndex).equals("공휴일")) {
                continue;
            }

            LocalDateTime localDateTime = null;
            try {
                localDateTime = localDateTimes.get(idx);
            } catch (IndexOutOfBoundsException e) {
                checkAbsence(dayIndex, attendanceResultDtos);
                continue;
            }

            int dayOfMonth = DateTimeUtil.getDateBy(localDateTime.toLocalDate());
//            int dayOfMonth = localDateTime.getDayOfMonth();

            if (dayIndex == dayOfMonth) {
                String state = AttendanceState.findStateBy(localDateTime.toLocalTime(),
                        localDateTime.toLocalDate());
                AttendanceResultDto attendanceResultDto = new AttendanceResultDto(localDateTime, state);
                attendanceResultDtos.add(attendanceResultDto);
                idx++;
                continue;
            }
            checkAbsence(dayIndex, attendanceResultDtos);
        }
        return attendanceResultDtos;
    }

    private void checkAbsence(final int dayIndex, final List<AttendanceResultDto> attendanceResultDtos) {
        String state = "결석";
        LocalDateTime newLocalDateTime = LocalDateTime.of(2024, 12, dayIndex, 0, 0);
        AttendanceResultDto attendanceResultDto = new AttendanceResultDto(newLocalDateTime, state);
        attendanceResultDtos.add(attendanceResultDto);
    }

    public Map<Crew, AbsenceResultDto> getAbsence() {
        Map<Crew, AbsenceResultDto> absenceMap = new HashMap<>();

        for (Crew crew : attendanceMap.keySet()) {
            List<AttendanceResultDto> attendanceResultDtos = readRecord(crew);

            AbsenceHistory absenceHistory = new AbsenceHistory(attendanceResultDtos);
            AbsenceResultDto absenceResultDto = absenceHistory.calculate();

            absenceMap.put(crew, absenceResultDto);
        }

        return absenceMap;
    }
}
