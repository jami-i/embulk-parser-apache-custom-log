package org.embulk.parser.apache.log;


import org.embulk.spi.PageBuilder;
import org.embulk.spi.type.Types;

public class StringLogElement extends LogElement<String> {

    public StringLogElement(String name, String regex) {
        super(name, regex, Types.STRING, String.class);
    }

    @Override
    public String parse(String s) {
        if("-".equals(s)){
            return null;
        }else{
            return s;
        }

    }

    @Override
    public void setToPageBuilder(PageBuilder pageBuilder, int i, String value) {
        String parsed = parse(value);
        if(parsed != null){
            pageBuilder.setString(i, parsed);
        }else{
            pageBuilder.setNull(i);
        }

    }
}
