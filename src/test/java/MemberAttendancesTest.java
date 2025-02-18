import domain.Attendance;
import domain.MemberAttendances;
import dto.AttendanceResultDTO;
import dto.AttendanceResultDTOs;
import dto.ExpelMeasurementDTO;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MemberAttendancesTest {
    
    private final List<Attendance> attendanceList = List.of(
            new Attendance(LocalDateTime.of(2024, 12, 2, 10, 0)),
            new Attendance(LocalDateTime.of(2024, 12, 3, 10, 1)),
            new Attendance(LocalDateTime.of(2024, 12, 4, 10, 5)),
            new Attendance(LocalDateTime.of(2024, 12, 5, 10, 6)),
            new Attendance(LocalDateTime.of(2024, 12, 6, 10, 15)),
            new Attendance(LocalDateTime.of(2024, 12, 10, 10, 30)),
            new Attendance(LocalDateTime.of(2024, 12, 11, 10, 31)),
            new Attendance(LocalDateTime.of(2024, 12, 12, 10, 32))
    );
    
    @Test
    void 이름_입력시_출석기록_반환() {
        // given
        MemberAttendances attendances = new MemberAttendances("Lemon", attendanceList);
        
        // when
        AttendanceResultDTOs result = attendances.getAttendanceResult();
        
        // then
        assertThat(result.getName()).isEqualTo("Lemon");
        assertThat(result.getAttendCount()).isEqualTo(3);
        assertThat(result.getLateCount()).isEqualTo(3);
        assertThat(result.getAbsentCount()).isEqualTo(2);
        assertThat(result.getAttendanceResults()).containsExactlyInAnyOrder(
                new AttendanceResultDTO(LocalDateTime.of(2024, 12, 2, 10, 0), "출석"),
                new AttendanceResultDTO(LocalDateTime.of(2024, 12, 3, 10, 1), "출석"),
                new AttendanceResultDTO(LocalDateTime.of(2024, 12, 4, 10, 5), "출석"),
                new AttendanceResultDTO(LocalDateTime.of(2024, 12, 5, 10, 6), "지각"),
                new AttendanceResultDTO(LocalDateTime.of(2024, 12, 6, 10, 15), "지각"),
                new AttendanceResultDTO(LocalDateTime.of(2024, 12, 10, 10, 30), "지각"),
                new AttendanceResultDTO(LocalDateTime.of(2024, 12, 11, 10, 31), "결석"),
                new AttendanceResultDTO(LocalDateTime.of(2024, 12, 12, 10, 32), "결석")
        );
    }
    
    @Test
    void 제적위험여부를_반환_경고() {
        //given
        MemberAttendances attendances = new MemberAttendances("Lemon", List.of(
                new Attendance(LocalDateTime.of(2024, 12, 5, 10, 6)),
                new Attendance(LocalDateTime.of(2024, 12, 6, 10, 15)),
                new Attendance(LocalDateTime.of(2024, 12, 10, 10, 30)),
                new Attendance(LocalDateTime.of(2024, 12, 11, 10, 31))
        ));
        
        //when
        var result = attendances.measureExpelRisk();
        
        //then
        assertThat(result).isEqualTo(new ExpelMeasurementDTO("Lemon", 3, 1, "경고"));
    }
    
    @Test
    void 제적위험여부를_반환_면담() {
        //given
        MemberAttendances attendances = new MemberAttendances("Lemon", List.of(
                new Attendance(LocalDateTime.of(2024, 12, 5, 10, 6)),
                new Attendance(LocalDateTime.of(2024, 12, 6, 10, 15)),
                new Attendance(LocalDateTime.of(2024, 12, 10, 10, 30)),
                new Attendance(LocalDateTime.of(2024, 12, 11, 10, 31)),
                new Attendance(LocalDateTime.of(2024, 12, 12, 10, 32))
        ));
        
        //when
        var result = attendances.measureExpelRisk();
        
        //then
        assertThat(result).isEqualTo(new ExpelMeasurementDTO("Lemon", 3, 2, "면담"));
    }
    
    @Test
    void 제적위험여부를_반환_제적() {
        //given
        MemberAttendances attendances = new MemberAttendances("Lemon", List.of(
                new Attendance(LocalDateTime.of(2024, 12, 4, 10, 31)),
                new Attendance(LocalDateTime.of(2024, 12, 5, 10, 31)),
                new Attendance(LocalDateTime.of(2024, 12, 6, 10, 31)),
                new Attendance(LocalDateTime.of(2024, 12, 10, 10, 31)),
                new Attendance(LocalDateTime.of(2024, 12, 11, 10, 31)),
                new Attendance(LocalDateTime.of(2024, 12, 12, 10, 31))
        ));
        
        //when
        var result = attendances.measureExpelRisk();
        
        //then
        assertThat(result).isEqualTo(new ExpelMeasurementDTO("Lemon", 0, 6, "제적"));
    }
    
}
