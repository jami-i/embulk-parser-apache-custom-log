package org.embulk.parser.apache.log;

import junit.framework.TestCase;
import org.embulk.config.TaskSource;
import org.embulk.parser.ApacheLogParserPlugin;
import org.embulk.spi.util.Newline;
import org.joda.time.DateTimeZone;
import org.jruby.embed.ScriptingContainer;

import java.nio.charset.Charset;
import java.util.regex.Pattern;

public class LogFormatsTest extends TestCase {

    public void testLogFormat2Regexp() throws Exception {

        String format = "%!100<v %!100,200<v %100,200,300>v %!100,200,300>{hogeHoge}v %v %{X-Forwarded-For}i %t %{%D}t %l %u %t \"%r\" %>s %b \"%{Referer}i\" \"%{User-Agent}i\" %I %O %D";

        String s = new LogFormats().logFormat2Regexp(format);

        System.out.println(Pattern.quote(s));


    }
}