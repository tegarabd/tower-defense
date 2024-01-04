package input;

import java.util.Scanner;

public class Input {

	private Scanner scanner;

	public Input() {
		super();
		this.scanner = new Scanner(System.in);
	}

	public void clear() {
		for (int i = 0; i < 32; i++) {
			System.out.println();
		}
	}
	
	public interface Validation<T> {
		boolean validate(T input);
	}
	
	public interface StringValidation {
		boolean validate(String n);
	}
	
	public int validateNextInt(Validation<Integer> validation, String... prompt) {
		int n = -1;
		
		do {
			for (String str : prompt) System.out.print(str + " ");
			System.out.print(">> ");
			n = nextInt();
		} while (!validation.validate(n));
		
		return n;
	}
	
	public String validateNextLine(Validation<String> validation, String... prompt) {
		String s = "";
		
		do {
			for (String str : prompt) System.out.print(str + " ");
			System.out.print(">> ");
			s = scanner.nextLine();
		} while (!validation.validate(s));
		
		return s;
	}
	
	public int nextInt() {
		int n = -1;
		
		try {
			n = scanner.nextInt();
		} catch (Exception e) {}
		
		scanner.nextLine();
		return n;
	}
	
	public void enter() {
		System.out.println();
		System.out.println("Press Enter to Continue...");
		scanner.nextLine();
	}

}
