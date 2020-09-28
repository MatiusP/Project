package by.epamtc.protsko.rentcar.dao.reader;

import java.util.ResourceBundle;

public class RegexPropertyReader implements PropertyReader {
    private static final String REGEX_PROPERTY_FILE = "property/validator_regex";
    private ResourceBundle bundle = ResourceBundle.getBundle(REGEX_PROPERTY_FILE);

    @Override
    public String getPropertyValue(String key) {
        return bundle.getString(key);
    }
}
