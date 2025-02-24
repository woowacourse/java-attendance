package domain;

import vo.Attendance;
import vo.AttendanceModify;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AttendanceBook {
    
    private final Map<String, CrewAttendance> crewAttendances;
    
    public AttendanceBook(final List<String> crews, final LocalDate today) {
        this.crewAttendances = crews.stream()
                .collect(Collectors.toMap(crew -> crew, crew -> new CrewAttendance(today)));
    }
    
    public Attendance attend(final String nickname, final LocalDate date, final LocalTime time) {
        validateNicknameExist(nickname);
        
        return crewAttendances.get(nickname).attend(date, time);
    }
    
    public AttendanceModify modify(final String nickname, final LocalDate targetDate, final LocalTime newTime) {
        validateNicknameExist(nickname);
        
        return crewAttendances.get(nickname).modify(targetDate, newTime);
    }
    
    private void validateNicknameExist(final String nickname) {
        if (!isNicknameExist(nickname)) {
            throw new IllegalArgumentException("등록되지 않은 닉네임입니다.");
        }
    }
    
    private boolean isNicknameExist(final String nickname) {
        return crewAttendances.containsKey(nickname);
    }
}
