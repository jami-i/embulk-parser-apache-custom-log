package org.embulk.parser.apache.log;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(Enclosed.class)
public class StringLogElementFactoryTest extends TestCase {

    public static class WithName{

        @Test
        public void testCreateWithNull() throws Exception {

            StringLogElementFactory factory = new StringLogElementFactory("test-name");

            StringLogElement logElement = factory.create(null);

            assertThat(logElement.getName(), is("test-name"));
            assertThat(logElement.getRegexp(), is("(.*)"));

        }

        @Test
        public void testCreateWithEmptyString() throws Exception {

            StringLogElementFactory factory = new StringLogElementFactory("test-name");

            StringLogElement logElement = factory.create(null);

            assertThat(logElement.getName(), is("test-name"));
            assertThat(logElement.getRegexp(), is("(.*)"));

        }

        @Test
        public void testCreateWithParameter() throws Exception {

            StringLogElementFactory factory = new StringLogElementFactory("test-name");

            StringLogElement logElement = factory.create("param");

            assertThat(logElement.getName(), is("test-name-param"));
            assertThat(logElement.getRegexp(), is("(.*)"));

        }
    }

    public static class WithNameAndRegexp{

        @Test
        public void testCreateWithNull() throws Exception {

            StringLogElementFactory factory = new StringLogElementFactory("test-name", "(.+)");

            StringLogElement logElement = factory.create(null);

            assertThat(logElement.getName(), is("test-name"));
            assertThat(logElement.getRegexp(), is("(.+)"));

        }

        @Test
        public void testCreateWithEmptyString() throws Exception {

            StringLogElementFactory factory = new StringLogElementFactory("test-name", "(.+)");

            StringLogElement logElement = factory.create(null);

            assertThat(logElement.getName(), is("test-name"));
            assertThat(logElement.getRegexp(), is("(.+)"));

        }

        @Test
        public void testCreateWithParameter() throws Exception {

            StringLogElementFactory factory = new StringLogElementFactory("test-name", "(.+)");

            StringLogElement logElement = factory.create("param");

            assertThat(logElement.getName(), is("test-name-param"));
            assertThat(logElement.getRegexp(), is("(.+)"));

        }
    }
}