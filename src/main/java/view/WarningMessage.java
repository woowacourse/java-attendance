package view;

import domain.WarningStatus;

public enum WarningMessage {
    CLEAR("", "") {
        @Override
        public boolean match(WarningStatus warningStatus) {
            return warningStatus == WarningStatus.CLEAR;
        }
    },
    WARNING("경고", "경고 대상자 입니다.") {
        @Override
        public boolean match(WarningStatus warningStatus) {
            return warningStatus == WarningStatus.WARNING;
        }
    },
    INTERVIEW("면담", "면담 대상자 입니다.") {
        @Override
        public boolean match(WarningStatus warningStatus) {
            return warningStatus == WarningStatus.INTERVIEW;
        }
    },
    EXPEL("제적", "제적 대상자 입니다.") {
        @Override
        public boolean match(WarningStatus warningStatus) {
            return warningStatus == WarningStatus.EXPEL;
        }
    };

    private final String message;
    private final String longMessage;

    WarningMessage(String message, String longMessage) {
        this.message = message;
        this.longMessage = longMessage;
    }

    abstract public boolean match(WarningStatus warningStatus);

    public String getMessage() {
        return message;
    }

    public String getLongMessage() {
        return longMessage;
    }
}
