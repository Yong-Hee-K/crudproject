package com.shinhan.emp;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// JavaBeans기술에서 사용 가능 (default 생성자, getter, setter)
// DTO(Data Transfer Object)
// 칼럼의 이름과 field 가 일치하는 것이 제일 좋다

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class EmpDTO {
	private Integer employee_id;
	private String first_name;
	private String last_name;
	private String email;
	private String phone_number;
	private Date hire_date;
	private String job_id;
	private Double salary;
	private Double commission_pct;
	private Integer manager_id;
	private Integer department_id;
}
