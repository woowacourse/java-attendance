public class ExpellPolicy {
    public String checkExpellStatus(int lateCount, int absentCount) {
        absentCount += lateCount/3;

        if(absentCount > 5) {
            return "제적";
        }
        return null;
    }
}

