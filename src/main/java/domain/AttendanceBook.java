package domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class AttendanceBook {

    private final Map<String, Crew> crewRecords;

    public AttendanceBook() {
        this.crewRecords = new HashMap<>();
    }

    public int countCrew() {
        return crewRecords.size();
    }

    public void initializeCrewRecords(Scanner scanner) {
        // TODO: 파일에 있는 데이터들을 크루별로 분리해서 Crew 객체 생성
    }
}
