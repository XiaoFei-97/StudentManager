package com.jzfblog.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jzfblog.domain.Student;
import com.jzfblog.service.impl.StudentServiceImpl;

/**
 * 处理单个学生的更新，查询一个学生的信息，然后跳转到页面
 * @author 蒋振飞
 *
 */
public class EditServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		try {
			// 1.接收id
			int sid = Integer.parseInt(request.getParameter("sid"));
			
			// 2.查询学生数据
			StudentServiceImpl service = new StudentServiceImpl();
			Student stu = service.findStudentById(sid);
			
			request.setAttribute("stu", stu);
			request.getRequestDispatcher("edit.jsp").forward(request, response);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
