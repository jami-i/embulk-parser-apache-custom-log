package org.embulk.tester;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.embulk.command.Runner;

public class EmbulkPluginTester {

	public EmbulkPluginTester(Class<?> iface, String name, Class<?> impl)
	{
		TestExtension.addPlugin(iface, name, impl);
	}

	public void run(String ymlPath) throws Exception
	{
		Runner runner = new Runner("{}");
		runner.run(convert(ymlPath));
	}

	private String convert(String yml) throws Exception
	{
		File rootPath = new File(EmbulkPluginTester.class.getResource("/resource.txt").toURI()).getParentFile();
		File ymlPath = new File(EmbulkPluginTester.class.getResource(yml).toURI());
		File tempYmlPath = new File(ymlPath.getParentFile(), "temp-" + ymlPath.getName());
		Pattern pathPrefixPattern = Pattern.compile("^ *path(_prefix)?: '(.*)'$");
		try (BufferedReader reader = new BufferedReader(new FileReader(ymlPath))) {
			try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempYmlPath))) {
				String line;
				while ((line = reader.readLine()) != null) {
					Matcher matcher = pathPrefixPattern.matcher(line);
					if (matcher.matches()) {
						int group = 2;
						writer.write(line.substring(0, matcher.start(group)));
						writer.write(new File(rootPath, matcher.group(group)).getAbsolutePath());
						writer.write(line.substring(matcher.end(group)));
					} else {
						writer.write(line);
					}
					writer.newLine();
				}
			}
		}
		return tempYmlPath.getAbsolutePath();
	}

}
