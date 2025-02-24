package domain;

import java.util.List;
import java.util.stream.Collectors;

public class Attends {

    private List<Attend> attends;

    public Attends(List<Attend> attends) {
        this.attends = attends;
    }

    public void addAttend(Attend attend) {
        validateDuplicate(attend);
        attends.add(attend);
    }

    private void validateDuplicate(Attend targetAttend) {
        if (hasDayEqualsAttend(targetAttend)) {
            throw new IllegalArgumentException("같은 날짜에 출석할 수 없다.");
        }
    }

    public void edit(Attend attend) {
        if (hasDayEqualsAttend(attend)) {
            Attend before = findByAttend(attend);
            removeContainedAttend(before);
        }
        addAttend(attend);
    }

    public boolean hasDayEqualsAttend(Attend target) {
        return attends.stream()
                .anyMatch(target::isDayEqual);
    }

    public boolean hasDayEqualsAttend(int day) {
        return attends.stream()
                .anyMatch(attend -> attend.isDayEqual(day));
    }

    private Attend findByAttend(Attend target) {
        return attends.stream()
                .filter(target::isDayEqual)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("출석을 찾는데 실패하였음"));
    }

    private void removeContainedAttend(Attend target) {
        attends = attends.stream()
                .filter(before -> !before.isDayEqual(target))
                .collect(Collectors.toList());
    }

    public List<Attend> getAttends(List<Integer> dayOfWeek) {
        return dayOfWeek.stream()
                .map(this::findByDay)
                .toList();
    }

    public Attend findByDay(int day) {
        return attends.stream()
                .filter(attend -> attend.isDayEqual(day))
                .findFirst()
                .orElse(Attend.fromDay(day));
    }
}
