package domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Attendance {
    private final Map<WorkDate, WorkTime> dateTimes;

    public Attendance(Map<WorkDate, WorkTime> dateTimes) {
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
        WorkTime workTime = dateTimes.get(dateTime.getDate());

        return !workTime.isNull();
    }

    public DateTime retrieveDateTime(WorkDate workDate) {
        return new DateTime(workDate, dateTimes.get(workDate));
    }

    public List<DateTime> retrieveDateTimes() {
        return dateTimes.keySet().stream()
                .map(date -> new DateTime(date, dateTimes.get(date)))
                .toList();
    }

    public AttendanceStatus calculateAttendanceStatus(WorkDate workDate) {
        DateTime dateTime = new DateTime(workDate, dateTimes.get(workDate));

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
