package controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class CommandTest {
    @Nested
    class covertCommandTest{
        @DisplayName("1의 경우 ")
        @Test
        void command1Test(){
            String commandLine = "1";

            Command command = Command.convertToCommand(commandLine);

            assertThat(command).isEqualTo(Command.ATTEND);
        }

        @DisplayName("2의 경우 ")
        @Test
        void command2Test(){
            String commandLine = "2";

            Command command = Command.convertToCommand(commandLine);

            assertThat(command).isEqualTo(Command.EDIT);
        }

        @DisplayName("3의 경우 ")
        @Test
        void command3Test(){
            String commandLine = "3";

            Command command = Command.convertToCommand(commandLine);

            assertThat(command).isEqualTo(Command.FIND_CREW_RECORD);
        }

        @DisplayName("4의 경우 ")
        @Test
        void command4Test(){
            String commandLine = "4";

            Command command = Command.convertToCommand(commandLine);

            assertThat(command).isEqualTo(Command.FIND_WARNING_CREWS);
        }

        @DisplayName("q와 Q의 경우 ")
        @Test
        void commandQTest(){
            String exitCommandLowerCase = "q";
            String exitCommandUpperCase = "q";

            Command commandLowerCase = Command.convertToCommand(exitCommandLowerCase);
            Command commandUpperCase = Command.convertToCommand(exitCommandUpperCase);

            assertThat(commandLowerCase).isEqualTo(Command.EXIT);
            assertThat(commandUpperCase).isEqualTo(Command.EXIT);
        }

        @DisplayName("그 외의 경우 에러를 발생")
        @Test
        void commandError(){
            String errorCommand = "o";

            assertThatThrownBy(() -> Command.convertToCommand(errorCommand)).isInstanceOf(IllegalArgumentException.class);
        }
    }
}