import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttendanceBook {

    public Map<String, Attends> map;
    private static final LocalTime START_TIME = LocalTime.of(8, 0);
    private static final LocalTime END_TIME = LocalTime.of(23, 0);

    public AttendanceBook() {
        this.map = new HashMap<>();
    }

    public void attend(String name, Attend attend) {
        var attendOfUser = map.getOrDefault(name, new Attends(new ArrayList<>()));
        validateAttendableDay(attend);
        validateAttendableTime(attend);
        attendOfUser.addAttend(attend);
        map.put(name, attendOfUser);
    }

    public void edit(String name, Attend attend) {
        var attendOfUser = map.getOrDefault(name, new Attends(new ArrayList<>()));
        validateAttendableDay(attend);
        validateAttendableTime(attend);
        attendOfUser.edit(attend);
        map.put(name, attendOfUser);
    }

    private void validateAttendableTime(Attend attend) {
        if (DateUtil.isTimeOff(attend, START_TIME, END_TIME)) {
            throw new IllegalArgumentException("운영 시간 외에는 출석할 수 없음");

        }
    }

    private void validateAttendableDay(Attend attend) {
        if (DateUtil.isDayOff(attend)) {
            throw new IllegalArgumentException("쉬는날은 출석할 수 없음");
        }
    }

    public Attends findByName(String name) {
        return map.getOrDefault(name, null);
    }

    public List<Attend> getAttends(String name) {
        // TODO: 닉네임 존재 여부 확인하는 validate 추가해야함
        List<Integer> days = DateUtil.getAttendUntilDay(Current.TODAY.getYesterday());
        return map.get(name)
                .getAttends(days);
    }
}
