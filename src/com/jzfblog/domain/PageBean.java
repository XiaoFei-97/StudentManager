package com.jzfblog.domain;

import java.util.List;

/**
 * 用于封装了分页的数据
 * 里面包含：
 * 		该页的学生集合数据
 * 		总的记录数
 * 		总的页数
 * 		当前页
 * 		每页显示的记录数
 * @author 蒋振飞
 *
 */
public class PageBean<T> {
	/**
	 * 为了配合代码的高可用性，这里使用到了泛型
	 * 方便显示日后如老师分页，课程分页等等
	 */
	private int currentPage; // 当前页
	private int totalPage; // 总页数
	private int totalSize; // 总的记录数
	private int pageSize; // 每页的记录数
	private List<Student> list; // 当前页的学生集合
	
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getTotalSize() {
		return totalSize;
	}
	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public List<Student> getList() {
		return list;
	}
	public void setList(List<Student> list) {
		this.list = list;
	}
	
	
}
