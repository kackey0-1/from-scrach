# Database Design and Implementation

## Memory Management

## There are two principals for memory management

1. Minimize Disk Accesses
   - RAM operations are over 1000 times faster than flash, and 100,000 times faster than disk
   - So the time it takes to read/write from the block from disk is at least as large as the time it takes to process the block in RAM
   - To minimize block accesses is to avoid accessing a disk block multiple times
     - use caching
   - a database engine uses memory pages to cache disk blocks

2. DO NOT Rely on Virtual Memory
   - The virtual memory spaces are supported by an OS is usually far larger than a computer's physical memory. Since not all virtual pages fit in physical memory, the OS must store some of them on disk
   - As the database system accessed these pages, the virtual memory mechanism will swap them between disk and physical memory, as needed
     - this is easily implemented strategy
     - but it has serious problems, which that the OS, not a database engine, controls when pages get written to disk
       - Issue1: the OS's page-swapping strategy can impair the database engine's ability to recover after a system crash
       - Issue2: the OS has idea which pages are currently in use and which pages the database engine no longer cares about
       - Therefore, the database engine must be able to control when pages are written to disk
         - most straightforward way to manage disk block is to give each block its own virtual page


