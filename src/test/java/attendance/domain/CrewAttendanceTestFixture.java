package attendance.domain;

import attendance.util.FileLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CrewAttendanceTestFixture {
    public static Map<String, CrewAttendance> createCrewAttendances() {
        List<String> contents = new ArrayList<>();

        contents.add("쿠키,2024-12-13 10:08");
        contents.add("빙봉,2024-12-13 10:07");
        contents.add("빙티,2024-12-13 10:07");
        contents.add("이든,2024-12-13 10:07");
        contents.add("빙봉,2024-12-12 11:11");
        contents.add("이든,2024-12-12 10:06");
        contents.add("짱수,2024-12-12 10:00");
        contents.add("빙봉,2024-12-11 10:02");
        contents.add("쿠키,2024-12-11 10:02");
        contents.add("빙티,2024-12-10 10:08");
        contents.add("빙봉,2024-12-10 10:06");
        contents.add("이든,2024-12-10 10:02");
        contents.add("쿠키,2024-12-10 10:01");
        contents.add("짱수,2024-12-10 10:00");
        contents.add("쿠키,2024-12-09 13:03");
        contents.add("빙봉,2024-12-09 13:02");
        contents.add("이든,2024-12-09 13:01");
        contents.add("짱수,2024-12-09 13:00");
        contents.add("빙봉,2024-12-06 10:08");
        contents.add("이든,2024-12-06 10:07");
        contents.add("빙티,2024-12-06 10:01");
        contents.add("짱수,2024-12-06 10:00");
        contents.add("쿠키,2024-12-05 10:07");
        contents.add("빙봉,2024-12-05 10:06");
        contents.add("빙티,2024-12-05 10:06");
        contents.add("짱수,2024-12-05 10:00");
        contents.add("이든,2024-12-04 10:08");
        contents.add("빙봉,2024-12-04 10:07");
        contents.add("빙티,2024-12-04 10:02");
        contents.add("쿠키,2024-12-04 10:02");
        contents.add("짱수,2024-12-04 10:00");
        contents.add("빙티,2024-12-03 09:58");
        contents.add("이든,2024-12-03 10:06");
        contents.add("쿠키,2024-12-03 10:06");
        contents.add("빙봉,2024-12-03 10:03");
        contents.add("짱수,2024-12-03 10:00");
        contents.add("빙봉,2024-12-02 13:06");
        contents.add("이든,2024-12-02 13:02");
        contents.add("쿠키,2024-12-02 13:01");
        contents.add("빙티,2024-12-02 13:00");
        contents.add("짱수,2024-12-02 13:00");

        return FileLoader.loadAll(contents);
    }
}
