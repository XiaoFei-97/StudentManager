package com.jzfblog.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jzfblog.dao.impl.StudentDaoImpl;
import com.jzfblog.domain.Student;
import com.jzfblog.service.impl.StudentServiceImpl;

/**
 * 负责查询所有的学生信息，然后呈现给页面
 * @author 蒋振飞
 *
 */
public class StudentListServlet extends HttpServlet {
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			// 1.查询所有出来的学生
			StudentServiceImpl service = new StudentServiceImpl();
			List<Student> list = service.findAll();
			request.getSession().setAttribute("list", list);
			
			// 2.将数据保存到域中
			request.setAttribute("list", list);
			
			// 2.跳转页面
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
