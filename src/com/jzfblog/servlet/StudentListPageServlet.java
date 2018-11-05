package com.jzfblog.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jzfblog.domain.PageBean;
import com.jzfblog.service.impl.StudentServiceImpl;

/**
 * 用于分页显示学生列表的servlet
 * @author 蒋振飞
 *
 */
public class StudentListPageServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		try {
			// 1.获取需要显示的页码数
			int currentPage = Integer.parseInt(request.getParameter("currentPage"));
			
			// 2.根据指定页数，获取该页的数据
			StudentServiceImpl service = new StudentServiceImpl();
			PageBean pageBean = service.findStudentPage(currentPage);
			request.setAttribute("pageBean", pageBean);
			
			// 3.跳转界面
			request.getRequestDispatcher("page_list.jsp").forward(request, response);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
