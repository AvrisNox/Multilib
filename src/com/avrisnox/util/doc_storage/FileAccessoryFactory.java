package com.avrisnox.util.doc_storage;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.zip.*;

/**FileAccessoryFactory
 * Offers a small selection of functions to call on a file, collection of files, or directory; currently includes:
 * 	zipping
 */
public class FileAccessoryFactory {
	private FileAccessoryFactory instance = new FileAccessoryFactory();

	/* This class is required for directory traversal - due to its private nature, it has been left undocumented. */
	private class ZipDir extends SimpleFileVisitor<Path> {
		private Path source;
		private ZipOutputStream zos;

		public ZipDir(Path source, ZipOutputStream zos) {
			this.source = source;
			this.zos = zos;
		}

		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attributes) {
			try {
				Path target = source.relativize(file);
				zos.putNextEntry(new ZipEntry(target.toString()));

				byte[] bytes = Files.readAllBytes(file);
				zos.write(bytes, 0, bytes.length);
				zos.closeEntry();
			} catch (IOException e) {
				System.err.println("Zipstream error: IO exception.");
			}

			return FileVisitResult.CONTINUE;
		}
	}

	private FileAccessoryFactory() {
	}

	/**
	 * getInstance()
	 * Gets the singleton instance for the factory.
	 *
	 * @return The factory instance
	 */
	public FileAccessoryFactory getInstance() {
		return instance;
	}

	/**singleZip(String)
	 * Compresses and zips a single file.
	 * @param filepath The path to and name of the file to zip
	 */
	public void singleZip(String filepath) {
		multiZip(filepath);
	}

	/**multiZip(String...)
	 * Compresses and zips an array of files, named after whatever the first file is called.
	 * @param filepaths The paths to and names of the files to zip together
	 */
	public void multiZip(String... filepaths) {
		try {
			File first = new File(filepaths[0]);
			String zipFile = first.getName().concat(".zip");

			ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFile));

			for (String filepath : filepaths) {
				try {
					zos.putNextEntry(new ZipEntry(new File(filepath).getName()));

					byte[] bytes = Files.readAllBytes(Paths.get(filepath));
					zos.write(bytes, 0, bytes.length);
					zos.closeEntry();
				} catch (IOException e) {
					System.err.println("Cannot read from " + filepath + ": IO exception.");
				}
			}
			zos.close();
		} catch (FileNotFoundException e) {
			System.err.println("Cannot write to zipfile : FileNotFound exception.");
		} catch (IOException e) {
			System.err.println("Zipstream error: IO exception.");
		}
	}

	/**directoryZip(String)
	 * Compresses and zips an entire directory and its contents.
	 * @param direcpath The path to and name of the directory to zip
	 */
	public void directoryZip(String direcpath) {
		Path source = Paths.get(direcpath);
		try {
			String zipFile = direcpath.concat(".zip");
			ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFile));

			Files.walkFileTree(source, new ZipDir(source, zos));
			zos.close();
		} catch (FileNotFoundException e) {
			System.err.println("Cannot write to zipfile : FileNotFound exception.");
		} catch (IOException e) {
			System.err.println("Zipstream error: IO exception.");
		}
	}
}
