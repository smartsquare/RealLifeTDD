package de.smartsquare.reallifetdd.ugly;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.attribute.FileTime;
import java.util.Calendar;
import java.util.Date;

public class FileCleaner {

    public boolean cleanFileModifiedBeforeDate( File file, Date date )
        throws IOException {
        FileTime fileTime = Files.getLastModifiedTime( file.toPath(), LinkOption.NOFOLLOW_LINKS );
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis( fileTime.toMillis() );
        Date modifiedDate = calendar.getTime();

        if ( modifiedDate.before( date ) ) {
            return file.delete();
        }

        return false;
    }
}
