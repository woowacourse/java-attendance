package attendance.model.campus;

import java.time.LocalTime;

public class CampusOperationPolicy {

    private static final LocalTime DEFAULT_OPEN_TIME = LocalTime.of(8, 0);
    private static final LocalTime DEFAULT_CLOSE_TIME = LocalTime.of(23, 0);

    public boolean isCampusOpen(LocalTime time) {
        return true;
    }
}
