public class ExpellPolicy {

    private static final int LATE_ABSENT_RATE = 3;

    private static final int EXPELL_LIMIT = 5;
    private static final int INTERVIEW_LIMIT = 3;
    private static final int WARNING_LIMIT = 2;

    public String checkExpellStatus(int lateCount, int absentCount) {
        absentCount += lateCount/ LATE_ABSENT_RATE;

        if(absentCount > EXPELL_LIMIT) {
            return "제적";
        }

        if(absentCount >= INTERVIEW_LIMIT) {
            return "면담";
        }

        if(absentCount == WARNING_LIMIT) {
            return "경고";
        }

        return "경고없음";
    }
}

