package testUtil;

import util.outputHandler.OutputHandler;

public class OutputHandlerStub implements OutputHandler {
    
    private String result = "";
    
    @Override
    public void handle(String value) {
        result += value;
    }
    
    public String getResult() {
        return result;
    }
}
