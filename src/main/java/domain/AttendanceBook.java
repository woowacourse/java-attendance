package domain;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttendanceBook {

    private final Map<Nickname, AttendResult> attendBook;
    private final LocalDate today;

    public AttendanceBook(LocalDate today) {
        this.attendBook = new HashMap<>();
        this.today = today;
    }

    public void register(final Nickname name) {
        if (!attendBook.containsKey(name)) {
            attendBook.put(name, new AttendResult(today));
        }
    }

    public void addAttend(final Nickname name, final Attend attend) {
        checkContainsName(name);
        AttendResult attendResult = attendBook.get(name);
        attendResult.addAttend(attend);
    }

    private void checkContainsName(final Nickname name) {
        if (!attendBook.containsKey(name)) {
            throw new IllegalArgumentException("존재하지 않는 닉네임입니다.");
        }
    }

    public Attend edit(final Nickname name, final Attend afterAttend) {
        checkContainsName(name);
        AttendResult attendResult = attendBook.get(name);
        return attendResult.edit(afterAttend);
    }

    public List<Attend> searchAttend(Nickname name, final int day) {
        checkContainsName(name);
        AttendResult attendResult = attendBook.get(name);
        return attendResult.getAttendResult(day);
    }

    public WarningStatus judgeAttendStatus(Nickname name) {
        checkContainsName(name);
        AttendResult attendResult = attendBook.get(name);
        return attendResult.judgeWarningStatus(today.getDayOfMonth());
    }

    public AttendCount countAttend(Nickname name) {
        checkContainsName(name);
        AttendResult attendResult = attendBook.get(name);
        return attendResult.countAttendStatus(today.getDayOfMonth());
    }

    public List<WarningCrew> searchWarningCrew() {
        List<WarningCrew> warningCrews = attendBook.keySet().stream()
                .filter(name -> judgeAttendStatus(name) != WarningStatus.PASS)
                .map(name -> new WarningCrew(name, countAttend(name), judgeAttendStatus(name)))
                .toList();
        return WarningCrew.sort(warningCrews);
    }
}
