package com.anurag.cmds;

import java.io.*;
import java.time.*;

public class Tail {

	RandomAccessFile raf;
	int lines;
	String filePath;

	public Tail(String[] args)throws IllegalArgumentException {
		try {
			validate(args);	
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}

	public static void main(String[] args) { 
		try {
			Tail o = new Tail(args);
			Instant start = java.time.Instant.now();
			o.tail();
			Instant end = java.time.Instant.now();
			Duration between = java.time.Duration.between(start, end);
			System.out.println(o.formatDuration(between));
			
		} catch (IllegalArgumentException e) {
			System.out.println(" >> Error: " + e.getMessage());
		} catch (IOException e) {
			System.out.println(" >> Error: " + e.getMessage());
		} catch (Exception e) {
			System.out.println(" >> Unknow exception: " + e.getMessage());
			e.printStackTrace(System.out);
		}
	}

	public void tail()throws IOException {
		long length = this.raf.length();	// Can be the bottleneck depending on implementation
		// System.out.println("Length: " +  this.raf.length());
		// System.out.println("File Offset: " + this.raf.getFilePointer());
		long pos = length-1;
		int count = 0;
		while ( pos >= 0 ) {
			raf.seek(pos);
			byte b = raf.readByte();
			char c = (char) (b & 0xFF);
			if ( c == '\n') {
				count++;
				if ( count == this.lines )
					break;	
			}
			pos--;
		}
		pos++;
		raf.seek(pos);
		while ( pos < length ) {
			byte b = raf.readByte();
			char c = (char) (b & 0xFF);
			System.out.print(c);
			pos++;
		}
	}


	public void validate(String[] args)throws IllegalArgumentException {
		// TODO: Can implement a default value for lines (`tail` does a default 10 lines)
		if ( args.length != 2 ) {
			throw new IllegalArgumentException("Usage: java Tail [file] #");
		}
		validateFilePath(args[0]);
		validateLines(args[1]);
	}

	public void validateFilePath(String filePath) {
		try {
			raf = new RandomAccessFile(new File(filePath), "r");
		} catch (Exception e) {
			throw new IllegalArgumentException (e.getMessage());
		}
		
	}

	public void validateLines(String argument) {
		try { 
			int lines = Integer.parseInt(argument);
			if ( lines <= 0 ) {
				throw new IllegalArgumentException (lines + " is not a positive integer");
			}
			this.lines = lines;
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException (argument + " is not a valid integer");
		}
	}

	public String formatDuration(Duration duration) {
	    long seconds = duration.getSeconds();
	    long absSeconds = Math.abs(seconds);
	    String positive = String.format(
	        "%d:%02d:%02d",
	        absSeconds / 3600,
	        (absSeconds % 3600) / 60,
	        absSeconds % 60);
	    return seconds < 0 ? "-" + positive : positive;
	}
}