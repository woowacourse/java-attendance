package dto;

import java.util.List;

public class AttendanceResultDTOs {
    
    private String name;
    private List<AttendanceResultDTO> attendanceResults;
    private int attendCount;
    private int lateCount;
    private int absentCount;
    
    public AttendanceResultDTOs(String name, List<AttendanceResultDTO> attendanceResults, int attendCount, int lateCount, int absentCount) {
        this.name = name;
        this.attendanceResults = attendanceResults;
        this.attendCount = attendCount;
        this.lateCount = lateCount;
        this.absentCount = absentCount;
    }
    
    public String getName() {
        return name;
    }
    
    public List<AttendanceResultDTO> getAttendanceResults() {
        return attendanceResults;
    }
    
    public int getAttendCount() {
        return attendCount;
    }
    
    public int getLateCount() {
        return lateCount;
    }
    
    public int getAbsentCount() {
        return absentCount;
    }
    
    @Override
    public String toString() {
        return "AttendanceResultDTOs{" +
                "name='" + name + '\'' +
                ", attendanceResults=" + attendanceResults +
                ", attendCount=" + attendCount +
                ", lateCount=" + lateCount +
                ", absentCount=" + absentCount +
                '}';
    }
}
