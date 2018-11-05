package com.jzfblog.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jzfblog.domain.Student;
import com.jzfblog.service.impl.StudentServiceImpl;


public class SearchStudentServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		try {
			// 1.获取客户端提交的查询条件
			String sname = request.getParameter("sname");
			String gender = request.getParameter("gender"); 
			// 2.使用service查询
			StudentServiceImpl service = new StudentServiceImpl();
			List<Student> list = service.searchStudent(sname, gender);
			request.setAttribute("list", list);
			
			// 3.查询结果实现跳转
			request.getRequestDispatcher("list.jsp").forward(request, response);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
