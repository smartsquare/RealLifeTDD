package de.smartsquare.reallifetdd.neat;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith( MockitoExtension.class )
class FileCleanerTest {

    @Mock
    private FileAttributes fileAttributes;

    @Mock
    private File file;

    private FileCleaner fileCleaner;

    private Calendar calendar;

    @BeforeEach
    public void setup() {
        calendar = Calendar.getInstance();
        fileCleaner = new FileCleaner( fileAttributes );
    }

    @Test
    void should_delete_file_if_modified_before_given_date()
        throws IOException {
        calendar.add( Calendar.DAY_OF_MONTH, -1 );
        when( fileAttributes.getLastModifiedTime( file ) ).thenReturn( calendar.getTime() );

        fileCleaner.cleanFileModifiedBeforeDate( file, new Date() );

        verify( file ).delete();
    }

    @Test
    void should_not_delete_file_if_modified_equalTo_or_after_given_date()
        throws IOException {
        when( fileAttributes.getLastModifiedTime( file ) ).thenReturn( calendar.getTime() );

        fileCleaner.cleanFileModifiedBeforeDate( file, calendar.getTime() );

        verify( file, never() ).delete();
    }

}
