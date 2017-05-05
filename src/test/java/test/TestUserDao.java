package test;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.tedu.cloudnote.dao.UserDao;
import com.tedu.cloudnote.entity.User;

public class TestUserDao {
	@Test
	public void test1() throws SQLException{
		String[] conf = {"conf/spring-mvc.xml","conf/spring-mybatis.xml"};
		ApplicationContext ac = new ClassPathXmlApplicationContext(conf);
		//获取DataSource
		DataSource ds = ac.getBean("dbcp",DataSource.class);
		Connection con = ds.getConnection();
		System.out.println(con);
		con.close();
		//获取SqlSessionFactory
		SqlSessionFactory ssf = ac.getBean("ssf",SqlSessionFactory.class);
		System.out.println(ssf.openSession());
		//获取UserDao
		UserDao dao = ac.getBean("userDao",UserDao.class);
		User user = dao.findByName("demo");
		if(user == null){
			System.out.println("用户不存在");
		}else{
			System.out.println(user.getCn_user_password());
		}
		
	}
}
