/*
 * ClassResource.java
 */
package com.tx.simplescheduling.resource;

import com.tx.simplescheduling.exception.NotFoundException;
import java.util.List;
import javax.ejb.Singleton;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import com.tx.simplescheduling.model.Class;
import com.tx.simplescheduling.model.Student;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.DELETE;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author Diego Rojas
 */
@Singleton
@Path("classes")
public class ClassResource {
    
    private static final Map<String, Class> classMap = new HashMap<>();

    /**
     * @param code String
     * @return the classList
     */
    public static Class getClass(String code) {
        synchronized (classMap) {
            return classMap.get(code);
        }
    }
    
    /**
     * Enrolls a student to a class.
     * @param studentId int the student to be enrolled.
     * @param code the class code of the class to enroll.
     * @return boolean
     */
    public static boolean enrollStudentToClass(int studentId, String code) {
        synchronized (classMap) {
            Class theClass;
            if ((theClass = classMap.get(code)) != null) {
                theClass.enroll(studentId);
                return true;
            }
            return false;
        }
    }
    
    /**
     * Disenrolls a student from a class.
     * @param studentId int
     * @param code String
     * @return boolean
     */
    public static boolean disenrollStudentFromClass(int studentId, String code) {
        synchronized (classMap) {
            Class theClass;
            if ((theClass = classMap.get(code)) != null) {
                theClass.disenroll(studentId);
                return true;
            }
            return false;
        }
    }
    
    /**
     * Creates a new class.
     * @param code int
     * @param title String
     * @param description String
     * @param studentIdList List
     * @return Class
     */
    @POST
    @Produces(MediaType.APPLICATION_XML)
    public Class classes(@QueryParam("code") String code, 
            @QueryParam("title") String title, 
            @QueryParam("description") String description,
            @QueryParam("studentIdList") List<Integer> studentIdList) 
    {
        System.out.println("Create parameters: code = " + code + 
                " title = " + title + " description = " + description +
                " studentIdList = " + (studentIdList != null ? studentIdList : ""));
        try {
            Class newClass = new Class(code, title, description);
            
            synchronized (classMap) {
                if (!classMap.containsKey(code)) {
                    classMap.put(code, newClass);
                }
            }
            if (studentIdList != null) {
                // register student into this class
                newClass.setStudentIdList(studentIdList);
                // assign this class to students
                studentIdList.stream().forEach((studentId) -> {
                    StudentsResource.assignClassToStudent(code, studentId);
                });
            }            
            
            return newClass;             
        } catch (Exception ex) {
            Logger.getLogger(StudentsResource.class.getName()).log(Level.SEVERE, 
                    "Exception while creating new class.", ex);
            throw new InternalServerErrorException();
        }
    }    
       
    /**
     * Deletes a class based on the code.
     * @param code String
     * @return Response
     */
    @DELETE
    @Produces(MediaType.APPLICATION_XML)
    public Response students(@QueryParam("code") String code) 
    {
        Response response;
        try {
            synchronized (classMap) {
                if (classMap.containsKey(code)) { // class exists
                    // Remove this class from student's enrollments
                    classMap.get(code).getStudentIdList().stream().forEach((studentId) -> {
                        StudentsResource.unassignClassFromStudent(code, studentId);
                    });
                    classMap.remove(code);
                    // successfully deleted, return 204 NO CONTENT
                    response = Response.noContent().build();                
                }
                else {
                    // inexistant class, return 404 NOT FOUND
                    response = Response.status(Response.Status.NOT_FOUND).build();
                }
            }
        } catch (Exception ex) {
            // 500 INTERNAL SERVER ERROR
            response = Response.serverError().build();
            Logger.getLogger(StudentsResource.class.getName()).log(Level.SEVERE, 
                    "Exception while deleting class with code = " +  code + ".", ex);
        }
        
        return response;         
    }
    
    /**
     * Returns all classes.
     * @return List
     */
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public List<Class> classes() {
        synchronized (classMap) {
            return new ArrayList<>(classMap.values());
        }
    }  
    
    /**
     * Returns all students assigned to a class.
     * @param code String The class code to filter with.
     * @return List
     */
    @Path("{code}")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public List<Student> classes(@PathParam("code") String code) {
        try {
            Class theClass = classMap.get(code);
            if (theClass != null) {
                List<Student> studentList = new ArrayList<>();
                theClass.getStudentIdList().stream().filter((studentId) -> 
                        (StudentsResource.getStudent(studentId) != null)).forEach((studentId) -> {
                    studentList.add(StudentsResource.getStudent(studentId));
                });

                return studentList;
            }
            else {
                throw new NotFoundException("Class with code = " + 
                        code + " not found.");
            }
        } catch (Exception e) {
            Logger.getLogger(StudentsResource.class.getName()).log(Level.SEVERE, 
                    "Exception retrieving assigned classes's students.", e);            
            throw new InternalServerErrorException();
            
        }
    }        

}
