# Web Browser from scratch

- OS: Ubuntu
- Lang: C

## How to setup ubuntu on M1 Mac
https://medium.com/@paulrobu/how-to-run-ubuntu-22-04-vms-on-apple-m1-arm-based-systems-for-free-c8283fb38309

```bash
brew install multipass
multipass version
# launch a 22.04 Ubuntu VM named primary that has allocated 2 CPUs, 4 GB of memory and 50 GB of disk space
multipass launch 22.04 -n primary -c 2 -m 4G -d 50G
multipass launch 22.04
multipass shell
```

setup ubuntu
```bash
sudo apt update && sudo apt upgrade
sudo apt install ubuntukylin-desktop xrdp -y
# if necessary
sudo passwd ubuntu
sudo adduser username
sudo usermod -aG sudo username
```

