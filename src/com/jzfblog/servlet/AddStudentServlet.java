package com.jzfblog.servlet;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.dbutils.QueryRunner;

import com.jzfblog.domain.Student;
import com.jzfblog.service.impl.StudentServiceImpl;
import com.jzfblog.util.JDBCUtil;


public class AddStudentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
			
		try {
			// 1.获取客户端提交上来的数据
			String sname = request.getParameter("name");
			String gender = request.getParameter("gender");
			String phone = request.getParameter("phone");
			String birthday = request.getParameter("birthday");
			// hobby需要对应的多个
			String[] h = request.getParameterValues("hobby");
			String hobby= Arrays.toString(h);
			hobby = hobby.substring(1, hobby.length() - 1);
			String info = request.getParameter("info");
			
			// 2.添加到数据库
			StudentServiceImpl service = new StudentServiceImpl();
			Student student = new Student();
			// 注意日期这里需要parse一下
			Date date = null;
			date = new SimpleDateFormat("yyyy-MM-dd").parse(birthday);
		
			student.setSname(sname);
			student.setGender(gender);
			student.setPhone(phone);
			student.setBirthday(date);
			student.setHobby(hobby);
			student.setInfo(info);
			
			service.insert(student);
			// 3.跳转到列表页
			// servlet除了能跳转jsp，还能跳转servlet
			// request.getRequestDispatcher("list.jsp").forward(request, response);
			request.getRequestDispatcher("StudentListServlet").forward(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();	
		}
		
	}
 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
