package com.anurag.cmds;

import java.util.Random;
import java.io.*;
import java.time.*;

public class FileCreator {
	
	String candidates = "ABCDEFGHIJKLMNOPQRSTUVWXYZ abcdefghijklmnopqrstuvezyz 0123456789 `~!@#$%^&*()-_=+[]{};':\",./<>?|\\";
	int candidatesLength;
	Random ran;

	public FileCreator() {
		this.candidatesLength = this.candidates.length();
		this.ran = new Random();
	}
	public FileCreator(String candidates) {
		this.candidates = candidates;
		this.candidatesLength = this.candidates.length();
		this.ran = new Random();
	}

	public static void main(String[] args) {
		try {
			FileCreator fc = new FileCreator();
			fc.createFile("test");
		} catch (IOException e) {
			System.out.println(" >> Error: " + e.getMessage());
		} catch (Exception e) {
			System.out.println(" >> Unknow exception: " + e.getMessage());
			e.printStackTrace(System.out);
		}
	}

	public void createFile(String filePath)throws IOException {
		RandomAccessFile raf = new RandomAccessFile(filePath, "rw");

		int first = 1;	//10^5
		int second = this.ran.nextInt(900001) + 100000;	// 10^5 - 10^6
		// int first = 10;
		// int second = 20;
		long fileLength = (long)first * second;
		System.out.println(" ]] Creating a file with " + fileLength + " lines ...");
		StringBuilder line;
		long count = 1;
		Instant start = java.time.Instant.now();
		for ( int i = 0; i < first; i++ ) {
			for ( int j = 0; j < second; j++ ) {
				// System.out.println("creating line number: " + count++);
				line = createRandomString();
				line.append('\n');
				raf.writeBytes(line.toString());
			}
		}
		Instant end = java.time.Instant.now();
		Duration between = java.time.Duration.between(start, end);
		System.out.println(this.formatDuration(between));
		System.out.println(" ]] Done!!!");
	}

	public StringBuilder createRandomString() {
		StringBuilder salt = new StringBuilder();
        int randomStringLength = this.ran.nextInt(901) + 100;	//create a random string of length 100-1000
        int currentLength = 0;
        while (currentLength < randomStringLength) {
            int index = (int) (ran.nextFloat() * this.candidatesLength);
            salt.append(candidates.charAt(index));
            currentLength++;
        }
        return salt;
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