package uwyo.cs.uwreg.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import uwyo.cs.uwreg.dao.model.Course;
import uwyo.cs.uwreg.dao.model.Student;

public class UWregDAOImpl implements UWregDAO {
	
	private JdbcTemplate jdbcTemplate = null;
    
    public UWregDAOImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
        
	@Override
	public Student getStudentByWnumber(String wnumber) {
    	// TODO 3 (10 pts) - Get the record of student with given wnumber
	    
		
		// careful ..
		//Student student = null;
		
		// Using Spring
		// setString(1, "w87501680"), myStmt
		
		// This one works but doesn't take the w-number into account
		//List<Student> listOfStudents = jdbcTemplate.query("select * from coscuw.students LIMIT 10", new RowMapper<Student>() {
			
		List<Student> listOfStudents = jdbcTemplate.query("select * from coscuw.students WHERE wNumber = ?", new Object[] { wnumber }, new RowMapper<Student>() {
		
/*		List<Student> listOfStudents = jdbcTemplate.query("select * from coscuw.students WHERE wNumber = ?", 
				new PreparedStatementSetter() {
            	public void setValues(PreparedStatement preparedStatement) throws
            		SQLException {
            			preparedStatement.setString(1, wnumber);
            		}
			}, new RowMapper<Student>() {*/


			public Student mapRow(ResultSet myRs, int rowNum) throws SQLException {
				
				// Example code
				//Offer offer = new Offer();
				//offer.setId(myRs.getInt("id"));
				//offer.setName(myRs.getString("name"));
				//offer.setText(myRs.getString("text"));
				//offer.setEmail(myRs.getString("email"));
				//return offer;
				
				// Student
				String studentWNum = myRs.getString("wNumber");
				String studentLastName = myRs.getString("lastname");
				String studentFirstName = myRs.getString("firstname");
				String studentGender = myRs.getString("gender");
				
				//For debugging purposes:
				System.out.printf("%s, %s, %s, %s\n", studentWNum, studentLastName, studentFirstName, studentGender);
				
				Student student = new Student(studentWNum, studentFirstName, studentLastName, studentGender);
				return student;
				
			}
			
		});
	    
		
		
	    // Using JDBC only (no Spring)
	    /*Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		
		try {
			// 1. Get a connection to database
			//myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/coscuw", "student" , "student");
			myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/coscuw", "root" , "90dziaDANmySQL~+!");
			
			// 2. Prepare statement
			//myStmt = myConn.prepareStatement("select * from enrolledin where wNumber = ? and CRN > ? ");
			myStmt = myConn.prepareStatement("select * from coscuw.students WHERE wNumber = ? ");
			
			// 3. Set the parameters
			//myStmt.setString(1, "w87501680");
			//myStmt.setDouble(2, 10479);
			myStmt.setString(1, wnumber);
			
			// 4. Execute SQL query
			myRs = myStmt.executeQuery();
			
			// 5. Display the result set
			//display(myRs);
			while (myRs.next()) {
				
				//double courseCRN = myRs.getDouble("CRN");
				String studentWNum = myRs.getString("wNumber");
				String studentLastName = myRs.getString("lastname");
				String studentFirstName = myRs.getString("firstname");
				String studentGender = myRs.getString("gender");
				
				System.out.printf("%s, %s, %s, %s\n", studentWNum, studentLastName, studentFirstName, studentGender);
				
				//student = new Student(studentWNum, studentFirstName, studentLastName, studentGender);
			}
			
			
		
			

		}
		catch (Exception exc) {
			exc.printStackTrace();
		}
		finally {
			
			if (myRs != null) {
				//myRs.close();
			}
			
			if (myStmt != null) {
				//myStmt.close();
			}
			
			if (myConn != null) {
				//myConn.close();
			}
		}*/
	    
	    
		
	    // careful ...
		//return student;
		return listOfStudents.get(0);
    }

	@Override
	public List<Course> getCoursesRegistered(String wnumber) {
    	// TODO 4 (10 pts) - Get the courses the given student is registered for
	    List<Course> courses = null;
	    
	    System.out.printf("Working on TODO 4\n");
	    List<Course> listOfCourses = jdbcTemplate.query("SELECT enrolledin.CRN, coursedetails.usp, offeringof.subject, " +
	    												"offeringof.number, scheduledcourses.section, offeringof.title, " +
	    												"courses.credits, coursemeetings.days, coursemeetings.start, coursemeetings.stop, " +
	    												"coursemeetings.bldg, coursemeetings.room " +
	    												"FROM coscuw.enrolledin " +
	    												"JOIN coscuw.offeringof ON enrolledin.CRN = offeringof.CRN " +
	    												"JOIN coscuw.coursemeetings ON enrolledin.CRN = coursemeetings.crn " +
	    												"JOIN coscuw.coursedetails ON enrolledin.CRN = coursedetails.CRN " +
	    												"JOIN coscuw.scheduledcourses ON enrolledin.CRN = scheduledcourses.CRN " +
	    												"JOIN coscuw.courses ON offeringof.subject=courses.subject " +
	    													"AND offeringof.number=courses.number " +
	    													"AND offeringof.title = courses.title " +
	    												"WHERE wNumber = ? "
	    , 
	    new Object[] { wnumber }, new RowMapper<Course>() {
	    	
			public Course mapRow(ResultSet myRs, int rowNum) throws SQLException {
				
				System.out.printf("Working on mapRow, TODO 4 \n");				
								
				// Course
				String courseCRN = myRs.getString("CRN");
				System.out.printf("crn: %s \n", courseCRN);
				String courseUSP = myRs.getString("usp");
				System.out.printf("usp: %s \n", courseUSP);
				String courseSubject = myRs.getString("subject");
				System.out.printf("subject: %s \n", courseSubject);
				String courseNumber = myRs.getString("number");
				System.out.printf("number: %s \n", courseNumber);
				String courseSection = myRs.getString("section");
				System.out.printf("section: %s \n", courseSection);
				String courseTitle = myRs.getString("title");
				
				// String courseCredits= myRs.getString(""); 
				//int courseCredits = 1;
				int courseCredits = myRs.getInt("credits");
				System.out.printf("credits: %d \n", courseCredits);
				
				String courseDay = myRs.getString("days");
				System.out.printf("days: %s \n", courseDay);
				String courseStart = myRs.getString("start");
				System.out.printf("start: %s \n", courseStart);
				String courseStop = myRs.getString("stop");
				System.out.printf("stop: %s \n", courseStop);
				String courseBuilding = myRs.getString("bldg");
				System.out.printf("bldg: %s \n", courseBuilding);
				String courseRoom = myRs.getString("room");
				System.out.printf("room: %s \n", courseRoom);
								
				// String courseInstructor= myRs.getString("");
				String courseInstructor = "Gamboa";
				String[] courseNotes = {"Note1", "Note2"};
		
				
				//For debugging purposes:
				//System.out.printf("%s, %s, %s, %s\n", studentWNum, studentLastName, studentFirstName, studentGender);
				
				// Student student = new Student(studentWNum, studentFirstName, studentLastName, studentGender);
				Course course = new Course(courseCRN, courseUSP, courseSubject, courseNumber, courseSection, courseTitle, courseCredits, courseDay, courseStart, courseStop, courseBuilding, courseRoom, courseInstructor, courseNotes);
				//Course course = new Course(courseCRN, courseUSP, courseSubject, courseNumber, courseSection, courseTitle, courseCredits, courseDay, courseStart, courseStop, courseBuilding, courseRoom, courseInstructor, courseNotes);

				
				//return student;
				return course;
				
			}
	    
	    });

	    
	 // Combine notes from the same CRN into a single Course object ---------------------------------------------
	 // ---------------------------------------------------------------------------------------------------------
	    
	    //danielsFunction(listOfCourses);
	    
	    /*
        System.out.println(" === COMBINING COURSES === \n");
        String previousCRN = "x123";
        String previousDay = "";
      //-------------------------------------
        String storedSubject = "";
        String storedUsp = "";
        String storedCNumber = "";
        String storedSection = "";
        String storedTitle = "";
        int storedCredits = 0;
        
        String storedDays = "";
        
        String storedStart = "";
        String storedStop = ""; 
        String storedBuilding = "";
        String storedRoom = "";
        
        String storedInstructor = "";
      //-------------------------------------
        
        ArrayList<String> tempNotesArray = new ArrayList<String>();
        List<Course> finalList = new ArrayList<Course>();
        
        int loopCounter = 0;
        
        // Loop through the courses that were returned from the ResultSet
        for (Course element : listOfCourses) {

            String currentCRN = null;
            String currentDays = null;
            String[] currentNotes = null;
            
            loopCounter++ ;
            System.out.println("\n\nWhat's the count?");
            System.out.println(loopCounter);
            
            // Get the current CRN info
            currentCRN = element.getCrn();
            System.out.println("The CRN is: ");
            System.out.println(currentCRN);
            System.out.println("The previousCRN is ");
            System.out.println(previousCRN);
            System.out.println("Do the CRNs match?");
            System.out.println(currentCRN.equals(previousCRN));
            // Get the current days info
            currentDays = element.getDays();
            System.out.println("Here are the days");
            System.out.println(currentDays);
            System.out.println("previousDays is ");
            System.out.println(previousDay);
            // Get the current info
            currentNotes = element.getNotes();
            System.out.println("The number of notes is:");
            System.out.println(currentNotes.length);
            System.out.println("The notes are:");
            System.out.println(currentNotes[0]);
            System.out.println(currentNotes[1]);
            
            // (1 of 7) Ignore (consecutive) duplicates
            if( (currentCRN.equals(previousCRN)) && (currentDays.equals(previousDay)) ) {
            	System.out.println("Duplicate course found");
            	
            // (2 of 7) A repeated CRN, with more CRNs to come
            } else if( (currentCRN.equals(previousCRN)) && (loopCounter != listOfCourses.size())) {
                System.out.println("SAME course found");

                //Since the CRN has not changed, combine the notes
                for(int i=0; i < currentNotes.length; i++){
                    tempNotesArray.add(currentNotes[i]);
                }
                
                //Since the CRN has not changed, combine the days
                storedDays = storedDays + currentDays;
                System.out.println("Here are the storedDays UPDATED");
                System.out.println(storedDays);
                
                //Keep track of the single, most recent day for comparison
                previousDay = currentDays;
                System.out.println("Here is previousDay UPDATED");
                System.out.println(previousDay);
                

            // (3 of 7) A repeated CRN, with no more CRNs to check
            } else if ((currentCRN.equals(previousCRN)) && (loopCounter == listOfCourses.size())){ 
            	
            	//Since the CRN has not changed, combine the notes
                for(int i=0; i < currentNotes.length; i++){
                    tempNotesArray.add(currentNotes[i]);
                }
                
                //Since the CRN has not changed, combine the days
                storedDays = storedDays + currentDays;
                System.out.println("Here are the storedDays UPDATED");
                System.out.println(storedDays);                                            
                
                // Create a Course object 
                //String completeDays = "";
                //completeDays = storedDays;
                
                String[] completeNotes = new String[tempNotesArray.size()];
                completeNotes = tempNotesArray.toArray(completeNotes);
                
                Course completeCourse = new Course(previousCRN, storedUsp, storedSubject, storedCNumber, storedSection, storedTitle, storedCredits, storedDays, storedStart, storedStop, storedBuilding, storedRoom, storedInstructor, completeNotes);
                finalList.add(completeCourse);
                
            // (4 of 7) The last CRN is being read in and it's not also the first (and not a repeat)
            } else if(loopCounter == listOfCourses.size() && ( !previousCRN.equals("x123") )) {

                System.out.println("This is the last shot \n");

                //Create an object for the previous course
                //String completeDays = "";
                //completeDays = previousDay;
                
                String[] completeNotes = new String[tempNotesArray.size()];
                completeNotes = tempNotesArray.toArray(completeNotes);
                
                Course completeCourse = new Course(previousCRN, storedUsp, storedSubject, storedCNumber, storedSection, storedTitle, storedCredits, storedDays, storedStart, storedStop, storedBuilding, storedRoom, storedInstructor, completeNotes);
                finalList.add(completeCourse);
                
                
                // Create an object for the new course
                //-------------------------------------
                storedSubject = element.getSubject();
                storedUsp = element.getUsp();
                storedCNumber = element.getCnumber();
                storedSection = element.getSection();
                storedTitle = element.getTitle();
                storedCredits = element.getCredits();
                
                storedStart = element.getStart();
                storedStop = element.getStop(); 
                storedBuilding = element.getBuilding();
                storedRoom = element.getRoom();
                
                storedInstructor = element.getInstructor();
                //-------------------------------------
                Course lastCourse = new Course(currentCRN, storedUsp, storedSubject, storedCNumber, storedSection, storedTitle, storedCredits, currentDays, storedStart, storedStop, storedBuilding, storedRoom, storedInstructor, currentNotes);
                finalList.add(lastCourse);

            // (5 of 7) There first CRN is being read and there are more to come
            } else if( (previousCRN.equals("x123")) && (loopCounter != listOfCourses.size()) ) {

                System.out.println("This is the first course \n");
                previousCRN = currentCRN;
                System.out.println("previousCRN has been updated to: ");
                System.out.println(previousCRN);
                
                //Save the notes and days
                for(int i=0; i < currentNotes.length; i++){
                    tempNotesArray.add(currentNotes[i]);
                }
                storedDays = currentDays;
                      
                // Save other course info
                //-------------------------------------
                storedSubject = element.getSubject();
                storedUsp = element.getUsp();
                storedCNumber = element.getCnumber();
                storedSection = element.getSection();
                storedTitle = element.getTitle();
                storedCredits = element.getCredits();
                
                storedStart = element.getStart();
                storedStop = element.getStop(); 
                storedBuilding = element.getBuilding();
                storedRoom = element.getRoom();
                
                storedInstructor = element.getInstructor();
                //-------------------------------------
                
                //Update the previous day
                previousDay = currentDays;
                

            // (6 of 7) There is only one CRN
            } else if ( (previousCRN.equals("x123")) && (loopCounter == listOfCourses.size()) ) { 
                
                // Create an object for for this one course
            	//-------------------------------------
                storedSubject = element.getSubject();
                storedUsp = element.getUsp();
                storedCNumber = element.getCnumber();
                storedSection = element.getSection();
                storedTitle = element.getTitle();
                storedCredits = element.getCredits();
                
                storedStart = element.getStart();
                storedStop = element.getStop(); 
                storedBuilding = element.getBuilding();
                storedRoom = element.getRoom();
                
                storedInstructor = element.getInstructor();
                //-------------------------------------
                Course lastCourse = new Course(currentCRN, storedUsp, storedSubject, storedCNumber, storedSection, storedTitle, storedCredits, currentDays, storedStart, storedStop, storedBuilding, storedRoom, storedInstructor, currentNotes);
                finalList.add(lastCourse);
            	
            	
            // (7 of 7) A new CRN has been read (it's not the first) and there are more to come
            } else {
                System.out.println("new course found");
                // Create a Course object for the old course
                //String completeDays = "";
                //completeDays = previousDay;
                
                String[] completeNotes = new String[tempNotesArray.size()];
                completeNotes = tempNotesArray.toArray(completeNotes);

                Course completeCourse = new Course(previousCRN, storedUsp, storedSubject, storedCNumber, storedSection, storedTitle, storedCredits, storedDays, storedStart, storedStop, storedBuilding, storedRoom, storedInstructor, completeNotes);
                finalList.add(completeCourse);
                //System.out.println(finalList.size());

                // Save the (new) CRN, days, and notes
                previousCRN = currentCRN;
                previousDay = currentDays;
                System.out.println("Here are the days UPDATED");
                System.out.println(previousDay);                
                tempNotesArray.clear();
                for(int i=0; i < currentNotes.length; i++){
                    tempNotesArray.add(currentNotes[i]);
                }
                
                storedDays = currentDays;
                
                // Save other (new) course info
                //-------------------------------------
                storedSubject = element.getSubject();
                storedUsp = element.getUsp();
                storedCNumber = element.getCnumber();
                storedSection = element.getSection();
                storedTitle = element.getTitle();
                storedCredits = element.getCredits();
                
                storedStart = element.getStart();
                storedStop = element.getStop(); 
                storedBuilding = element.getBuilding();
                storedRoom = element.getRoom();
                
                storedInstructor = element.getInstructor();
                //-------------------------------------
                
            }
            
            
            
        } // end for loop
   	 // ---------------------------------------------------------------------------------------------------------
   	  */
	    

	    // Deprecated
        //courses = finalList;
        //finalList.clear();
	    
	    
	    
	  //RETURN A LIST OF COURSES WITH NOTES AND DAYS COMBINED -- AND DUPLICATES ELIMINATED
        courses = condenseCourseSchedule(listOfCourses);
	    return courses;
	}

	@Override
	public List<Course> getCoursesByCrn(String crn) {
    	// TODO 5 (10 pts) - Get the course with the given crn, or an empty list if no such course exists
	    List<Course> courses = null;
	    
	    System.out.printf("Working on TODO 5\n");
	    List<Course> listOfCourses = jdbcTemplate.query("SELECT offeringof.CRN, coursedetails.usp, offeringof.subject, " +
	    												"offeringof.number, scheduledcourses.section, offeringof.title, " +
	    												"courses.credits, coursemeetings.days, coursemeetings.start, coursemeetings.stop, " +
	    												"coursemeetings.bldg, coursemeetings.room, instructors.lastname, instructors.firstname, " +
	    												"coursenotes.note " +
	    												"FROM coscuw.offeringof " +	    												
	    												"JOIN coscuw.coursemeetings ON offeringof.CRN = coursemeetings.crn " +
	    												"JOIN coscuw.coursedetails ON offeringof.CRN = coursedetails.CRN " +
	    												"JOIN coscuw.scheduledcourses ON offeringof.CRN = scheduledcourses.CRN " +
	    												"JOIN coscuw.courses ON offeringof.subject=courses.subject " +
	    													"AND offeringof.number=courses.number " +
	    													"AND offeringof.title = courses.title " +
	    												"LEFT JOIN coscuw.coursenotes ON offeringof.CRN = coursenotes.CRN " +
	    												"JOIN coscuw.instructorfor ON offeringof.CRN = instructorfor.CRN " +
	    												"JOIN coscuw.instructors ON instructorfor.id = instructors.id " +
	    												"WHERE offeringof.CRN = ? "
	    , 
	    new Object[] { crn }, new RowMapper<Course>() {
	    	
	    	public Course mapRow(ResultSet myRs, int rowNum) throws SQLException {
				
				System.out.printf("Working on mapRow, TODO 5 \n");
				
			
				// Course
				String courseCRN = myRs.getString("CRN");
				System.out.printf("crn: %s \n", courseCRN);
				String courseUSP = myRs.getString("usp");
				System.out.printf("usp: %s \n", courseUSP);
				String courseSubject = myRs.getString("subject");
				System.out.printf("subject: %s \n", courseSubject);
				String courseNumber = myRs.getString("number");
				System.out.printf("number: %s \n", courseNumber);
				String courseSection = myRs.getString("section");
				System.out.printf("section: %s \n", courseSection);
				String courseTitle = myRs.getString("title");
				
				int courseCredits = myRs.getInt("credits");
				System.out.printf("credits: %d \n", courseCredits);
				
				String courseDay = myRs.getString("days");
				System.out.printf("days: %s \n", courseDay);
				String courseStart = myRs.getString("start");
				System.out.printf("start: %s \n", courseStart);
				String courseStop = myRs.getString("stop");
				System.out.printf("stop: %s \n", courseStop);
				String courseBuilding = myRs.getString("bldg");
				System.out.printf("bldg: %s \n", courseBuilding);
				String courseRoom = myRs.getString("room");
				System.out.printf("room: %s \n", courseRoom);
								
				// String courseInstructor= myRs.getString("");
				String courseInstructor = "Gamboa";
				String[] courseNotes = {"Note1", "Note2"};
	    
				
				
				
				
				Course course = new Course(courseCRN, courseUSP, courseSubject, courseNumber, courseSection, courseTitle, courseCredits, courseDay, courseStart, courseStop, courseBuilding, courseRoom, courseInstructor, courseNotes);				
				
				return course;
				
			}
	    
	    });
	    
	    
	    
	    //courses = listOfCourses;
	    courses = condenseCourseSchedule(listOfCourses);
		return courses;
	}
	
	@Override
	public List<Course> getCoursesByCnumber(String dept, String cnumber) {
    	// TODO 6 (10 pts) - Get the course with the given department and course number (e.g., COSC 4820)
	    List<Course> courses = null;
	    
	    System.out.printf("Working on TODO 6\n");
	    List<Course> listOfCourses = jdbcTemplate.query("SELECT offeringof.CRN, coursedetails.usp, offeringof.subject, " +
	    												"offeringof.number, scheduledcourses.section, offeringof.title, " +
	    												"courses.credits, coursemeetings.days, coursemeetings.start, coursemeetings.stop, " +
	    												"coursemeetings.bldg, coursemeetings.room, instructors.lastname, instructors.firstname, " +
	    												"coursenotes.note " +
	    												"FROM coscuw.offeringof " +	    												
	    												"JOIN coscuw.coursemeetings ON offeringof.CRN = coursemeetings.crn " +
	    												"JOIN coscuw.coursedetails ON offeringof.CRN = coursedetails.CRN " +
	    												"JOIN coscuw.scheduledcourses ON offeringof.CRN = scheduledcourses.CRN " +
	    												"JOIN coscuw.courses ON offeringof.subject=courses.subject " +
	    													"AND offeringof.number=courses.number " +
	    													"AND offeringof.title = courses.title " +
	    												"LEFT JOIN coscuw.coursenotes ON offeringof.CRN = coursenotes.CRN " +
	    												"JOIN coscuw.instructorfor ON offeringof.CRN = instructorfor.CRN " +
	    												"JOIN coscuw.instructors ON instructorfor.id = instructors.id " +
	    												"WHERE offeringof.subject = ? AND  offeringof.number = ?  "
	    , 
	    new Object[] { dept, cnumber }, new RowMapper<Course>() {
	    	
	    	public Course mapRow(ResultSet myRs, int rowNum) throws SQLException {
				
				System.out.printf("Working on mapRow, TODO 5 \n");
				
			
				// Course
				String courseCRN = myRs.getString("CRN");
				System.out.printf("crn: %s \n", courseCRN);
				String courseUSP = myRs.getString("usp");
				System.out.printf("usp: %s \n", courseUSP);
				String courseSubject = myRs.getString("subject");
				System.out.printf("subject: %s \n", courseSubject);
				String courseNumber = myRs.getString("number");
				System.out.printf("number: %s \n", courseNumber);
				String courseSection = myRs.getString("section");
				System.out.printf("section: %s \n", courseSection);
				String courseTitle = myRs.getString("title");
				
				int courseCredits = myRs.getInt("credits");
				System.out.printf("credits: %d \n", courseCredits);
				
				String courseDay = myRs.getString("days");
				System.out.printf("days: %s \n", courseDay);
				String courseStart = myRs.getString("start");
				System.out.printf("start: %s \n", courseStart);
				String courseStop = myRs.getString("stop");
				System.out.printf("stop: %s \n", courseStop);
				String courseBuilding = myRs.getString("bldg");
				System.out.printf("bldg: %s \n", courseBuilding);
				String courseRoom = myRs.getString("room");
				System.out.printf("room: %s \n", courseRoom);
								
				// String courseInstructor= myRs.getString("");
				String courseInstructor = "Gamboa";
				String[] courseNotes = {"Note1", "Note2"};
	    
				
				
				Course course = new Course(courseCRN, courseUSP, courseSubject, courseNumber, courseSection, courseTitle, courseCredits, courseDay, courseStart, courseStop, courseBuilding, courseRoom, courseInstructor, courseNotes);				
				
				return course;
				
			}
	    
	    });
	    
		//return courses;
		courses = condenseCourseSchedule(listOfCourses);
		return courses;
	}
	
	@Override
	public List<Course> getCoursesByTitle(String title) {
    	// TODO 7 (10 pts) - Get the courses that partially match the given title
	    List<Course> courses = null;
	    
	    System.out.printf("Working on TODO 7\n");
	    List<Course> listOfCourses = jdbcTemplate.query("SELECT offeringof.CRN, coursedetails.usp, offeringof.subject, " +
	    												"offeringof.number, scheduledcourses.section, offeringof.title, " +
	    												"courses.credits, coursemeetings.days, coursemeetings.start, coursemeetings.stop, " +
	    												"coursemeetings.bldg, coursemeetings.room, instructors.lastname, instructors.firstname, " +
	    												"coursenotes.note " +
	    												"FROM coscuw.offeringof " +	    												
	    												"JOIN coscuw.coursemeetings ON offeringof.CRN = coursemeetings.crn " +
	    												"JOIN coscuw.coursedetails ON offeringof.CRN = coursedetails.CRN " +
	    												"JOIN coscuw.scheduledcourses ON offeringof.CRN = scheduledcourses.CRN " +
	    												"JOIN coscuw.courses ON offeringof.subject=courses.subject " +
	    													"AND offeringof.number=courses.number " +
	    													"AND offeringof.title = courses.title " +
	    												"LEFT JOIN coscuw.coursenotes ON offeringof.CRN = coursenotes.CRN " +
	    												"JOIN coscuw.instructorfor ON offeringof.CRN = instructorfor.CRN " +
	    												"JOIN coscuw.instructors ON instructorfor.id = instructors.id " +
	    												"WHERE offeringof.title RLIKE ?  "
	    , 
	    new Object[] { title }, new RowMapper<Course>() {
	    	
	    	public Course mapRow(ResultSet myRs, int rowNum) throws SQLException {
				
				System.out.printf("Working on mapRow, TODO 5 \n");
				
			
				// Course
				String courseCRN = myRs.getString("CRN");
				System.out.printf("crn: %s \n", courseCRN);
				String courseUSP = myRs.getString("usp");
				System.out.printf("usp: %s \n", courseUSP);
				String courseSubject = myRs.getString("subject");
				System.out.printf("subject: %s \n", courseSubject);
				String courseNumber = myRs.getString("number");
				System.out.printf("number: %s \n", courseNumber);
				String courseSection = myRs.getString("section");
				System.out.printf("section: %s \n", courseSection);
				String courseTitle = myRs.getString("title");
				
				int courseCredits = myRs.getInt("credits");
				System.out.printf("credits: %d \n", courseCredits);
				
				String courseDay = myRs.getString("days");
				System.out.printf("days: %s \n", courseDay);
				String courseStart = myRs.getString("start");
				System.out.printf("start: %s \n", courseStart);
				String courseStop = myRs.getString("stop");
				System.out.printf("stop: %s \n", courseStop);
				String courseBuilding = myRs.getString("bldg");
				System.out.printf("bldg: %s \n", courseBuilding);
				String courseRoom = myRs.getString("room");
				System.out.printf("room: %s \n", courseRoom);
								
				// String courseInstructor= myRs.getString("");
				String courseInstructor = "Gamboa";
				String[] courseNotes = {"Note1", "Note2"};
	    
				
				
				Course course = new Course(courseCRN, courseUSP, courseSubject, courseNumber, courseSection, courseTitle, courseCredits, courseDay, courseStart, courseStop, courseBuilding, courseRoom, courseInstructor, courseNotes);				
				
				return course;
				
			}
	    
	    });
	    
	    
	    
	    	//return courses;
	  		courses = condenseCourseSchedule(listOfCourses);
	  		return courses;
	}

	@Override
	public void addDropCourses(String wnumber, List<String> adds, List<String> drops) throws UWregDAOException {
    	// TODO 8 (20 pts) - add & drop the given courses (CRNs) from the user's schedule
		// 10 points: Add and drop the right courses 
    	// 10 points: Check that (new) schedule has at least 12 and at most 18 credit
    	//            hours, and that there are no time conflicts
		// If something goes wrong (e.g., schedule fails sanity checks), throw a UWregDAOException
		throw new UWregDAOException("Not implemented");
	}
	
	//Better name: condenseCourseSchedule
	public List<Course> condenseCourseSchedule(List<Course> listOfCourses){
	//public void danielsFunction(List<Course> listOfCourses){
		
		System.out.printf("\n\n");
		System.out.println("Hello, Daniel");
		System.out.println("**********************  condenseCourseSchedule **********  ");
		
		System.out.println(" === COMBINING COURSES === \n");
        String previousCRN = "x123";
        String previousDay = "";
      //-------------------------------------
        String storedSubject = "";
        String storedUsp = "";
        String storedCNumber = "";
        String storedSection = "";
        String storedTitle = "";
        int storedCredits = 0;
        
        String storedDays = "";
        
        String storedStart = "";
        String storedStop = ""; 
        String storedBuilding = "";
        String storedRoom = "";
        
        String storedInstructor = "";
      //-------------------------------------
        
        ArrayList<String> tempNotesArray = new ArrayList<String>();
        List<Course> finalList = new ArrayList<Course>();
        
        int loopCounter = 0;
        
        for (Course element : listOfCourses) {

            String currentCRN = null;
            String currentDays = null;
            String[] currentNotes = null;
            
            loopCounter++ ;
            System.out.println("\n\nWhat's the count?");
            System.out.println(loopCounter);
            
            // Get the current CRN info
            currentCRN = element.getCrn();
            System.out.println("The CRN is: ");
            System.out.println(currentCRN);
            System.out.println("The previousCRN is ");
            System.out.println(previousCRN);
            System.out.println("Do the CRNs match?");
            System.out.println(currentCRN.equals(previousCRN));
            // Get the current days info
            currentDays = element.getDays();
            System.out.println("Here are the days");
            System.out.println(currentDays);
            System.out.println("previousDays is ");
            System.out.println(previousDay);
            // Get the current info
            currentNotes = element.getNotes();
            System.out.println("The number of notes is:");
            System.out.println(currentNotes.length);
            System.out.println("The notes are:");
            System.out.println(currentNotes[0]);
            System.out.println(currentNotes[1]);
            
            // (1 of 7) Ignore (consecutive) duplicates
            if( (currentCRN.equals(previousCRN)) && (currentDays.equals(previousDay)) ) {
            	System.out.println("Duplicate course found");
            	
            // (2 of 7) A repeated CRN, with more CRNs to come
            } else if( (currentCRN.equals(previousCRN)) && (loopCounter != listOfCourses.size())) {
                System.out.println("SAME course found");

                //Since the CRN has not changed, combine the notes
                for(int i=0; i < currentNotes.length; i++){
                    tempNotesArray.add(currentNotes[i]);
                }
                
                //Since the CRN has not changed, combine the days
                storedDays = storedDays + currentDays;
                System.out.println("Here are the storedDays UPDATED");
                System.out.println(storedDays);
                
                //Keep track of the single, most recent day for comparison
                previousDay = currentDays;
                System.out.println("Here is previousDay UPDATED");
                System.out.println(previousDay);
                

            // (3 of 7) A repeated CRN, with no more CRNs to check
            } else if ((currentCRN.equals(previousCRN)) && (loopCounter == listOfCourses.size())){ 
            	
            	//Since the CRN has not changed, combine the notes
                for(int i=0; i < currentNotes.length; i++){
                    tempNotesArray.add(currentNotes[i]);
                }
                
                //Since the CRN has not changed, combine the days
                storedDays = storedDays + currentDays;
                System.out.println("Here are the storedDays UPDATED");
                System.out.println(storedDays);                                            
                
                // Create a Course object 
                //String completeDays = "";
                //completeDays = storedDays;
                
                String[] completeNotes = new String[tempNotesArray.size()];
                completeNotes = tempNotesArray.toArray(completeNotes);
                
                Course completeCourse = new Course(previousCRN, storedUsp, storedSubject, storedCNumber, storedSection, storedTitle, storedCredits, storedDays, storedStart, storedStop, storedBuilding, storedRoom, storedInstructor, completeNotes);
                finalList.add(completeCourse);
                
            // (4 of 7) The last CRN is being read in and it's not also the first (and not a repeat)
            } else if(loopCounter == listOfCourses.size() && ( !previousCRN.equals("x123") )) {

                System.out.println("This is the last shot \n");

                //Create an object for the previous course
                //String completeDays = "";
                //completeDays = previousDay;
                
                String[] completeNotes = new String[tempNotesArray.size()];
                completeNotes = tempNotesArray.toArray(completeNotes);
                
                Course completeCourse = new Course(previousCRN, storedUsp, storedSubject, storedCNumber, storedSection, storedTitle, storedCredits, storedDays, storedStart, storedStop, storedBuilding, storedRoom, storedInstructor, completeNotes);
                finalList.add(completeCourse);
                
                
                // Create an object for the new course
                //-------------------------------------
                storedSubject = element.getSubject();
                storedUsp = element.getUsp();
                storedCNumber = element.getCnumber();
                storedSection = element.getSection();
                storedTitle = element.getTitle();
                storedCredits = element.getCredits();
                
                storedStart = element.getStart();
                storedStop = element.getStop(); 
                storedBuilding = element.getBuilding();
                storedRoom = element.getRoom();
                
                storedInstructor = element.getInstructor();
                //-------------------------------------
                Course lastCourse = new Course(currentCRN, storedUsp, storedSubject, storedCNumber, storedSection, storedTitle, storedCredits, currentDays, storedStart, storedStop, storedBuilding, storedRoom, storedInstructor, currentNotes);
                finalList.add(lastCourse);

            // (5 of 7) There first CRN is being read and there are more to come
            } else if( (previousCRN.equals("x123")) && (loopCounter != listOfCourses.size()) ) {

                System.out.println("This is the first course \n");
                previousCRN = currentCRN;
                System.out.println("previousCRN has been updated to: ");
                System.out.println(previousCRN);
                
                //Save the notes and days
                for(int i=0; i < currentNotes.length; i++){
                    tempNotesArray.add(currentNotes[i]);
                }
                storedDays = currentDays;
                      
                // Save other course info
                //-------------------------------------
                storedSubject = element.getSubject();
                storedUsp = element.getUsp();
                storedCNumber = element.getCnumber();
                storedSection = element.getSection();
                storedTitle = element.getTitle();
                storedCredits = element.getCredits();
                
                storedStart = element.getStart();
                storedStop = element.getStop(); 
                storedBuilding = element.getBuilding();
                storedRoom = element.getRoom();
                
                storedInstructor = element.getInstructor();
                //-------------------------------------
                
                //Update the previous day
                previousDay = currentDays;
                

            // (6 of 7) There is only one CRN
            } else if ( (previousCRN.equals("x123")) && (loopCounter == listOfCourses.size()) ) { 
                
                // Create an object for for this one course
            	//-------------------------------------
                storedSubject = element.getSubject();
                storedUsp = element.getUsp();
                storedCNumber = element.getCnumber();
                storedSection = element.getSection();
                storedTitle = element.getTitle();
                storedCredits = element.getCredits();
                
                storedStart = element.getStart();
                storedStop = element.getStop(); 
                storedBuilding = element.getBuilding();
                storedRoom = element.getRoom();
                
                storedInstructor = element.getInstructor();
                //-------------------------------------
                Course lastCourse = new Course(currentCRN, storedUsp, storedSubject, storedCNumber, storedSection, storedTitle, storedCredits, currentDays, storedStart, storedStop, storedBuilding, storedRoom, storedInstructor, currentNotes);
                finalList.add(lastCourse);
            	
            	
            // (7 of 7) A new CRN has been read (it's not the first) and there are more to come
            } else {
                System.out.println("new course found");
                // Create a Course object for the old course
                //String completeDays = "";
                //completeDays = previousDay;
                
                String[] completeNotes = new String[tempNotesArray.size()];
                completeNotes = tempNotesArray.toArray(completeNotes);

                Course completeCourse = new Course(previousCRN, storedUsp, storedSubject, storedCNumber, storedSection, storedTitle, storedCredits, storedDays, storedStart, storedStop, storedBuilding, storedRoom, storedInstructor, completeNotes);
                finalList.add(completeCourse);
                //System.out.println(finalList.size());

                // Save the (new) CRN, days, and notes
                previousCRN = currentCRN;
                previousDay = currentDays;
                System.out.println("Here are the days UPDATED");
                System.out.println(previousDay);                
                tempNotesArray.clear();
                for(int i=0; i < currentNotes.length; i++){
                    tempNotesArray.add(currentNotes[i]);
                }
                
                storedDays = currentDays;
                
                // Save other (new) course info
                //-------------------------------------
                storedSubject = element.getSubject();
                storedUsp = element.getUsp();
                storedCNumber = element.getCnumber();
                storedSection = element.getSection();
                storedTitle = element.getTitle();
                storedCredits = element.getCredits();
                
                storedStart = element.getStart();
                storedStop = element.getStop(); 
                storedBuilding = element.getBuilding();
                storedRoom = element.getRoom();
                
                storedInstructor = element.getInstructor();
                //-------------------------------------
                
            }
            
            
            
        } // end of listOfCourses for loop
		
		return finalList;
	}

}
