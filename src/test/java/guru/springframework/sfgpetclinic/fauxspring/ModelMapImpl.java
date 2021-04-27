package guru.springframework.sfgpetclinic.fauxspring;

import java.util.HashMap;
import java.util.Map;

public class ModelMapImpl implements Model {
    Map<String, Object> modelMap = new HashMap<>();

    @Override
    public void addAttribute(String key, Object o) {
        modelMap.put(key, o);
    }

    @Override
    public void addAttribute(Object o) {
        // modelMap.put(o, o);
    }

    public Map<String, Object> getModelMap() {
        return modelMap;
    }
}
