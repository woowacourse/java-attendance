import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AttendanceBook {

    public Map<String, Attends> map;

    public AttendanceBook() {
        this.map = new HashMap<>();
    }

    public void attend(String name, Attend attend) {
        var attendOfUser = map.getOrDefault(name, new Attends(new ArrayList<>()));
        validateAttendableDay(attend);
        attendOfUser.addAttend(attend);
        map.put(name, attendOfUser);
    }

    public void edit(String name, Attend attend) {
        var attendOfUser = map.getOrDefault(name, new Attends(new ArrayList<>()));
        validateAttendableDay(attend);
        attendOfUser.edit(attend);
        map.put(name, attendOfUser);
    }
    private void validateAttendableDay(Attend attend) {
        if (DateUtil.isDayOff(attend)) {
            throw new IllegalArgumentException("쉬는날은 출석할 수 없음");
        }
    }

    public Attends findByName(String name) {
        return map.getOrDefault(name, null);
    }
}
