package by.epamtc.protsko.rentcar.dao;

import java.util.List;

import by.epamtc.protsko.rentcar.bean.User;
import by.epamtc.protsko.rentcar.bean.UserData;
import by.epamtc.protsko.rentcar.dao.exception.DAOException;

public interface UserDAO {
	
	User authentication(String login, String password) throws DAOException;
	
	boolean registration(UserData userData) throws DAOException;
	
	boolean editUserData(UserData userData) throws DAOException;
	
	boolean deleteUser(int userId) throws DAOException;
	
	List<User> getUser(String criteria) throws DAOException;

}
