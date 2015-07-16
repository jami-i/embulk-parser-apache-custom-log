package org.embulk.parser.apache.log;

public interface Patterns {

    String IP_ADDRESS = "(\\d+(?:\\.\\d+){3})";

    String LONG = "(-?\\d+|-)";

    String ANY = "(.*)";

}
