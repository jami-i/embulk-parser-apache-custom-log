package org.embulk.parser.apache.log;


public interface LogElementFactory<T extends LogElement> {
    T create(String parameter);
}
