
public class Test {
	public static void main(String args[]) {
		function(new Parent());
		function(new Child());
	}
	
	public static void function(ParentInterface p) {
		System.out.println(p.toStringParent());
	}
	
	public static void function(ChildInterface c) {
		System.out.println(c.toStringChild());
		System.out.println(c.toStringParent());
	}
}

interface ParentInterface {
	public String toStringParent();
}

interface ChildInterface extends ParentInterface {
	public String toStringChild();
}

class Parent implements ParentInterface {
	@Override
	public String toStringParent() {
		return "parent";
	}
}

class Child implements ChildInterface {
	@Override
	public String toStringChild() {
		return "child";
	}

	@Override
	public String toStringParent() {
		return "child";
	}
}
