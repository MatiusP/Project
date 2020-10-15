package by.epamtc.protsko.rentcar.service.validator;

public interface UserCommandValidator {

    boolean execute(String userDataParameter);

    String getDataEntryRules();
}
