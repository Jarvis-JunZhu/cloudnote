package test;


import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.tedu.cloudnote.dao.AssociationDao;
import com.tedu.cloudnote.dao.BookDao;
import com.tedu.cloudnote.dao.CollectionDao;
import com.tedu.cloudnote.dao.EmpDao;
import com.tedu.cloudnote.dao.NoteDao;
import com.tedu.cloudnote.dao.UserDao;
import com.tedu.cloudnote.entity.Book;
import com.tedu.cloudnote.entity.Emp;
import com.tedu.cloudnote.entity.Note;
import com.tedu.cloudnote.entity.User;
import com.tedu.cloudnote.service.UserService;
import com.tedu.cloudnote.util.NoteResult;

public class TestCase {
	//DataSourceTransactionManager
	private ApplicationContext ctx;
	@Before
	public void init(){
		String[] conf = {"conf/spring-mvc.xml","conf/spring-mybatis.xml"};
		ctx = new ClassPathXmlApplicationContext(conf);
	}
	
	@Test
	public void test1(){
		UserDao dao = ctx.getBean("userDao",UserDao.class);
		User user = dao.findByName("zhouj");
		System.out.println(user.getCn_user_password());
	}
	
	@Test
	public void test2(){
		UserService service = ctx.getBean("userService",UserService.class);
		NoteResult rs = service.checkLogin("zhouj", "55587a910882016321201e6ebbc9f595");
		System.out.println(rs.getStatus());
		System.out.println(rs.getMsg());
		System.out.println(rs.getData());
	}
	@Test
	public void test3(){
		UserService service = ctx.getBean("userService",UserService.class);
		NoteResult rs = service.addUser("zhujun", "��ѧ�ľ���", "1234567");
		System.out.println(rs.getMsg());
		System.out.println(rs.getStatus());
	}
	
	@Test
	public void test4(){
		BookDao dao = ctx.getBean("bookDao",BookDao.class);
		System.out.println(dao.findByUserId("52f9b276-38ee-447f-a3aa-0d54e7a736e4"));
	}
	
	@Test
	public void test5(){
		NoteDao dao = ctx.getBean("noteDao",NoteDao.class);
		//创建查询条件params
		Map<String,Object> params = new HashMap<String,Object>();
		//params.put("title", "%1%");
		params.put("status", "1");
		String s1 = "2016-07-01";
		Date beginDate = Date.valueOf(s1);
		Long begin = beginDate.getTime();
		params.put("begin", begin);
//		params.put("end", value);
		List<Note> list = dao.findNotes(params);
		for(Note note:list){
			System.out.println(note.getCn_note_title());
		}
	}
	
	@Test
	public void testBatchDelete(){
		NoteDao dao = ctx.getBean("noteDao",NoteDao.class);
		String[] ids = {
				"e3164ed5-025c-4e3d-bf97-635c18aec845",
				"e361d00f-e9e2-457a-bd1a-8b7d60e96c74",
				"e410fc11-0fe3-44b4-9972-347279a46cc2",
				"e42e03b3-287d-4a44-95fa-498eca565a35",
				"e5a7bced-bbe8-4fc9-add7-2eaa9201a464"
		};
		int n = dao.batchDelete(ids);
		System.out.println("删除的记录行数："+n);
	}
	
	@Test
	public void test6(){
		AssociationDao dao = ctx.getBean("associationDao",AssociationDao.class);
		Book book = dao.findById("fe9c6b15bbdc46d5a3af5e33a19bafe4");
		System.out.println(book.getCn_notebook_name());
		System.out.println(book.getUser().getCn_user_name());
		List<Book> books = dao.findAllBook();
		for(Book book1:books){
			System.out.print(book1.getCn_notebook_name()+" *** ");
			System.out.println(book1.getUser().getCn_user_name());
		}
	}
	
	@Test
	public void test7(){
		CollectionDao dao = ctx.getBean("collectionDao",CollectionDao.class);
		User user = dao.findById("ea09d9b1-ede7-4bd8-b43d-a546680df00b");
		List<Book> books = user.getBooks();
		System.out.println(user.getCn_user_name()+":");
		for(Book book:books){
			System.out.println(book.getCn_notebook_name());
		}
	}
	
	@Test
	public void test8(){
		CollectionDao dao = ctx.getBean("collectionDao",CollectionDao.class);
		List<User> users = dao.findAllUser();
		for(User user:users){
			System.out.print(user.getCn_user_name()+":");
			List<Book> books = user.getBooks();
			for(Book book:books){
				System.out.print(book.getCn_notebook_name()+" | ");
			}
			System.out.println();
		}
	}
	
	@Test
	public void test9(){
		EmpDao dao = ctx.getBean("empDao",EmpDao.class);
		Emp emp = new Emp();
		emp.setName("rose");
		dao.save(emp);
		//获取记录的no值
		System.out.println(emp.getNo());
	}
}


















