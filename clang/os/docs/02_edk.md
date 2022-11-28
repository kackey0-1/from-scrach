```bash
export OS_FROM_SCRATCH=$HOME/dev/from-scrach/os-c
cd $OS_FROM_SCRATCH/edk2
ln -s $OS_FROM_SCRATCH/workspace/oscode/MikanLoaderPkg ./

l MikanLoaderPkg
rm MikanLoaderPkg
ln -s $OS_FROM_SCRATCH/workspace/oscode/MikanLoaderPkg ./

source edksetup.sh

vim Conf/target.txt

build

ll $OS_FROM_SCRATCH/edk2/Build/MikanLoaderX64/DEBUG_CLANGPDB/X64/Loader.efi
$OS_FROM_SCRATCH/osbook/devenv/run_qemu.sh $OS_FROM_SCRATCH/edk2/Build/MikanLoaderX64/DEBUG_CLANGPDB/X64/Loader.efi
```
