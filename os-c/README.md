# OS自作

## Environment
- OS: Ubuntu
  - [How to run Ubuntu on M1 Mac](https://medium.com/@paulrobu/how-to-run-ubuntu-22-04-vms-on-apple-m1-arm-based-systems-for-free-c8283fb38309)
- Emulator
  - https://www.qemu.org/download/

## How to setup
[How to setup for OS from scrach](https://qiita.com/yamoridon/items/4905765cc6e4f320c9b5)

```bash
export OS_FROM_SCRATCH=$HOME/dev/from-scrach/os-c
```

### EDK II の準備

```bash
cd $OS_FROM_SCRATCH
git clone https://github.com/tianocore/edk2.git
cd edk2
git checkout 38c8be123aced4cc8ad5c7e0da9121a181b94251
git submodule init
git submodule update
cd BaseTools/Source/C
make
```

### mikanos-build リポジトリの準備
```bash
cd $OS_FROM_SCRATCH
git clone https://github.com/uchan-nos/mikanos-build.git osbook
cd osbook/devenv
curl -L https://github.com/uchan-nos/mikanos-build/releases/download/v2.0/x86_64-elf.tar.gz | tar xz
cd ..
```

mac.patch というファイルを作って適用

```diff
--- a/devenv/make_image.sh
+++ b/devenv/make_image.sh
@@ -23,11 +23,24 @@ qemu-img create -f raw $DISK_IMG 200M
 mkfs.fat -n 'MIKAN OS' -s 2 -f 2 -R 32 -F 32 $DISK_IMG

 $DEVENV_DIR/mount_image.sh $DISK_IMG $MOUNT_POINT
-sudo mkdir -p $MOUNT_POINT/EFI/BOOT
-sudo cp $EFI_FILE $MOUNT_POINT/EFI/BOOT/BOOTX64.EFI
+if [ `uname` = 'Darwin' ]; then
+    mkdir -p $MOUNT_POINT/EFI/BOOT
+    cp $EFI_FILE $MOUNT_POINT/EFI/BOOT/BOOTX64.EFI
+else
+    sudo mkdir -p $MOUNT_POINT/EFI/BOOT
+    sudo cp $EFI_FILE $MOUNT_POINT/EFI/BOOT/BOOTX64.EFI
+fi
 if [ "$ANOTHER_FILE" != "" ]
 then
-    sudo cp $ANOTHER_FILE $MOUNT_POINT/
+    if [ `uname` = 'Darwin' ]; then
+        cp $ANOTHER_FILE $MOUNT_POINT/
+    else
+        sudo cp $ANOTHER_FILE $MOUNT_POINT/
+    fi
 fi
 sleep 0.5
-sudo umount $MOUNT_POINT
+if [ `uname` = 'Darwin' ]; then
+    hdiutil detach $MOUNT_POINT
+else
+    sudo umount $MOUNT_POINT
+fi
diff --git a/devenv/mount_image.sh b/devenv/mount_image.sh
index ba8233e..aea4d7d 100755
--- a/devenv/mount_image.sh
+++ b/devenv/mount_image.sh
@@ -16,5 +16,9 @@ then
     exit 1
 fi

-mkdir -p $MOUNT_POINT
-sudo mount -o loop $DISK_IMG $MOUNT_POINT
+if [ `uname` = 'Darwin' ]; then
+    hdiutil attach -mountpoint $MOUNT_POINT $DISK_IMG
+else
+    mkdir -p $MOUNT_POINT
+    sudo mount -o loop $DISK_IMG $MOUNT_POINT
+fi
```

```bash
patch -p1 < mac.patch
```

### QEMU のインストール
```bash
brew install qemu
```

### LLVM のインストール
```bash
brew install llvm
# Intel Mac の場合
# export PATH=/usr/local/opt/llvm/bin:$PATH
# Apple Silicon Mac の場合
export PATH=/opt/homebrew/opt/llvm/bin:$PATH
```

### その他のビルドに必要なパッケージのインストール
```bash
brew install nasm dosfstools binutils
# Intel Mac の場合
# export PATH=/usr/local/opt/binutils/bin:$PATH
# Apple Silicon Mac の場合
export PATH=/opt/homebrew/sbin:/opt/homebrew/opt/binutils/bin:$PATH
```

### 「2.2 EDK II でハローワールド」をビルドする
```bash
cd $OS_FROM_SCRATCH
mkdir workspace
cd workspace
git clone https://github.com/uchan-nos/mikanos.git
cd mikanos
git checkout osbook_day02a
cd $OS_FROM_SCRATCH/edk2
ln -s $OS_FROM_SCRATCH/workspace/mikanos/MikanLoaderPkg .
source edksetup.sh
```

- Conf/target.txt を開き次のように変更
  - TOOL_CHAIN_TAG が本とは異なるので注意
  - CLANG38 ツールチェインは Linux でしか使えないため代わりに CLANGPDB ツールチェインを使用

| 設定項目            | 設定値                               |
|-----------------|-----------------------------------|
| ACTIVE_PLATFORM | MikanLoaderPkg/MikanLoaderPkg.dsc |
| TARGET          | DEBUG                             |
| TARGET_ARCH     | X64                               |
| TOOL_CHAIN_TAG  | CLANGPDB                          |

```bash
build
```

$OS_FROM_SCRATCH/edk2/Build/MikanLoaderX64/DEBUG_CLANGPDB/X64/Loader.efi というファイルが作られれば成功

### QEMU で実行
```bash
$OS_FROM_SCRATCH/osbook/devenv/run_qemu.sh $OS_FROM_SCRATCH/edk2/Build/MikanLoaderX64/DEBUG_CLANGPDB/X64/Loader.efi
```

