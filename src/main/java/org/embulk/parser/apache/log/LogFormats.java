package org.embulk.parser.apache.log;

import com.google.common.collect.Lists;
import org.embulk.spi.time.TimestampParser;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class LogFormats implements Patterns {

    TimestampParser.ParserTask task;

    public LogFormats() {
        this.task = null;
    }

    public LogFormats(TimestampParser.ParserTask task) {
        this.task = task;
    }

    public Map<String, LogElementFactory<? extends LogElement>> get(){

        Map<String, LogElementFactory<? extends LogElement>> mapping = new HashMap<>();

        mapping.put("a", new StringLogElementFactory("remote-ip",     IP_ADDRESS));
        mapping.put("A", new StringLogElementFactory("local-ip",      IP_ADDRESS));
        mapping.put("b", new LongLogElementFactory("response-bytes"));
        mapping.put("B", new LongLogElementFactory("response-bytes"));
        mapping.put("C", new StringLogElementFactory("request-cookie"));
        mapping.put("D", new LongLogElementFactory("request-time"));
        mapping.put("e", new StringLogElementFactory("env"));
        mapping.put("f", new StringLogElementFactory("file-name"));
        mapping.put("h", new StringLogElementFactory("remote-host"));
        mapping.put("H", new StringLogElementFactory("request-protocol"));
        mapping.put("i", new StringLogElementFactory("request-header"));
        mapping.put("l", new StringLogElementFactory("remote-log-name"));
        mapping.put("m", new StringLogElementFactory("request-method", METHOD));

        mapping.put("n", new StringLogElementFactory("module-note"));
        mapping.put("o", new StringLogElementFactory("response-header"));

        mapping.put("p", new LongLogElementFactory("request-port"));

        mapping.put("P", new LongLogElementFactory("request-process"));

        mapping.put("q", new StringLogElementFactory("request-query", QUERY));

        mapping.put("r", new StringLogElementFactory("request-line"));
        mapping.put("s", new LongLogElementFactory("response-status", STATUS));

        mapping.put("t", new TimestampLogElementFactory(task, "request-time"));
        //mapping.put("t", new StringLogElementFactory("request-time"));

        mapping.put("T", new LongLogElementFactory("ele-time"));

        mapping.put("u", new StringLogElementFactory("request-user"));
        mapping.put("U", new StringLogElementFactory("request-path", PATH));
        mapping.put("v", new StringLogElementFactory("request-server-name"));
        mapping.put("V", new StringLogElementFactory("canonical-server-name"));
        mapping.put("X", new StringLogElementFactory("connection-status", CONN_STATUS));
        mapping.put("I", new LongLogElementFactory("request-total-bytes"));
        mapping.put("O", new LongLogElementFactory("response-total-bytes"));

        return mapping;
    }

    private static final Pattern logFormatExtractor =
            Pattern.compile("(%((!)?(\\d{3}(,\\d{3})*))?(<|>)?(\\{([^\\}]+)\\})?([A-z]))",
                    Pattern.DOTALL);
    //has 9 group

    public String logFormat2Regexp(String logFormat){

        List<Replacement> replacements = getReplacements(logFormat);

        return replace(logFormat, replacements);

    }

    private String replace(String logFormat, List<Replacement> replacements) {
        int offset = 0;

        for (Replacement replacement : replacements) {
            String left = logFormat.substring(0, offset + replacement.start);
            String right = logFormat.substring(offset + replacement.end, logFormat.length());
            int originalLength = logFormat.length() - left.length() - right.length();

            logFormat = left + replacement.logElement.getRegexp() + right;
            offset += replacement.logElement.getRegexp().length() - originalLength;
        }
        return logFormat;
    }

    public List<Replacement> getReplacements(String logFormat) {
        Matcher matcher = logFormatExtractor.matcher(logFormat);

        List<Replacement> replacements = Lists.newArrayList();

        while(matcher.find()){
            if(matcher.groupCount() != 9){
                throw new IllegalArgumentException("invalid regexp pattern");
            }
            String all = empty(matcher.group(1));
            //String ignoreStatus = empty(matcher.group(3));
            //Object[] statuses = Arrays.stream(empty(matcher.group(4)).split(",")).toArray();
            //String position = empty(matcher.group(6));

            String parameter = matcher.group(8);
            String key = empty(matcher.group(9));

            LogElementFactory<? extends LogElement> factory = get().get(key);

            if(factory != null){
                int start = matcher.start();
                int end   = matcher.end();
                LogElement logElement = factory.create(parameter);
                replacements.add(new Replacement(start, end, logElement));
            }else{
                throw new IllegalStateException("unknown log format key " + all);
            }

        }
        return replacements;
    }

    public String empty(String s){
        return s == null ? "" : s;
    }

    public static class Replacement{
        public final int start;
        public final int end;
        private final LogElement<?> logElement;

        public Replacement(int start, int end, LogElement<?> logElement) {
            this.logElement = logElement;
            this.end = end;
            this.start = start;
        }

        public LogElement<?> getLogElement() {
            return logElement;
        }
    }

}
