package com.shinhan.emp;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.shinhan.day15.DBUtil;




// DAO(Data Access Object) : DB에 CRUD작업(Select, Insert, Update, Delete)
// Statement : SQL문을 보내는 통로, 바인딩 변수를 지원하지 않음
// PreparedStatement : Statement 상속받음, 바인딩 변수 지원, sp 호출 지원 x
// CallableStatement : sp 호출 지원
public class EmpDAO {
	
	// Stored Procedure 실행 (직원번호를 받아서 이메일과 급여를 return)
	public EmpDTO execute_sp(int empid) {
		EmpDTO emp = null;
		Connection conn = DBUtil.getConnection();
	 	CallableStatement st = null;
		String sql = "{call sp_empinfo2(?, ?, ?)}";
	 	try {
			st = conn.prepareCall(sql);
			st.setInt(1, empid);
			st.registerOutParameter(2, Types.VARCHAR);
			st.registerOutParameter(3, Types.DECIMAL);
			
			boolean result = st.execute();
//			if(result) {
			emp = new EmpDTO();
			String email = st.getString(2);
			double salary = st.getDouble(3);
			emp.setEmail(email);
			emp.setSalary(salary);
//			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 	return emp;
	}
	
	
	// 수정2
			public int empUpdate2(EmpDTO emp) {
				int result = 0;
				Connection conn = DBUtil.getConnection();
			 	PreparedStatement st = null; 
			 	
			 	Map<String, Object> dynamicSQL = new HashMap<>();
				 	
			 	if(emp.getFirst_name() != null) dynamicSQL.put("FIRST_NAME", emp.getFirst_name());
			 	if(emp.getLast_name() != null) dynamicSQL.put("LAST_NAME", emp.getLast_name());
			 	if(emp.getEmail() != null) dynamicSQL.put("EMAIL", emp.getEmail());
			 	if(emp.getPhone_number() != null) dynamicSQL.put("PHONE_NUMBER", emp.getPhone_number());
			 	if(emp.getHire_date() != null) dynamicSQL.put("HIRE_DATE", emp.getHire_date());
			 	if(emp.getJob_id() != null) dynamicSQL.put("JOB_ID", emp.getJob_id());
			 	if(emp.getSalary() != null) dynamicSQL.put("SALARY", emp.getSalary());
			 	if(emp.getCommission_pct() != null) dynamicSQL.put("COMMISSION_PCT", emp.getCommission_pct());
			 	if(emp.getManager_id() != null) dynamicSQL.put("MANAGER_ID", emp.getManager_id());
			 	if(emp.getDepartment_id() != null) dynamicSQL.put("DEPARTMNET_ID", emp.getDepartment_id());
				 	
			 	String sql = " update employees set ";
			 	String sql2 = " where EMPLOYEE_ID = ? ";		 	
			 	for(String key:dynamicSQL.keySet()) {
			 		sql += key + "=" + "?," ;  //salary=?,email=?, 		
			 	}
			 	sql = sql.substring(0, sql.length()-1);
			 	sql += sql2;
			 	System.out.println(sql);
			 	
				try {
					st = conn.prepareStatement(sql);
					int i=1;
					for(String key:dynamicSQL.keySet()) {
				 		st.setObject(i++, dynamicSQL.get(key));
				 	} 
					st.setInt(i, emp.getEmployee_id());
					result = st.executeUpdate();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				return result;
			}


	// 수정
			public int empUpdate(EmpDTO emp) {
				int result = 0;
				Connection conn = DBUtil.getConnection();
			 	PreparedStatement st = null; 
				ResultSet rs = null;
				String sql = """
						update employees set
								FIRST_NAME = ?,    
								LAST_NAME = ?,     
								EMAIL = ?,         
								PHONE_NUMBER = ?,  
								HIRE_DATE = ?,     
								JOB_ID = ?,        
								SALARY = ?,        
								COMMISSION_PCT = ?,
								MANAGER_ID = ?,    
								DEPARTMENT_ID = ?
						where EMPLOYEE_ID = ?
							""";
				
				try {
					st = conn.prepareStatement(sql); // SQL문 준비
					st.setInt(11, emp.getEmployee_id()); 
					st.setString(1, emp.getFirst_name()); 
					st.setString(2, emp.getLast_name()); 
					st.setString(3, emp.getEmail()); 
					st.setString(4, emp.getPhone_number()); 
					st.setDate(5, emp.getHire_date()); 
					st.setString(6, emp.getJob_id()); 
					st.setDouble(7, emp.getSalary()); 
					st.setDouble(8, emp.getCommission_pct()); 
					st.setInt(9, emp.getManager_id()); 
					st.setInt(10, emp.getDepartment_id()); 
					result = st.executeUpdate();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					DBUtil.dbDisconnect(conn, st, rs);
				}
				return result;
			}
	
	// 입력
		public int empInsert(EmpDTO emp) {
			int result = 0;
			Connection conn = DBUtil.getConnection();
		 	PreparedStatement st = null; 
			ResultSet rs = null;
			String sql = """
					insert into employees(
							EMPLOYEE_ID,   
							FIRST_NAME,    
							LAST_NAME,     
							EMAIL,         
							PHONE_NUMBER,  
							HIRE_DATE,     
							JOB_ID,        
							SALARY,        
							COMMISSION_PCT,
							MANAGER_ID,    
							DEPARTMENT_ID)
						values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
						""";
			
			try {
				st = conn.prepareStatement(sql); // SQL문 준비
				st.setInt(1, emp.getEmployee_id()); // 1번째 ?에 값을 Setting
				st.setString(2, emp.getFirst_name()); // 2번째 ?에 값을 Setting
				st.setString(3, emp.getLast_name()); // 3번째 ?에 값을 Setting
				st.setString(4, emp.getEmail()); // 4번째 ?에 값을 Setting
				st.setString(5, emp.getPhone_number()); // 5번째 ?에 값을 Setting
				st.setDate(6, emp.getHire_date()); // 6번째 ?에 값을 Setting
				st.setString(7, emp.getJob_id()); // 7번째 ?에 값을 Setting
				st.setDouble(8, emp.getSalary()); // 8번째 ?에 값을 Setting
				st.setDouble(9, emp.getCommission_pct()); // 9번째 ?에 값을 Setting
				st.setInt(10, emp.getManager_id()); // 10번째 ?에 값을 Setting
				st.setInt(11, emp.getDepartment_id()); // 11번째 ?에 값을 Setting
				result = st.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				DBUtil.dbDisconnect(conn, st, rs);
			}
			return result;
		}
		
	// 삭제
		public int empDeleteById(int empid) {
			int result = 0;
			Connection conn = DBUtil.getConnection();
		 	PreparedStatement st = null; 
			ResultSet rs = null;
			String sql = """
					delete from employees
					where employee_id = ?
							""";
			
			try {
				st = conn.prepareStatement(sql); // SQL문 준비
				st.setInt(1, empid); // 1번째 ?에 값을 Setting
				result = st.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				DBUtil.dbDisconnect(conn, st, rs);
			}
			return result;
		}
	
	// 부서코드, 직책코드, 급이(이상), 입사일(이상) 조회
		public List<EmpDTO> selectByCondition(int deptid, String jobid, int salary, String hdate) {
			List<EmpDTO> emplist = new ArrayList<>();
			Connection conn = DBUtil.getConnection();
		 	PreparedStatement st = null; 
			ResultSet rs = null;
			String sql = """
							select * from employees
							where department_id = ?
							and job_id like ?
							and salary >= ?
							and hire_date >= ?
									""";
			
			try {
				st = conn.prepareStatement(sql); // SQL문 준비
				st.setInt(1, deptid); // 1번째 ?에 값을 Setting
				st.setString(2, "%"+jobid+"%"); // 2번째 ?에 값을 Setting
				st.setInt(3, salary); // 3번째 ?에 값을 Setting
				
				Date d = DateUtil.converToSQLDate(DateUtil.converToDate(hdate));
				st.setDate(4, d); // 4번째 ?에 값을 Setting
				rs = st.executeQuery();
				while(rs.next()) {
					EmpDTO emp = makeEmp(rs);
					emplist.add(emp);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				DBUtil.dbDisconnect(conn, st, rs);
			}
			return emplist;
			}
	
	// 직책코드, 부서코드로 직원 조회
		public List<EmpDTO> selectByJobAndDept(String jobid, int deptid) {
			List<EmpDTO> emplist = new ArrayList<>();
			Connection conn = DBUtil.getConnection();
		 	PreparedStatement st = null; 
			ResultSet rs = null;
			String sql = "select * from employees where job_id = ? and department_id = ?";
			
			try {
				st = conn.prepareStatement(sql); // SQL문 준비
				st.setString(1, jobid); // 1번째 ?에 값을 Setting
				st.setInt(2, deptid); // 2번째 ?에 값을 Setting
				rs = st.executeQuery();
				while(rs.next()) {
					EmpDTO emp = makeEmp(rs);
					emplist.add(emp);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				DBUtil.dbDisconnect(conn, st, rs);
			}
			return emplist;
			}
	
	// 직책코드로 직원 조회
		public List<EmpDTO> selectByJob(String jobid) {
			List<EmpDTO> emplist = new ArrayList<>();
			Connection conn = DBUtil.getConnection();
		 	PreparedStatement st = null; 
			ResultSet rs = null;
			String sql = "select * from employees where job_id = ? ";
			
			try {
				st = conn.prepareStatement(sql); // SQL문 준비
				st.setString(1, jobid); // 1번째 ?에 값을 Setting
				rs = st.executeQuery();
				while(rs.next()) {
					EmpDTO emp = makeEmp(rs);
					emplist.add(emp);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				DBUtil.dbDisconnect(conn, st, rs);
			}
			return emplist;
			}
	
	// 부서코드로 직원 조회
		public List<EmpDTO> selectByDept(int deptid) {
			List<EmpDTO> emplist = new ArrayList<>();
			Connection conn = DBUtil.getConnection();
			Statement st = null;
			ResultSet rs = null;
			String sql = "select * from employees where department_id = " + deptid;
			
			try {
				st = conn.createStatement();
				rs = st.executeQuery(sql);
				while(rs.next()) {
					EmpDTO emp = makeEmp(rs);
					emplist.add(emp);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				DBUtil.dbDisconnect(conn, st, rs);
			}
			return emplist;
			}
	
	// 직원번호로 직원정보를 상세보기
	public EmpDTO selectById(int empid) {
		EmpDTO emp = null;
		Connection conn = DBUtil.getConnection();
		Statement st = null;
		ResultSet rs = null;
		String sql = "select * from employees where employee_id = " + empid;
		
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			if(rs.next()) {
				emp = makeEmp(rs);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, st, rs);
		}
		return emp;
		}
	
	// 모든 직원 조회
	public List<EmpDTO> selectAll() {
		List<EmpDTO> emplist = new ArrayList<>();
		Connection conn = DBUtil.getConnection();
		Statement st = null;
		ResultSet rs = null;
		String sql = "select * from employees";
		
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()) {
				EmpDTO emp = makeEmp(rs);
				emplist.add(emp);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, st, rs);
		}
		return emplist;
		}

	private EmpDTO makeEmp(ResultSet rs) throws SQLException {
		EmpDTO emp = EmpDTO.builder()
				.employee_id(rs.getInt("employee_id"))
				.commission_pct(rs.getDouble("commission_pct"))
				.department_id(rs.getInt("department_id"))
				.email(rs.getString("email"))
				.first_name(rs.getString("first_name"))
				.hire_date(rs.getDate("hire_date"))
				.job_id(rs.getNString("job_id"))
				.last_name(rs.getString("last_name"))
				.manager_id(rs.getInt("manager_id"))
				.phone_number(rs.getNString("phone_number"))
				.salary(rs.getDouble("salary"))
				.build();
		
		return emp;
	}
	
}
