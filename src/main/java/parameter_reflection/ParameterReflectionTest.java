package parameter_reflection;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
/**
 * 
 * http://mike-neck.hatenadiary.com/entry/2015/08/21/034542
 *
 */
public class ParameterReflectionTest {
	private static final Map<String, String> standard_map = 
			new HashMap<String, String>(){
		{
			put("Hello","World");
			put("bill","not paid");
		}
	};
	
    private static final Map<String, String> map = map(
            hello -> "world",
            john -> john,
            bill -> "not paid",
            aa -> aa
    );
    public static Optional<String> get(String key) {
        return Optional.ofNullable(map.get(key));
    }

    @SafeVarargs
    public static <T> Map<String, T> map(NamedValue<T>... kvPair) {
        Map<String, T> m = new HashMap<>();
        Arrays.stream(kvPair).forEach(kv -> m.put(kv.name(), kv.value()));
        return Collections.unmodifiableMap(m);
    }
    
    public void test(){
    	standard_map.entrySet().stream().forEach(kv -> System.out.println(kv.getValue()));
    	
    	map.entrySet().stream().forEach(kv -> System.out.println(kv.getValue()));
    }
    
    public static void main(String[] args) {
		new ParameterReflectionTest().test();
		
	}
}
