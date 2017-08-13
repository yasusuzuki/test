package builderpattern;

import java.util.ArrayList;
import java.util.function.Consumer;

public class Person {
	private String firstName;
	private String lastName;
	private ArrayList<Qualification> qualifications;
	
	public static ArrayList<Consumer<String>> getBuilders(){
		ArrayList<Consumer< String >> builders = new ArrayList<>();
		Person p = new Person();
		builders.add( p::setFirstName );
		return builders;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public ArrayList<Qualification> getQualifications() {
		return qualifications;
	}
	public void setQualifications(ArrayList<Qualification> qualifications) {
		this.qualifications = qualifications;
	}
	
	
}
