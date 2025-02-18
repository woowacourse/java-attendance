package attendance.domain;

import java.util.List;

public class Attendance {

    private final List<String> names;

    public Attendance(List<String> names){
        this.names = names;
    }

    public void checkName(String name) {
        if(!names.contains(name)){
            throw new IllegalArgumentException("[ERROR] 출석부에 없는 크루원입니다.");
        }
    }
}
