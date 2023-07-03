package model;

public class Child extends Parent {
	
//	public String name;
//	public int age;
//	private int money;
	
//	public void print(){
//		System.out.println(name);
//		System.out.println(age);
//		System.out.println(money);
//	}
	
//	public int getMoney() {
//		return money;
//	}
//
//	public void setMoney(int money) {
//		
//		if (money < 0) {
//			this.money = 0;
//		}else {
//			this.money = money;	
//		}
//	}
	
	public String hobby;	
	
	public Child() {
		System.out.println("자식 생성자 호출됨");
	}
	
	public void printChild(){
		System.out.println(name);
		System.out.println(age);
		System.out.println( getMoney() );
		System.out.println(hobby);
	}

}
