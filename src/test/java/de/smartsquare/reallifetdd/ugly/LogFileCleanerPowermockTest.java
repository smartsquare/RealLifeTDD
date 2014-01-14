package de.smartsquare.reallifetdd.ugly;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.isA;
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
        when( logfile.getAbsolutePath() ).thenReturn( "path/to/file.log" );
        when( pwd.listFiles() ).thenReturn( new File[] { logfile } );
        PowerMockito.whenNew( File.class ).withAnyArguments().thenReturn( pwd );

        PowerMockito.whenNew( FileCleaner.class ).withAnyArguments().thenReturn( fileCleaner );

        logFileCleaner = new LogFileCleaner();
    }

    @Test
    public void should_delete_log_file()
        throws IOException {
        when( logfile.isFile() ).thenReturn( true );
        when( fileCleaner.cleanFileModifiedBeforeDate( isA( File.class ), any( Date.class ) ) ).thenReturn( true );

        int fileCount = logFileCleaner.cleanLogFiles();

        assertThat( fileCount, is( equalTo( 1 ) ) );
    }

}
