package domain;

import dto.result.AttendResult;
import dto.result.AttendanceModifyResult;
import dto.result.ExpelMeasurementResult;
import dto.result.MemberAttendResult;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MemberAttendancesTest {
    
    private final SoftAssertions soft = new SoftAssertions();
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
    
    @Nested
    class 출석_테스트 {
        
        @Test
        void 출석을_등록한다() {
            // given
            MemberAttendances attendances = new MemberAttendances("Lemon", attendanceList);
            LocalDateTime attendTime = LocalDateTime.of(2024, 12, 13, 10, 0);
            
            // when
            AttendResult result = attendances.attend(attendTime);
            
            // then
            assertThat(result).isEqualTo(new AttendResult(LocalDateTime.of(2024, 12, 13, 10, 0), AttendanceStatus.출석, true));
        }
    }
    
    @Nested
    class 출석_기록_확인_테스트 {
        
        @Test
        void 출석_기록을_반환한다() {
            // given
            MemberAttendances attendances = new MemberAttendances("Lemon", attendanceList);
            
            // when
            MemberAttendResult result = attendances.getAttendanceResult();
            
            // then
            soft.assertThat(result.name()).isEqualTo("Lemon");
            soft.assertThat(result.attendCount()).isEqualTo(3);
            soft.assertThat(result.lateCount()).isEqualTo(3);
            soft.assertThat(result.absentCount()).isEqualTo(2);
            soft.assertThat(result.attendanceResults()).containsExactlyInAnyOrder(
                    new AttendResult(LocalDateTime.of(2024, 12, 2, 10, 0), AttendanceStatus.출석, true),
                    new AttendResult(LocalDateTime.of(2024, 12, 3, 10, 1), AttendanceStatus.출석, true),
                    new AttendResult(LocalDateTime.of(2024, 12, 4, 10, 5), AttendanceStatus.출석, true),
                    new AttendResult(LocalDateTime.of(2024, 12, 5, 10, 6), AttendanceStatus.지각, true),
                    new AttendResult(LocalDateTime.of(2024, 12, 6, 10, 15), AttendanceStatus.지각, true),
                    new AttendResult(LocalDateTime.of(2024, 12, 10, 10, 30), AttendanceStatus.지각, true),
                    new AttendResult(LocalDateTime.of(2024, 12, 11, 10, 31), AttendanceStatus.결석, true),
                    new AttendResult(LocalDateTime.of(2024, 12, 12, 10, 32), AttendanceStatus.결석, true)
            );
        }
    }
    
    @Nested
    class 출석_기록_수정_테스트 {
        
        @Test
        void 출석_정보를_수정한다() {
            // given
            MemberAttendances attendances = new MemberAttendances("Lemon", List.of(
                    new Attendance(LocalDateTime.of(2024, 12, 4, 10, 31)),
                    new Attendance(LocalDateTime.of(2024, 12, 5, 10, 31)),
                    new Attendance(LocalDateTime.of(2024, 12, 6, 10, 31)),
                    new Attendance(LocalDateTime.of(2024, 12, 10, 10, 31)),
                    new Attendance(LocalDateTime.of(2024, 12, 11, 10, 31)),
                    new Attendance(LocalDateTime.of(2024, 12, 12, 10, 31))
            ));
            var date = LocalDate.of(2024, 12, 4);
            var time = LocalTime.of(10, 5);
            
            // when
            AttendanceModifyResult result = attendances.modifyAttendance(date, time);
            
            // then
            assertThat(result).isEqualTo(new AttendanceModifyResult(
                    LocalDate.of(2024, 12, 4),
                    LocalTime.of(10, 31), AttendanceStatus.결석,
                    LocalTime.of(10, 5), AttendanceStatus.출석)
            );
        }
    }
    
    @Nested
    class 제적_위험여부_확인_테스트 {
        
        @Test
        void 제적위험여부를_반환한다_경고() {
            // given
            MemberAttendances attendances = new MemberAttendances("Lemon", List.of(
                    new Attendance(LocalDateTime.of(2024, 12, 5, 10, 6)),
                    new Attendance(LocalDateTime.of(2024, 12, 6, 10, 15)),
                    new Attendance(LocalDateTime.of(2024, 12, 10, 10, 30)),
                    new Attendance(LocalDateTime.of(2024, 12, 11, 10, 31))
            ));
            
            // when
            var result = attendances.measureExpelRisk();
            
            // then
            assertThat(result).isEqualTo(new ExpelMeasurementResult("Lemon", 3, 1, ExpelRisk.경고));
        }
        
        @Test
        void 제적위험여부를_반환한다_면담() {
            // given
            MemberAttendances attendances = new MemberAttendances("Lemon", List.of(
                    new Attendance(LocalDateTime.of(2024, 12, 5, 10, 6)),
                    new Attendance(LocalDateTime.of(2024, 12, 6, 10, 15)),
                    new Attendance(LocalDateTime.of(2024, 12, 10, 10, 30)),
                    new Attendance(LocalDateTime.of(2024, 12, 11, 10, 31)),
                    new Attendance(LocalDateTime.of(2024, 12, 12, 10, 32))
            ));
            
            // when
            var result = attendances.measureExpelRisk();
            
            // then
            assertThat(result).isEqualTo(new ExpelMeasurementResult("Lemon", 3, 2, ExpelRisk.면담));
        }
        
        @Test
        void 제적위험여부를_반환한다_제적() {
            // given
            MemberAttendances attendances = new MemberAttendances("Lemon", List.of(
                    new Attendance(LocalDateTime.of(2024, 12, 4, 10, 31)),
                    new Attendance(LocalDateTime.of(2024, 12, 5, 10, 31)),
                    new Attendance(LocalDateTime.of(2024, 12, 6, 10, 31)),
                    new Attendance(LocalDateTime.of(2024, 12, 10, 10, 31)),
                    new Attendance(LocalDateTime.of(2024, 12, 11, 10, 31)),
                    new Attendance(LocalDateTime.of(2024, 12, 12, 10, 31))
            ));
            
            // when
            var result = attendances.measureExpelRisk();
            
            // then
            assertThat(result).isEqualTo(new ExpelMeasurementResult("Lemon", 0, 6, ExpelRisk.제적));
        }
    }
}
