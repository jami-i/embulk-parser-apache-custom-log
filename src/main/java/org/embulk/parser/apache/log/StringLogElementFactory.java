package org.embulk.parser.apache.log;


import org.apache.commons.lang3.StringUtils;

public class StringLogElementFactory implements LogElementFactory<StringLogElement>, Patterns {

    private String name;
    private String regexp;

    public StringLogElementFactory(String name, String regexp) {

        this.name = name;
        this.regexp = regexp;
    }

    public StringLogElementFactory(String name) {
        this.name = name;
        this.regexp = ANY;
    }

    @Override
    public StringLogElement create(String parameter) {
        if(StringUtils.isEmpty(parameter)){
            return new StringLogElement(name, regexp);
        }else{
            return new StringLogElement(name + "-" + parameter, regexp);
        }
    }
}
