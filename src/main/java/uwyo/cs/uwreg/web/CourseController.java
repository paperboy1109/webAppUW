/*
 * The MIT License (MIT)
 * Copyright (C) 2012 Jason Ish
 *
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the Software), to deal in the Software without
 * restriction, including without limitation the rights to use, copy,
 * modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED AS IS, WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS
 * BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN
 * ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package uwyo.cs.uwreg.web;

import java.security.Principal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import uwyo.cs.uwreg.dao.UWregDAO;
import uwyo.cs.uwreg.dao.UWregDAOException;
import uwyo.cs.uwreg.dao.model.Course;
import uwyo.cs.uwreg.dao.model.Student;

/**
 * Handles requests for the application home page.
 */
@Controller
public class CourseController {

    private static final Logger logger = LoggerFactory
            .getLogger(CourseController.class);

    @Autowired
    private UWregDAO dao;
    
    @RequestMapping(value = "/username", method = RequestMethod.GET)
    @ResponseBody
    public Student currentUserName(Principal principal) {
    	String wnumber = principal.getName();
    	Student student = dao.getStudentByWnumber(wnumber);
        return student;
    }

    /**
     * Get information for a course
     *
     */
    @RequestMapping(value = "/courses_registered", method = RequestMethod.GET)
    public @ResponseBody List<Course> coursesRegistered(Principal principal) {
    	logger.info("Getting courses for " + principal.getName());
    	String wnumber = principal.getName();
        List<Course> courses = dao.getCoursesRegistered(wnumber);
        return courses;
    }

    /**
     * Search for a course by crn
     *
     */
    @RequestMapping(value = "/update_registration", method = RequestMethod.GET)
    public @ResponseBody Map<String,String> updateRegistration(Principal principal,
    		@RequestParam String add, @RequestParam String drop) {
    	// TODO 9 (10 pts) - Make sure changes happen transactionally, i.e., atomically
    	
    	logger.info("Updating registration for " + principal.getName());
    	String wnumber = principal.getName();
    	Map<String,String> retval = new HashMap<String,String> ();
    	
    	List<String> adds  = Arrays.asList(add.split(",")); 
    	List<String> drops = Arrays.asList(drop.split(",")); 

    	try {
    		dao.addDropCourses (wnumber, adds, drops);
        	retval.put("status", "success");
    	}
    	catch (UWregDAOException e) {
    		retval.put("status", "failure");
    		retval.put("error_msg", e.getMessage());
    	}
    	    	
    	return retval;
    }

	/**
     * Search for a course by crn
     *
     */
    @RequestMapping(value = "/search/crn", method = RequestMethod.GET)
    public @ResponseBody List<Course> searchByCrn(@RequestParam String crn) {
    	logger.info("Searching for crn=" + crn);
        List<Course> courses = dao.getCoursesByCrn(crn);
        return courses;
    }

	/**
     * Search for a course by department and course number
     *
     */
    @RequestMapping(value = "/search/cnumber", method = RequestMethod.GET)
    public @ResponseBody List<Course> searchByCnumber(@RequestParam String dept, @RequestParam String cnumber) {
    	logger.info("Searching for course " + dept + " " + cnumber);
        List<Course> courses = dao.getCoursesByCnumber(dept, cnumber);
        return courses;
    }

	/**
     * Search for a course by course title
     *
     */
    @RequestMapping(value = "/search/title", method = RequestMethod.GET)
    public @ResponseBody List<Course> searchByTitle(@RequestParam String title) {
    	logger.info("Searching for course title: " + title);
        List<Course> courses = dao.getCoursesByTitle(title);
        return courses;
    }

}
