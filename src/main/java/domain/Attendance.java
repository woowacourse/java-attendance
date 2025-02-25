package domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Attendance {
    private final Map<WorkDate, WorkTime> dateTimes;

    public Attendance(Map<WorkDate, WorkTime> dateTimes) {
        this.dateTimes = new HashMap<>(dateTimes);
    }

    public void addDateTime(WorkDateTime workDateTime) {
        if (isAlreadyExists(workDateTime)) {
            throw new IllegalArgumentException("해당 날짜의 출석 정보가 이미 존재합니다.");
        }

        dateTimes.put(workDateTime.getDate(), workDateTime.getTime());
    }

    public void updateDateTime(WorkDateTime updateWorkDateTime) {
        if (!isAlreadyExists(updateWorkDateTime)) {
            throw new IllegalArgumentException("해당 날짜의 출석 정보가 없습니다.");
        }

        dateTimes.put(updateWorkDateTime.getDate(), updateWorkDateTime.getTime());
    }

    private boolean isAlreadyExists(WorkDateTime workDateTime) {
        WorkTime workTime = dateTimes.get(workDateTime.getDate());

        return !workTime.isNull();
    }

    public WorkDateTime retrieveDateTime(WorkDate workDate) {
        return new WorkDateTime(workDate, dateTimes.get(workDate));
    }

    public List<WorkDateTime> retrieveDateTimes() {
        return dateTimes.keySet().stream()
                .map(date -> new WorkDateTime(date, dateTimes.get(date)))
                .toList();
    }
}
