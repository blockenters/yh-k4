package model;

public class Person {
	
	public int num;
	public String name;
	public String dept;
	public String address;
	
	public Person(int num, String name, String dept, String address) {		
		this.num = num;
		this.name = name;
		this.dept = dept;
		this.address = address;
	}
	
	public Person() {
		
	}

	public void print() {
		// TODO Auto-generated method stub
		System.out.println(num);
		System.out.println(name);
		System.out.println(dept);
		System.out.println(address);
	}
	
	

}
