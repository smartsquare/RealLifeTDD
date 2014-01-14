package de.smartsquare.reallifetdd.neat;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class FileCleaner {

    private FileAttributes fileAttributes;

    public FileCleaner( FileAttributes fileAttributes ) {
        this.fileAttributes = fileAttributes;
    }

    public boolean cleanFileModifiedBeforeDate( File file, Date date )
        throws IOException {
        Date lastModifiedTime = fileAttributes.getLastModifiedTime( file );
        if ( lastModifiedTime.before( date ) ) {
            return file.delete();
        }

        return false;
    }
}
