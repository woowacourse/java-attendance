package domain;

import vo.Attendance;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AttendanceBook {
    
    private static final LocalTime CAMPUS_OPEN_TIME = LocalTime.of(8, 0);
    private static final LocalTime CAMPUS_CLOSE_TIME = LocalTime.of(23, 0);
    private static final List<DayOfWeek> weekends = List.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);
    
    private final Map<String, Boolean> crews;
    private final LocalDate today;
    
    public AttendanceBook(final List<String> crews, final LocalDate today) {
        validateNotWeekend(today.getDayOfWeek());
        validateNotHoliday(today);
        this.crews = crews.stream().collect(Collectors.toMap(
                nickname -> nickname,
                nickname -> false
        ));
        this.today = today;
    }
    
    private void validateNotWeekend(final DayOfWeek dayOfWeek) {
        if (weekends.contains(dayOfWeek)) {
            throw new IllegalArgumentException("주말에는 출석할 수 없습니다.");
        }
    }
    
    private void validateNotHoliday(final LocalDate today) {
        if (Holiday.isHoliday(today)) {
            throw new IllegalArgumentException("공휴일에는 출석할 수 없습니다.");
        }
    }
    
    public Attendance attend(final String nickname, final LocalTime time) {
        if (!isCampusOpen(time)) {
            throw new IllegalArgumentException("캠퍼스 운영시간이 아닙니다. (08:00~23:00)");
        }
        
        if (!isNicknameExist(nickname)) {
            throw new IllegalArgumentException("등록되지 않은 닉네임입니다.");
        }
        
        if (hasAlreadyAttended(nickname)) {
            throw new IllegalArgumentException("하루에 여러번 출석할 수 없습니다. 수정 기능을 이용하세요.");
        }
        
        crews.put(nickname, true);
        
        return new Attendance(nickname, today, time, AttendanceStatus.of(today.getDayOfWeek(), time));
    }
    
    private static boolean isCampusOpen(final LocalTime time) {
        return !time.isBefore(CAMPUS_OPEN_TIME) && !time.isAfter(CAMPUS_CLOSE_TIME);
    }
    
    private boolean isNicknameExist(final String nickname) {
        return crews.containsKey(nickname);
    }
    
    private boolean hasAlreadyAttended(final String nickname) {
        return crews.get(nickname);
    }
}
