public class ExpellPolicy {
    public String checkExpellStatus(int lateCount, int absentCount) {
        if(absentCount > 5) {
            return "제적";
        }
        return null;
    }
}

