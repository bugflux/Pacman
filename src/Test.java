
public class Test {
	public static void main(String args[]) {
		Parent po = new Parent();
		Child co = new Child();
		System.out.print(po.toString(po) + " ");
		System.out.print(co.toString(co) + " ");
		
		ParentInterface pi = po;
		ChildInterface ci = co;
		System.out.print(pi.toString(pi) + " ");
		System.out.print(ci.toString(ci));
	}
}

interface ParentInterface {
	public String toString(ParentInterface p);
}

interface ChildInterface extends ParentInterface {
	public String toString(ChildInterface c);
}

class Parent implements ParentInterface {
	public String toString(ParentInterface p) {
		return "parent";
	}
}

class Child extends Parent implements ChildInterface {
	@Override
	public String toString(ChildInterface p) {
		return "child";
	}
}
