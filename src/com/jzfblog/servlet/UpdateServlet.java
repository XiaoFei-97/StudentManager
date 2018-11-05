package com.jzfblog.servlet;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jzfblog.domain.Student;
import com.jzfblog.service.impl.StudentServiceImpl;


/**
 * 更新学生信息的逻辑处理
 * @author 蒋振飞
 *
 */
public class UpdateServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		try {
			// 1.获取客户端提交上来的数据
			int sid = Integer.parseInt(request.getParameter("sid"));
			String sname = request.getParameter("sname");
			String gender = request.getParameter("gender");
			String birthday = request.getParameter("birthday");
			String info = request.getParameter("info");
			String phone = request.getParameter("phone");
			String[] h = request.getParameterValues("hobby");
			String hobby = Arrays.toString(h);
			hobby = hobby.substring(1, hobby.length() - 1);
			
			// 2.构造学生个人信息
			Date date = new SimpleDateFormat("yyyy-MM-dd").parse(birthday);
			
			Student student = new Student();
			student.setSid(sid);
			student.setSname(sname);
			student.setGender(gender);
			student.setPhone(phone);
			student.setBirthday(date);
			student.setHobby(hobby);
			student.setInfo(info);
			
			// 3.更新数据库
			StudentServiceImpl service = new StudentServiceImpl();
			service.update(student);
			// 4.跳转页面
			request.getRequestDispatcher("StudentListServlet").forward(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
