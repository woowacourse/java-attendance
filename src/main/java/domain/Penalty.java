package domain;

public enum Penalty {
    EXPELLED("제적", 6),
    COUNSELING("면담", 3),
    WARNING("경고", 2),
    NONE("패스",0);

    public String penalty;
    public int count;

    Penalty(String penalty, int count) {
        this.penalty = penalty;
        this.count = count;
    }

    public static Penalty check(int absenceCount, int latenessCount) {
        absenceCount += latenessCount / 3;
        for (Penalty penalty : values()) {
            if(absenceCount >= penalty.count) {
                return penalty;
            }
        }
        return NONE;
    }
}
