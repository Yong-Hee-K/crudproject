package com.shinhan.common;

import java.util.Scanner;

import javax.naming.ldap.ControlFactory;

import com.shinhan.dept.DeptController;
import com.shinhan.emp.EmpController;


// FrontController Pattern : Controller가 여러 개일 경우 하나로 통합하는 것(Front는 1개)
// Servlet : DispatcherServle
public class FrontController {
	
	public static void main(String[] args) {
		// 사용자가 emp, dept 작업할 것인지 결정
		Scanner sc = new Scanner(System.in);
		boolean isStop = false;
		CommonControllerInterface controller = null;
		while(!isStop) {
			System.out.print("작업을 선택하세요.(emp, dept, job) >> ");
			String job = sc.next();
			switch(job) {
			case "emp" -> {controller = ControllerFactory.make("emp");}
			case "dept" -> {controller = ControllerFactory.make("dept");}
			case "job" -> {controller = ControllerFactory.make("job");}
			case "end" -> {isStop = true; continue;}
			default -> {continue;}
			}
 			controller.execute(); // 작업이 달라져도 사용법은 같다 (전략 패턴)
			
		}
		sc.close();
		System.out.println("----- MAIN END -----");
	}
}
