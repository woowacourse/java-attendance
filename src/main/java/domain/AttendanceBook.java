package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class AttendanceBook {
    
    private final Map<String, CrewAttendance> crewAttendances;
    
    public AttendanceBook(final List<String> crews, final LocalDate today) {
        this.crewAttendances = crews.stream()
                .collect(Collectors.toMap(crew -> crew, crew -> new CrewAttendance(today)));
    }
    
    public AttendanceBook(final Map<String, Set<LocalDateTime>> attendances, final LocalDate today) {
        this.crewAttendances = attendances.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        (entry) -> new CrewAttendance(today, parseDateTimeToMap(entry.getValue()))
                ));
    }
    
    private Map<LocalDate, LocalTime> parseDateTimeToMap(final Set<LocalDateTime> dateTime) {
        return dateTime.stream().collect(Collectors.toMap(
                LocalDateTime::toLocalDate,
                LocalDateTime::toLocalTime
        ));
    }
    
    public void attend(final String nickname, final LocalDate date, final LocalTime time) {
        validateNicknameExist(nickname);
        
        crewAttendances.get(nickname).attend(date, time);
    }
    
    public AttendanceStatus getAttendanceStatusOf(final String nickname, final LocalDate date) {
        validateNicknameExist(nickname);
        
        return crewAttendances.get(nickname).getAttendanceStatusOf(date);
    }
    
    public void modify(final String nickname, final LocalDate targetDate, final LocalTime newTime) {
        validateNicknameExist(nickname);
        
        crewAttendances.get(nickname).modify(targetDate, newTime);
    }
    
    public Set<Attendance> getAllAttendnaces(final String nickname) {
        validateNicknameExist(nickname);
        
        return crewAttendances.get(nickname).getAllAttendances();
    }
    
    public Map<String, ExpelWarning> getExpelWarnings() {
        return crewAttendances.entrySet().stream()
                .filter(entry -> entry.getValue().getExpelWarning() != ExpelWarning.정상)
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().getExpelWarning()));
    }
    
    public int countAttendanceStatusOf(final String nickname, final AttendanceStatus status) {
        validateNicknameExist(nickname);
        
        return crewAttendances.get(nickname).countAttendanceStatusOf(status);
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
