package com.jzfblog.dao;

import java.sql.SQLException;
import java.util.List;

import com.jzfblog.domain.Student;

/**
 * 针对学生表的访问
 * @author 蒋振飞
 *
 */
public interface StudentDao {
	
	// 一页显示5条数据 
	int PAGE_SIZE = 5;
	
	/**
	 * 查询结果的记录个数
	 * @return
	 * @throws SQLException
	 */
	int findCount() throws SQLException;
	
	/**
	 * 查询当页的学生数据
	 * @param currentPage
	 * @return
	 * @throws SQLException
	 */
	List<Student> findStudentPage(int currentPage) throws SQLException;
	
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
	 * @param student 需要添加到数据库的学生
	 */
	void insert(Student student) throws SQLException;
	
	/**
	 * 删除学生
	 * @param sid 学生编号
	 * @throws SQLException
	 */
	void delete(int sid) throws SQLException;
	
	/**
	 * 修改学生信息
	 * @param sid 学生编号
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
