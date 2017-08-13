package builderpattern;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class Main {
	public static void main(String[] args) {
		ArrayList<Consumer<String>> builders = Person.getBuilders();
		Iterator<Consumer<String>> itr = builders.iterator();
		while(itr.hasNext()){
			Consumer<String> cons = itr.next();
			cons.accept("aa");
		}
		Person a = new Person();
		Consumer<String> p = a::setFirstName;
		Supplier<String> d = a::getFirstName;
		Consumer<String> b = e -> System.out.println(e);
		
		b.accept("aacc");
	}
	
	
}
