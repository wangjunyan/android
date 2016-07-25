// IMyService.aidl
package com.example.wangjunyan.myfirstapp;

// Declare any non-default types here with import statements
import com.example.wangjunyan.myfirstapp.Student;

interface IMyService {
    List<Student> getStudent();
    void addStudent(in Student student);
}
