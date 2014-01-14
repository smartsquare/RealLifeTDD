package de.smartsquare.reallifetdd.ugly;

import java.util.Date;

public class Calendar {

    java.util.Calendar calendar = java.util.Calendar.getInstance();

    public Date oneWeekAgo() {
        calendar.add( java.util.Calendar.DAY_OF_MONTH, -7 );
        return calendar.getTime();
    }
}
