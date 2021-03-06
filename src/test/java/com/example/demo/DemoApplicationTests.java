package com.example.demo;

import com.example.dao.DataDeal;
import com.example.dao.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

import static com.example.batch.Excel.*;
import static java.lang.System.exit;

@SpringBootTest
class DemoApplicationTests {
    @Test
    public  void batch() {
        try {
            String str = "files/1.xls";
		//通过类名.class.getClassLoader()，获取到类加载器
            ClassLoader classLoader = DataDeal.class.getClassLoader();
		//通过classLoader.getResource()获取到输入流后，通过getFile()获取文件
            File file = new File(classLoader.getResource(str).getFile());


            //读取行列的值
            List<String> student_list = readRowsAndColums(file);
//            for(String item: student_list)
//            {
//                System.out.println(item);
//            }
            //先添加已存在的
            List<Student> Total_student = new LinkedList<>();
           Total_student.addAll(read_file());
           //转换
            List<Student> excel_student =  excel_student(student_list);
            Total_student.addAll(excel_student);
            storeStudentDisk(Total_student);



        } catch (Exception e) {
            e.printStackTrace();

        }
    }
     private void  storeStudentDisk(List<Student> stu_list){
//        for(Student item : stu_list)
//        {
//            System.out.println(item);
//        }
        String str = "files/data.txt";
//		//通过类名.class.getClassLoader()，获取到类加载器
        ClassLoader classLoader = DataDeal.class.getClassLoader();
//		//通过classLoader.getResource()获取到输入流后，通过getFile()获取文件
        File file = new File(classLoader.getResource(str).getFile());
//		//判断该文件是否存在
        if(file == null || !file.exists())
        {
            System.out.println("没有检测到数据文件,严重的错误");
            exit(0);
        }
        //持久化
        BufferedWriter bfw = null;
        try {
            bfw = new BufferedWriter(new FileWriter(file));
            for(Student item: stu_list){
                //System.out.println("store "+item.toString());
                bfw.write(student_to_string(item));
                bfw.newLine();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                bfw.flush();
                bfw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    private String student_to_string(Student s){
        return s.getSno()+" "+s.getName()+" "+s.getGender()+" "+s.getBorn_data()+" "+s.getBorn_place()+ " "+ s.getAcademy();
    }
    private List<Student> excel_student(List<String> str_list){
        List<Student> temp_student_list = new LinkedList<>();
        for(String item:str_list){
            String[] srl_arr = item.split(" ");
            if (srl_arr.length == 5){


                temp_student_list.add(new Student(srl_arr[0],srl_arr[1],"未知","未知","未知",srl_arr[2]+srl_arr[3]));
            }
        }




        return temp_student_list;
    }
    private List<Student> read_file(){
        List<Student> stu_list = new LinkedList<>();
        String str = "files/data.txt";
//		//通过类名.class.getClassLoader()，获取到类加载器
        ClassLoader classLoader = DataDeal.class.getClassLoader();
//		//通过classLoader.getResource()获取到输入流后，通过getFile()获取文件
        File file = new File(classLoader.getResource(str).getFile());
//		//判断该文件是否存在
        if(file == null || !file.exists())
        {
            System.out.println("没有检测到数据文件,严重的错误");
            exit(0);
        }
        //装载文件到内存中
        BufferedReader bfr = null;
        try {
            bfr = new BufferedReader(new FileReader(file));
            String string_line = null;
            while ((string_line = bfr.readLine()) != null){
                if(string_line.trim() == "")
                    break;
                stu_list.add(string_to_student(string_line));
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                bfr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return stu_list;
    }

    private Student string_to_student(String text_line){
        String[] srl_arr = text_line.split(" ");
        return new Student(srl_arr[0],srl_arr[1],srl_arr[2],srl_arr[3],srl_arr[4],srl_arr[5]);
    }

    @Test
    /**
     * 连接池连接方式
     */
    public void demo2(){
        // 获取连接池配置对象
        JedisPoolConfig config = new JedisPoolConfig();
        // 设置最大连接数
        config.setMaxTotal(30);
        // 设置最大的空闲连接数
        config.setMaxIdle(10);

        // 获得连接池: JedisPool jedisPool = new JedisPool(poolConfig,host,port);
        JedisPool jedisPool = new JedisPool(config,"localhost",6379);

        // 获得核心对象：jedis
        Jedis jedis = null;
        try{
            // 通过连接池来获得连接
            jedis = jedisPool.getResource();
            // 设置数据
            jedis.set("name","张三");
            // 获取数据
            String value = jedis.get("name");
            System.out.println(value);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            // 释放资源
            if(jedis != null){
                jedis.close();
            }
            // 释放连接池
            if(jedisPool != null){
                jedisPool.close();
            }
        }

    }


    @Test
    public void redis_test()
    {
        // 1. 设置IP地址和端口
        Jedis jedis = new Jedis("localhost",6379);
        // 2. 保存数据
        //jedis.set("name",);
        // 3. 获取数据
        String value = jedis.get("name");
        System.out.println(value);
        // 4.释放资源
        jedis.close();
    }


//    private RedisTemplate redisTemplate;
//
//
//    private StringRedisTemplate stringRedisTemplate;
//
//    @Autowired
//    public void setRedisTemplate(RedisTemplate redisTemplate) {
//        System.out.printf("注入");
//        this.redisTemplate = redisTemplate;
//    }
//    @Autowired
//    public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
//        System.out.println("注入");
//        this.stringRedisTemplate = stringRedisTemplate;
//    }





    @Test
    void daotest(){
        DataDeal my_datadeal = new DataDeal();
        Student  s = new Student("mf20330075","老王", "男" ,"1967832","河南郑州","英国大学");

//		System.out.println("添加"+my_datadeal.addstudent(s));
//		System.out.println("删除"+my_datadeal.delete_student(s));
//		System.out.println("修改"+my_datadeal.edit_student(s));
        System.out.println("查询"+my_datadeal.get_student(s));
        //new DataDeal();
    }




}
