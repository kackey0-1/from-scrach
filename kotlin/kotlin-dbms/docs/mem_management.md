# Database Design and Implementation

## Memory Management

There are two principals for memory management

1. Minimize Disk Accesses
   - RAM operations are over 1000 times faster than flash, and 100,000 times faster than disk
   - So the time it takes to read/write from the block from disk is at least as large as the time it takes to process the block in RAM
   - To minimize block accesses is to avoid accessing a disk block multiple times
     - use caching
   - a database engine uses memory pages to cache disk blocks

2. DO NOT Rely on Virtual Memory
   - 