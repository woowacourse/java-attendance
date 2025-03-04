package attendance.domain;

import java.time.LocalTime;

public class EmptyLocalTime extends NullableLocalTime {

    public EmptyLocalTime() {
        super(null);
    }

    @Override
    public boolean isPresent() {
        return false;
    }
}
