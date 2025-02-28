package controller;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import view.InputView;
import view.OutputView;

@Nested
public class AttendanceControllerTest {

    private final InputView inputView = new InputView();
    private final OutputView outputView = new OutputView();
    private final AttendanceController controller = new AttendanceController(inputView, outputView);

    @Nested
    @DisplayName("기능 선택 테스트")
    class selectFeatureTest {

        @Test
        @DisplayName("1번 기능을 선택할 수 있다.")
        void selectFunction1() {
            String input = "1";
            Runnable action = controller.selectFeature(input);

            assertThat(action).isInstanceOf(Runnable.class);
        }

        @Test
        @DisplayName("2번 기능을 선택할 수 있다.")
        void selectFunction2() {
            String input = "2";
            Runnable action = controller.selectFeature(input);

            assertThat(action).isInstanceOf(Runnable.class);
        }

        @Test
        @DisplayName("3번 기능을 선택할 수 있다.")
        void selectFunction3() {
            String input = "3";
            Runnable action = controller.selectFeature(input);

            assertThat(action).isInstanceOf(Runnable.class);
        }

        @Test
        @DisplayName("4번 기능을 선택할 수 있다.")
        void selectFunction4() {
            String input = "4";
            Runnable action = controller.selectFeature(input);

            assertThat(action).isInstanceOf(Runnable.class);
        }

        @Test
        @DisplayName("제공되지 않는 기능을 선택할 수 없다.")
        void selectNotFunction() {
            String input = "9";
            assertThatThrownBy(() -> controller.selectFeature(input));
        }
    }
}