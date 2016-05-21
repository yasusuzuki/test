package ocjp_silver8;

public class ConstructorTest {
	class Parent{
		String name;
		Parent(){
			System.out.println("Parent()");
		}
		Parent(int i){
			this();
			System.out.println("Parent(int i)");
		}
		public void print(){
			System.out.println("Parent.print" + name);
		}
	}
	
	class Child extends Parent{
		String name;
		Child(){
			super(10);
			System.out.println("Child()");
		}
		Child(int i){
			this();
			System.out.println("Child(int i)");
		}
		public void print(){
			System.out.println("Child.print" + name);
		}
	}
	
	public static void main(String[] args) {
		ConstructorTest constructorTest = new ConstructorTest();
		
		Child child = constructorTest.new Child(100);
		child.name = "CHILD";
		Parent parent = child;
		System.out.println("Child Name=" + child.name + "Parent Name=" + parent.name);
		child.print();
		parent.print();
	}
}
