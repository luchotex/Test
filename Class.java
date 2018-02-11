/*
 * Class.java
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
public class Class {

    private String code;
    private String title;
    private String description;
    private List<Integer> studentIdList;

    /**
     * Creates a new instance of Class.
     */    
    public Class() {
        code = title = description = "";
        studentIdList = new ArrayList<>();
    }

    /**
     * Creates a new instance of Class.
     * @param code String
     * @param title String
     * @param description String
     */
    public Class(String code, String title, String description) {
        this.code = code;
        this.title = title;
        this.description = description;
        this.studentIdList = new ArrayList<>();
    }
    
    /**
     * @return the code
     */
    public synchronized String getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public synchronized void setCode(String code) {
        this.code = code;
    }

    /**
     * @return the title
     */
    public synchronized String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public synchronized void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the description
     */
    public synchronized String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public synchronized void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the studentIdList
     */
    public synchronized List<Integer> getStudentIdList() {
        return studentIdList;
    }

    /**
     * @param studentIdList the studentIdList to set
     */
    public synchronized void setStudentIdList(List<Integer> studentIdList) {
        this.studentIdList = studentIdList;
    }
    
    public synchronized void enroll(int studentId) {
        if (!studentIdList.contains(studentId)) {
            studentIdList.add(studentId);
        }
    }
    
    public synchronized void disenroll(Integer studentId) {
        studentIdList.remove(studentId);
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
        final Class theClass = (Class) obj;
        return (this.code == null ? theClass.code == null : 
                this.code.equals(theClass.code));
    }        

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.code);
        hash = 89 * hash + Objects.hashCode(this.title);
        hash = 89 * hash + Objects.hashCode(this.description);
        return hash;
    }
}
