package org.embulk.parser.apache.log;


import org.apache.commons.lang3.StringUtils;

public class LongLogElementFactory implements LogElementFactory<LongLogElement>, Patterns {

    private String name;
    private String regexp;

    public LongLogElementFactory(String name) {
        this.name = name;
        this.regexp = LONG;
    }

    @Override
    public LongLogElement create(String parameter) {
        if(StringUtils.isEmpty(parameter)){
            return new LongLogElement(name, regexp);
        }else {
            return new LongLogElement(name + "-" + parameter, regexp);
        }

    }
}
