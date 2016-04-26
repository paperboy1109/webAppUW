package uwyo.cs.uwreg.dao;

import java.util.List;

import uwyo.cs.uwreg.dao.model.Course;
import uwyo.cs.uwreg.dao.model.Student;

public interface UWregDAO {
	public Student getStudentByWnumber(String wnumber);

	public List<Course> getCoursesRegistered(String wnumber);

	public List<Course> getCoursesByCrn(String crn);

	public List<Course> getCoursesByCnumber(String dept, String cnumber);

	public List<Course> getCoursesByTitle(String title);

	public void addDropCourses(String wnumber, List<String> adds, List<String> drops) throws UWregDAOException;

}
