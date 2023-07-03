package model;

public class Professor extends Person {
	
//	int num;
//	String name;
//	String dept;
//	String address;
	
	String[] subjects;

	public Professor(int num, String name, String dept, String address, String[] subjects) {
		super(num, name, dept, address);
		this.subjects = subjects;
	}

	@Override
	public void print() {
		// TODO Auto-generated method stub
		super.print();
		
		for(int i = 0; i < subjects.length; i++) {
			System.out.println(subjects[i]);
		}
		
	}
	
	
	

}



