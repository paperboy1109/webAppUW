package uwyo.cs.uwreg.dao.model;

public class Student {
	private String wnumber;
	private String first_name;
	private String last_name;
	private String gender;
	
	public Student(String wnumber, String first_name, String last_name, String gender) {
		super();
		this.wnumber = wnumber;
		this.first_name = first_name;
		this.last_name = last_name;
		this.gender = gender;
	}

	public String getWnumber() {
		return wnumber;
	}
	public String getFirst_name() {
		return first_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public String getGender() {
		return gender;
	}
	
}
