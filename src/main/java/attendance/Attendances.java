package attendance;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class Attendances {
    private final List<LocalDateTime> attendances;

    public Attendances(List<LocalDateTime> attendances) {
        this.attendances = attendances;
    }

    public void add(LocalDateTime dateTime) {
        if (existsByDate(LocalDate.from(dateTime))) {
            throw new IllegalArgumentException("[ERROR] 이미 출석하셨습니다. 수정 기능을 이용해주세요.");
        }
        attendances.add(dateTime);
    }

    public boolean existsByDate(LocalDate date) {
        List<LocalDate> dates = attendances.stream()
                .map(LocalDate::from)
                .toList();
        return dates.contains(date);
    }
}
