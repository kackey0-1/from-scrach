

# Database Engine Components
- FileManager
- LogManager
- BufferManager
- TransactionManager

## FileManager
- responsible for the operation of the OS's file system
- the database engine uses the file manager to read/write blocks between disk and [pages on memory]

## LogManager
- responsible for writing log records to the log file
  - to keep track of the changes made to the database in case it needs to be undone


## BufferManager
- responsible for the pages that hold user data
- the buffer manager allocates a fixed set of pages, called *the buffer pool*
- the buffer pool should fit into the computer's physical memory, and these pages should come from the I/O buffers held by the OS
- Buffer Replacement Strategies
  - The Naive Strategy
  - The FIFO Strategy
  - The LRU Strategy
  - The Clock Strategy

## Transaction Manager

- Transaction consists of three categories
  - 1st category consists of methods related to transaction's lifespan
    - commit/rollback
  - 2nd category consists of methods to access buffers(memory)
    - pin/unpin
  - 3rd category consists of methods related to the file manager
    - size/append/blockSize

# [About Derby](https://db.apache.org/derby/docs/10.16/getstart/index.html)

- Start Derby Server

```bash
asdf local java corretto-17.0.4.9.1
java -jar $DERBY_HOME/lib/derbyrun.jar ij [-p propertiesfile] [sql_script]
java -jar $DERBY_HOME/lib/derbyrun.jar sysinfo [-cp ...] [-cp help]
java -jar $DERBY_HOME/lib/derbyrun.jar dblook [arg]* (or no arguments for usage)
java -jar $DERBY_HOME/lib/derbyrun.jar server [arg]* (or no arguments for usage)
```