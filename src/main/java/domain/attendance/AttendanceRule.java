package domain.attendance;

import domain.attendance.constant.AttendanceStatus;
import domain.datetime.CampusTime;

public interface AttendanceRule {

    AttendanceStatus calculateStatus(CampusTime time);
}
