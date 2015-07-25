package org.embulk.parser.apache.log;

import junit.framework.TestCase;
import org.embulk.spi.PageBuilder;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(Enclosed.class)
public class StringLogElementTest extends TestCase {

    public static class TestParse {

        StringLogElement elem = new StringLogElement("test-elem", "(.*)");

        @Test
        public void testParseWithNull() throws Exception {
            assertThat(elem.parse(null), is((String)null));
        }

        @Test
        public void testParseWithEmpty() throws Exception {
            assertThat(elem.parse(""), is(""));
        }

        @Test
        public void testParseWithNonEmptyString() throws Exception {
            assertThat(elem.parse("str"), is("str"));
        }

        @Test
        public void testParseWithCLFEmptyString() throws Exception {
            assertThat(elem.parse("-"), is((String)null));
        }

    }

    public static class TestSetToPageBuilder{
        @Test
        public void testSetToPageBuilder() throws Exception {
            //TODO implement
        }
    }




}