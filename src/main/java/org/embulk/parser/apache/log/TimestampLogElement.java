package org.embulk.parser.apache.log;

import org.embulk.spi.PageBuilder;
import org.embulk.spi.time.Timestamp;
import org.embulk.spi.time.TimestampParser;


import static org.embulk.spi.type.Types.TIMESTAMP;


public class TimestampLogElement extends LogElement<Timestamp> {

    private final TimestampParser parser;

    public TimestampLogElement(TimestampParser.ParserTask task, String name, String regex) {
        this(task, name, regex, "%d/%b/%Y:%T %z");
    }

    public TimestampLogElement(TimestampParser.ParserTask task, String name, String regex, String pattern) {
        super(name, regex, TIMESTAMP, Timestamp.class);
        this.parser = new TimestampParser(pattern, task);
    }

    @Override
    public Timestamp parse(String s) {
        try{
            return parser.parse(s);
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
