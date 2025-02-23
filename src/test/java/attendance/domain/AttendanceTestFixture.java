package attendance.domain;

import attendance.util.FileLoader;
import java.util.ArrayList;
import java.util.List;

public class AttendanceTestFixture {
    public static List<Attendance> createAttendances() {
        List<String> datas = new ArrayList<>();
        datas.add("쿠키,2024-12-13 10:08");
        datas.add("빙봉,2024-12-13 10:07");
        datas.add("빙티,2024-12-13 10:07");
        datas.add("이든,2024-12-13 10:07");
        datas.add("빙봉,2024-12-12 11:11");
        datas.add("이든,2024-12-12 10:06");
        datas.add("짱수,2024-12-12 10:00");
        datas.add("빙봉,2024-12-11 10:02");
        datas.add("쿠키,2024-12-11 10:02");
        datas.add("빙티,2024-12-10 10:08");
        datas.add("빙봉,2024-12-10 10:06");
        datas.add("이든,2024-12-10 10:02");
        datas.add("쿠키,2024-12-10 10:01");
        datas.add("짱수,2024-12-10 10:00");
        datas.add("쿠키,2024-12-09 13:03");
        datas.add("빙봉,2024-12-09 13:02");
        datas.add("이든,2024-12-09 13:01");
        datas.add("짱수,2024-12-09 13:00");
        datas.add("빙봉,2024-12-06 10:08");
        datas.add("이든,2024-12-06 10:07");
        datas.add("빙티,2024-12-06 10:01");
        datas.add("짱수,2024-12-06 10:00");
        datas.add("쿠키,2024-12-05 10:07");
        datas.add("빙봉,2024-12-05 10:06");
        datas.add("빙티,2024-12-05 10:06");
        datas.add("짱수,2024-12-05 10:00");
        datas.add("이든,2024-12-04 10:08");
        datas.add("빙봉,2024-12-04 10:07");
        datas.add("빙티,2024-12-04 10:02");
        datas.add("쿠키,2024-12-04 10:02");
        datas.add("짱수,2024-12-04 10:00");
        datas.add("빙티,2024-12-03 09:58");
        datas.add("이든,2024-12-03 10:06");
        datas.add("쿠키,2024-12-03 10:06");
        datas.add("빙봉,2024-12-03 10:03");
        datas.add("짱수,2024-12-03 10:00");
        datas.add("빙봉,2024-12-02 13:06");
        datas.add("이든,2024-12-02 13:02");
        datas.add("쿠키,2024-12-02 13:01");
        datas.add("빙티,2024-12-02 13:00");
        datas.add("짱수,2024-12-02 13:00");

        return FileLoader.loadAll(datas);
    }
}
