package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        if (hasDayEqualsAttend(targetAttend)) {
            throw new IllegalArgumentException("같은 날짜에 출석할 수 없다.");
        }
    }

    public Attend findByDay(int day) {
        return attends.stream()
                .filter(attend -> attend.isDayEqual(day))
                .findFirst()
                .orElse(Attend.fromDay(day));
        // TODO: 예외를 던지던 테스트 코드 수정
    }

    public boolean hasDayEqualsAttend(Attend target) {
        return attends.stream()
                .anyMatch(target::isDayEqual);
    }

    public void edit(Attend attend) {
        if (hasDayEqualsAttend(attend)) {
            // 똑같은 거 찾기
            Attend before = findByAttend(attend);

            // 찾은 객체를 토대로 list에서 삭제
            removeByAttend(before);
        }
        addAttend(attend);
    }

    private Attend findByAttend(Attend target) {
        return attends.stream()
                .filter(target::isDayEqual)
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }

    private void removeByAttend(Attend target) {
        attends = attends.stream()
                .filter(before -> !before.isDayEqual(target))
                .collect(Collectors.toList());
    }

    public boolean hasDayEqualsAttend(int day) {
        return attends.stream()
                .anyMatch(attend -> attend.isDayEqual(day));
    }

    public List<Attend> getAttends(List<Integer> dayOfWeek) {
        List<Integer> existAttendDay = dayOfWeek.stream()
                .filter(day -> hasDayEqualsAttend(day))
                .toList();

        List<Attend> result = new ArrayList<>();
        for (int day : existAttendDay) {
            result.add(findByDay(day));
        }
        return result;
    }
}
