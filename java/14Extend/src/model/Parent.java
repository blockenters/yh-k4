package model;

public class Parent {
	
	public String name;
	public int age;
	// 머니는 프라이빗으로 설정. => 상속은 받는다!
	// 프라이빗은 노출을 안하겠다는 뜻!
	private int money;
	
	public Parent(){
		System.out.println("부모 생성자 호출됨!");
	}
	
	public void print(){
		System.out.println(name);
		System.out.println(age);
		System.out.println(getMoney());
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		
		if (money < 0) {
			this.money = 0;
		}else {
			this.money = money;	
		}
	}

}
