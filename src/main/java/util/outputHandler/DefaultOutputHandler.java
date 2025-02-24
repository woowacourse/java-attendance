package util.outputHandler;

public class DefaultOutputHandler implements OutputHandler {
    
    @Override
    public void handle(String value) {
        System.out.println(value);
    }
}
