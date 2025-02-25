package domain;

import domain.vo.AttendResult;
import domain.vo.AttendanceModifyResult;
import domain.vo.AttendanceRecordFindResults;
import domain.vo.ExpelWarningResult;

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
    
    public AttendResult attend(final String nickname, final LocalDate date, final LocalTime time) {
        validateNicknameExist(nickname);
        
        return crewAttendances.get(nickname).attend(date, time);
    }
    
    public AttendanceModifyResult modify(final String nickname, final LocalDate targetDate, final LocalTime newTime) {
        validateNicknameExist(nickname);
        
        return crewAttendances.get(nickname).modify(targetDate, newTime);
    }
    
    public AttendanceRecordFindResults findRecords(final String nickname) {
        validateNicknameExist(nickname);
        
        return crewAttendances.get(nickname).findRecord();
    }
    
    public Map<String, ExpelWarningResult> calculateExpelWarnings() {
        return crewAttendances.entrySet().stream()
                .filter(entry -> entry.getValue().calculateExpelWarning().expelWarning() != ExpelWarning.정상)
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().calculateExpelWarning()));
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
