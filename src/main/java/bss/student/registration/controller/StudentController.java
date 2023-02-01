package bss.student.registration.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import bss.student.registration.enumeration.Result;
import bss.student.registration.exception.DuplicationStudentException;
import bss.student.registration.exception.InvalidJwtAuthenticationException;
import bss.student.registration.exception.StudentNotFoundException;
import bss.student.registration.model.Student;
import bss.student.registration.model.StudentDTO;
import bss.student.registration.model.StudentResponse;
import bss.student.registration.service.StudentService;

@RestController
@RequestMapping("/student")
public class StudentController {

	@Autowired
	private StudentService studentService;

	@PostMapping
	public ResponseEntity<StudentResponse> register(@RequestBody Student student) {

		StudentResponse response = null;
		try {

			StudentDTO createdStudent = this.studentService.register(student);
			response = new StudentResponse();
			response.setResult(Result.SUCCESS.toString());
			response.setStudent(createdStudent);

		} catch (DuplicationStudentException e1) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e1.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Fail to save student info!");
		}

		return new ResponseEntity<StudentResponse>(response, HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<StudentResponse> getStudents(@RequestParam(value = "pageNo", defaultValue = "0") int pageNo,
			@RequestParam(value = "pageSize", defaultValue = "1") int pageSize) {

		StudentResponse response = null;
		List<StudentDTO> dtos = this.studentService.getAll(pageNo, pageSize);
		response = new StudentResponse();
		response.setResult(Result.SUCCESS.toString());
		response.setStudents(dtos);

		return new ResponseEntity<StudentResponse>(response, HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<StudentResponse> updateStudent(@PathVariable("id") Long stuId, @RequestBody Student student) {

		student.setId(stuId);
		StudentResponse response = null;
		try {

			StudentDTO updatedStudent = this.studentService.update(student);
			response = new StudentResponse();
			response.setResult(Result.SUCCESS.toString());
			response.setStudent(updatedStudent);

		} catch (StudentNotFoundException e1) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e1.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Fail to update student info!");
		}
		return new ResponseEntity<StudentResponse>(response, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<StudentResponse> deleteStudent(@PathVariable("id") Long stuId) {

		StudentResponse response = null;
		try {

			this.studentService.delete(stuId);
			response = new StudentResponse();
			response.setResult(Result.SUCCESS.toString());

		} catch (StudentNotFoundException e1) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e1.getMessage());
		}
		return new ResponseEntity<StudentResponse>(response, HttpStatus.OK);
	}
}
