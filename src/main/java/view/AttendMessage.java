package view;

import domain.AttendStatus;

public enum AttendMessage {

    ATTEND("출석") {
        @Override
        public boolean match(AttendStatus attendStatus) {
            return attendStatus == AttendStatus.ATTEND;
        }
    },
    LATE("지각") {
        @Override
        public boolean match(AttendStatus attendStatus) {
            return attendStatus == AttendStatus.LATE;
        }
    },
    ABSENCE("결석") {
        @Override
        public boolean match(AttendStatus attendStatus) {
            return attendStatus == AttendStatus.ABSENCE;
        }
    };

    private final String message;

    AttendMessage(String message) {
        this.message = message;
    }

    abstract public boolean match(AttendStatus attendStatus);

    public String getMessage() {
        return message;
    }
}
