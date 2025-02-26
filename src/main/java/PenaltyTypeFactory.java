public class PenaltyTypeFactory {
    public static String create(int lateCount, int absenceCount) {
        absenceCount += lateCount / 3;

        if (absenceCount > 5) {
            return "제적";
        }
        if (absenceCount >= 3) {
            return "면담";
        }
        if (absenceCount >= 2) {
            return "경고";
        }

        return "";
    }
}
