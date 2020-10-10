package by.epamtc.protsko.rentcar.service.validator;

public interface CommandValidator {

    boolean execute(String userDataParameter);

    String getDataEntryRules();
}
