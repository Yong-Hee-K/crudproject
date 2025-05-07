package com.shinhan.day15;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CRUDTest2 {

	public static void main(String[] args) throws SQLException {
		// 모두 성골하면 commit
		// 하나라도 실패하면 rollback
		// insert
		// update
		Connection conn = null;
		Statement st1 = null;
		Statement st2 = null;
		String sql1 = """
				insert into emp1 
				(employee_id, first_name, last_name, email, hire_date, job_id)
				 values(8, 'aa', 'bb', 'asd', sysdate, 'IT')
				""";
		String sql2 = """
				update emp1 set salary = 3000 where employee_id = 198
				""";
		
		conn = DBUtil.getConnection();
		conn.setAutoCommit(false);
		st1 = conn.createStatement();
		st2 = conn.createStatement();
		
		int result1 = st1.executeUpdate(sql1);
		int result2 = st2.executeUpdate(sql2);
		
		if(result1 >= 1 && result2 >= 1) {
			conn.commit();
		} else conn.rollback();
	}
	
	
	public static void f3(String[] args) throws SQLException {
		Connection conn = null;
		Statement st = null;
		int result = 0;
		String sql = """
			delete from emp1
			where employee_id = 1
				""";
		conn = DBUtil.getConnection();
		st = conn.createStatement();
		result = st.executeUpdate(sql);
		System.out.println(result >= 0 ? "Delete Success" : "Delete Fail");
	}
	
	
	
	
	public static void f2(String[] args) throws SQLException {
		Connection conn = null;
		Statement st = null;
		int result = 0;
		String sql = """
			update emp1
				set department_id = (select department_id
                    				from emp1
									where employee_id = 102), 
					salary = (select salary
							 from emp1
							where employee_id =101)
					where employee_id = 1
				""";
		conn = DBUtil.getConnection();
		st = conn.createStatement();
		result = st.executeUpdate(sql);
		System.out.println(result >= 1 ? "Update Success" : "update Fail");
	}
	
	public static void f1(String[] args) throws SQLException {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		
		String sql = """
				select ename, sal, mgr
				from emp
				where mgr = (
				        	select empno 
				        	from emp
				        	where ename = 'KING')
				""";
		
		conn = DBUtil.getConnection();
		st = conn.createStatement();
		rs = st.executeQuery(sql);
		while(rs.next()) {
			String a = rs.getString(1);
			int b = rs.getInt(2);
			int c = rs.getInt(3);
			System.out.println(a + "\t" + b + "\t" + c);
		}
		
		DBUtil.dbDisconnect(conn, st, rs);
	}

}
