package com.grokonez.jwtauthentication.controller;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

public class UnicodeRead {

	public static void main(String[] args) {

		String fileName = "d:\\testEncodage\\WEBPOST037011220.prv";

		// readUnicodeJava11(fileName);
		cleanEncoding(fileName);
		readUnicodeFiles(fileName);
		readUnicodeFilesANSI(fileName);
		// readUnicodeFiles(fileName);
		// readUnicodeClassic(fileName);

	}
	public static void cleanEncoding(String fileName) {

		Path path = Paths.get(fileName);
		// Getting the Path object
		Path pathW = Paths.get("D:\\testEncodage\\WEBPOST037011220outclear.txt");
		try {

			// Creating a BufferedWriter object
			BufferedWriter writer = Files.newBufferedWriter(pathW);

			// Java 8
			List<String> list = Files.readAllLines(path);
			// list.forEach(System.out::println);

			// Appending the UTF-8 String to the file
			for (String string : list) {
				writer.append(string);
				// Flushing data to the file
				writer.append("\n");
				writer.flush();
				System.out.println("ecriture en clear");
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	public static void readUnicodeFiles(String fileName) {

		Path path = Paths.get(fileName);
		// Getting the Path object
		Path pathW = Paths.get("D:\\testEncodage\\WEBPOST037011220out.txt");
		try {

			// Creating a BufferedWriter object
			BufferedWriter writer = Files.newBufferedWriter(pathW, StandardCharsets.UTF_8);

			// Java 8
			List<String> list = Files.readAllLines(path, StandardCharsets.UTF_8);
			// list.forEach(System.out::println);

			// Appending the UTF-8 String to the file
			for (String string : list) {
				writer.append(string);
				// Flushing data to the file
				writer.append("\n");
				writer.flush();
				System.out.println("ecriture en ut8");
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void readUnicodeFilesANSI(String fileName) {

		Path path = Paths.get(fileName);
		// Getting the Path object
		Path pathW = Paths.get("D:\\testEncodage\\WEBPOST037011220outAnsi.txt");
		try {

			// Creating a BufferedWriter object
			BufferedWriter writer = Files.newBufferedWriter(pathW, StandardCharsets.ISO_8859_1);

			// Java 8
			List<String> list = Files.readAllLines(path, StandardCharsets.ISO_8859_1);
			// list.forEach(System.out::println);

			// Appending the UTF-8 String to the file
			for (String string : list) {
				writer.append(string);
				// Flushing data to the file
				writer.append("\n");
				writer.flush();
				System.out.println("ecriture en ISO_8859_1");
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// Java 7 - Files.newBufferedReader(path, StandardCharsets.UTF_8)
	// Java 8 - Files.newBufferedReader(path) // default UTF-8
	public static void readUnicodeBufferedReader(String fileName) {

		Path path = Paths.get(fileName);

		// Java 8, default UTF-8
		try (BufferedReader reader = Files.newBufferedReader(path)) {

			String str;
			while ((str = reader.readLine()) != null) {
				System.out.println(str);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// Java 11, adds charset to FileReader
//    public static void readUnicodeJava11(String fileName) {
//
//        Path path = Paths.get(fileName);
//
//        try (FileReader fr = new FileReader(fileName, StandardCharsets.UTF_8);
//             BufferedReader reader = new BufferedReader(fr)) {
//
//            String str;
//            while ((str = reader.readLine()) != null) {
//                System.out.println(str);
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }

	public static void readUnicodeClassic(String fileName) {

		File file = new File(fileName);

		try (FileInputStream fis = new FileInputStream(file);
				InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
				BufferedReader reader = new BufferedReader(isr)) {

			String str;
			while ((str = reader.readLine()) != null) {
				System.out.println(str);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}