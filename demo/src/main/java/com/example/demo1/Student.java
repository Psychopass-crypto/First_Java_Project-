package com.example.demo1;

import java.util.Vector;

public class Student {
    private String id;
    private String name;
    private int age;

    public Student(String id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    private Vector<Grade> grades = new Vector<>();

    public void addGrade(Grade grade) {
        grades.add(grade);
    }

    static class Grade {
        private String studentId;
        private String course;
        private double grade;

        public Grade(String studentId, String course, double grade) {
            this.studentId = studentId;
            this.course = course;
            this.grade = grade;
        }

        public String getStudentId() {
            return studentId;
        }

        public void setStudentId(String studentId) {
            this.studentId = studentId;
        }

        public String getCourse() {
            return course;
        }

        public void setCourse(String course) {
            this.course = course;
        }

        public double getGrade() {
            return grade;
        }

        public void setGrade(double grade) {
            this.grade = grade;
        }
    }


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
