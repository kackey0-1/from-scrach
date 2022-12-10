# [About Derby](https://db.apache.org/derby/docs/10.16/getstart/index.html)

- Start Derby Server

```bash
asdf local java corretto-17.0.4.9.1
java -jar $DERBY_HOME/lib/derbyrun.jar ij [-p propertiesfile] [sql_script]
java -jar $DERBY_HOME/lib/derbyrun.jar sysinfo [-cp ...] [-cp help]
java -jar $DERBY_HOME/lib/derbyrun.jar dblook [arg]* (or no arguments for usage)
java -jar $DERBY_HOME/lib/derbyrun.jar server [arg]* (or no arguments for usage)
```

