package by.epamtc.protsko.rentcar.dao.reader;

public class PropertyReaderFactory {

	public PropertyReader getPropertyReader(PropertyType propertyType) {
		PropertyReader propertyReader = null;

		switch (propertyType.toString()) {
		case "DB_CONNECTION_PROPERTY":
			propertyReader = new DBPropertyReader();
			break;

		case "VALIDATOR_REGEX_PROPERTY":
			propertyReader = new RegexPropertyReader();
			break;

		default:
			throw new IllegalArgumentException("Wrong property type:" + propertyType);
		}
		return propertyReader;
	}
}
