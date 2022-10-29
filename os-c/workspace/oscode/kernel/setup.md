### Compile kernel resources

```bash
cd $OS_FROM_SCRATCH/workspace/oscode/kernel/main.cpp
clang++ -O2 -Wall -g --target=x86_64-elf -ffreestanding -mno-red-zone \
  -fno-exceptions -fno-rtti  -std=c++17 -c main.cpp

ld.lld --entry KernelMain -z norelro --image-base 0x100000 --static \
  -o kernel.elf main.o
```
| option              | description                        |
|---------------------|------------------------------------|
| -O2                 | レベル2の最適化を行う                        |
| -Wall               | 警告を出力                              |
| -g                  | でバック情報付きでコンパイル                     |
| --target=x86_64-elf | x86_64向けの機械語を生成する.出力ファイルの形式をELFとする |
| -ffreestanding      | フリースタンディング環境向けにコンパイル               |
| -mno-red-zone       | Red Zone機能を無効にする                   |
| -fno-exceptions     | C++の例外機能を使わない                      |
| -fno-rtti           | C++の動的型情報を使わない                     |
| -std=c++17          | C++ version 17を指定                  |
| -c                  | コンパイルのみ、リンクはしない                    |

| option                | description                |
|-----------------------|----------------------------|
| --entry KernelMain    | KernelMain()をエントリポイントとする   |
| -z norelro            | リロケーション情報を読み込み専用にする機能を使わない |
| --image-base 0x100000 | 出力されたバイナリのベースアドレスを0x100000 |
| -o kernel.elf         | 出力ファイル名をkernel.elfとする      |
| --static              | 静的リンクを行う                   |

### Build Image and Run created OS
```bash
cd $OS_FROM_SCRATCH/edk2
build
$OS_FROM_SCRATCH/osbook/devenv/run_qemu.sh Build/MikanLoaderX64/DEBUG_CLANGPDB/X64/Loader.efi $OS_FROM_SCRATCH/workspace/oscode/kernel/kernl.elf
```

- 起動すると、カーネルの動作が永久ループで止まり、最終の `Print(L"All done\n")`が実行されない
- 確認方法
  1. `info registers`コマンドにて`RIP`の値を確認
    - 何度か実行し、値が変わっていなければ永久ループしている
  2. `RIP`の値付近のメインメモリ内容を確認
    - 

### How to check Register & Main memory
```bash
# step1
info registers
# output: RIP=000000003fb73016 RFL=00000046 [---Z-P-] CPL=0 II=0 A20=1 SMM=0 HLT=0

# step2
x /2i 0x101010
```
References: http://ylb.jp/Tech/x86_64ASM/x86_64_onMac.html
