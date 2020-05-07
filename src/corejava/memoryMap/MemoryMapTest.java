package corejava.memoryMap;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.CRC32;

public class MemoryMapTest {
    public static void main(String[] args) throws IOException{
        //指定一个jar计算CRC
        String filepath = "C:\\Program Files\\Java\\jre1.8.0_211\\lib\\jfxswt.jar";
        System.out.println("Input Stream: ");
        long start = System.currentTimeMillis();
        Path filename = Paths.get(filepath);
        long crcValue = checksumInputStream(filename);
        long end = System.currentTimeMillis();
        System.out.println(Long.toHexString(crcValue));
        System.out.println((end - start) + " milliseconds.");

        System.out.println("Buffered Input Stream: ");
        start = System.currentTimeMillis();
        filename = Paths.get(filepath);
        crcValue = checksumBufferedInputStream(filename);
        end = System.currentTimeMillis();
        System.out.println(Long.toHexString(crcValue));
        System.out.println((end - start) + " milliseconds.");

        System.out.println("Random Access File: ");
        start = System.currentTimeMillis();
        filename = Paths.get(filepath);
        crcValue = checksumRandomAccessFile(filename);
        end = System.currentTimeMillis();
        System.out.println(Long.toHexString(crcValue));
        System.out.println((end - start) + " milliseconds.");

        System.out.println("Mapped File: ");
        start = System.currentTimeMillis();
        filename = Paths.get(filepath);
        crcValue = checksumMapperFile(filename);
        end = System.currentTimeMillis();
        System.out.println(Long.toHexString(crcValue));
        System.out.println((end - start) + " milliseconds.");
    }

    public static long checksumInputStream(Path filename)throws IOException{
        try(InputStream in = Files.newInputStream(filename)){
            CRC32 crc = new CRC32();
            int c;
            while( (c=in.read()) != -1)
                crc.update(c);
            return crc.getValue();
        }
    }

    public static long checksumBufferedInputStream(Path filename)throws IOException{
        try(InputStream in = new BufferedInputStream(Files.newInputStream(filename))){
            CRC32 crc = new CRC32();
            int c;
            while( (c=in.read()) != -1)
                crc.update(c);
            return crc.getValue();
        }
    }

    public static long checksumRandomAccessFile(Path filename)throws IOException{
        try(RandomAccessFile file = new RandomAccessFile(filename.toFile(),"r")){
            long length = file.length();
            CRC32 crc = new CRC32();
            for(long p = 0;p <length;p++){
                file.seek(p);
                int c = file.readByte();
                crc.update(c);
            }
            return crc.getValue();
        }
    }

    public static long checksumMapperFile(Path filename) throws IOException{
        try(FileChannel channel = FileChannel.open(filename)){
            CRC32 crc = new CRC32();
            int length = (int)channel.size();
            //将整个文件映射到内存中
            MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_ONLY,0,length);
            for(int p = 0;p <length;p++){
                int c = buffer.get(p);
                crc.update(c);
            }
            return crc.getValue();
        }
    }


}
