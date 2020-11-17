package by.epamtc.protsko.rentcar.service.validator.user;

/**
 * This interface provides method for user data validation.
 *
 * @author Matvey Protsko
 */
public interface UserCommandValidator {

    boolean execute(String userDataParameter);

}
