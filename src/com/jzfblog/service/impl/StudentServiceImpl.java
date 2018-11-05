package com.jzfblog.service.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;

import com.jzfblog.dao.StudentDao;
import com.jzfblog.dao.impl.StudentDaoImpl;
import com.jzfblog.domain.PageBean;
import com.jzfblog.domain.Student;
import com.jzfblog.service.StudentService;
import com.jzfblog.util.JDBCUtil;

/**
 * 这是学生业务实现
 * @author 蒋振飞
 *
 */
public class StudentServiceImpl implements StudentService{

	StudentDaoImpl dao = new StudentDaoImpl();
	
	/**
	 * 封装分页的该页数据
	 */
	@Override
	public PageBean findStudentPage(int currentPage) throws SQLException {
		
		PageBean<Student> pageBean = new PageBean<Student>();
		
		// 当前页数
		pageBean.setCurrentPage(currentPage);
		// 每页的记录数
		int pageSize = StudentDao.PAGE_SIZE;
		pageBean.setPageSize(pageSize);
		// 当前页的学生对象
		List<Student> list = dao.findStudentPage(currentPage);
		pageBean.setList(list);
		// 总记录个数
		int count = dao.findCount();
		pageBean.setTotalSize(count);
		// 总页数，使用三元运算符
		int totalPage = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;
		pageBean.setTotalPage(totalPage);
		return pageBean;
	}

	/**
	 * 查询所有学生
	 */
	@Override
	public List<Student> findAll() throws SQLException {
		return dao.findAll();
	}
	
	/**
	 * 查找一个学生
	 */
	@Override
	public Student findStudentById(int sid) throws SQLException {
		return dao.findStudentById(sid);
	}

	/**
	 * 添加学生
	 */
	@Override
	public void insert(Student student) throws SQLException {
		dao.insert(student);
	}

	/**
	 * 从数据库中删除学生
	 */
	@Override
	public void delete(int sid) throws SQLException {
		dao.delete(sid);
	}

	/**
	 * 更新学生信息
	 */
	@Override
	public void update(Student student) throws SQLException {

		dao.update(student);
	}
	
	/**
	 * 模糊查询
	 */
	@Override
	public List<Student> searchStudent(String sname, String gender) throws SQLException {
		return dao.searchStudent(sname, gender);
	}

	

	
}
