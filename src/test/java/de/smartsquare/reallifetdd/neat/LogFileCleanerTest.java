package de.smartsquare.reallifetdd.neat;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith( MockitoExtension.class )
class LogFileCleanerTest {

    @Mock
    private File pwd;

    @Mock
    private File logfile;

    @Mock
    private FileCleaner fileCleaner;

    @Mock
    private Calendar calendar;

    private LogFileCleaner logFileCleaner;

    @BeforeEach
    public void setup() {
        // mock current directory containing one log file
        when( logfile.isFile() ).thenReturn( true );
        when( pwd.listFiles() ).thenReturn( new File[] { logfile } );

        lenient().when(calendar.oneWeekAgo()).thenReturn(new Date());
        logFileCleaner = new LogFileCleaner( fileCleaner, calendar );
    }

    @Test
    void should_delete_log_file_older_than_one_week()
        throws IOException {
    	boolean deleteFile = true;
        when( logfile.getAbsolutePath() ).thenReturn( "path/to/file.log" );
        when( fileCleaner.cleanFileModifiedBeforeDate( eq(logfile), any( Date.class ) ) ).thenReturn( deleteFile );

        int fileCount = logFileCleaner.cleanLogFiles( pwd );

        assertThat(fileCount).isEqualTo(1);
    }
    
    @Test
    void should_not_delete_log_file_younger_than_one_week()
    		throws IOException {
    	boolean deleteFile = false;
        when( logfile.getAbsolutePath() ).thenReturn( "path/to/file.log" );
        when( fileCleaner.cleanFileModifiedBeforeDate( eq(logfile), any( Date.class ) ) ).thenReturn( deleteFile );

    	int fileCount = logFileCleaner.cleanLogFiles( pwd );
    	
    	verify(fileCleaner).cleanFileModifiedBeforeDate(eq(logfile), any(Date.class));
        assertThat(fileCount).isZero();
    }

    @Test
    void should_not_delete_non_log_file()
        throws IOException {
        when( logfile.getAbsolutePath() ).thenReturn( "path/to/file.txt" );

        int fileCount = logFileCleaner.cleanLogFiles( pwd );

        verify(fileCleaner, never()).cleanFileModifiedBeforeDate(any(File.class), any(Date.class));
      assertThat(fileCount).isZero();
    }

}
