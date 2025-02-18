import java.time.LocalTime;

public class Student {

    String name;
    int absent;
    int attendance;

    public Student() {
    }

    public void isStartTime(LocalTime localTime) {
        if (localTime.isAfter(LocalTime.of(23,0)) || localTime.isBefore(LocalTime.of(8,0))){
            throw new IllegalArgumentException("[ERROR] 캠퍼스 운영 시간이 아닙니다.");
        }
    }

}
