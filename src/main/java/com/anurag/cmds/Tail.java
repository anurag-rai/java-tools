package com.anurag.cmds;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

public class Tail {

	RandomAccessFile raf;
	int lines;
	Path filePath;
	String fileName;
	long currentPointer;

	public Tail(String[] args) throws IllegalArgumentException {
		try {
			this.currentPointer = 0;
			validate(args);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}

	public static void main(String[] args) {
		try {
			Tail o = new Tail(args);
			o.tailf();
		} catch (IllegalArgumentException e) {
			System.out.println(" >> Error: " + e.getMessage());
		} catch (IOException e) {
			System.out.println(" >> Error: " + e.getMessage());
		} catch (Exception e) {
			System.out.println(" >> Unknow exception: " + e.getMessage());
			e.printStackTrace(System.out);
		}
	}

	public void tailf() throws IOException {
		long length = this.raf.length();
		// System.out.println("Seeking to update ... ");
		// System.out.println("File length: " + length);
		// System.out.println("Initial : " + this.currentPointer);
		raf.seek(this.currentPointer);
		while (this.currentPointer < length) {
			byte b = raf.readByte();
			char c = (char) (b & 0xFF);
			System.out.print(c);
			this.currentPointer++;
		}
		// System.out.println("Finally: " + this.currentPointer);
		writePointerDataToFile();
		// System.out.println("Listening ... ");
		Path path = this.filePath;
		WatchService watcher = path.getFileSystem().newWatchService();
		path.register(watcher, StandardWatchEventKinds.ENTRY_MODIFY);
		for (;;) {
			WatchKey key;
			try {
				key = watcher.take();
			} catch (InterruptedException x) {
				return;
			}

			for (WatchEvent<?> event : key.pollEvents()) {
				WatchEvent.Kind<?> kind = event.kind();
				if (kind == StandardWatchEventKinds.OVERFLOW || kind == StandardWatchEventKinds.ENTRY_CREATE
						|| kind == StandardWatchEventKinds.ENTRY_DELETE) {
					continue;
				}

				@SuppressWarnings("unchecked")
				WatchEvent<Path> ev = (WatchEvent<Path>) event;
				Path filename = ev.context();
				if (filename.toString().equals(this.fileName)) {
					length = this.raf.length();
					while (this.currentPointer < length) {
						byte b = raf.readByte();
						char c = (char) (b & 0xFF);
						System.out.print(c);
						this.currentPointer++;
					}
					writePointerDataToFile();
				}
			}
			boolean valid = key.reset();
			if (!valid) {
				break;
			}
		}

	}

	public void writePointerDataToFile() {
		String filename = this.fileName + ".record";
		try {
			// Assume default encoding.
			FileWriter fileWriter = new FileWriter(filename);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.write(Long.toString(this.currentPointer));
			bufferedWriter.newLine();
			bufferedWriter.close();
		} catch (IOException ex) {
			System.out.println("Error writing to file '" + fileName + "'");
		}
	}

	public void validate(String[] args) throws IllegalArgumentException {
		if (args.length != 1) {
			throw new IllegalArgumentException("Usage: java Tail [file]");
		}
		validateFilePath(args[0]);
	}

	public void validateFilePath(String filePath) {
		try {
			raf = new RandomAccessFile(new File(filePath), "r");
			// TODO: Handle other relative paths
			// "test" "./test" "./dir/test" "/dir/dir/test" "../../dir/test"
			String[] splitted = filePath.split("/");
			if (splitted.length == 1) {
				this.fileName = splitted[0];
				this.filePath = Paths.get(".");
				try {
					FileReader fileReader = new FileReader(this.fileName + ".record");

					BufferedReader bufferedReader = new BufferedReader(fileReader);
					String line;
					while ((line = bufferedReader.readLine()) != null) {
//						System.out.println("Reading from record file: " + line);
						this.currentPointer = Integer.parseInt(line);
					}
					bufferedReader.close();
//					System.out.println("Setting pointer to : " + this.currentPointer);
				} catch (FileNotFoundException ex) {
					return;
				} catch (IOException ex) {
					System.out.println("Error reading file '" + fileName + "'");
				}
			} else {
				throw new IllegalArgumentException("Specify only filename. Cannot parse other paths for now.");
			}

		} catch (Exception e) {
			throw new IllegalArgumentException(e.getMessage());
		}

	}

}