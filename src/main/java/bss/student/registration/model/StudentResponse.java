package bss.student.registration.model;

import java.util.List;

import lombok.Data;

@Data
public class StudentResponse {

	private String result;

	private StudentDTO student;

	private List<StudentDTO> students;
	 

}
