package com.user.userservice.main;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Test {

	public static void main(String[] args) {
	
		
		    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		    String password = "password123";
		    String encoded = encoder.encode(password); //$2a$10$qdo6atSutJ88iGfVNjcnDOhfdUViAX20/l3jMblG/BMpsZ6W1sp3S /db - $2a$10$wMYykwXxvSt5xKipTQNLG.gqaDZ5uRpxqP2EZ5pPHatkTr5b2BsV.
		    if(encoder.matches(password, encoded)) {
		    	System.out.println("matches");
		    }
		}


}
