package com.shinhan.emp;

import java.util.List;

// Service : business logic 수행
// 1. 이체 업무 : (인출, 입금)
// 2. 비밀번호 암호화
public class EmpService {

	EmpDAO empDAO = new EmpDAO();
	
	public EmpDTO execute_sp(int empid) {
		return empDAO.execute_sp(empid);
	}
	
	public int empUpdate(EmpDTO emp) {
		return empDAO.empUpdate(emp);
	}
	
	public int empInsert(EmpDTO emp) {
		return empDAO.empInsert(emp);
	}
	
	public int empDeleteById(int empid) {
		return empDAO.empDeleteById(empid);
	}
	
	public List<EmpDTO> selectByCondition(int deptid, String jobid, int salary, String hdate) {
		return empDAO.selectByCondition(deptid, jobid, salary, hdate);
	}
	
	public List<EmpDTO> selectByJobAndDept(String jobid, int deptid) {
		// 직책, 부서 조회
		return empDAO.selectByJobAndDept(jobid, deptid);
	}
	
	public List<EmpDTO> selectByJob(String jobid) {
		// 직책코드로 직원 조회
		return empDAO.selectByJob(jobid);
	}
	
	public List<EmpDTO> selectByDept(int deptid) {
		// 부서로 직원 조회
		return empDAO.selectByDept(deptid);
	}
	
	public EmpDTO selectById(int empid) {
		// 직원번호로 직원 조회
		return empDAO.selectById(empid);
	}
	
	public List<EmpDTO> selectAll() {
		// .........
		return empDAO.selectAll();
	}
	
}
