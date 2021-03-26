package com.example.service;

import com.example.dao.DataDeal;
import com.example.dao.Student;
import org.springframework.stereotype.Service;

@Service
public class WebService {
    static DataDeal mydatadeal = null;
    static {
        mydatadeal = new DataDeal();
    }
    // 增加一个学生
    public static  boolean addstudent(Student s){
        return mydatadeal.addstudent(s);
    }
    // 删除一个学生
    public  static  boolean deletestuddent(Student s){
        return mydatadeal.delete_student(s);
    }
    // 修改一个学生的信息
    public  static  boolean editstudent(Student s){
        return mydatadeal.edit_student(s);
    }
    // 查询一个学生的信息
    public  static  Student get_student(Student s){
        return  mydatadeal.get_student(s);
    }
}
