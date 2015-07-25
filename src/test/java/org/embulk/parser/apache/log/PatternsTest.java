package org.embulk.parser.apache.log;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import java.util.regex.Pattern;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(Enclosed.class)
public class PatternsTest extends TestCase {

    public static class Test_IP_ADDRESS {
        Pattern pattern = Pattern.compile(Patterns.IP_ADDRESS);
        @Test
        public void test_match_with_ipv4(){
            assertThat(pattern.matcher("127.0.0.1").matches(), is(true));
            assertThat(pattern.matcher("255.255.255.255").matches(), is(true));
        }
    }

    public static class Test_LONG {
        Pattern pattern = Pattern.compile(Patterns.LONG);
        @Test
        public void test_match_with_long(){
            assertThat(pattern.matcher("1").matches(), is(true));
            assertThat(pattern.matcher("-1").matches(), is(true));
            assertThat(pattern.matcher("-").matches(), is(true));

            assertThat(pattern.matcher("a").matches(), is(false));
        }
    }

    public static class Test_ANY {
        Pattern pattern = Pattern.compile(Patterns.ANY);
        @Test
        public void test_match_with_long(){
            assertThat(pattern.matcher("1").matches(), is(true));
            assertThat(pattern.matcher("-1").matches(), is(true));
            assertThat(pattern.matcher("-").matches(), is(true));

            assertThat(pattern.matcher("a").matches(), is(true));
            assertThat(pattern.matcher("").matches(), is(true));
        }
    }

    public static class Test_PATH {
        Pattern pattern = Pattern.compile(Patterns.PATH);
        @Test
        public void test_match_with_long(){
            assertThat(pattern.matcher("/1").matches(), is(true));
            assertThat(pattern.matcher("/abc/123").matches(), is(true));

            assertThat(pattern.matcher("").matches(), is(false));
        }
    }

    public static class Test_QUERY {
        Pattern pattern = Pattern.compile(Patterns.QUERY);
        @Test
        public void test_match_with_long(){
            assertThat(pattern.matcher("?1").matches(), is(true));
            assertThat(pattern.matcher("?abc=123&p=v#hash").matches(), is(true));

            assertThat(pattern.matcher("").matches(), is(true));
        }
    }

    public static class Test_STATUS {
        Pattern pattern = Pattern.compile(Patterns.STATUS);
        @Test
        public void test_match_with_long(){
            assertThat(pattern.matcher("100").matches(), is(true));
            assertThat(pattern.matcher("200").matches(), is(true));
            assertThat(pattern.matcher("302").matches(), is(true));
            assertThat(pattern.matcher("404").matches(), is(true));
            assertThat(pattern.matcher("500").matches(), is(true));
            assertThat(pattern.matcher("999").matches(), is(true));

            assertThat(pattern.matcher("99").matches(), is(false));
            assertThat(pattern.matcher("099").matches(), is(false));
            assertThat(pattern.matcher("1000").matches(), is(false));
        }
    }

    public static class Test_METHOD {
        Pattern pattern = Pattern.compile(Patterns.METHOD);
        @Test
        public void test_match_with_long(){
            assertThat(pattern.matcher("HEAD").matches(), is(true));
            assertThat(pattern.matcher("GET").matches(), is(true));
            assertThat(pattern.matcher("POST").matches(), is(true));
            assertThat(pattern.matcher("PUT").matches(), is(true));
            assertThat(pattern.matcher("OPTIONS").matches(), is(true));
            assertThat(pattern.matcher("TRACE").matches(), is(true));
            assertThat(pattern.matcher("CONNECT").matches(), is(true));

            assertThat(pattern.matcher("").matches(), is(false));
            assertThat(pattern.matcher("OTHER").matches(), is(false));
        }
    }

    public static class Test_CONN_STATUS {
        Pattern pattern = Pattern.compile(Patterns.CONN_STATUS);
        @Test
        public void test_match_with_long(){
            assertThat(pattern.matcher("X").matches(), is(true));
            assertThat(pattern.matcher("-").matches(), is(true));
            assertThat(pattern.matcher("+").matches(), is(true));

            assertThat(pattern.matcher("").matches(), is(false));
            assertThat(pattern.matcher("foo").matches(), is(false));
        }
    }


}