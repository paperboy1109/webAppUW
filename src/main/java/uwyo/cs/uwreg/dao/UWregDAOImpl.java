package uwyo.cs.uwreg.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

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
	    
		Student student = null;	 
	    
	    // Using JDBC only (no Spring)
	    Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		
		try {
			// 1. Get a connection to database
			//myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/coscuw", "student" , "student");
			myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/coscuw", "root" , "90dziaDANmySQL~+!");
			
			// 2. Prepare statement
			//myStmt = myConn.prepareStatement("select * from enrolledin where wNumber = ? and CRN > ? ");
			myStmt = myConn.prepareStatement("select * from coscuw.students where wNumber = ? ");
			
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
				
				student = new Student(studentWNum, studentFirstName, studentLastName, studentGender);
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
		}
	    
	    
	    
		return student;
    }

	@Override
	public List<Course> getCoursesRegistered(String wnumber) {
    	// TODO 4 (10 pts) - Get the courses the given student is registered for
	    List<Course> courses = null;
	    return courses;
	}

	@Override
	public List<Course> getCoursesByCrn(String crn) {
    	// TODO 5 (10 pts) - Get the course with the given crn, or an empty list if no such course exists
	    List<Course> courses = null;
		return courses;
	}
	
	@Override
	public List<Course> getCoursesByCnumber(String dept, String cnumber) {
    	// TODO 6 (10 pts) - Get the course with the given department and course number (e.g., COSC 4820)
	    List<Course> courses = null;
		return courses;
	}
	
	@Override
	public List<Course> getCoursesByTitle(String title) {
    	// TODO 7 (10 pts) - Get the courses that partially match the given title
	    List<Course> courses = null;
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

}
