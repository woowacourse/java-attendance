package domain;

import dto.AttendanceHistoryDto;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import util.DateTimeUtil;

public class Attendance {

    //    private final AttendanceResult attendanceResult;
    private final Map<Crew, List<LocalDateTime>> attendanceMap;

    public Attendance(final Map<Crew, List<LocalDateTime>> attendanceMap) {
//        this.attendanceResult = attendanceResult;
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

    public void save(final Crew crew, final String schoolStartTime, LocalDate localDate) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        List<LocalDateTime> localDateTimes = attendanceMap.get(crew);

        String today = String.format("%d-%02d-%02d %s",
                DateTimeUtil.getYearBy(localDate),
                DateTimeUtil.getMonthBy(localDate),
                DateTimeUtil.getDateBy(localDate),
                schoolStartTime);
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
        DateTimeUtil.validateHolyDay(date);

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

    public List<AttendanceHistoryDto> getAttendanceHistory(final Crew crew) {
        List<LocalDateTime> localDateTimes = attendanceMap.get(crew);

        return localDateTimes.stream()
                .map(localDateTime -> {
                    String state = AttendanceState.findStateBy(localDateTime.toLocalTime(),
                            localDateTime.toLocalDate()).getDescription();
                    return new AttendanceHistoryDto(localDateTime, state);
                })
                .toList();
    }
}
