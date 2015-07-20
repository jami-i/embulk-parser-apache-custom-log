package org.embulk.parser.apache.log;


import org.embulk.spi.PageBuilder;
import org.embulk.spi.type.Types;

public class LongLogElement extends LogElement<Long> {

    public LongLogElement(String name, String regex) {
        super(name, regex, Types.LONG);
    }

    @Override
    public Long parse(String s) {
        try{
            if("-".equals(s)){
                return 0L;
            }
            return Long.parseLong(s);
        }catch (NumberFormatException e){
            return 0L;
        }
    }

    @Override
    public void setToPageBuilder(PageBuilder pageBuilder, int i, String value) {
        pageBuilder.setLong(i, parse(value));
    }
}
