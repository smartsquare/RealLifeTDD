package de.smartsquare.reallifetdd.neat;

import java.io.File;
import java.io.IOException;

import de.smartsquare.reallifetdd.Calendar;

public class LogFileCleaner {

    private FileCleaner fileCleaner;

    private Calendar calendar;

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
