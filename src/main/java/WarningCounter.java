public class WarningCounter {
    private int tardies;
    private int absences;

    public WarningCounter(int absences, int tardies) {
        this.tardies = tardies;
        this.absences = absences;
    }

    public int getConvertedAbsences() {
        return absences + tardies / 3;
    }

    public String getStatus() {
        if (getConvertedAbsences() >= 3) {
            return "면담";
        }
        if (getConvertedAbsences() >= 2) {
            return "경고";
        }
        return null;
    }
}
