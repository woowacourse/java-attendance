package domain;

import dto.result.AttendResult;
import dto.result.ExpelMeasurementResult;
import dto.result.MemberAttendResult;
import dto.result.MemberAttendanceModifyResult;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import util.exception.CrewNotExistException;
import util.exception.IllegalAttendTimeException;
import util.exception.WeekendAttendException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class AttendanceBookTest {
    
    private final SoftAssertions soft = new SoftAssertions();
    private final Map<String, MemberAttendances> attendancesMap = Map.of(
            "Lemon", new MemberAttendances("Lemon", List.of(
                    new Attendance(LocalDateTime.of(2024, 12, 2, 10, 0)),
                    new Attendance(LocalDateTime.of(2024, 12, 3, 10, 1)),
                    new Attendance(LocalDateTime.of(2024, 12, 4, 10, 5)),
                    new Attendance(LocalDateTime.of(2024, 12, 5, 10, 6)))),
            "Dompoo", new MemberAttendances("Dompoo", List.of(
                    new Attendance(LocalDateTime.of(2024, 12, 6, 10, 15)),
                    new Attendance(LocalDateTime.of(2024, 12, 10, 10, 30)),
                    new Attendance(LocalDateTime.of(2024, 12, 11, 10, 31)),
                    new Attendance(LocalDateTime.of(2024, 12, 12, 10, 32))))
    );
    
    @Nested
    class 출석_테스트 {
        
        @Test
        void 닉네임과_날짜를_입력하면_출석이_기록된다() {
            // given
            String inputName = "Lemon";
            LocalDateTime attendDateTime = LocalDateTime.of(2024, 12, 13, 10, 2);
            AttendanceBook attendanceBook = new AttendanceBook(attendancesMap);
            
            // expected
            assertThatCode(() -> attendanceBook.addAttendance(inputName, attendDateTime)).doesNotThrowAnyException();
        }
        
        @Test
        void 출석이_기록되면_정보를_확인할_수_있다() {
            // given
            String inputName = "Lemon";
            LocalDateTime attendDateTime = LocalDateTime.of(2024, 12, 13, 10, 2);
            AttendanceBook attendanceBook = new AttendanceBook(attendancesMap);
            
            // when
            AttendResult attendResult = attendanceBook.addAttendance(inputName, attendDateTime);
            
            // then
            soft.assertThat(attendResult.attendanceStatus()).isEqualTo(AttendanceStatus.출석);
            soft.assertThat(attendResult.attendanceDateTime()).isEqualTo(LocalDateTime.of(2024, 12, 13, 10, 2));
        }
        
        @Test
        void 존재하지_않는_멤버를_입력시_예외가_발생한다() {
            // given
            String inputName = "WANNI";
            LocalDateTime attendDateTime = LocalDateTime.of(2024, 12, 13, 10, 2);
            AttendanceBook attendanceBook = new AttendanceBook(attendancesMap);
            
            // expected
            assertThatThrownBy(() -> attendanceBook.addAttendance(inputName, attendDateTime))
                    .isExactlyInstanceOf(CrewNotExistException.class)
                    .hasMessage("WANNI는 존재하는 크루가 아닙니다.");
        }
        
        @Test
        void 주말에_출석시_예외가_발생한다() {
            // given
            String inputName = "Lemon";
            LocalDateTime attendDateTime = LocalDateTime.of(2024, 12, 1, 10, 2);
            AttendanceBook attendanceBook = new AttendanceBook(attendancesMap);
            
            // expected
            assertThatThrownBy(() -> attendanceBook.addAttendance(inputName, attendDateTime))
                    .isExactlyInstanceOf(WeekendAttendException.class)
                    .hasMessage("주말에는 출석할 수 없습니다.");
        }
        
        @Test
        void 지정된_시간이_아닐_때_출석시_예외가_발생한다() {
            // given
            String inputName = "Lemon";
            LocalDateTime attendDateTime = LocalDateTime.of(2024, 12, 3, 7, 30);
            AttendanceBook attendanceBook = new AttendanceBook(attendancesMap);
            
            // expected
            assertThatThrownBy(() -> attendanceBook.addAttendance(inputName, attendDateTime))
                    .isExactlyInstanceOf(IllegalAttendTimeException.class)
                    .hasMessage("출석 가능한 시간이 아닙니다. (출석 가능 시간 : 08:00 ~ 23:00)");
        }
    }
    
    @Nested
    class 출석_기록_수정_테스트 {
        
        @Test
        void 출석_기록을_수정할_수_있다() {
            // given
            String name = "Dompoo";
            LocalDate date = LocalDate.of(2024, 12, 6);
            LocalTime time = LocalTime.of(10, 5);
            AttendanceBook attendanceBook = new AttendanceBook(attendancesMap);
            
            // when
            MemberAttendanceModifyResult result = attendanceBook.editAttendance(name, date, time);
            
            // then
            assertThat(result).isEqualTo(new MemberAttendanceModifyResult(
                    "Dompoo",
                    LocalDate.of(2024, 12, 6),
                    LocalTime.of(10, 15),
                    AttendanceStatus.지각,
                    LocalTime.of(10, 5),
                    AttendanceStatus.출석
            ));
        }
        
        @Test
        void 존재하지_않는_회원의_기록을_수정하려고_하면_예외가_발생한다() {
            // given
            String name = "Moko";
            LocalDate date = LocalDate.of(2024, 12, 6);
            LocalTime time = LocalTime.of(10, 5);
            AttendanceBook attendanceBook = new AttendanceBook(attendancesMap);
            
            // expected
            assertThatThrownBy(() -> attendanceBook.editAttendance(name, date, time))
                    .isExactlyInstanceOf(CrewNotExistException.class)
                    .hasMessage("Moko는 존재하는 크루가 아닙니다.");
        }
        
        @Test
        void 주말_기록을_수정하려하면_예외가_발생한다() {
            // given
            String name = "Dompoo";
            LocalDate date = LocalDate.of(2024, 12, 1);
            LocalTime time = LocalTime.of(10, 5);
            AttendanceBook attendanceBook = new AttendanceBook(attendancesMap);
            
            // expected
            assertThatThrownBy(() -> attendanceBook.editAttendance(name, date, time))
                    .isExactlyInstanceOf(WeekendAttendException.class)
                    .hasMessage("주말에는 출석할 수 없습니다.");
        }
        
        @Test
        void 수정시_출석_가능한_시간이_아니면_예외가_발생한다() {
            // given
            String name = "Dompoo";
            LocalDate date = LocalDate.of(2024, 12, 3);
            LocalTime time = LocalTime.of(7, 5);
            AttendanceBook attendanceBook = new AttendanceBook(attendancesMap);
            
            // expected
            assertThatThrownBy(() -> attendanceBook.editAttendance(name, date, time))
                    .isExactlyInstanceOf(IllegalAttendTimeException.class)
                    .hasMessage("출석 가능한 시간이 아닙니다. (출석 가능 시간 : 08:00 ~ 23:00)");
        }
    }
    
    @Nested
    class 크루별_출석_기록_확인_테스트 {
        
        @Test
        void 크루별_출석기록을_확인할_수_있다() {
            // given
            String name = "Dompoo";
            AttendanceBook attendanceBook = new AttendanceBook(Map.of("Dompoo", new MemberAttendances("Dompoo", List.of(
                    new Attendance(LocalDateTime.of(2024, 12, 6, 10, 15)),
                    new Attendance(LocalDateTime.of(2024, 12, 10, 10, 30)),
                    new Attendance(LocalDateTime.of(2024, 12, 11, 10, 31)))
            )));
            
            // when
            MemberAttendResult result = attendanceBook.getAttendanceResult(name);
            
            // then
            assertThat(result).isEqualTo(new MemberAttendResult(
                    "Dompoo", List.of(
                    new AttendResult(LocalDateTime.of(2024, 12, 6, 10, 15), AttendanceStatus.지각, true),
                    new AttendResult(LocalDateTime.of(2024, 12, 10, 10, 30), AttendanceStatus.지각, true),
                    new AttendResult(LocalDateTime.of(2024, 12, 11, 10, 31), AttendanceStatus.결석, true)
            ), 0, 2, 1, ExpelRisk.정상
            ));
        }
        
        @Test
        void 크루별_출석기록을_확인할_수_있다_경고() {
            // given
            String name = "Dompoo";
            AttendanceBook attendanceBook = new AttendanceBook(Map.of("Dompoo", new MemberAttendances("Dompoo", List.of(
                    new Attendance(LocalDateTime.of(2024, 12, 6, 10, 15)),
                    new Attendance(LocalDateTime.of(2024, 12, 10, 10, 30)),
                    new Attendance(LocalDateTime.of(2024, 12, 11, 10, 31)),
                    new Attendance(LocalDateTime.of(2024, 12, 12, 10, 32)))
            )));
            
            // when
            MemberAttendResult result = attendanceBook.getAttendanceResult(name);
            
            // then
            assertThat(result).isEqualTo(new MemberAttendResult(
                    "Dompoo", List.of(
                    new AttendResult(LocalDateTime.of(2024, 12, 6, 10, 15), AttendanceStatus.지각, true),
                    new AttendResult(LocalDateTime.of(2024, 12, 10, 10, 30), AttendanceStatus.지각, true),
                    new AttendResult(LocalDateTime.of(2024, 12, 11, 10, 31), AttendanceStatus.결석, true),
                    new AttendResult(LocalDateTime.of(2024, 12, 12, 10, 32), AttendanceStatus.결석, true)
            ), 0, 2, 2, ExpelRisk.경고
            ));
        }
        
        @Test
        void 크루별_출석기록을_확인할_수_있다_면담() {
            // given
            String name = "Dompoo";
            AttendanceBook attendanceBook = new AttendanceBook(Map.of("Dompoo", new MemberAttendances("Dompoo", List.of(
                    new Attendance(LocalDateTime.of(2024, 12, 6, 10, 15)),
                    new Attendance(LocalDateTime.of(2024, 12, 10, 10, 30)),
                    new Attendance(LocalDateTime.of(2024, 12, 11, 10, 31)),
                    new Attendance(LocalDateTime.of(2024, 12, 12, 10, 32)),
                    new Attendance(LocalDateTime.of(2024, 12, 13, 10, 33)))
            )));
            
            // when
            MemberAttendResult result = attendanceBook.getAttendanceResult(name);
            
            // then
            assertThat(result).isEqualTo(new MemberAttendResult(
                    "Dompoo", List.of(
                    new AttendResult(LocalDateTime.of(2024, 12, 6, 10, 15), AttendanceStatus.지각, true),
                    new AttendResult(LocalDateTime.of(2024, 12, 10, 10, 30), AttendanceStatus.지각, true),
                    new AttendResult(LocalDateTime.of(2024, 12, 11, 10, 31), AttendanceStatus.결석, true),
                    new AttendResult(LocalDateTime.of(2024, 12, 12, 10, 32), AttendanceStatus.결석, true),
                    new AttendResult(LocalDateTime.of(2024, 12, 13, 10, 33), AttendanceStatus.결석, true)
            ), 0, 2, 3, ExpelRisk.면담
            ));
        }
        
        @Test
        void 크루별_출석기록을_확인할_수_있다_제적() {
            // given
            String name = "Dompoo";
            AttendanceBook attendanceBook = new AttendanceBook(Map.of("Dompoo", new MemberAttendances("Dompoo", List.of(
                    new Attendance(LocalDateTime.of(2024, 12, 6, 10, 15)),
                    new Attendance(LocalDateTime.of(2024, 12, 10, 10, 30)),
                    new Attendance(LocalDateTime.of(2024, 12, 11, 10, 31)),
                    new Attendance(LocalDateTime.of(2024, 12, 12, 10, 32)),
                    new Attendance(LocalDateTime.of(2024, 12, 13, 10, 33)),
                    new Attendance(LocalDateTime.of(2024, 12, 17, 10, 34)),
                    new Attendance(LocalDateTime.of(2024, 12, 18, 10, 35)),
                    new Attendance(LocalDateTime.of(2024, 12, 19, 10, 35)))
            )));
            
            // when
            MemberAttendResult result = attendanceBook.getAttendanceResult(name);
            
            // then
            assertThat(result).isEqualTo(new MemberAttendResult(
                    "Dompoo", List.of(
                    new AttendResult(LocalDateTime.of(2024, 12, 6, 10, 15), AttendanceStatus.지각, true),
                    new AttendResult(LocalDateTime.of(2024, 12, 10, 10, 30), AttendanceStatus.지각, true),
                    new AttendResult(LocalDateTime.of(2024, 12, 11, 10, 31), AttendanceStatus.결석, true),
                    new AttendResult(LocalDateTime.of(2024, 12, 12, 10, 32), AttendanceStatus.결석, true),
                    new AttendResult(LocalDateTime.of(2024, 12, 13, 10, 33), AttendanceStatus.결석, true),
                    new AttendResult(LocalDateTime.of(2024, 12, 17, 10, 34), AttendanceStatus.결석, true),
                    new AttendResult(LocalDateTime.of(2024, 12, 18, 10, 35), AttendanceStatus.결석, true),
                    new AttendResult(LocalDateTime.of(2024, 12, 19, 10, 35), AttendanceStatus.결석, true)
            ), 0, 2, 6, ExpelRisk.제적
            ));
        }
    }
    
    @Nested
    class 제적_위험자_확인_테스트 {
        
        @Test
        void 제적_위험자를_확인할_수_있다() {
            // given
            AttendanceBook attendanceBook = new AttendanceBook(Map.of(
                    "Dompoo", new MemberAttendances("Dompoo", List.of(
                            new Attendance(LocalDateTime.of(2024, 12, 6, 10, 15)),
                            new Attendance(LocalDateTime.of(2024, 12, 10, 10, 30)),
                            new Attendance(LocalDateTime.of(2024, 12, 11, 10, 31)))
                    ), "Dompoo_경고", new MemberAttendances("Dompoo_경고", List.of(
                            new Attendance(LocalDateTime.of(2024, 12, 6, 10, 15)),
                            new Attendance(LocalDateTime.of(2024, 12, 10, 10, 30)),
                            new Attendance(LocalDateTime.of(2024, 12, 11, 10, 31)),
                            new Attendance(LocalDateTime.of(2024, 12, 12, 10, 32)))
                    ), "Dompoo_면담", new MemberAttendances("Dompoo_면담", List.of(
                            new Attendance(LocalDateTime.of(2024, 12, 6, 10, 15)),
                            new Attendance(LocalDateTime.of(2024, 12, 10, 10, 30)),
                            new Attendance(LocalDateTime.of(2024, 12, 11, 10, 31)),
                            new Attendance(LocalDateTime.of(2024, 12, 12, 10, 32)),
                            new Attendance(LocalDateTime.of(2024, 12, 13, 10, 33)))
                    ), "Dompoo_제적", new MemberAttendances("Dompoo_제적", List.of(
                            new Attendance(LocalDateTime.of(2024, 12, 6, 10, 15)),
                            new Attendance(LocalDateTime.of(2024, 12, 10, 10, 30)),
                            new Attendance(LocalDateTime.of(2024, 12, 11, 10, 31)),
                            new Attendance(LocalDateTime.of(2024, 12, 12, 10, 32)),
                            new Attendance(LocalDateTime.of(2024, 12, 13, 10, 33)),
                            new Attendance(LocalDateTime.of(2024, 12, 17, 10, 34)),
                            new Attendance(LocalDateTime.of(2024, 12, 18, 10, 35)),
                            new Attendance(LocalDateTime.of(2024, 12, 19, 10, 35)))
                    )));
            
            // when
            List<ExpelMeasurementResult> result = attendanceBook.createExpelWarnings();
            
            // then
            assertThat(result).containsExactlyInAnyOrder(
                    new ExpelMeasurementResult("Dompoo", 2, 1, ExpelRisk.정상),
                    new ExpelMeasurementResult("Dompoo_경고", 2, 2, ExpelRisk.경고),
                    new ExpelMeasurementResult("Dompoo_면담", 2, 3, ExpelRisk.면담),
                    new ExpelMeasurementResult("Dompoo_제적", 2, 6, ExpelRisk.제적)
            );
        }
    }
}