package bss.student.registration.service;

import java.util.List;

import bss.student.registration.exception.DuplicationStudentException;
import bss.student.registration.exception.StudentNotFoundException;
import bss.student.registration.model.Student;
import bss.student.registration.model.StudentDTO;

public interface StudentService {

	public StudentDTO register(Student student) throws DuplicationStudentException;

	public StudentDTO update(Student student) throws StudentNotFoundException;

	public List<StudentDTO> getAll(int offset, int limit);

	public void delete(Long id) throws StudentNotFoundException;

}
