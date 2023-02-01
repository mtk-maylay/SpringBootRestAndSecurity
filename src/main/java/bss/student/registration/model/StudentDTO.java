package bss.student.registration.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class StudentDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;
	private String mobile;
	private String nric;
	private String address;

	public StudentDTO(String name, String mobile, String nric, String address) {
		this.name = name;
		this.mobile = mobile;
		this.nric = nric;
		this.address = address;
	}

}
