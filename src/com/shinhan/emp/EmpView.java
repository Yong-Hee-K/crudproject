package com.shinhan.emp;

import java.util.List;

// data를 display 하기 위함, 나중에 웹으로 변경되믄 JSP로 만들 예정

public class EmpView {
	
	// 여러 건 출력
	public static void display(List<EmpDTO> emplist) {
		if(emplist.size() == 0) {
			display("해당하는 직원이 존재하지 않습니다.");
			return;
		}
		System.out.println("----- 직원 여러 건 조회 -----");
		emplist.stream().forEach(emp -> System.out.println(emp));
	}
	
	// 한 건 출력
	public static void display(EmpDTO emp) {
		if(emp == null) {
			display("해당하는 직원이 존재하지 않습니다.");
			return;
		}
		System.out.println("직원 정보 : " + emp);
	}
	
	// 메세지 출력
	public static void display(String message) {
		System.out.println("알림 : " + message);
	}
		
}
