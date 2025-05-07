package com.shinhan.emp;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

import com.shinhan.common.CommonControllerInterface;

// MVC2 모델
// FrontController -> Controller 선택 -> Service -> DAO -> DB
//                 <-                <-         <-     <-
public class EmpController implements CommonControllerInterface{
	
	static Scanner sc = new Scanner(System.in);
	static EmpService empService = new EmpService();
	
	public void execute() {
		boolean isStop = false;
		while(!isStop) {
			menuDisplay();
			int job = sc.nextInt();
			switch(job) {
			case 1 -> {f_selectAll();}
			case 2 -> {f_selectById();}
			case 3 -> {f_selectByDept();}
			case 4 -> {f_selectByJob();}
			case 5 -> {f_selectByJobAndDept();}
			case 6 -> {f_selectByCondition();}
			case 7 -> {f_empDeleteById();}
			case 8 -> {f_empInsert();}
			case 9 -> {f_empUpdate();}
			case 10 -> {f_sp_call();}
			case 99 -> {isStop = true;}
			}
		}
		System.out.println("------- Good Bye -------");
	}

	
	private static void f_sp_call() {
		System.out.println("조회할 직원 ID를 입력하세요 >> ");
		int employee_id = sc.nextInt();
		EmpDTO emp = empService.execute_sp(employee_id);
		String message = "해당 직원이 존재하지 않습니다.";
		if(emp != null) {
			message = emp.getEmail() + " --- " + emp.getSalary();
		}
		EmpView.display(message);
		
		
	}


	static EmpDTO makeEmp2(int employee_id) {
		System.out.print("first_name>>");
		String first_name = sc.next();
		System.out.print("last_name>>");
		String last_name = sc.next();
		System.out.print("email>>");
		String email = sc.next();
		System.out.print("phone_number>>");
		String phone_number = sc.next();
		System.out.print("hdate(yyyy-MM-dd)>>");
		String hdate = sc.next();
		Date hire_date = null;
		if(!hdate.equals("0"))
		   hire_date = DateUtil.converToSQLDate(DateUtil.converToDate(hdate));
		System.out.print("job_id(FK:IT_PROG)>>");
		String job_id = sc.next();
		System.out.print("salary>>");
		Double salary = sc.nextDouble();
		System.out.print("commission_pct(0.2)>>");
		Double commission_pct = sc.nextDouble();
		System.out.print("manager_id(FK:100)>>");
		Integer manager_id = sc.nextInt();
		System.out.print("department_id(FK:60,90)>>");
		Integer department_id = sc.nextInt();
		
		if(first_name.equals("0")) first_name = null;
		if(last_name.equals("0")) last_name = null;
		if(email.equals("0")) email = null;
		if(phone_number.equals("0")) phone_number = null;
		if(job_id.equals("0")) job_id = null;
		if(salary==0) salary = null;
		if(commission_pct==0) commission_pct = null;
		if(manager_id==0) manager_id = null;
		if(department_id==0) department_id = null;
		
		EmpDTO emp = EmpDTO.builder()
				.commission_pct(commission_pct)
				.department_id(department_id)
				.email(email)
				.employee_id(employee_id)
				.first_name(first_name)
				.hire_date(hire_date)
				.job_id(job_id)
				.last_name(last_name)
				.manager_id(manager_id)
				.phone_number(phone_number)
				.salary(salary)
				.build();
		return emp;
	}
	
	static EmpDTO makeEmp(int employee_id) {
		System.out.print("이름을 입력하세요 >> ");
		String first_name = sc.next();
		System.out.print("성을 입력하세요 >> ");
		String last_name = sc.next();
		System.out.print("이메일을 입력하세요 >> ");
		String email = sc.next();
		System.out.print("전화번호를 입력하세요 >> ");
		String phone_number = sc.next();
		System.out.print("입사일을 입력하세요 >> ");
		String hdate = sc.next();
		Date hire_date = DateUtil.converToSQLDate(DateUtil.converToDate(hdate));
		System.out.print("직책을 입력하세요 >> ");
		String job_id = sc.next();
		System.out.print("급여를 입력하세요 >> ");
		Double salary = sc.nextDouble();
		System.out.print("커미션을 입력하세요 >> ");
		Double commision_pct = sc.nextDouble();
		System.out.print("관리자의 직원 ID를 입력하세요 >> ");
		Integer manager_id = sc.nextInt();
		System.out.print("부서 코드를 입력하세요 >> ");
		Integer department_id = sc.nextInt();
		
		if(first_name.equals("0")) first_name = null;
		if(last_name.equals("0")) last_name = null;
		if(email.equals("0")) email = null;
		if(phone_number.equals("0")) phone_number = null;
		if(job_id.equals("0")) job_id = null;
		if(salary == 0) salary = null;
		if(commision_pct == 0) commision_pct = null;
		if(manager_id == 0) manager_id = null;
		if(department_id == 0) department_id = null;
		
		EmpDTO emp = EmpDTO.builder()
				.employee_id(employee_id)
				.first_name(first_name)
				.last_name(last_name)
				.email(email)
				.phone_number(phone_number)
				.hire_date(hire_date)
				.job_id(job_id)
				.salary(salary)
				.commission_pct(commision_pct)
				.manager_id(manager_id)
				.department_id(department_id)
				.build();
		
		return emp;
	}

	private static void f_empUpdate() {
		System.out.print("수정할 직원 ID를 입력하세요 >> ");
		int employee_id = sc.nextInt();
		EmpDTO exist_emp = empService.selectById(employee_id);
		if(exist_emp == null) {
			EmpView.display("존재하지 않는 직원입니다.");
			return;
		}
		EmpView.display("수정하려는 직원 정보입니다.");
		EmpView.display(exist_emp);
		int result = empService.empUpdate(makeEmp(employee_id));
		EmpView.display(result + "건 수정 되었습니다.");
	}


	private static void f_empInsert() {
		System.out.print("신규 직원 ID를 입력하세요 >> ");
		int employee_id = sc.nextInt();
		int result = empService.empInsert(makeEmp2(employee_id));
		EmpView.display(result + "건 입력 되었습니다.");
	}


	private static void f_empDeleteById() {
		System.out.print("삭제할 직원 ID를 입력하세요 >> ");
		int empid = sc.nextInt();
		int result = empService.empDeleteById(empid);
		EmpView.display(result + "건 삭제 되었습니다.");
	}


	private static void f_selectByCondition() {
		// = 부서, like 직책, >= 급여, >= 입사일
		System.out.print("조회할 부서 ID를 입력하세요 >> ");
		int deptid = sc.nextInt();
		System.out.print("조회할 직책 ID를 입력하세요 >> ");
		String jobid = sc.next();
		System.out.print("조회할 급여(이상)를 입력하세요 >> ");
		int salary = sc.nextInt();
		System.out.print("조회할 입사일(이상)을 입력하세요 >> ");
		String hdate = sc.next();
		
		List<EmpDTO> emplist = empService.selectByCondition(deptid, jobid, salary, hdate);
		EmpView.display(emplist);
	}


	private static void f_selectByJobAndDept() {
		System.out.print("조회할 직책 ID, 부서 ID를 입력하세요 >> ");
		String data = sc.next();
		String[] arr = data.split(",");
		String jobid = arr[0];
		int deptid = Integer.parseInt(arr[1]);
		List<EmpDTO> emplist = empService.selectByJobAndDept(jobid, deptid);
		EmpView.display(emplist);
	}


	private static void f_selectByJob() {
		System.out.print("조회할 직책 ID를 입력하세요 >> ");
		String jobid = sc.next();
		List<EmpDTO> emplist = empService.selectByJob(jobid);
		EmpView.display(emplist);
	}


	private static void f_selectByDept() {
		System.out.print("조회할 부서 ID를 입력하세요 >> ");
		int deptid = sc.nextInt();
		List<EmpDTO> emplist = empService.selectByDept(deptid);
		EmpView.display(emplist);
	}


	private static void f_selectById() {
		System.out.print("조회할 ID를 입력하세요 >> ");
		int empid = sc.nextInt();
		EmpDTO emp = empService.selectById(empid);
		EmpView.display(emp);
	}


	private static void f_selectAll() {
		List<EmpDTO> emplist = empService.selectAll();
		EmpView.display(emplist);
	}


	private static void menuDisplay() {
		System.out.println("----------------------------------------------------------------");
		System.out.println("1. 조회(전체) 2. 조회(직원번호) 3. 조회(부서) 4. 조회(직책) 5. 조회(직책, 부서)");
		System.out.println("6. 조회(컨디션) 7. 정보 삭제 8. 정보 입력 9. 정보 수정 10. SP 호출   99. 종료");
		System.out.println("----------------------------------------------------------------");
		System.out.print("작업을 선택하세요 >> ");
		
	}
}
