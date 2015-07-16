package org.embulk.parser.apache.log;


public interface LogElementFactory<T extends LogElement> {

    public T create(String parameter);


}
