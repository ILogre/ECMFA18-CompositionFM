package criteria;

import java.util.Map;
import java.util.Optional;

public class ScalarDescription implements CriteriaDescriptor {

    private String key;

    public ScalarDescription(String key) {
        this.key = key;
    }

    @Override public Optional<String> getValue(Map<String, String> data) {
        if(data.containsKey(key))
            return Optional.of(data.get(key));
        return Optional.empty();
    }

    @Override public String getKey() { return key; }

}
