import java.io.RandomAccessFile;

public class Main {
    public static void main(String[] args) throws Exception {
        RandomAccessFile f = new RandomAccessFile("junk", "rws");
        f.seek(7992);
        int n = f.readInt();
        f.write(n + 1);
        f.close();
    }
}