package org.embulk.parser.apache.log;

public interface Patterns {

    String IP_ADDRESS = "(\\d+(?:\\.\\d+){3})";

    String LONG = "(-?\\d+|-)";

    String ANY = "(.*)";

    String PATH = "(/[^\\?]+)";

    String QUERY = "(\\?.*)?";

    String STATUS = "([1-9]\\d{2})";

    String METHOD = "(GET|POST|PUT|DELETE|HEAD|OPTIONS|TRACE|CONNECT)";

    String CONN_STATUS = "([X+\\-])";

}
