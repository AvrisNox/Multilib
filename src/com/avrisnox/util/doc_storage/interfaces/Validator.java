package com.avrisnox.util.doc_storage.interfaces;

/**Validator
 * Used to prevent invalid strings from causing bad behavior.
 */
public interface Validator {
	/**
	 * validate(String)
	 * Runs some validation on the string to ensure it is a valid string for the desired object type.
	 *
	 * @param contents The string that may represent the object
	 * @return True iff the string represents a valid object of the desired type
	 */
	boolean validate(String contents);
}
