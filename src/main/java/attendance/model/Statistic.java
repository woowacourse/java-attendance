package attendance.model;

import java.util.Map;

public class Statistic {
    private int presentCount;
    private int lateCount;
    private int absentCount;
    private Status status;

    public Statistic() {
        presentCount = 0;
        lateCount = 0;
        absentCount = 0;
        status = Status.NONE;
    }

    public void updateStatistic(Attendances attendances) {
        Map<AttendanceType, Integer> info = attendances.getInfo();

        this.presentCount = info.get(AttendanceType.PRESENT);
        this.lateCount = info.get(AttendanceType.LATE);
        this.absentCount = info.get(AttendanceType.ABSENT);
    }


    public int getPresentCount() {
        return presentCount;
    }

    public int getLateCount() {
        return lateCount;
    }

    public int getAbsentCount() {
        return absentCount;
    }
}
