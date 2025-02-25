public class ExpellPolicy {

    private static final int LATE_ABSENT_RATE = 3;
    private static final int EXPELL_LIMIT = 5;

    public String checkExpellStatus(int lateCount, int absentCount) {
        absentCount += lateCount/ LATE_ABSENT_RATE;

        if(absentCount > EXPELL_LIMIT) {
            return "제적";
        }

        if(absentCount >= 3) {
            return "면담";
        }
        return null;
    }
}

