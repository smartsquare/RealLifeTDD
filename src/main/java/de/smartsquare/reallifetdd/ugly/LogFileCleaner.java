package de.smartsquare.reallifetdd.ugly;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

public class LogFileCleaner {

	public int cleanLogFiles() throws IOException {
		File pwd = new File(".");
		int deletedFileCount = 0;

		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.WEEK_OF_YEAR, -1);
		for (File file : pwd.listFiles()) {
			if (file.isFile() && file.getAbsolutePath().endsWith(".log")) {
				boolean fileDeleted = new FileCleaner()
						.cleanFileModifiedBeforeDate(file, calendar.getTime());
				if (fileDeleted) {
					deletedFileCount++;
				}
			}
		}

		return deletedFileCount;
	}
}
