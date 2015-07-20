package org.embulk.parser.apache.log;


public class Replacement {
    private final int start;
    private final int end;
    private final LogElement<?> logElement;

    public Replacement(int start, int end, LogElement<?> logElement) {
        this.logElement = logElement;
        this.end = end;
        this.start = start;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public LogElement<?> getLogElement() {
        return logElement;
    }

}
