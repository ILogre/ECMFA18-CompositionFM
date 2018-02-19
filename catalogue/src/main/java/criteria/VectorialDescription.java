package criteria;

import java.util.*;
import java.util.stream.Collectors;



public class VectorialDescription implements CriteriaDescriptor {

    private String key;
    private Set<String> candidates;

    public VectorialDescription(String key, String... candidates) {
        this.key = key;
        this.candidates = Arrays.stream(candidates).collect(Collectors.toSet());
    }

    @Override public String getKey() { return key; }

    @Override public Optional<String> getValue(Map<String, String> data) {
        Set<String> kept = candidates.stream()
                .filter(v -> data.get(v).equals("Yes"))
                .collect(Collectors.toSet());
        if(kept.isEmpty())
            return Optional.empty();
        StringJoiner joiner = new StringJoiner(" ");
        kept.forEach(joiner::add);
        return Optional.of(joiner.toString());
    }
}
