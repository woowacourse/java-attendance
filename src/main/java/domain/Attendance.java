package domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Attendance {
    private final Map<Date, Time> dateTimes;

    public Attendance(Map<Date, Time> dateTimes) {
        this.dateTimes = new HashMap<>(dateTimes);
    }

    public void addDateTime(DateTime dateTime) {
        if (isAlreadyExists(dateTime)) {
            throw new IllegalArgumentException("해당 날짜의 출석 정보가 이미 존재합니다.");
        }
        dateTimes.put(dateTime.getDate(), dateTime.getTime());
    }

    public void updateDateTime(DateTime updateDateTime) {
        if (!isAlreadyExists(updateDateTime)) {
            throw new IllegalArgumentException("해당 날짜의 출석 정보가 없습니다.");
        }
        dateTimes.put(updateDateTime.getDate(), updateDateTime.getTime());
    }

    private boolean isAlreadyExists(DateTime dateTime) {
        Time time = dateTimes.get(dateTime.getDate());

        return !time.isNull();
    }

    public DateTime retrieveDateTime(Date date) {
        return new DateTime(date, dateTimes.get(date));
    }

    public List<DateTime> retrieveDateTimes() {
        return dateTimes.keySet().stream()
                .map(date -> new DateTime(date, dateTimes.get(date)))
                .toList();
    }

    public AttendanceStatus calculateAttendanceStatus(Date date) {
        DateTime dateTime = new DateTime(date, dateTimes.get(date));

        return AttendanceStatus.from(dateTime);
    }

    public List<AttendanceStatus> calculateAttendanceStatuses() {
        return retrieveDateTimes().stream()
                .map(dateTime -> new DateTime(dateTime.getDate(),
                        dateTimes.get(dateTime.getDate())))
                .map(AttendanceStatus::from)
                .toList();
    }

    public Map<AttendanceStatus, Integer> calculateAttendanceStatusCount() {
        return AttendanceStatus.calculateAttendanceStatusCount(retrieveDateTimes().stream()
                .map(dateTime -> new DateTime(dateTime.getDate(),
                        dateTimes.get(dateTime.getDate())))
                .map(AttendanceStatus::from)
                .toList());
    }
}
