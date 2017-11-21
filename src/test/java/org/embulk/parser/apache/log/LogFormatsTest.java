package org.embulk.parser.apache.log;

import org.embulk.EmbulkTestRuntime;
import org.embulk.spi.time.TimestampParser;
import org.joda.time.DateTimeZone;
import org.jruby.embed.ScriptingContainer;
import org.junit.Rule;
import org.junit.Test;

import java.util.regex.Pattern;

public class LogFormatsTest {

    @Rule
    public EmbulkTestRuntime runtime = new EmbulkTestRuntime();

    @Test
    public void testLogFormat2Regexp() throws Exception {

        String format = "%!100<v %!100,200<v %100,200,300>v %!100,200,300>{hogeHoge}v %v %{X-Forwarded-For}i %t %{%D}t %l %u %t \"%r\" %>s %b \"%{Referer}i\" \"%{User-Agent}i\" %I %O %D";

        LogFormats logFormats = new LogFormats(new TimestampParser.Task() {
            @Override
            public DateTimeZone getDefaultTimeZone() {
                return DateTimeZone.UTC;
            }

            @Override
            public String getDefaultTimestampFormat() {
                return "\"%Y-%m-%d %H:%M:%S.%N %z\"";
            }

            @Override
            public ScriptingContainer getJRuby() {
                return new ScriptingContainer();
            }
        });

        String s = logFormats.logFormat2RegexpString(format);

        System.out.println(Pattern.quote(s));


    }
}
