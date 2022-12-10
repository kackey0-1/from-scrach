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
        - readBlock(n, p)
            - read the bytes at block n of the disk into page p of memory
        - writeBlock(n, p)
            - write the bytes in page p of memory to block n of the disk
        - allocate(k, n)
            - find k contiguous unused blocks on disk, marks them as used, and returns the block number of the first
            - the new blocks should be located as close to block n as possible
        - deallocate
            - marks the k contiguous blocks starting with block n as unused

- File-level interface
    - the operating system provides a file-level interface to the disk
        - the contents of a file can be accessed directly from the disk
        - to modify the contents of a file, the operating system must read the entire file into memory, modify the file, and write the file back to the disk
    - file-level enables a file to be thought of as a sequence of blocks
    - given a particular file location, the seek method determines actual disk block that holds that location. in particular, seek performs two conversions
        - converts the specified byte position  to a logical block reference
        - converts the logical block reference to a physical block reference
    - Allocation
        - Continuous Allocation
        - Extent-Based Allocation
        - Indexed Allocation

# Database System and OS
- Which Interface(block-level or file-level) would be good for the database system?
    - block-level support
        - Advantage
            - giving the engine complete control over which disk blocks can be stored in the middle of the disk, where the seek time will be less
            - blocks that tend to be accessed together can be stored near each other
            - database engine is not constrained by OS limitations on files, allowing it to support tables that are larger than the OS limit or span multiple files
        - Disadvantage
            - complex to implement such a strategy
            - it requires the disk be formatted and mounted as raw disk, that is a disk whose blocks are not part of the file system
            - it requires DB administtrators to be familiar with the disk formatting and mounting process
    - file-level support
        - Advantage
            - database engine can use the OS file system as much as possible
            - much easier to implement than block-level support
            - allows the OS  to hide the actual disk location of the file from database engine

        - But these situation is not acceptable because of the following reasons
            - the database system needs to know where the block baundaries are, so that it can organize and retrieve data efficiently
            - the database system needs to manage its own pages because the OS way of managing I/O buffers is inappropriate for database query



