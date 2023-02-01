package bss.student.registration.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import bss.student.registration.exception.DuplicationStudentException;
import bss.student.registration.exception.StudentNotFoundException;
import bss.student.registration.model.Student;
import bss.student.registration.model.StudentDTO;
import bss.student.registration.repository.StudentRepository;
import bss.student.registration.service.StudentService;

@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	private StudentRepository studentRepository;

	@Override
	public StudentDTO register(Student student) throws DuplicationStudentException {

		Optional<Student> optStudent = this.studentRepository.findByMobile(student.getMobile());
		if (optStudent.isPresent()) {

			throw new DuplicationStudentException("Duplication Student Exception!");
		}
		this.studentRepository.save(student);

		StudentDTO dto = new StudentDTO(student.getName(), student.getMobile(), student.getNric(),
				student.getAddress());
		return dto;
	}

	@Override
	public List<StudentDTO> getAll(int offset, int limit) {

		PageRequest pageable = PageRequest.of(offset, limit);
		Page<Student> students = this.studentRepository.findAll(pageable);

		List<StudentDTO> dtos = new ArrayList<StudentDTO>();
		dtos = students.getContent().stream().map(student -> student.maptoDTO()).collect(Collectors.toList());
		return dtos;
	}

	@Override
	public StudentDTO update(Student student) throws StudentNotFoundException {

		Optional<Student> optStudent = this.studentRepository.findById(student.getId());
		if (!optStudent.isPresent()) {

			throw new StudentNotFoundException("Student Not Found!");
		}

		Student oldStudent = optStudent.get();
		oldStudent.setName(student.getName());
		oldStudent.setMobile(student.getMobile());
		oldStudent.setNric(student.getNric());
		oldStudent.setAddress(student.getAddress());

		Student updatedStudent = this.studentRepository.save(oldStudent);
		return new StudentDTO(updatedStudent.getName(), updatedStudent.getMobile(), updatedStudent.getNric(),
				updatedStudent.getAddress());
	}

	@Override
	public void delete(Long id) throws StudentNotFoundException {

		Optional<Student> optStudent = this.studentRepository.findById(id);
		if (!optStudent.isPresent()) {

			throw new StudentNotFoundException("Student Not Found!");
		}
		this.studentRepository.delete(optStudent.get());

	}

}
