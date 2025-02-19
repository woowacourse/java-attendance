package attendance.domain;

import java.util.Objects;

public class Crew {
    private final String crewName;
    private int safeCount;
    private int lateCount;
    private int absentCount;

    public Crew(String crewName) {
        this.crewName = crewName;
        this.safeCount = 0;
        this.lateCount = 0;
        this.absentCount = 0;
    }

    public boolean isSameCrewName(final String crewName) {
        return this.crewName.equals(crewName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true; // 만일 현 객체 this와 매개변수 객체가 같을 경우 true
        }
        if (!(o instanceof Crew)) {
            return false; // 만일 매개변수 객체가 Crew 타입과 호환되지 않으면 false
        }
        Crew crew = (Crew) o; // 만일 매개변수 객체가 Crew 타입과 호환된다면 다운캐스팅(down casting) 진행
        return Objects.equals(this.crewName, crew.crewName);
        // this 객체 이름과 매개변수 객체 이름이 같을경우 true, 다를 경우 false
    }

    public void plusSafeCount() {
        this.safeCount++;
    }

    public void plusLateCount() {
        this.lateCount++;
    }

    public void plusAbsentCount() {
        this.absentCount++;
    }

    public int getLateCount() {
        return lateCount;
    }

    public int getAbsentCount() {
        return absentCount;
    }

    public String getName() {
        return crewName;
    }
}
