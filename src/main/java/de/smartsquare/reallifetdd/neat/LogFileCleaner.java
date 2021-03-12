package de.smartsquare.reallifetdd.neat;

import java.io.File;
import java.io.IOException;

public class LogFileCleaner {

    private final FileCleaner fileCleaner;

    private final Calendar calendar;

    public LogFileCleaner( FileCleaner fileCleaner, Calendar calendar ) {
        this.fileCleaner = fileCleaner;
        this.calendar = calendar;
    }

    public int cleanLogFiles( File pwd )
        throws IOException {
        int deletedFileCount = 0;

        for ( File file : pwd.listFiles() ) {
            if ( file.isFile() && file.getAbsolutePath().endsWith( ".log" ) ) {
                boolean fileDeleted = fileCleaner.cleanFileModifiedBeforeDate( file, calendar.oneWeekAgo() );
                if ( fileDeleted ) {
                    deletedFileCount++;
                }
            }
        }

        return deletedFileCount;
    }
}
