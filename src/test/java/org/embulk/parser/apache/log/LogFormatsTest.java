package org.embulk.parser.apache.log;

import junit.framework.TestCase;
import org.embulk.spi.time.TimestampParser;
import org.joda.time.DateTimeZone;
import org.jruby.embed.ScriptingContainer;

import java.util.regex.Pattern;

public class LogFormatsTest extends TestCase {

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