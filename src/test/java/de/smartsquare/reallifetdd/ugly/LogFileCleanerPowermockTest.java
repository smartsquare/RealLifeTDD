package de.smartsquare.reallifetdd.ugly;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import de.smartsquare.reallifetdd.neat.Calendar;

@RunWith( PowerMockRunner.class )
@PrepareForTest( LogFileCleaner.class )
public class LogFileCleanerPowermockTest {

    @Mock
    private File pwd;

    @Mock
    private File logfile;

    @Mock
    private FileCleaner fileCleaner;

    @Mock
    private Calendar calendar;

    private LogFileCleaner logFileCleaner;

    @Before
    public void setup()
        throws Exception {
        // mock current directory containing one log file
    	when( logfile.isFile() ).thenReturn( true );
        when( pwd.listFiles() ).thenReturn( new File[] { logfile } );
        PowerMockito.whenNew( File.class ).withAnyArguments().thenReturn( pwd );

        PowerMockito.whenNew( FileCleaner.class ).withAnyArguments().thenReturn( fileCleaner );

        logFileCleaner = new LogFileCleaner();
    }

    @Test
    public void should_delete_log_file_older_than_one_week()
        throws IOException {
    	when( logfile.getAbsolutePath() ).thenReturn( "path/to/file.log" );
        when( fileCleaner.cleanFileModifiedBeforeDate( isA( File.class ), any( Date.class ) ) ).thenReturn( true );

        int fileCount = logFileCleaner.cleanLogFiles();

        verify(fileCleaner).cleanFileModifiedBeforeDate(eq(logfile), any(Date.class));
        assertThat( fileCount, is( equalTo( 1 ) ) );
    }
    
    @Test
    public void should_not_delete_log_file_younger_than_one_week()
    		throws IOException {
    	when( logfile.getAbsolutePath() ).thenReturn( "path/to/file.log" );
        when( fileCleaner.cleanFileModifiedBeforeDate( isA( File.class ), any( Date.class ) ) ).thenReturn( false );

        int fileCount = logFileCleaner.cleanLogFiles();

        verify(fileCleaner).cleanFileModifiedBeforeDate(eq(logfile), any(Date.class));
        assertThat( fileCount, is( equalTo( 0 ) ) );
    }

    @Test
    public void should_not_delete_non_log_file()
        throws IOException {
    	when( logfile.getAbsolutePath() ).thenReturn( "path/to/file.txt" );

        int fileCount = logFileCleaner.cleanLogFiles();

        verify(fileCleaner, never()).cleanFileModifiedBeforeDate(any(File.class), any(Date.class));
        assertThat( fileCount, is( equalTo( 0 ) ) );    	
    }    

}
