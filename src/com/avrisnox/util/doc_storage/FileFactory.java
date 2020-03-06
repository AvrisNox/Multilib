package com.avrisnox.util.doc_storage;

import com.avrisnox.util.doc_storage.interfaces.Refactor;
import com.avrisnox.util.doc_storage.interfaces.StorableAsFile;
import com.avrisnox.util.doc_storage.interfaces.ValidateAndRefactor;
import com.avrisnox.util.doc_storage.interfaces.Validator;

import java.io.*;

/**FileFactory
 * Allows for converting a writing a class to a file and reading a file back into a class.
 * Objects that want to use this factory must implement the "StorableAsFile" interface.
 */
public class FileFactory {
	private FileFactory instance = new FileFactory();

	private FileFactory() {
	}

	/**
	 * getInstance()
	 * Gets the singleton instance for the factory.
	 *
	 * @return The factory instance
	 */
	public FileFactory getInstance() {
		return instance;
	}

	/**
	 * putObject(StorableAsFile)
	 * Performs a simple, no refactor conversion from object to storable object.
	 *
	 * @param object The object to store
	 * @param <T>    The type of the object to store
	 * @return A string representing the object; this can be written to a file or stored for later
	 */
	public <T extends StorableAsFile> String putObject(T object) {
		return putObject(object, null);
	}

	/**
	 * putObject(StorableAsFile, Refactor)
	 * Performs a simple conversion from object to storable object with refactorization.
	 *
	 * @param object   The object to store
	 * @param refactor The refactor to run on the object string (null if no refactor is desired)
	 * @param <T>      The type of the object to store
	 * @return A string representing the object; this can be written to a file or stored for later
	 */
	public <T extends StorableAsFile> String putObject(T object, Refactor refactor) {
		return (refactor != null ? refactor.run(object.toFile()) : object.toFile());
	}

	/**
	 * getObject(StorableAsFile, String)
	 * Rebuilds an object from a content string without refactoring or validating the string.
	 *
	 * @param object   The object to rebuild in - this may have its variables overwritten
	 * @param contents The string representing the object to rebuild
	 * @param <T>      The type of the object to rebuild
	 */
	public <T extends StorableAsFile> void getObject(T object, String contents) {
		getObject(object, null, null, contents);
	}

	/**
	 * getObject(StorableAsFile, String)
	 * Rebuilds an object from a content string after validating the string but without refactoring it.
	 *
	 * @param object    The object to rebuild in - this may have its variables overwritten
	 * @param validator The validator to run on the object string (null if no validation is desired)
	 * @param contents  The string representing the object to rebuild
	 * @param <T>       The type of the object to rebuild
	 */
	public <T extends StorableAsFile> void getObject(T object, Validator validator, String contents) {
		getObject(object, validator, null, contents);
	}

	/**
	 * getObject(StorableAsFile, Refactor, String)
	 * Rebuilds an object from a content string after refactoring the string but without validating it.
	 *
	 * @param object   The object to rebuild in - this may have its variables overwritten
	 * @param refactor The refactor to run on the object string (null if no refactor is desired)
	 * @param contents The string representing the object to rebuild
	 * @param <T>      The type of the object to rebuild
	 */
	public <T extends StorableAsFile> void getObject(T object, Refactor refactor, String contents) {
		getObject(object, null, refactor, contents);
	}

	/**
	 * getObject(StorableAsFile, ValidateAndRefactor, String)
	 * Rebuilds an object from a content string after validating and refactoring the string.
	 * Uses a single object to perform both validation and refactorization.
	 *
	 * @param object   The object to rebuild in - this may have its variables overwritten
	 * @param valref   A single object that implements both Validator and Refactor methods (null if no validation AND no refactorization is desired)
	 * @param contents The string representing the object to rebuild
	 * @param <T>      The type of object to rebuild
	 */
	public <T extends StorableAsFile> void getObject(T object, ValidateAndRefactor valref, String contents) {
		getObject(object, valref, valref, contents);
	}

	/**
	 * getObject(StorableAsFile, Validator, Refactor, String)
	 * Rebuilds an object from a content string after validating and refactoring the string.
	 *
	 * @param object    The object to rebuild in - this may have its variables overwritten
	 * @param validator The validator to run on the object string (null if no validation is desired)
	 * @param refactor  The refactor to run on the object string (null if no refactor is desired)
	 * @param contents  The string representing the object to rebuild
	 * @param <T>       The type of the object to rebuild
	 * @throws IllegalArgumentException Thrown when validator detects an invalid content string
	 */
	public <T extends StorableAsFile> void getObject(T object, Validator validator, Refactor refactor, String contents) throws IllegalArgumentException {
		if (validator != null && !validator.validate(contents))
			throw new IllegalArgumentException("Content string not valid for String->Object.");
		if (refactor != null) object.fromFile(refactor.run(contents));
		else object.fromFile(contents);
	}

	/**
	 * putObjectToHere(StorableAsFile, String)
	 * Converts an object to a string and stores it in the given file without refactoring the string.
	 *
	 * @param object   The object to store
	 * @param filepath The path to and name of the file to store the object in
	 * @param <T>      The type of the object to store
	 * @throws IOException Thrown when the file could not be found or there was a generic IO error (see stderr for more info)
	 */
	public <T extends StorableAsFile> void putObjectToHere(T object, String filepath) throws IOException {
		putObjectToHere(object, null, filepath);
	}

	/**
	 * putObjectToHere(StorableAsFile, Refactor, String)
	 * Converts an object to a string and stores it in the given file after refactoring the string.
	 *
	 * @param object   The object to store
	 * @param refactor The refactor to run on the object string (null if no refactor is desired)
	 * @param filepath The path to and name of the file to store the object in
	 * @param <T>      The type of the object to store
	 * @throws IOException Thrown when the file could not be found or there was a generic IO error (see stderr for more info)
	 */
	public <T extends StorableAsFile> void putObjectToHere(T object, Refactor refactor, String filepath) throws IOException {
		String contents = putObject(object, refactor);
		try (FileWriter out = new FileWriter(filepath)) {
			out.write(contents);
		} catch (FileNotFoundException e) {
			System.err.println("Cannot write to '" + filepath + "': File not found.");
			throw e;
		} catch (IOException e) {
			System.err.println("Cannot write to '" + filepath + "': IO error.");
			throw e;
		}
	}

	/**
	 * getObjectFromHere(StorableAsFile, String)
	 * Rebuilds an object from a file without refactoring or validating the contents.
	 *
	 * @param object   The object to rebuild in - this may have its variables overwritten
	 * @param filepath The path to and name of the file to pull the object from
	 * @param <T>      The type of the object to rebuild
	 * @throws IOException Thrown when the file could not be found or there was a generic IO error (see stderr for more info)
	 */
	public <T extends StorableAsFile> void getObjectFromHere(T object, String filepath) throws IOException {
		getObjectFromHere(object, null, null, filepath);
	}

	/**
	 * getObjectFromHere(StorableAsFile, Validator, String)
	 * Rebuilds an object from a file after validating the contents but without refactoring them.
	 *
	 * @param object    The object to rebuild in - this may have its variables overwritten
	 * @param validator The validator to run on the object string (null if no validation is desired)
	 * @param filepath  The path to and name of the file to pull the object from
	 * @param <T>       The type of the object to rebuild
	 * @throws IOException Thrown when the file could not be found or there was a generic IO error (see stderr for more info)
	 */
	public <T extends StorableAsFile> void getObjectFromHere(T object, Validator validator, String filepath) throws IOException {
		getObjectFromHere(object, validator, null, filepath);
	}

	/**
	 * getObjectFromHere(StorableAsFile, Refactor, String)
	 * Rebuilds an object from a file after refactoring the contents but without validating them.
	 *
	 * @param object   The object to rebuild in - this may have its variables overwritten
	 * @param refactor The refactor to run on the object string (null if no refactor is desired)
	 * @param filepath The path to and name of the file to pull the object from
	 * @param <T>      The type of the object to rebuild
	 * @throws IOException Thrown when the file could not be found or there was a generic IO error (see stderr for more info)
	 */
	public <T extends StorableAsFile> void getObjectFromHere(T object, Refactor refactor, String filepath) throws IOException {
		getObjectFromHere(object, null, refactor, filepath);
	}

	/**
	 * getObjectFromHere(StorableAsFile, ValidateAndRefactor, String)
	 * Rebuilds an object from a file after validating and refactoring the contents.
	 *
	 * @param object   The object to rebuild in - this may have its variables overwritten
	 * @param valref   A single object that implements both Validator and Refactor methods (null if no validation AND no refactorization is desired)
	 * @param filepath The path to and name of the file to pull the object from
	 * @param <T>      The type of the object to rebuild
	 * @throws IOException Thrown when the file could not be found or there was a generic IO error (see stderr for more info)
	 */
	public <T extends StorableAsFile> void getObjectFromHere(T object, ValidateAndRefactor valref, String filepath) throws IOException {
		getObjectFromHere(object, valref, valref, filepath);
	}

	/**
	 * getObjectFromHere(StorableAsFile, Validator, Refactor, String)
	 * Rebuilds an object from a file after validating and refactoring the contents.
	 *
	 * @param object    The object to rebuild in - this may have its variables overwritten
	 * @param validator The validator to run on the object string (null if no validation is desired)
	 * @param refactor  The refactor to run on the object string (null if no refactor is desired)
	 * @param filepath  The path to and name of the file to pull the object from
	 * @param <T>       The type of the object to rebuild
	 * @throws IOException Thrown when the file could not be found or there was a generic IO error (see stderr for more info)
	 */
	public <T extends StorableAsFile> void getObjectFromHere(T object, Validator validator, Refactor refactor, String filepath) throws IOException {
		String contents;
		try (BufferedReader in = new BufferedReader(new FileReader(filepath))) {
			StringBuilder str = new StringBuilder();
			String inc;
			while ((inc = in.readLine()) != null)
				str.append(inc);
			contents = str.toString();
		} catch (FileNotFoundException e) {
			System.err.println("Cannot read from '" + filepath + "': File not found.");
			throw e;
		} catch (IOException e) {
			System.err.println("Cannot read from '" + filepath + "': IO error.");
			throw e;
		}

		if (validator != null && !validator.validate(contents))
			throw new IllegalArgumentException("Content string not valid for String->Object.");
		object.fromFile(refactor != null ? refactor.run(contents) : contents);
	}
}
