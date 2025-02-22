package domain;

import java.time.LocalTime;

public enum AttendStatus {
    ATTEND {
        @Override
        public boolean match(Attend attend, LocalTime lateTime, LocalTime absenceTime) {
            return attend.hasTime() && attend.isBefore(lateTime);
        }
    },
    LATE {
        @Override
        public boolean match(Attend attend, LocalTime lateTime, LocalTime absenceTime) {
            return attend.hasTime() && (attend.isEqual(lateTime) || attend.isAfter(lateTime))
                    && attend.isBefore(absenceTime);
        }
    },
    ABSENCE {
        @Override
        public boolean match(Attend attend, LocalTime lateTime, LocalTime absenceTime) {
            return !attend.hasTime() || attend.isEqual(absenceTime) || attend.isAfter(absenceTime);
        }
    };

    abstract public boolean match(Attend attend, LocalTime lateTime, LocalTime absenceTime);
}
