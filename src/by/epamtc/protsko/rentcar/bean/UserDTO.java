package by.epamtc.protsko.rentcar.bean;

import java.io.Serializable;
import java.time.LocalDate;

public class UserDTO implements Serializable {
	private static final long serialVersionUID = -7140832636495909933L;

	private int id;
	private String login;
	private String password;
	private String surname;
	private String name;
	private String passportIdNumber;
	private String driverLicense;
	private LocalDate dateOfBirth;
	private String eMail;
	private String phone;
	private int role; /////////////// разобраться с типом (необходимо Role)

	public UserDTO() {
	}

	public UserDTO(int id, String login, String password, String surname, String name, String passportIdNumber,
			String driverLicense, LocalDate dateOfBirth, String eMail, String phone, int role) {
		super();
		this.id = id;
		this.login = login;
		this.password = password;
		this.surname = surname;
		this.name = name;
		this.passportIdNumber = passportIdNumber;
		this.driverLicense = driverLicense;
		this.dateOfBirth = dateOfBirth;
		this.eMail = eMail;
		this.phone = phone;
		this.role = role;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassportIdNumber() {
		return passportIdNumber;
	}

	public void setPassportIdNumber(String passportIdNumber) {
		this.passportIdNumber = passportIdNumber;
	}

	public String getDriverLicense() {
		return driverLicense;
	}

	public void setDriverLicense(String driverLicense) {
		this.driverLicense = driverLicense;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String geteMail() {
		return eMail;
	}

	public void seteMail(String eMail) {
		this.eMail = eMail;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dateOfBirth == null) ? 0 : dateOfBirth.hashCode());
		result = prime * result + ((driverLicense == null) ? 0 : driverLicense.hashCode());
		result = prime * result + ((eMail == null) ? 0 : eMail.hashCode());
		result = prime * result + id;
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((passportIdNumber == null) ? 0 : passportIdNumber.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
		result = prime * result + role;
		result = prime * result + ((surname == null) ? 0 : surname.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserDTO other = (UserDTO) obj;
		if (dateOfBirth == null) {
			if (other.dateOfBirth != null)
				return false;
		} else if (!dateOfBirth.equals(other.dateOfBirth))
			return false;
		if (driverLicense == null) {
			if (other.driverLicense != null)
				return false;
		} else if (!driverLicense.equals(other.driverLicense))
			return false;
		if (eMail == null) {
			if (other.eMail != null)
				return false;
		} else if (!eMail.equals(other.eMail))
			return false;
		if (id != other.id)
			return false;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (passportIdNumber == null) {
			if (other.passportIdNumber != null)
				return false;
		} else if (!passportIdNumber.equals(other.passportIdNumber))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (phone == null) {
			if (other.phone != null)
				return false;
		} else if (!phone.equals(other.phone))
			return false;
		if (role != other.role)
			return false;
		if (surname == null) {
			if (other.surname != null)
				return false;
		} else if (!surname.equals(other.surname))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "UserDTO [id=" + id + ", login=" + login + ", password=" + password + ", surname=" + surname + ", name="
				+ name + ", passportIdNumber=" + passportIdNumber + ", driverLicense=" + driverLicense
				+ ", dateOfBirth=" + dateOfBirth + ", eMail=" + eMail + ", phone=" + phone + ", role=" + role + "]";
	}
}
