package by.epamtc.protsko.rentcar.dao.validator;

public interface CommandValidator {

    boolean execute(String userDataParameter);

    String getDataEntryRules();
}
