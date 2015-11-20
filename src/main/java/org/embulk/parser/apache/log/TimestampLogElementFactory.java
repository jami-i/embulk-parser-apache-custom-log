package org.embulk.parser.apache.log;


import org.apache.commons.lang3.StringUtils;
import org.embulk.spi.time.TimestampParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TimestampLogElementFactory implements LogElementFactory<TimestampLogElement>, Patterns {

    private static final Logger logger = LoggerFactory.getLogger(TimestampLogElementFactory.class);

    private TimestampParser.Task task;
    private String name;

    public TimestampLogElementFactory(TimestampParser.Task task, String name) {
        this.task = task;
        this.name = name;
    }

    @Override
    public TimestampLogElement create(String parameter) {
        if(StringUtils.isEmpty(parameter)){
            logger.info("since format parameter is not given, use DateTimeFormatter.");
            return new SimpleDateFormatTimestampLogElement(task, name);
        }else{
            String regex = toTimestampRegex(parameter);
            return new TimestampLogElement(task, name, regex, parameter);
        }
    }

    private String toTimestampRegex(String parameter) {
        String regex = "(" + parameter + ")";
        regex = regex.replaceAll("\\[|\\]","\\\\$0");

        regex = regex.replaceAll("%[abhpABPZ]","[A-z]+");
        regex = regex.replaceAll("%c","[A-z]{3} [A-z]{3} \\\\d{2} \\\\d{2}:\\\\d{2}:\\\\d{2} \\\\d{4}");
        regex = regex.replaceAll("%[dgmyCHIMSUVW]","\\\\d{2}");
        regex = regex.replaceAll("%[Dx]","\\\\d{2}/\\\\d{2}/\\\\d{2}");
        regex = regex.replaceAll("%[ekl]","[1-9 ]\\\\d");
        regex = regex.replaceAll("%F","\\\\d{4}-\\\\d{2}-\\\\d{2}");
        regex = regex.replaceAll("%[GY]","\\\\d{4}");
        regex = regex.replaceAll("%j","\\\\d{3}");
        regex = regex.replaceAll("%r","\\\\d{2}:\\\\d{2}:\\\\d{2} [A-z]+");
        regex = regex.replaceAll("%R","\\\\d{2}:\\\\d{2}");
        regex = regex.replaceAll("%s","\\\\d+");
        regex = regex.replaceAll("%[TX]","\\\\d{2}:\\\\d{2}:\\\\d{2}");
        regex = regex.replaceAll("%[uw]","\\\\d");
        regex = regex.replaceAll("%z","\\\\+\\\\d{4}");

        return regex;
    }
}
