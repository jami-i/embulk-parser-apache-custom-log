package org.embulk.parser;

import org.embulk.spi.ParserPlugin;
import org.embulk.tester.EmbulkPluginTester;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.function.Consumer;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TestApacheLogParserPlugin {

    private static EmbulkPluginTester tester = new EmbulkPluginTester(ParserPlugin.class, "apache-log", ApacheLogParserPlugin.class);

    @Test
    public void test_common() throws Exception {
        tester.run("/yml/test_common.yml");

        assertResult(
                "/temp/result_common.000.00.tsv",
                cols -> {
                    String[] expected = new String[]{
                            "remote-host",
                            "remote-log-name",
                            "request-user",
                            "request-time",
                            "request-line",
                            "response-status",
                            "response-bytes"
                    };
                    assertThat(cols, is(expected));
                },
                cols -> {
                    String[] expected = new String[]{
                            "127.0.0.1",
                            "",
                            "frank",
                            "2000-10-10 20:55:36.000000 +0000",
                            "GET /apache_pb.gif HTTP/1.0",
                            "200",
                            "2326"
                    };
                    assertThat(cols, is(expected));
                }
        );

    }

    @Test
    public void test_combined() throws Exception {
        tester.run("/yml/test_combined.yml");

        assertResult(
                "/temp/result_combined.000.00.tsv",
                cols -> {
                    String[] expected = new String[]{
                            "remote-host",
                            "remote-log-name",
                            "request-user",
                            "request-time",
                            "request-line",
                            "response-status",
                            "response-bytes",
                            "request-header-Referer",
                            "request-header-User-agent"
                    };
                    assertThat(cols, is(expected));
                },
                cols -> {
                    String[] expected = new String[]{
                            "127.0.0.1",
                            "",
                            "frank",
                            "2000-10-10 20:55:36.000000 +0000",
                            "GET /apache_pb.gif HTTP/1.0",
                            "200",
                            "2326",
                            "http://www.example.com/start.html",
                            "Mozilla/4.08 [en] (Win98; I ;Nav)"
                    };
                    assertThat(cols, is(expected));
                }
        );

    }

    @Test
    public void test_combined2() throws Exception {
        tester.run("/yml/test_combined2.yml");

        assertResult(
                "/temp/result_2_combined.000.00.tsv",
                cols -> {
                    String[] expected = new String[]{
                            "remote-host",
                            "remote-log-name",
                            "request-user",
                            "request-time",
                            "request-method",
                            "request-path",
                            "request-query",
                            "request-protocol",
                            "response-status",
                            "response-bytes",
                            "request-header-Referer",
                            "request-header-User-agent"
                    };
                    assertThat(cols, is(expected));
                },
                cols -> {
                    String[] expected = new String[]{
                            "24.93.39.209",
                            "",
                            "",
                            "2015-07-25 06:31:32.000000 +0000",
                            "POST",
                            "/search/",
                            "?c=Computers",
                            "HTTP/1.1",
                            "200",
                            "88",
                            "/category/health",
                            "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0; WOW64; Trident/4.0; GTB6; SLCC1; .NET CLR 2.0.50727; Media Center PC 5.0; .NET CLR 3.5.30729; .NET CLR 3.0.30618; .NET4.0C)"
                    };
                    assertThat(cols, is(expected));
                }
        );

    }

    @SafeVarargs
    private final void assertResult(String path, Consumer<String[]> headerAssert, Consumer<String[]>... bodyHeadAsserts) throws URISyntaxException, IOException {

        File resultFile = new File(TestApacheLogParserPlugin.class.getResource(path).toURI());

        try (BufferedReader reader = new BufferedReader(new FileReader(resultFile))) {

            String[] headerLine = reader.readLine().split("\t");

            for (Consumer<String[]> bodyHeadAssert : bodyHeadAsserts) {
                String[] bodyHeadLine = reader.readLine().split("\t");

                assertThat("body column length mismatch.", bodyHeadLine.length, is(headerLine.length));

                headerAssert.accept(headerLine);

                bodyHeadAssert.accept(bodyHeadLine);
            }

        }


    }


}
