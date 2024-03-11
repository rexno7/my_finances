package mywebsite.javapractice;

import org.springframework.stereotype.Service;

@Service
public class Practice {

	public static void playground() {
		for (int i=0; i<1; i++) {
			System.out.println(i);
		}
		int x = (int) (Math.random()*3);
		switch(x) {
			case 0:
				System.out.println("case 0");
				break;
			case 1:
				System.out.println("case 1");
				break;
			case 2:
				System.out.println("case 2");
				break;
			default:
				System.out.println("default");
		}
	}
}
