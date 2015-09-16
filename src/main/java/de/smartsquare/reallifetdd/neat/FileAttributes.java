package de.smartsquare.reallifetdd.neat;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.attribute.FileTime;
import java.util.Date;

public class FileAttributes {

    private Calendar calendar;

    public FileAttributes( Calendar calendar ) {
        this.calendar = calendar;
    }

    public Date getLastModifiedTime( File file )
        throws IOException {
        FileTime fileTime = Files.getLastModifiedTime( file.toPath(), LinkOption.NOFOLLOW_LINKS );
        return calendar.getDateFor( fileTime.toMillis() );
    }
}
