import java.util.List;

public class Attends {

    public List<Attend> attends;

    public Attends(List<Attend> attends) {
        this.attends = attends;
    }

    public void addAttend(Attend attend) {
        validateDuplicate(attend);
        attends.add(attend);
    }

    private void validateDuplicate(Attend targetAttend) {
        if (attends.stream()
                .anyMatch(attend -> attend.isDayEqual(targetAttend))) {
            throw new IllegalArgumentException("같은 날짜에 출석할 수 없다.");
        }
    }
}
