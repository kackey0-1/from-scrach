# EDK II入門 & メモリマップ

## EDK IIとは？

- EDK II は元々Intelが`UEFI`とその周辺プログラムを実装し、それが後にオープンソースとして公開されたもの
- `https://github.com/tianocore/edk2` が公式Repository

| Directory or File | Content                                                                                     |
|-------------------|---------------------------------------------------------------------------------------------|
| edksetup.sh       | EDK IIビルドコマンドが動くようにするための準備スクリプト                                                             |
| Conf Dir          | 何をビルドするかを設定する`target.txt`と、ビルドに用いるコンパイルを設定する`tools_def.txt`を配置                              |
| HogePkg Dir       | 各種のプログラムがパッケージ単位で格納されている(Hoge=任意の値)                                                         |
| MdePkg Dir        | EDKの中心的ライブラリで他のプログラムからよく利用されるライブラリ                                                          |
| AppPkg Dir        | UEFIアプリケーションをいくつか含んでいる                                                                      |
| OvmPkg Dir        | UEFI BIOSのOSS実装であるOVMFが格納されている.EDK IIはUEFI BIOS上で動くアプリケーションだけでなく、UEFI BIOSそのものを開発するためにも使われる |




