package com.jzfblog.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.management.Query;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.jzfblog.dao.StudentDao;
import com.jzfblog.domain.Student;
import com.jzfblog.util.JDBCUtil;
import com.jzfblog.util.TextUtils;

/**
 * 这是StudentDao的实现，针对前面定义的规范，作出具体的实现
 * @author 蒋振飞
 *
 */

public class StudentDaoImpl implements StudentDao{
	
	/**
	 * 查询结果的总记录个数
	 */
	@Override
	public int findCount() throws SQLException {
		QueryRunner queryRunner = new QueryRunner(JDBCUtil.getDataSource());
		String sql = "select count(*) from stu";
		// 注意：返回数值使用scalar，而不能继续使用BeanListHandler或者BeanHandler
		Long result =  (Long) queryRunner.query(sql, new ScalarHandler());
		// 将long类型转化为int类型
		return result.intValue();
	}
	
	/**
	 * 学生的分页显示
	 */
	@Override
	public List<Student> findStudentPage(int currentPage) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(JDBCUtil.getDataSource());
		String sql = "select * from stu limit ? offset ?";
		return queryRunner.query(sql, 
				new BeanListHandler<Student>(Student.class), 
				PAGE_SIZE, (currentPage - 1)*PAGE_SIZE);
	}
	
	/**
	 * 查询所有学生
	 * @throws SQLException 
	 */
	@Override
	public List<Student> findAll() throws SQLException {
		// 1.创建query对象
		QueryRunner queryRunner = new QueryRunner(JDBCUtil.getDataSource());
		// 2.生成sql语句
		String sql = "select * from stu";
		// 3.返回查询对象
		List<Student> list = queryRunner.query(sql, new BeanListHandler<Student>(Student.class));
		return list;
	}
	
	/**
	 * 查询单个学生对象
	 */
	@Override
	public Student findStudentById(int sid) throws SQLException {
		
		QueryRunner queryRunner = new QueryRunner(JDBCUtil.getDataSource());
		// 查询sql语句
		String sql = "select * from stu where sid=?";
		return queryRunner.query(sql, new BeanHandler<Student>(Student.class), sid);
			
	}
	
	/**
	 * 添加学生的处理功能
	 * @throws SQLException 
	 * 
	 */
	@Override
	public void insert(Student student) throws SQLException {

		QueryRunner queryRunner = new QueryRunner(JDBCUtil.getDataSource());
		
		String sql = "insert into stu values(null,?,?,?,?,?,?)";
		queryRunner.update(sql, 
				student.getSname(),
				student.getGender(),
				student.getPhone(),
				student.getBirthday(),
				student.getHobby(),
				student.getInfo()
				);
	}

	/**
	 * 从数据库中删除学生
	 */
	@Override
	public void delete(int sid) throws SQLException {
		
		QueryRunner queryRunner = new QueryRunner(JDBCUtil.getDataSource());
		String sql = "delete from stu where sid=?";
		queryRunner.update(sql, sid);
	}

	/**
	 * 修改学生信息
	 */
	@Override
	public void update(Student student) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(JDBCUtil.getDataSource());
		String sql = "update stu set sname=?, gender=?, phone=?, birthday=?, hobby=?, info=? where sid=?";
		queryRunner.update(sql,
				student.getSname(),
				student.getGender(),
				student.getPhone(),
				student.getBirthday(),
				student.getHobby(),
				student.getInfo(),
				student.getSid()
				);
	}

	/**
	 * 模糊查询
	 */
	@Override
	public List<Student> searchStudent(String sname, String gender) throws SQLException {
		
		QueryRunner queryRunner = new QueryRunner(JDBCUtil.getDataSource());
		// String sql = "select * from stu where sname like ? and gender =?";
		
		// 这么写的做法是为了避免出现执行两条where语句
		 String sql = "select * from stu where 1=1";
		 List<String> list = new ArrayList<String>();
		 
		 // 判断有没有姓名，如果有，组拼到sql语句中
		 if(!TextUtils.isEmpty(sname)){
			 sql = sql + " and sname like ?";
			 list.add("%" + sname + "%");
		 }
		 
		 // 判断有没有性别，如果有，组拼到sql语句中
		 if(!TextUtils.isEmpty(gender)){
			 sql = sql + " and gender = ?";
			 list.add(gender);
		 }

		return queryRunner.query(sql, new BeanListHandler<Student>(Student.class), list.toArray());
	}



	

}
