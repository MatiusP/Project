package by.epamtc.protsko.rentcar.service;

import java.util.List;

import by.epamtc.protsko.rentcar.dto.EditUserDTO;
import by.epamtc.protsko.rentcar.dto.FullUserDTO;
import by.epamtc.protsko.rentcar.dto.RegistrationUserDTO;
import by.epamtc.protsko.rentcar.service.exception.UserServiceException;

public interface UserService {

    RegistrationUserDTO authentication(String login, String password) throws UserServiceException;

    boolean add(FullUserDTO fullUserDTO) throws UserServiceException;

    boolean edit(EditUserDTO user) throws UserServiceException;

    boolean delete(int userId) throws UserServiceException;

    FullUserDTO findByUserId(int userId) throws UserServiceException;

    List<FullUserDTO> findByCriteria(FullUserDTO criteria) throws UserServiceException;
}
