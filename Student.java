/*
 * StudentEdited.java
 */

package com.tx.simplescheduling.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Diego Rojas
 */
@XmlRootElement
public class Student {

    /* The class properties */
    private int studentId;
    private String lastName;
    private String firstName;
    private List<String> classCodesList;

    /**
     * Creates a new instance of Student.
     */
    public Student() {
        lastName = firstName = "";
        classCodesList = new ArrayList<>();
    }

    /**
     * Creates a new instance of Student.
     * @param studentId int
     * @param lastName String
     * @param firstName String
     */
    public Student(int studentId, String lastName, String firstName) {
        this.studentId = studentId;
        this.lastName = lastName;
        this.firstName = firstName;
        this.classCodesList = new ArrayList<>();
    }
    
    /**
     * @return the studentId
     */
    public synchronized int getStudentId() {
        return studentId;
    }

    /**
     * @param studentId the studentId to set
     */
    public synchronized void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    /**
     * @return the lastName
     */
    public synchronized String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public synchronized void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the firstName
     */
    public synchronized String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public synchronized void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    /**
     * @return the classCodesList
     */
    public synchronized List<String> getClassCodesList() {
        return classCodesList;
    }

    /**
     * @param classCodesList the classCodesList to set
     */
    public synchronized void setClassCodesList(List<String> classCodesList) {
        this.classCodesList = classCodesList;
    }
    
    public synchronized void assign(String code) {
        if (!classCodesList.contains(code)) {
            classCodesList.add(code);
        }
    }
    
    public synchronized void unassign(String code) {
        classCodesList.remove(code);
    }
    
    @Override
    public synchronized boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Student student = (Student) obj;
        return this.studentId == student.studentId;
    }     

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + this.studentId;
        hash = 67 * hash + Objects.hashCode(this.lastName);
        hash = 67 * hash + Objects.hashCode(this.firstName);
        return hash;
    }    
    
}
