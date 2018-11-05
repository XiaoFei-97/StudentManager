package com.jzfblog.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class JDBCUtil {
	static ComboPooledDataSource dataSource = null;
	static{
		dataSource  = new ComboPooledDataSource();
	}
	
	/**
	 * 获取dataSource数据源
	 * @return dataSource
	 */
	public static DataSource getDataSource() {
		return dataSource;
	}
	
	/**
	 * 获取连接对象	
	 * @return
	 * @throws SQLException 
	 */
	public static Connection getConn() throws SQLException {
		
		return dataSource.getConnection();
	}
	
	/**
	 * 释放资源
	 * @param rs
	 * @param st
	 * @param conn
	 */
	
	public static void release(ResultSet rs, Statement st, Connection conn) {
		closeRs(rs);
		closeSt(st);
		closeConn(conn);
	}
	
	private static void closeRs(ResultSet rs) {
		try {
			if(rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			rs = null;
		}
	}
	
	private static void closeSt(Statement st) {
		try {
			if(st != null) {
				st.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			st = null;
		}
	}
	
	private static void closeConn(Connection conn) {
		try {
			if(conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			conn = null;
		}
	}
}
