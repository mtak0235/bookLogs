package collection.set;

public class StringHashMain {
	static final int CAPACITY = 10;

	public static void main(String[] args) {
		char a = 'A';
		char b = 'B';
		System.out.println("a = " + (int)a);
		System.out.println("b = " + (int)b);
		System.out.println("hashCode(\"A\") = " + hashCode("A"));
		System.out.println("hashCode(\"B\") = " + hashCode("B"));
		System.out.println("hashCode(\"AB\") = " + hashCode("AB"));

		System.out.println("hashIndex(hashCode(\"A\")) = " + hashIndex(hashCode("A")));
		System.out.println("hashIndex(hashCode(\"B\")) = " + hashIndex(hashCode("B")));
		System.out.println("hashIndex(hashCode(\"AB\")) = " + hashIndex(hashCode("AB")));

	}

	static int hashCode(String string) {
		char[] charArray = string.toCharArray();
		int sum = 0;
		for (char c : charArray) {
			sum += c;
		}
		return sum;
	}

	static int hashIndex(int value) {
		return value % CAPACITY;
	}
}
