package de.smartsquare.reallifetdd.ugly;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyVararg;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.util.Calendar;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith( PowerMockRunner.class )
@PrepareForTest( { FileCleaner.class, Files.class, Calendar.class } )
public class FileCleanerPowermockTest {

    @Mock
    private Files files;

    @Mock
    private Calendar calendarMock;

    @Mock
    private FileTime fileTime;

    @Mock
    private File file;

    private FileCleaner fileCleaner;

    private Calendar calendar;

    @Before
    public void setup()
        throws Exception {
        // mock java.nio.file.Files
        PowerMockito.mockStatic( Files.class );
        when( Files.getLastModifiedTime( any( Path.class ), (LinkOption[]) anyVararg() ) ).thenReturn( fileTime );
        when( fileTime.toMillis() ).thenReturn( 0l );

        // mock Calendar
        calendar = Calendar.getInstance();
        PowerMockito.mockStatic( Calendar.class );
        when( Calendar.getInstance() ).thenReturn( calendarMock );

        fileCleaner = new FileCleaner();
    }

    @Test
    public void should_delete_file_if_modified_before_given_date()
        throws IOException {
        calendar.add( Calendar.DAY_OF_MONTH, -1 );
        when( calendarMock.getTime() ).thenReturn( calendar.getTime() );

        fileCleaner.cleanFileModifiedBeforeDate( file, new Date() );

        verify( file ).delete();
    }

    @Test
    public void should_not_delete_file_if_modified_equalTo_or_after_given_date()
        throws IOException {
        when( calendarMock.getTime() ).thenReturn( new Date() );

        fileCleaner.cleanFileModifiedBeforeDate( file, calendar.getTime() );

        verify( file, never() ).delete();
    }

}
