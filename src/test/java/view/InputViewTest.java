package view;

import org.junit.jupiter.api.Test;
import testUtil.DateProviderStub;
import testUtil.InputProviderStub;
import testUtil.OutputHandlerStub;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class InputViewTest {
    
    @Test
    void 입력_선택_출력_테스트() {
        var outputHandler = new OutputHandlerStub();
        var dateProvider = new DateProviderStub(LocalDate.of(2025, 2, 20));
        var inputView = new InputView(
                new InputProviderStub("1"),
                outputHandler,
                dateProvider
        );
        
        var result = inputView.inputDecision();
        
        assertThat(result).isEqualTo("1");
        
        assertThat(outputHandler.getResult()).isEqualTo("""
                오늘은 2월 20일 목요일입니다. 기능을 선택해 주세요.
                1. 출석 확인
                2. 출석 수정
                3. 크루별 출석 기록 확인
                4. 제적 위험자 확인
                Q. 종료
                """);
    }
}