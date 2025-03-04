package dto.requeset;

import java.time.LocalTime;

public record AttendRequest(
        String name,
        LocalTime attendTime
) {
}
