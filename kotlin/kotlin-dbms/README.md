# [About Derby](https://db.apache.org/derby/docs/10.16/getstart/index.html)

- Start Derby Server

```bash
asdf local java corretto-17.0.4.9.1
java -jar $DERBY_HOME/lib/derbyrun.jar ij [-p propertiesfile] [sql_script]
java -jar $DERBY_HOME/lib/derbyrun.jar sysinfo [-cp ...] [-cp help]
java -jar $DERBY_HOME/lib/derbyrun.jar dblook [arg]* (or no arguments for usage)
java -jar $DERBY_HOME/lib/derbyrun.jar server [arg]* (or no arguments for usage)
```

# Database Design and Implementation

## JDBC (Java Database Connectivity)

## Disk and File Management

### Measurement of disk drive performance

- Capacity
  - the number of bytes that can be stored, which depends on the number of platters, the number of tracks per platter, and the number of sectors per track
  - platter capacities of over 40GB are common
  - (500,000bytes/track * 10,000tracks)/platter * 4platters / drive = 20GB
- Rotation Speed
  - the rate at which the platters spin and is usually expressed in revolutions per minute (RPM)
  - speed range from 5400rpm to 15,000rpm are common
- Transfer Rate
  - the rate at which bytes pass by the disk head
  - 500,000bytes/revolution * 10,000revolutions / 60seconds = 83333333bytes/second 
- Seek Time
  - the time required to move the disk head to a particular track

### Improving disk performance
- Disk Caching
  - the operating system maintains a cache of recently accessed data in memory
  - the cache is used to reduce the number of disk accesses
  - the cache is also used to reduce the number of disk seeks
- Cylinder-Seek Scheduling
  - the database system can improve disk access time by storing related information on nearby sectors(tracks)
- Disk Striping
  - the database system can improve disk access time by using multiple drives
  - two small drives are faster than one large drive

### Raid
- Redundant Array of Independent Disks
  - a collection of disks that are treated as a single logical disk
  - the disks are organized into a RAID group
  - the disks in a RAID group are called RAID members
- For improving disk performance, RAID groups are organized into RAID levels 
  - striping to improve disk access time
  - mirroring for recovery
  - storing parity for recovery as well
- For Improving disk reliability by mirroring and storing parity

### Flash Drives
- Flash drives are non-volatile storage devices
- Flash drives are about 100 times faster than disk drives
  - seek time is around 50 microseconds

as far as the database engine is concerned, a flash drive has the same properties as a disk drive

### Interfaces to the Disk

- Block-level interface
  - the operating system provides a block-level interface to the disk
    - the contents of a block cannot be accessed directly from the disk, instead, the entire block must be read into memory page and accessed from there
    - to modify the contents of a block, the entire block must be read into memory, the page modified, and the block written back to the disk
  - an OS typically provides several types of method
    - readBlock(n, p): read the bytes at block n of the disk into page p of memory
    - writeBlock(n, p): write the bytes in page p of memory to block n of the disk
    - allocate: 
    - deallocate

- File-level interface
  - the operating system provides a file-level interface to the disk
    - the contents of a file can be accessed directly from the disk
    - to modify the contents of a file, the operating system must read the entire file into memory, modify the file, and write the file back to the disk

