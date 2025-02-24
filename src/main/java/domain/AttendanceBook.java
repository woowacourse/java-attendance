package domain;

import vo.AttendResult;
import vo.AttendanceModifyResult;
import vo.AttendanceRecordFindResults;
import vo.ExpelWarningResult;

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
