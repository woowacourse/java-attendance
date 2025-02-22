package domain;

public enum WarningStatus {
    CLEAR {
        @Override
        public boolean match(long totalAbsenceCount) {
            return totalAbsenceCount < 2;
        }
    },
    WARNING {
        @Override
        public boolean match(long totalAbsenceCount) {
            return totalAbsenceCount == 2;
        }
    },
    INTERVIEW {
        @Override
        public boolean match(long totalAbsenceCount) {
            return totalAbsenceCount >= 3 && totalAbsenceCount <= 5;
        }
    },
    EXPEL {
        @Override
        public boolean match(long totalAbsenceCount) {
            return totalAbsenceCount > 5;
        }
    };

    abstract public boolean match(long totalAbsenceCount);
}
