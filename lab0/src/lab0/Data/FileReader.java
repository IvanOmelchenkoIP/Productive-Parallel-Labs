/*
 * Лабораторна робота 0 ЛР0, Варіанти - 1.14, 2.23, 3.3
 * F1: D = (SORT(A + B) + C) * (MA * MB)
 * F2: q = MAX(MH * MK - ML)
 * F3: O = SORT(P)*(MR*MT)
 * Омельченко І. ІП-04
 * Дата відправлення: 06.03.2023 
 * 
 * файл: ./src/lab0/data/FileReader.java
 */

package lab0.Data;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class FileReader {
	
	public String read(String filepath) throws IOException {
		DataInputStream stream = null;
		try {
			stream = new DataInputStream(new FileInputStream(filepath));
			return new String(stream.readAllBytes(), StandardCharsets.UTF_8);
		} finally {
			if (stream != null) {
				stream.close();
			}
		}
	}
}
