package de.smartsquare.reallifetdd.ugly;

import java.io.File;
import java.io.IOException;

public class LogFileCleaner {

    public int cleanLogFiles()
        throws IOException {
        File pwd = new File( "." );
        int deletedFileCount = 0;

        for ( File file : pwd.listFiles() ) {
            if ( file.isFile() && file.getAbsolutePath().endsWith( ".log" ) ) {
                boolean fileDeleted = new FileCleaner().cleanFileModifiedBeforeDate( file, new Calendar().oneWeekAgo() );
                if ( fileDeleted ) {
                    deletedFileCount++;
                }
            }
        }

        return deletedFileCount;
    }
}
