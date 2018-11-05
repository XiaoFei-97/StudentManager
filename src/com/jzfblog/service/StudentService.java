package com.jzfblog.service;

import java.sql.SQLException;
import java.util.List;

import com.jzfblog.domain.PageBean;
import com.jzfblog.domain.Student;

/**
 * 这是学生的业务处理规则
 * @author 蒋振飞
 *
 */
public interface StudentService {
	
	/**
	 * 查询当页的数据
	 * @param currentPage
	 * @return
	 * @throws SQLException
	 */
	PageBean findStudentPage(int currentPage) throws SQLException;
	
	
	/**
	 * 查询所有学生
	 * @return List<Student>
	 */
	List<Student> findAll() throws SQLException;
	
	/**
	 * 根据ID查询单个学生对象
	 * @param sid
	 * @return
	 * @throws SQLException
	 */
	Student findStudentById(int sid) throws SQLException;
	
	/**
	 * 添加学生
	 * @param student
	 * @throws SQLException
	 */
	void insert(Student student) throws SQLException;

	/**
	 * 删除学生
	 * @param sid
	 * @throws SQLException
	 */
	void delete(int sid) throws SQLException;
	
	/**
	 * 更新学生信息
	 * @param student
	 * @throws SQLException
	 */
	void update(Student student) throws SQLException;
	
	/**
	 * 模糊查询
	 * @param sname 学生姓名
	 * @param gender 学生性别
	 * @return 学生
	 * @throws SQLException
	 */
	List<Student> searchStudent(String sname, String gender) throws SQLException;
}
