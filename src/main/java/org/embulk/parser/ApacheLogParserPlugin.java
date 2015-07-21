package org.embulk.parser;

import com.google.common.collect.Lists;
import org.embulk.config.Config;
import org.embulk.config.ConfigSource;
import org.embulk.config.Task;
import org.embulk.config.TaskSource;
import org.embulk.parser.apache.log.LogElement;
import org.embulk.parser.apache.log.LogFormats;
import org.embulk.parser.apache.log.Replacement;
import org.embulk.spi.*;
import org.embulk.spi.time.TimestampParser;
import org.embulk.spi.util.LineDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ApacheLogParserPlugin
        implements ParserPlugin
{

    private static final Logger logger = LoggerFactory.getLogger(ApacheLogParserPlugin.class);

    public interface PluginTask
            extends Task, LineDecoder.DecoderTask, TimestampParser.ParserTask
    {

        @Config("format")
        String getFormat();

    }

    @Override
    public void transaction(ConfigSource config, ParserPlugin.Control control)
    {
        PluginTask task = config.loadConfig(PluginTask.class);
        ArrayList<ColumnConfig> columns = Lists.newArrayList();
        final String format = task.getFormat();

        List<Replacement> replacements = new LogFormats(task).getReplacements(format);

        for (Replacement replacement : replacements) {
            LogElement<?> logElement = replacement.getLogElement();
            columns.add(logElement.getColumnConfig());
        }

        Schema schema = new SchemaConfig(columns).toSchema();
        control.run(task.dump(), schema);
    }

    @Override
    public void run(TaskSource taskSource, Schema schema,
            FileInput input, PageOutput output)
    {
        PluginTask task = taskSource.loadTask(PluginTask.class);
        LineDecoder lineDecoder = new LineDecoder(input,task);
        PageBuilder pageBuilder = new PageBuilder(Exec.getBufferAllocator(), schema, output);
        String line;
        final String format = task.getFormat();
        LogFormats logFormats = new LogFormats(task);

        List<Replacement> replacements = logFormats.getReplacements(format);

        String regexp = logFormats.logFormat2RegexpString(format);

        logger.info("LogFormat : " + format);
        logger.info("RegExp    : " + regexp);

        Pattern accessLogPattern = Pattern.compile("^" + regexp + "$", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        Matcher accessLogEntryMatcher;

        int replacementSize = replacements.size();

        logger.info("replacement : " + replacementSize);

        while( input.nextFile() ){
            while(true){
                line = lineDecoder.poll();

                if (line == null) {
                  break;
                }

                accessLogEntryMatcher = accessLogPattern.matcher(line);

                if(replacementSize != accessLogEntryMatcher.groupCount()){
                    logger.warn("group count mismatch. + expected : " + replacementSize);
                }

                while(accessLogEntryMatcher.find()){
                    for (int i = 0; i < replacementSize; i++) {
                        LogElement<?> logElement = replacements.get(i).getLogElement();
                        String value = accessLogEntryMatcher.group(i + 1);

                        logElement.setToPageBuilder(pageBuilder, i, value);
                    }
                }

                pageBuilder.addRecord();
            }
        }
        pageBuilder.finish();
    }

}
