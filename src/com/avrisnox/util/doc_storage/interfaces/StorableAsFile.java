package com.avrisnox.util.doc_storage.interfaces;

/**StorableAsFile
 * Any object that implements this interface can make use of FileFactor to quickly convert the object to a single string.
 * Likewise, an object that implements this interface may read a specific string and be rebuilt from the string's interpretation.
 */
public interface StorableAsFile {
	/**
	 * toFile()
	 * Converts this object to a string that represents it.
	 *
	 * @return A string representing this object
	 */
	String toFile();

	/**
	 * fromFile(String)
	 * Rebuilds this object from the given string.
	 * NOTE: Without a validator, the string is not guaranteed to be valid.
	 *
	 * @param filestring A string representing the desired object
	 */
	void fromFile(String filestring);
}
