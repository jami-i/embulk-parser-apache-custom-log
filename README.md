# Apache **CustomLog**  parser plugin for Embulk

Embulk parser plugin for apache **CustomLog**.

Parser configuration based  [Apache HTTPD 2.2 CustomLogFormat](http://httpd.apache.org/docs/2.2/en/mod/mod_log_config.html#formats)

## Overview

* **Plugin type**: parser
* **Guess supported**: no

## Configuration

- **format**: Apache CustomLog Format (string, required)

    see: http://httpd.apache.org/docs/2.2/en/mod/mod_log_config.html#customlog

    each format key

## Example

```yaml
in:
  type: any file input plugin type
  parser:
    type: apache-custom-log
    format: "%v %{X-Forwarded-For}i %l %u %t \"%m %U%q %H\" %>s %b \"%{Referer}i\" \"%{User-Agent}i\" %I %O %D"
```

```
$ embulk gem install embulk-parser-apache-custom-log
```

## Build

```
$ ./gradlew gem
```
