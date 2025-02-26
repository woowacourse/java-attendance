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
}
