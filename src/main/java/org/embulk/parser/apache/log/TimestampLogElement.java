package org.embulk.parser.apache.log;

import org.embulk.config.ConfigSource;
import org.embulk.config.Task;
import org.embulk.spi.Exec;
import org.embulk.spi.PageBuilder;
import org.embulk.spi.time.Timestamp;
import org.embulk.spi.time.TimestampParser;


import static org.embulk.spi.type.Types.TIMESTAMP;


public class TimestampLogElement extends LogElement<Timestamp> {

    private final TimestampParser parser;

    public TimestampLogElement(TimestampParser.Task task, String name, String regex) {
        this(task, name, regex, "%d/%b/%Y:%T %z");
    }

    private static interface ParserIntlTask extends Task, TimestampParser.Task {}
    private static interface ParserIntlColumnOption extends Task, TimestampParser.TimestampColumnOption {}

    public TimestampLogElement(TimestampParser.Task task, String name, String regex, String pattern) {
        super(name, regex, TIMESTAMP);
        // TODO: Switch to a newer TimestampParser constructor after a reasonable interval.
        // Traditional constructor is used here for compatibility.
        final ConfigSource configSource = Exec.newConfigSource();
        configSource.set("format", pattern);
        configSource.set("timezone", task.getDefaultTimeZone());
        this.parser = new TimestampParser(
            Exec.newConfigSource().loadConfig(ParserIntlTask.class),
            configSource.loadConfig(ParserIntlColumnOption.class));
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
