package org.embulk.parser.apache.log;

import junit.framework.TestCase;

import java.util.regex.Pattern;

public class LogFormatsTest extends TestCase {

    public void testLogFormat2Regexp() throws Exception {

        String format = "%!100<v %!100,200<v %100,200,300>v %!100,200,300>{hogeHoge}v %v %{X-Forwarded-For}i %t %{%D}t %l %u %t \"%r\" %>s %b \"%{Referer}i\" \"%{User-Agent}i\" %I %O %D";

        String s = new LogFormats().logFormat2RegexpString(format);

        System.out.println(Pattern.quote(s));


    }
}