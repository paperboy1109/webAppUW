package uwyo.cs.uwreg.dao.model;

public class Course {
	private String crn;
	private String usp;	
	private String subject;
	private String cnumber;
	private String section;
	private String title;
	private int    credits;
	private String days;
	private String start;
	private String stop;
	private String building;
	private String room;
	private String instructor;
	private String[] notes;
	
	public Course(String crn, String usp, String subject, String cnumber, String section, String title, int credits,
			String days, String start, String stop, String building, String room, String instructor,
			String[] notes) {
		super();
		this.crn = crn;
		this.usp = usp;
		this.subject = subject;
		this.cnumber = cnumber;
		this.section = section;
		this.title = title;
		this.credits = credits;
		this.days = days;
		this.start = start;
		this.stop = stop;
		this.building = building;
		this.room = room;
		this.instructor = instructor;
		this.notes = notes;
	}
	
	public String getCrn() {
		return crn;
	}
	public String getUsp() {
		return usp;
	}
	public String getSubject() {
		return subject;
	}
	public String getCnumber() {
		return cnumber;
	}
	public String getSection() {
		return section;
	}
	public String getTitle() {
		return title;
	}
	public int getCredits() {
		return credits;
	}
	public String getDays() {
		return days;
	}
	public String getStart() {
		return start;
	}
	public String getStop() {
		return stop;
	}
	public String getBuilding() {
		return building;
	}
	public String getRoom() {
		return room;
	}
	public String getInstructor() {
		return instructor;
	}
	public String[] getNotes() {
		return notes;
	}
}
