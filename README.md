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


## Field / Column mappings

see: [LogFormats.java](https://github.com/jami-i/embulk-parser-apache-custom-log/blob/develop/src/main/java/org/embulk/parser/apache/log/LogFormats.java)

|flag| type      | column-name             |parameter|
|:--:|-----------|-------------------------|:---------|
| a  | String    | remote-ip               |          |
| A  | String    | local-ip                |          |
| b  | Long      | response-bytes          |          |
| B  | Long      | response-bytes          |          |
| C  | String    | request-cookie          | cookie name ex: ```%{SESSIONID}C``` => request-cookie-SESSIONID|
| D  | Long      | request-process-time-us |          |
| e  | String    | env                     | environment variable name ex: ```%{UNIQUE_ID}C``` => env-UNIQUE_ID|
| f  | String    | file-name               |          |
| h  | String    | remote-host             |          |
| H  | String    | request-protocol        |          |
| i  | String    | request-header          | request header name ex: ```%{User-Agent}i``` => request-header-User-Agent|
| l  | String    | remote-log-name         |          |
| m  | String    | request-method          |          |
| n  | String    | module-note             |          |
| o  | String    | response-header         | response header name ex: ```%{Location}o``` => response-header-Location|
| p  | Long      | request-port            |          |
| P  | Long      | request-process         |          |
| q  | String    | request-query           |          |
| r  | String    | request-line            |          |
| s  | Long      | response-status         |          |
| t  | Timestamp | request-time            | timestamp format defined in [strptime](http://docs.ruby-lang.org/en/2.0.0/DateTime.html#method-c-_strptime)|
| T  | Long      | request-process-time-s  |          |
| u  | String    | request-user            |          |
| U  | String    | request-path            |          |
| v  | String    | request-server-name     |          |
| V  | String    | canonical-server-name   |          |
| X  | String    | connection-status       |          |
| I  | Long      | request-total-bytes     |          |
| O  | Long      | response-total-bytes    |          |
| %  | String    | %                       |          |
