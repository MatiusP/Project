package by.epamtc.protsko.rentcar.dto;

import java.io.Serializable;
import java.time.LocalDate;

public class FullUserDTO implements Serializable {
    private static final long serialVersionUID = -730577270846599955L;

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
    private String status;
    private String role;

    public FullUserDTO() {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FullUserDTO)) return false;

        FullUserDTO userDTO = (FullUserDTO) o;

        if (id != userDTO.id) return false;
        if (login != null ? !login.equals(userDTO.login) : userDTO.login != null) return false;
        if (password != null ? !password.equals(userDTO.password) : userDTO.password != null) return false;
        if (surname != null ? !surname.equals(userDTO.surname) : userDTO.surname != null) return false;
        if (name != null ? !name.equals(userDTO.name) : userDTO.name != null) return false;
        if (passportIdNumber != null ? !passportIdNumber.equals(userDTO.passportIdNumber) : userDTO.passportIdNumber != null)
            return false;
        if (driverLicense != null ? !driverLicense.equals(userDTO.driverLicense) : userDTO.driverLicense != null)
            return false;
        if (dateOfBirth != null ? !dateOfBirth.equals(userDTO.dateOfBirth) : userDTO.dateOfBirth != null) return false;
        if (eMail != null ? !eMail.equals(userDTO.eMail) : userDTO.eMail != null) return false;
        if (phone != null ? !phone.equals(userDTO.phone) : userDTO.phone != null) return false;
        if (status != null ? !status.equals(userDTO.status) : userDTO.status != null) return false;
        return role != null ? role.equals(userDTO.role) : userDTO.role == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (passportIdNumber != null ? passportIdNumber.hashCode() : 0);
        result = 31 * result + (driverLicense != null ? driverLicense.hashCode() : 0);
        result = 31 * result + (dateOfBirth != null ? dateOfBirth.hashCode() : 0);
        result = 31 * result + (eMail != null ? eMail.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "FullUserDTO{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", surname='" + surname + '\'' +
                ", name='" + name + '\'' +
                ", passportIdNumber='" + passportIdNumber + '\'' +
                ", driverLicense='" + driverLicense + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", eMail='" + eMail + '\'' +
                ", phone='" + phone + '\'' +
                ", status='" + status + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
