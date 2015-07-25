package org.embulk.parser.apache.log;

import org.apache.commons.lang3.StringUtils;
import org.embulk.spi.time.TimestampParser;

public class TimestampLogElementFactory implements LogElementFactory<TimestampLogElement>, Patterns {

    private TimestampParser.Task task;
    private String name;

    public TimestampLogElementFactory(TimestampParser.Task task, String name) {
        this.task = task;
        this.name = name;
    }

    @Override
    public TimestampLogElement create(String parameter) {
        if(StringUtils.isEmpty(parameter)){
            return new TimestampLogElement(task, name, "\\[([^\\]]+)\\]");
        }else{
            return new TimestampLogElement(task, name, parameter);
        }
    }
}
