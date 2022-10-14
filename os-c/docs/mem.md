# Memory

- OSが正しく動作するためにはメインメモリの様子をきちんと把握する必要がある
  - OS作りに先立ち、UEFIの機能を使ってメモリマップを取得するプログラムを作成する

## Main Memory 

- ハードウェアの視点
  - 複数のメモリチップから構成される
- ソフトウェアの視点
  -  CPUに搭載されているメモリコントローラのおかげで、以下のように多数のバイトが隙間なく直線上に整列しているように見える
  - それぞれのバイトには0から始まる連番が振られていて、CPUはこの連番を用いることで、メインメモリのデータをバイト単位で読み書き可能

```
0| | | | | | | | | 1バイト
1| | | | | | | | |
2| | | | | | | | |
.|               |
.|               |
.|               |
.|               |
.|               |
.|               |
```

## Memory Map

メモリマップはメインメモリがどの部分がどんな用途で利用されているかが載っている地図

- `PhysicalStart`列の数値: Memory Mapにおける住所(like番地)
  - `PhysicalStart`列の数値はバイト単位の値
- `Type`列はその領域が何に使われているかを示す
- `NumberOfPages`列はメモリ領域の大きさをページ単位で著した数値
  - 1pageの大きさは文脈によって異なるが、UEFIのメモリマップにおける1pageの大きさは4KiB
    - x86_l464 Architecture CPUでは、pagingという機能があり、ページサイズは4KiB, 2MiB, 4MiB, 1Gibのいずれかに設定可能
  - メモリ領域の大きさは全てページ単位で表せるのに対し、番地はバイト単位で表される

### Sample Memory Map
| PhysicalStart | Type                  | NumberOfPages |
|---------------|-----------------------|---------------|
| 0x00000000    | EfiBootServicesCode   | 0x1           |
| 0x00001000    | EfiConventionalMemory | 0x9F          |
| 0x00100000    | EfiConventionalMemory | 0x700         |
| 0x00000000    | EfiACPIMemoryNVS      | 0x8           |
| ...           | ...                   | ...           |

### Samples of Memory Map Usage Type
| Type値 | Type名                   | 意味                   |
|-------|-------------------------|----------------------|
| 1     | EfiLoaderCode           | UEFIアプリケーションの実行コード   |
| 2     | EfiLoaderData           | UEFIアプリケーションの使うデータ領域 |
| 3     | EfiBootService          | ブートサービスドライバの実行コード    |
| 4     | EfiBootServiceDate      | ブートサービスドライバが使うデータ領域  |
| 7     | EfiConventionalMemory   | 空き領域                 |
