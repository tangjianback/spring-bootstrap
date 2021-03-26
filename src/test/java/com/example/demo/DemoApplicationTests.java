package com.example.demo;

import com.example.dao.DataDeal;
import com.example.dao.Student;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;

@SpringBootTest
class DemoApplicationTests {




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







	@Test
	void contextLoads() {

//		new DataDeal();
//		System.out.println("jiangege");
//

		String str = "files/data.txt";
		//通过类名.class.getClassLoader()，获取到类加载器
		ClassLoader classLoader = DemoApplicationTests.class.getClassLoader();
		//通过classLoader.getResource()获取到输入流后，通过getFile()获取文件
		File file = new File(classLoader.getResource(str).getFile());
		//判断该文件是否存在
		try {
			BufferedReader bfr = new BufferedReader(new FileReader(file));
			System.out.println(bfr.readLine());
			bfr.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
//		File file2 =  new File("target/classes/files/mytest.txt");
//		try {
//			boolean fvar = file2.createNewFile();
//			System.out.println("创建成功 "+fvar);
//			System.out.println(file2.getAbsolutePath());
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}

}
