package by.epamtc.protsko.rentcar.bean;

import java.io.Serializable;
import java.time.LocalDate;

public class EditUserDTO implements Serializable {
    private static final long serialVersionUID = 6346261302539455026L;

    private int id;
    private String login;
    private String currentPassword;
    private String newPassword;
    private String surname;
    private String name;
    private String passportIdNumber;
    private String driverLicense;
    private LocalDate dateOfBirth;
    private String eMail;
    private String phone;
    private int role;

    public EditUserDTO() {
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

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EditUserDTO)) return false;

        EditUserDTO that = (EditUserDTO) o;

        if (id != that.id) return false;
        if (role != that.role) return false;
        if (login != null ? !login.equals(that.login) : that.login != null) return false;
        if (currentPassword != null ? !currentPassword.equals(that.currentPassword) : that.currentPassword != null)
            return false;
        if (newPassword != null ? !newPassword.equals(that.newPassword) : that.newPassword != null) return false;
        if (surname != null ? !surname.equals(that.surname) : that.surname != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (passportIdNumber != null ? !passportIdNumber.equals(that.passportIdNumber) : that.passportIdNumber != null)
            return false;
        if (driverLicense != null ? !driverLicense.equals(that.driverLicense) : that.driverLicense != null)
            return false;
        if (dateOfBirth != null ? !dateOfBirth.equals(that.dateOfBirth) : that.dateOfBirth != null) return false;
        if (eMail != null ? !eMail.equals(that.eMail) : that.eMail != null) return false;
        return phone != null ? phone.equals(that.phone) : that.phone == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (currentPassword != null ? currentPassword.hashCode() : 0);
        result = 31 * result + (newPassword != null ? newPassword.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (passportIdNumber != null ? passportIdNumber.hashCode() : 0);
        result = 31 * result + (driverLicense != null ? driverLicense.hashCode() : 0);
        result = 31 * result + (dateOfBirth != null ? dateOfBirth.hashCode() : 0);
        result = 31 * result + (eMail != null ? eMail.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + role;
        return result;
    }

    @Override
    public String toString() {
        return "EditUserDTO{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", currentPassword='" + currentPassword + '\'' +
                ", newPassword='" + newPassword + '\'' +
                ", surname='" + surname + '\'' +
                ", name='" + name + '\'' +
                ", passportIdNumber='" + passportIdNumber + '\'' +
                ", driverLicense='" + driverLicense + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", eMail='" + eMail + '\'' +
                ", phone='" + phone + '\'' +
                ", role=" + role +
                '}';
    }
}
