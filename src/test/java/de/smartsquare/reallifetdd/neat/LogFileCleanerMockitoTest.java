package de.smartsquare.reallifetdd.neat;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import de.smartsquare.reallifetdd.Calendar;

@RunWith( MockitoJUnitRunner.class )
public class LogFileCleanerMockitoTest {

    @Mock
    private File pwd;

    @Mock
    private File logfile;

    @Mock
    private FileCleaner fileCleaner;

    private LogFileCleaner logFileCleaner;

    @Before
    public void setup()
        throws Exception {
        // mock current directory containing one log file
        when( logfile.isFile() ).thenReturn( true );
        when( pwd.listFiles() ).thenReturn( new File[] { logfile } );

        when( fileCleaner.cleanFileModifiedBeforeDate( isA( File.class ), any( Date.class ) ) ).thenReturn( true );

        logFileCleaner = new LogFileCleaner( fileCleaner, mock( Calendar.class ) );
    }

    @Test
    public void should_delete_log_file()
        throws IOException {
        when( logfile.getAbsolutePath() ).thenReturn( "path/to/file.log" );

        int fileCount = logFileCleaner.cleanLogFiles( pwd );

        assertThat( fileCount, is( equalTo( 1 ) ) );
    }

    @Test
    public void should_not_delete_non_log_file()
        throws IOException {
        when( logfile.getAbsolutePath() ).thenReturn( "path/to/file.txt" );

        int fileCount = logFileCleaner.cleanLogFiles( pwd );

        assertThat( fileCount, is( equalTo( 0 ) ) );
    }

}
