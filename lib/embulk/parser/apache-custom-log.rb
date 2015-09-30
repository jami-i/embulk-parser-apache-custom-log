Embulk::JavaPlugin.register_parser(
  "apache-custom-log", "org.embulk.parser.ApacheCustomLogParserPlugin",
  File.expand_path('../../../../classpath', __FILE__))
