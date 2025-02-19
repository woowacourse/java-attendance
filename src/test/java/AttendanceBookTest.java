import domain.Attendance;
import domain.AttendanceBook;
import domain.MemberAttendances;
import dto.AttendanceModifyResult;
import dto.AttendanceResultDTO;
import dto.AttendanceResultDTOs;
import dto.ExpelMeasurementDTO;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import util.exception.IllegalAttendDateException;
import util.exception.IllegalAttendTimeException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class AttendanceBookTest {
    
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
        void 출석이_기록되면_정보를_확인할수_있다() {
            // given
            String inputName = "Lemon";
            LocalDateTime attendDateTime = LocalDateTime.of(2024, 12, 13, 10, 2);
            AttendanceBook attendanceBook = new AttendanceBook(attendancesMap);
            
            // when
            AttendanceResultDTO attendanceResultDTO = attendanceBook.addAttendance(inputName, attendDateTime);
            
            // then
            assertThat(attendanceResultDTO.attendanceStatus()).isEqualTo("출석");
            assertThat(attendanceResultDTO.attendanceTime()).isEqualTo(LocalDateTime.of(2024, 12, 13, 10, 2));
        }
        
        @Test
        void 존재하지_않는_멤버를_입력시_예외() {
            // given
            String inputName = "WANNI";
            LocalDateTime attendDateTime = LocalDateTime.of(2024, 12, 13, 10, 2);
            AttendanceBook attendanceBook = new AttendanceBook(attendancesMap);
            
            // expected
            assertThatThrownBy(() -> attendanceBook.addAttendance(inputName, attendDateTime))
                    .isExactlyInstanceOf(IllegalArgumentException.class)
                    .hasMessage("해당 멤버는 존재하지 않습니다.");
        }
        
        @Test
        void 공휴일에_출석시_예외() {
            // given
            String inputName = "Lemon";
            LocalDateTime attendDateTime = LocalDateTime.of(2024, 12, 1, 10, 2);
            AttendanceBook attendanceBook = new AttendanceBook(attendancesMap);
            
            // expected
            assertThatThrownBy(() -> attendanceBook.addAttendance(inputName, attendDateTime))
                    .isExactlyInstanceOf(IllegalAttendDateException.class)
                    .hasMessage("출석 가능한 날짜가 아닙니다.");
        }
        
        @Test
        void 지정된_시간이_아닐_때_출석시_예외() {
            // given
            String inputName = "Lemon";
            LocalDateTime attendDateTime = LocalDateTime.of(2024, 12, 3, 7, 30);
            AttendanceBook attendanceBook = new AttendanceBook(attendancesMap);
            
            // expected
            assertThatThrownBy(() -> attendanceBook.addAttendance(inputName, attendDateTime))
                    .isExactlyInstanceOf(IllegalAttendTimeException.class)
                    .hasMessage("출석 가능한 시간이 아닙니다.");
        }
    }
    
    @Nested
    class 수정_테스트 {
        
        @Test
        void 출석_기록을_수정할_수_있다() {
            //given
            String name = "Dompoo";
            LocalDate date = LocalDate.of(2024, 12, 6);
            LocalTime time = LocalTime.of(10, 5);
            AttendanceBook attendanceBook = new AttendanceBook(attendancesMap);
            
            //when
            AttendanceModifyResult result = attendanceBook.editAttendance(name, date, time);
            
            //then
            assertThat(result).isEqualTo(new AttendanceModifyResult(
                    "Dompoo",
                    LocalDate.of(2024, 12, 6),
                    LocalTime.of(10, 15),
                    "지각",
                    LocalTime.of(10, 5),
                    "출석"
            ));
        }
        
        @Test
        void 존재하지_않는_회원의_기록을_수정하려고_하면_예외() {
            // given
            String name = "Moko";
            LocalDate date = LocalDate.of(2024, 12, 6);
            LocalTime time = LocalTime.of(10, 5);
            AttendanceBook attendanceBook = new AttendanceBook(attendancesMap);
            
            // expected
            assertThatThrownBy(() -> attendanceBook.editAttendance(name, date, time))
                    .isExactlyInstanceOf(IllegalArgumentException.class)
                    .hasMessage("해당 멤버는 존재하지 않습니다.");
        }
        
        @Test
        void 주말_기록을_수정하려하면_예외() {
            // given
            String name = "Dompoo";
            LocalDate date = LocalDate.of(2024, 12, 1);
            LocalTime time = LocalTime.of(10, 5);
            AttendanceBook attendanceBook = new AttendanceBook(attendancesMap);
            
            // expected
            assertThatThrownBy(() -> attendanceBook.editAttendance(name, date, time))
                    .isExactlyInstanceOf(IllegalAttendDateException.class)
                    .hasMessage("수정 가능한 날짜가 아닙니다.");
        }
        
        @Test
        void 수정시_출석_가능한_시간이_아니면_예외() {
            // given
            String name = "Dompoo";
            LocalDate date = LocalDate.of(2024, 12, 3);
            LocalTime time = LocalTime.of(7, 5);
            AttendanceBook attendanceBook = new AttendanceBook(attendancesMap);
            
            // expected
            assertThatThrownBy(() -> attendanceBook.editAttendance(name, date, time))
                    .isExactlyInstanceOf(IllegalAttendTimeException.class)
                    .hasMessage("수정 가능한 시간이 아닙니다.");
        }
    }
    
    @Nested
    class 크루별_출석_기록_확인 {
        
        @Test
        void 크루별_출석기록을_확인할_수_있다() {
            //given
            String name = "Dompoo";
            AttendanceBook attendanceBook = new AttendanceBook(Map.of("Dompoo", new MemberAttendances("Dompoo", List.of(
                    new Attendance(LocalDateTime.of(2024, 12, 6, 10, 15)),
                    new Attendance(LocalDateTime.of(2024, 12, 10, 10, 30)),
                    new Attendance(LocalDateTime.of(2024, 12, 11, 10, 31)))
            )));
            
            //when
            AttendanceResultDTOs result = attendanceBook.getAttendanceResult(name);
            
            //then
            assertThat(result).isEqualTo(new AttendanceResultDTOs(
                    "Dompoo", List.of(
                    new AttendanceResultDTO(LocalDateTime.of(2024, 12, 6, 10, 15), "지각"),
                    new AttendanceResultDTO(LocalDateTime.of(2024, 12, 10, 10, 30), "지각"),
                    new AttendanceResultDTO(LocalDateTime.of(2024, 12, 11, 10, 31), "결석")
            ), 0, 2, 1, null
            ));
        }
        
        @Test
        void 크루별_출석기록을_확인할_수_있다_경고() {
            //given
            String name = "Dompoo";
            AttendanceBook attendanceBook = new AttendanceBook(Map.of("Dompoo", new MemberAttendances("Dompoo", List.of(
                    new Attendance(LocalDateTime.of(2024, 12, 6, 10, 15)),
                    new Attendance(LocalDateTime.of(2024, 12, 10, 10, 30)),
                    new Attendance(LocalDateTime.of(2024, 12, 11, 10, 31)),
                    new Attendance(LocalDateTime.of(2024, 12, 12, 10, 32)))
            )));
            
            //when
            AttendanceResultDTOs result = attendanceBook.getAttendanceResult(name);
            
            //then
            assertThat(result).isEqualTo(new AttendanceResultDTOs(
                    "Dompoo", List.of(
                    new AttendanceResultDTO(LocalDateTime.of(2024, 12, 6, 10, 15), "지각"),
                    new AttendanceResultDTO(LocalDateTime.of(2024, 12, 10, 10, 30), "지각"),
                    new AttendanceResultDTO(LocalDateTime.of(2024, 12, 11, 10, 31), "결석"),
                    new AttendanceResultDTO(LocalDateTime.of(2024, 12, 12, 10, 32), "결석")
            ), 0, 2, 2, "경고"
            ));
        }
        
        @Test
        void 크루별_출석기록을_확인할_수_있다_면담() {
            //given
            String name = "Dompoo";
            AttendanceBook attendanceBook = new AttendanceBook(Map.of("Dompoo", new MemberAttendances("Dompoo", List.of(
                    new Attendance(LocalDateTime.of(2024, 12, 6, 10, 15)),
                    new Attendance(LocalDateTime.of(2024, 12, 10, 10, 30)),
                    new Attendance(LocalDateTime.of(2024, 12, 11, 10, 31)),
                    new Attendance(LocalDateTime.of(2024, 12, 12, 10, 32)),
                    new Attendance(LocalDateTime.of(2024, 12, 13, 10, 33)))
            )));
            
            //when
            AttendanceResultDTOs result = attendanceBook.getAttendanceResult(name);
            
            //then
            assertThat(result).isEqualTo(new AttendanceResultDTOs(
                    "Dompoo", List.of(
                    new AttendanceResultDTO(LocalDateTime.of(2024, 12, 6, 10, 15), "지각"),
                    new AttendanceResultDTO(LocalDateTime.of(2024, 12, 10, 10, 30), "지각"),
                    new AttendanceResultDTO(LocalDateTime.of(2024, 12, 11, 10, 31), "결석"),
                    new AttendanceResultDTO(LocalDateTime.of(2024, 12, 12, 10, 32), "결석"),
                    new AttendanceResultDTO(LocalDateTime.of(2024, 12, 13, 10, 33), "결석")
            ), 0, 2, 3, "면담"
            ));
        }
        
        @Test
        void 크루별_출석기록을_확인할_수_있다_제적() {
            //given
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
            
            //when
            AttendanceResultDTOs result = attendanceBook.getAttendanceResult(name);
            
            //then
            assertThat(result).isEqualTo(new AttendanceResultDTOs(
                    "Dompoo", List.of(
                    new AttendanceResultDTO(LocalDateTime.of(2024, 12, 6, 10, 15), "지각"),
                    new AttendanceResultDTO(LocalDateTime.of(2024, 12, 10, 10, 30), "지각"),
                    new AttendanceResultDTO(LocalDateTime.of(2024, 12, 11, 10, 31), "결석"),
                    new AttendanceResultDTO(LocalDateTime.of(2024, 12, 12, 10, 32), "결석"),
                    new AttendanceResultDTO(LocalDateTime.of(2024, 12, 13, 10, 33), "결석"),
                    new AttendanceResultDTO(LocalDateTime.of(2024, 12, 17, 10, 34), "결석"),
                    new AttendanceResultDTO(LocalDateTime.of(2024, 12, 18, 10, 35), "결석"),
                    new AttendanceResultDTO(LocalDateTime.of(2024, 12, 19, 10, 35), "결석")
            ), 0, 2, 6, "제적"
            ));
        }
    }
    
    @Nested
    class 제적_위험자_확인 {
        
        @Test
        void 제적_위험자를_확인할_수_있다() {
            //given
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
            
            //when
            List<ExpelMeasurementDTO> result = attendanceBook.checkExpelWarnings();
            
            //then
            assertThat(result).containsExactlyInAnyOrder(
                    new ExpelMeasurementDTO("Dompoo_경고", 2, 2, "경고"),
                    new ExpelMeasurementDTO("Dompoo_면담", 2, 3, "면담"),
                    new ExpelMeasurementDTO("Dompoo_제적", 2, 6, "제적")
            );
        }
        
        /*
         제적 위험자는 제적 대상자, 면담 대상자, 경고 대상자순으로 출력하며,
         대상 항목별 정렬 순서는 지각을 결석으로 간주하여 내림차순한다.
         출석 상태가 같으면 닉네임으로 오름차순 정렬한다.
         */
        @Test
        void 제적_위험자를_정렬하여_반환한다() {
            //given
            AttendanceBook attendanceBook = new AttendanceBook(Map.of(
                    "빙티", new MemberAttendances("빙티", List.of(
                            new Attendance(LocalDateTime.of(2024, 12, 6, 10, 15)),
                            new Attendance(LocalDateTime.of(2024, 12, 10, 10, 15)),
                            new Attendance(LocalDateTime.of(2024, 12, 11, 10, 15)),
                            new Attendance(LocalDateTime.of(2024, 12, 12, 10, 15)),
                            new Attendance(LocalDateTime.of(2024, 12, 13, 10, 31)),
                            new Attendance(LocalDateTime.of(2024, 12, 17, 10, 31)),
                            new Attendance(LocalDateTime.of(2024, 12, 18, 10, 31)))
                    ), "이든", new MemberAttendances("이든", List.of(
                            new Attendance(LocalDateTime.of(2024, 12, 6, 10, 15)),
                            new Attendance(LocalDateTime.of(2024, 12, 10, 10, 15)),
                            new Attendance(LocalDateTime.of(2024, 12, 11, 10, 15)),
                            new Attendance(LocalDateTime.of(2024, 12, 12, 10, 15)),
                            new Attendance(LocalDateTime.of(2024, 12, 13, 10, 15)),
                            new Attendance(LocalDateTime.of(2024, 12, 17, 10, 32)),
                            new Attendance(LocalDateTime.of(2024, 12, 18, 10, 32)))
                    ), "빙봉", new MemberAttendances("빙봉", List.of(
                            new Attendance(LocalDateTime.of(2024, 12, 6, 10, 15)),
                            new Attendance(LocalDateTime.of(2024, 12, 10, 10, 15)),
                            new Attendance(LocalDateTime.of(2024, 12, 11, 10, 15)),
                            new Attendance(LocalDateTime.of(2024, 12, 12, 10, 15)),
                            new Attendance(LocalDateTime.of(2024, 12, 13, 10, 15)),
                            new Attendance(LocalDateTime.of(2024, 12, 17, 10, 15)),
                            new Attendance(LocalDateTime.of(2024, 12, 18, 10, 33)))
                    ), "쿠키", new MemberAttendances("쿠키", List.of(
                            new Attendance(LocalDateTime.of(2024, 12, 6, 10, 15)),
                            new Attendance(LocalDateTime.of(2024, 12, 10, 10, 15)),
                            new Attendance(LocalDateTime.of(2024, 12, 11, 10, 15)),
                            new Attendance(LocalDateTime.of(2024, 12, 18, 10, 35)),
                            new Attendance(LocalDateTime.of(2024, 12, 19, 10, 35)))
                    ), "장수", new MemberAttendances("장수", List.of(
                            new Attendance(LocalDateTime.of(2024, 12, 11, 10, 15)),
                            new Attendance(LocalDateTime.of(2024, 12, 12, 10, 15)),
                            new Attendance(LocalDateTime.of(2024, 12, 13, 10, 15)),
                            new Attendance(LocalDateTime.of(2024, 12, 17, 10, 15)),
                            new Attendance(LocalDateTime.of(2024, 12, 18, 10, 15)),
                            new Attendance(LocalDateTime.of(2024, 12, 19, 10, 15)))
                    ), "양수", new MemberAttendances("양수", List.of(
                            new Attendance(LocalDateTime.of(2024, 12, 11, 10, 15)),
                            new Attendance(LocalDateTime.of(2024, 12, 12, 10, 15)),
                            new Attendance(LocalDateTime.of(2024, 12, 13, 10, 15)),
                            new Attendance(LocalDateTime.of(2024, 12, 17, 10, 15)),
                            new Attendance(LocalDateTime.of(2024, 12, 18, 10, 15)),
                            new Attendance(LocalDateTime.of(2024, 12, 19, 10, 15)))
                    )
            ));
            
            //when
            List<ExpelMeasurementDTO> result = attendanceBook.checkExpelWarnings();
            
            //then
            assertThat(result).containsExactly(
                    new ExpelMeasurementDTO("빙티", 4, 3, "면담"),
                    new ExpelMeasurementDTO("이든", 5, 2, "면담"),
                    new ExpelMeasurementDTO("빙봉", 6, 1, "면담"),
                    new ExpelMeasurementDTO("쿠키", 3, 2, "면담"),
                    new ExpelMeasurementDTO("양수", 6, 0, "경고"),
                    new ExpelMeasurementDTO("장수", 6, 0, "경고")
            
            );
        }
    }
}