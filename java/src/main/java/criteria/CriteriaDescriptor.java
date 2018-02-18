package criteria;

import java.util.Map;
import java.util.Optional;

public interface CriteriaDescriptor {

    String getKey();

    Optional<String> getValue(Map<String, String> data);

}
