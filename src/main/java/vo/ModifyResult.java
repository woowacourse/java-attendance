package vo;

import domain.AttendanceDateTime;

public record ModifyResult(
    AttendanceDateTime before,
    AttendanceDateTime after
) {

}
