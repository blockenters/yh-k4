package main;

import model.Child;
import model.Parent;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Parent p = new Parent();
		
		p.name = "홍길동";
		p.age = 50;
		p.setMoney(1000000000);
		
		p.print();
		
		System.out.println();
		System.out.println();
		
		Child c = new Child();
		c.name = "김나나";
		c.age = 23;
		c.setMoney(10000000);				
		c.hobby = "게임";
		
		// c 의 데이터를 출력.
		c.printChild();		

	}

}
