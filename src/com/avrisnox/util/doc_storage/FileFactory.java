package com.avrisnox.util.doc_storage;

import com.avrisnox.util.doc_storage.interfaces.Refactor;
import com.avrisnox.util.doc_storage.interfaces.StorableAsFile;

import java.io.*;

public class FileFactory {
	private FileFactory instance = new FileFactory();
	private FileFactory() {}

	public FileFactory getInstance() {
		return instance;
	}

	public <T extends StorableAsFile> String putObject(T object, Refactor refactor) {
		return (refactor != null ? refactor.run(object.toFile()) : object.toFile());
	}

	public <T extends StorableAsFile> void getObject(T object, Refactor refactor, String contents) {
		if (refactor != null) object.fromFile(refactor.run(contents));
		else object.fromFile(contents);
	}

	public <T extends StorableAsFile> void putObjectToHere(T object, Refactor refactor, String filepath) {
		String contents = putObject(object, refactor);
		try(FileWriter out = new FileWriter(filepath)) {
			out.write(contents);
		} catch (FileNotFoundException e) {
			System.out.println("Cannot write to '" + filepath + "': File not found.");
		} catch (IOException e) {
			System.out.println("Cannot write to '" + filepath + "': IO error.");
		}
	}

	public <T extends StorableAsFile> void getObjectFromHere(T object, String filepath) {
		String contents= null;
		try(BufferedReader in = new BufferedReader(new FileReader(filepath))) {
			StringBuilder str = new StringBuilder();
			String inc;
			while((inc = in.readLine()) != null)
				str.append(inc);
			contents = str.toString();
		} catch (FileNotFoundException e) {
			System.out.println("Cannot read from '" + filepath + "': File not found.");
		} catch (IOException e) {
			System.out.println("Cannot read from '" + filepath + "': IO error.");
		}

		if(contents != null) {
			object.fromFile(contents);
		}
	}
}
