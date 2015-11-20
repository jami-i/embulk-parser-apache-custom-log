package org.embulk.parser.apache.log;

import org.embulk.spi.PageBuilder;
import org.embulk.spi.time.Timestamp;
import org.embulk.spi.time.TimestampParser;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Locale;


public class SimpleDateFormatTimestampLogElement extends TimestampLogElement {

    static final DateTimeFormatter formatter =
            DateTimeFormat
                    .forPattern("dd/MMM/yyyy:HH:mm:ss Z")
                    .withLocale(Locale.US);

    public SimpleDateFormatTimestampLogElement(TimestampParser.Task task, String name) {
        super(task, name, "\\[([^\\]]+)\\]", "");
    }

    @Override
    public Timestamp parse(String s) {
        try{
            long epoch = formatter.parseDateTime(s).getMillis();
            return Timestamp.ofEpochMilli(epoch);
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public void setToPageBuilder(PageBuilder pageBuilder, int i, String value) {
        Timestamp parse = parse(value);
        if(parse != null){
            pageBuilder.setTimestamp(i, parse);
        }else{
            pageBuilder.setNull(i);
        }
    }
}
