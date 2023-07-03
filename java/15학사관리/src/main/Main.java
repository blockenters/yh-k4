package main;

import model.Person;
import model.Professor;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Person p = new Person(1, "홍길동","경영학과","인천시 서구");
		
		p.print();
		
		Professor pf1 = new Professor(1, "김나나", "경제학과", "인천시 서구", new String[] {"경제원론","미시경제학"} );         
		pf1.print();

	}

}
