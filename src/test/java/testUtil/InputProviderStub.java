package testUtil;

import util.inputProvider.InputProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InputProviderStub implements InputProvider {
    
    private final List<String> stub = new ArrayList<>();
    
    private int index = 0;
    
    public InputProviderStub(String... values) {
        stub.addAll(Arrays.asList(values));
    }
    
    @Override
    public String get() {
        return stub.get(index++);
    }
}
