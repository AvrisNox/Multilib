package com.avrisnox.util.doc_storage.interfaces;

/**Refactor
 * Allows for a single String->String mapping to be applied when converting a StorableAsFile object to a string and back.
 */
public interface Refactor {
	/**
	 * run(String)
	 * Performs a mapping on the contents string of the object to change the information contained within the string, either excluding or including (or both) some of the contents.
	 *
	 * @param input The input string representing the object
	 * @return A new string that represents the object
	 */
	String run(String input);
}
