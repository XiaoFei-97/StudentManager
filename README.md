## 学生信息管理系统

## 数据库准备
```
CREATE DATABASE stus;
USE stus;
CREATE TABLE stu (
	sid INT PRIMARY KEY  AUTO_INCREMENT,
	sname VARCHAR (20),
	gender VARCHAR (5),
	phone VARCHAR (20),
	birthday DATE,
	hobby VARCHAR(50),
	info VARCHAR(200)
);
```
## 查询

1.    先写一个JSP 页面， 里面放一个超链接 。 
```
<a href="StudentListServlet"> 学生列表显示</a>
```
2.    写Servlet， 接收请求， 去调用 Service  , 由service去调用dao

3.    先写Dao , 做Dao实现。
```java
public interface StudentDao {

	/**
	*  查询所有学生
	* @return  List<Student>
	*/
	List<Student> findAll()  throws SQLException ;
}

---------------------------------------------

public class StudentDaoImpl implements StudentDao {

	/**
	 * 查询所有学生
	 * @throws SQLException 
	 */
	@Override
	public List<Student> findAll() throws SQLException {
		QueryRunner runner = new QueryRunner(JDBCUtil02.getDataSource());
		return runner.query("select * from stu", new BeanListHandler<Student>(Student.class));
	}

}	
```

4. 再Service , 做Service的实现。
```java
/**
 * 这是学生的业务处理规范
 * @author xiaomi
 *
 */
public interface StudentService {

	/**
	 * 查询所有学生
	 * @return  List<Student>
	 */
	List<Student> findAll()  throws SQLException ;

}

------------------------------------------

/**
 * 这是学生业务实现
 * @author xiaomi
 *
 */
public class StudentServiceImpl implements StudentService{

	@Override
	public List<Student> findAll() throws SQLException {
		StudentDao dao = new StudentDaoImpl();
		return dao.findAll();
	}
}
```

5. 在servlet 存储数据，并且做出页面响应。
```java
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
	try {
		//1. 查询出来所有的学生
		StudentService service = new StudentServiceImpl();
		List<Student> list = service.findAll();

		//2. 先把数据存储到作用域中
		request.setAttribute("list", list);
		//3. 跳转页面
		request.getRequestDispatcher("list.jsp").forward(request, response);

	} catch (SQLException e) {
		e.printStackTrace();
	}

}
```
6. 在list.jsp上显示数据

   EL + JSTL  + 表格


## 增加 

1. 先跳转到增加的页面 ， 编写增加的页面

```html
<h2 align="center">添加学生界面</h2>
<form action="AddStudentServlet" method="post">
	<table border="1" width="400" align="center">
		<tr>
			<td>姓名</td>
			<td><input type="text" name="name"/></td>
		</tr>
		<tr>
			<td>性别</td>
			<td>
				<input type="radio" name="gender" value="男" checked="checked"/>男
				<input type="radio" name="gender" value="女"/>女
			</td>
		</tr>
		<tr>
			<td>电话</td>
			<td><input type="text" name="phone"/></td>
		</tr>
		<tr>
			<td>生日</td>
			<td><input type="text" name="birthday"/></td>
		</tr>
		<tr>
			<td>爱好</td>
			<td>
				<input type="checkbox" name="hobby" value="游泳"/>游泳
				<input type="checkbox" name="hobby" value="篮球"/>篮球
				<input type="checkbox" name="hobby" value="足球"/>足球
				<input type="checkbox" name="hobby" value="看书"/>看书
				<input type="checkbox" name="hobby" value="鞋子"/>写字				
			</td>
		</tr>
		<tr>
			<td>简介</td>
			<td><input type="text" name="info"/></td>
		</tr>
		<tr align="right">
			<td colspan="2"><input type="submit" value="添加"/></td>
		</tr>
	</table>
</form>
```

2. 点击添加，提交数据到AddServlet . 处理数据。
```java
try {
	// 1.获取客户端提交上来的数据
	String sname = request.getParameter("name");
	String gender = request.getParameter("gender");
	String phone = request.getParameter("phone");
	String birthday = request.getParameter("birthday");
	// hobby需要对应的多个
	String[] h = request.getParameterValues("hobby");
	String hobby= Arrays.toString(h);
	hobby = hobby.substring(1, hobby.length() - 1);
	String info = request.getParameter("info");

	// 2.添加到数据库
	StudentServiceImpl service = new StudentServiceImpl();
	Student student = new Student();
	// 注意日期这里需要parse一下
	Date date = null;
	date = new SimpleDateFormat("yyyy-MM-dd").parse(birthday);

	student.setSname(sname);
	student.setGender(gender);
	student.setPhone(phone);
	student.setBirthday(date);
	student.setHobby(hobby);
	student.setInfo(info);

	service.insert(student);
	// 3.跳转到列表页
	// servlet除了能跳转jsp，还能跳转servlet
	// request.getRequestDispatcher("list.jsp").forward(request, response);
	request.getRequestDispatcher("StudentListServlet").forward(request, response);
} catch (Exception e) {
	// TODO Auto-generated catch block
	e.printStackTrace();	
}
```
3. 调用service

4. 调用dao, 完成数据持久化。
```java
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
```
5. 完成了这些存储工作后，需要跳转到列表页面。 这里不能直接跳转到列表页面，否则没有什么内容显示。 应该先跳转到查询所有学生信息的那个Servlet， 由那个Servlet再去跳转到列表页面。

6. 爱好的value 值有多个。

```java
request.getParameter("hobby");
String[] hobby = 	request.getParameterValues("hobby") ---> String[] 
String value = Arrays.toString(hobby): // [爱好， 篮球， 足球]
```

### 删除

1. 点击超链接，弹出一个询问是否删除的对话框，如果点击了确定，那么就真的删除。

```html
	<a href="#" onclick="doDelete(${stu.sid})">删除</a>
```

2. 让超链接，执行一个js方法

```javascript
<script type="text/javascript">

	function doDelete(sid) {
		/* 如果这里弹出的对话框，用户点击的是确定，就马上去请求Servlet。 
		如何知道用户点击的是确定。
		如何在js的方法中请求servlet。 */
		var flag = confirm("是否确定删除?");
		if(flag){
			//表明点了确定。 访问servlet。 在当前标签页上打开 超链接，
			//window.location.href="DeleteServlet?sid="+sid;
			location.href="DeleteServlet?sid="+sid;
		}
	}
</script>
```

3. 在js访问里面判断点击的选项，然后跳转到servlet。

4. servlet收到了请求，然后去调用service ， service去调用dao
```java
/**
 * 从数据库中删除学生
 */
@Override
public void delete(int sid) throws SQLException {

	QueryRunner queryRunner = new QueryRunner(JDBCUtil.getDataSource());
	String sql = "delete from stu where sid=?";
	queryRunner.update(sql, sid);
}
```

## 更新

1. 点击列表上的更新， 先跳转到一个EditServlet 

> 在这个Servlet里面，先根据ID 去查询这个学生的所有信息出来。
```java
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
```
2. 跳转到更新的页面。 ，然后在页面上显示数据

```html
<tr>
<td>姓名</td>
<td><input type="text" name="sname" value="${stu.sname }"></td>
</tr>


<tr>
<td>性别</td>
<td>
	<!-- 如果性别是男的，  可以在男的性别 input标签里面， 出现checked ,
	如果性别是男的，  可以在女的性别 input标签里面，出现checked -->
	<input type="radio" name="gender" value="男" <c:if test="${stu.gender == '男'}">checked</c:if>>男
	<input type="radio" name="gender" value="女" <c:if test="${stu.gender == '女'}">checked</c:if>>女
</td>
</tr>


<tr>
<td>爱好</td>


<td>
	<!-- 爱好： 篮球 ， 足球 ， 看书 
	因为爱好有很多个，  里面存在包含的关系 -->
	<input type="checkbox" name="hobby" value="游泳" <c:if test="${fn:contains(stu.hobby,'游泳') }">checked</c:if>>游泳
	<input type="checkbox" name="hobby" value="篮球" <c:if test="${fn:contains(stu.hobby,'篮球') }">checked</c:if>>篮球
	<input type="checkbox" name="hobby" value="足球" <c:if test="${fn:contains(stu.hobby,'足球') }">checked</c:if>>足球
	<input type="checkbox" name="hobby" value="看书" <c:if test="${fn:contains(stu.hobby,'看书') }">checked</c:if>>看书
	<input type="checkbox" name="hobby" value="写字" <c:if test="${fn:contains(stu.hobby,'写字') }">checked</c:if>>写字

</td>
</tr>
```

3. 修改完毕后，提交数据到UpdateServlet

> 提交上来的数据是没有带id的，所以我们要手动创建一个隐藏的输入框， 在这里面给定id的值， 以便提交表单，带上id。 
```html
<form method="post" action="UpdateServlet">
	<input type="hidden" name="sid" value="${stu.sid }">

	...
</form>
```

4. 获取数据，调用service， 调用dao.
```java
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
```
## 模糊查询

1.查询页面
```html
<tr>
	<td colspan="8">
		按姓名查询：<input type="text" name="sname" />
		按姓名查询：<select name="gender">
					<option value="">--请选择--
					<option value="男">男
					<option value="女">女
				</select>

		&nbsp;&nbsp;<input type="submit" value="查询" />
		<button><a href="add.jsp">添加</a></button>
	</td>
</tr>
```

2.dao实现
```java
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
```

## 分页功能

1.dao实现
```java
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
```

2.创建page_list.jsp文件，用于分页显示
```html
<tr>
	<td colspan="8">
		<span>第${pageBean.currentPage }/${pageBean.totalPage }</span>
		<span>每页显示${pageBean.pageSize }条</span>&nbsp;&nbsp;
		<span>总记录数${pageBean.totalSize }</span>&nbsp;&nbsp;

		<c:if test="${pageBean.currentPage != 1 }">
			<a href="StudentListPageServlet?currentPage=1">首页</a>
			<a href="StudentListPageServlet?currentPage=${pageBean.currentPage - 1 }">上一页</a>
		</c:if>

		<c:forEach begin="1" end="${pageBean.totalPage }" var="i">
			<c:if test="${pageBean.currentPage == i }">${i }</c:if>
			<c:if test="${pageBean.currentPage != i }">						
				<a href="StudentListPageServlet?currentPage=${i }">${i }</a>
			</c:if>
		</c:forEach>

		<c:if test="${pageBean.currentPage != pageBean.totalPage }">
			<a href="StudentListPageServlet?currentPage=${pageBean.currentPage + 1 }">下一页</a>
			<a href="StudentListPageServlet?currentPage=${pageBean.totalPage}">尾页</a>
		</c:if>
	</td>
</tr>
```

3.组拼pageBean
```java
/**
 * 用于封装了分页的数据
 * 里面包含：
 * 		该页的学生集合数据
 * 		总的记录数
 * 		总的页数
 * 		当前页
 * 		每页显示的记录数
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
}
-----------------------------------------
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
```

4.实现需要用到的dao
```java
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
------------------------------------------
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
```

* 物理分页 （真分页）

> 来数据库查询的时候，只查一页的数据就返回了。  

 	优点 内存中的数据量不会太大
	缺点：对数据库的访问频繁了一点。
	
```
SELECT * FROM stu LIMIT	5 OFFSET 2 
```

* 逻辑分页 （假分页）

> 一口气把所有的数据全部查询出来，然后放置在内存中。 

	优点： 访问速度快。
	缺点： 数据库量过大，内存溢出。
	

