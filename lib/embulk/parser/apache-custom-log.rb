Embulk::JavaPlugin.register_parser(
  "apache-log", "org.embulk.parser.ApacheCustomLogParserPlugin",
  File.expand_path('../../../../classpath', __FILE__))
