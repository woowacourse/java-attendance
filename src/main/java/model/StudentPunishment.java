package model;

public enum StudentPunishment {
    WARNING(2,"경고 대상자입니다."),
    INTERVIEW(3,"면담 대상자입니다."),
    DISMISSAL(5,"제적 대상자입니다.");

    private final int standard;
    private final String expulsionNotice;

    StudentPunishment(int standard, String expulsionNotice) {
        this.standard = standard;
        this.expulsionNotice = expulsionNotice;
    }

    public int getStandard() {
        return standard;
    }

    public static String makeExpulsionNotice(int riskLevel) {
        if (riskLevel >= StudentPunishment.DISMISSAL.getStandard()) {
            return DISMISSAL.expulsionNotice;
        }
        if (riskLevel >= StudentPunishment.INTERVIEW.getStandard()) {
            return INTERVIEW.expulsionNotice;
        }
        if (riskLevel >= StudentPunishment.WARNING.getStandard()) {
            return WARNING.expulsionNotice;
        }
        return null;
    }

}
