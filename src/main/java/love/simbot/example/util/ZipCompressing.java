package love.simbot.example.util;

import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.zip.Zip64Mode;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.Collection;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipCompressing {
    /**
     * 压缩文件夹下的所有文件
     * @param dir          要压缩的文件夹
     * @param outputStream 输出压缩后的文件流
     * @throws IOException      IO异常
     * @throws ArchiveException 压缩异常
     */
    public static void zip(File dir, OutputStream outputStream) throws IOException, ArchiveException {
        ZipArchiveOutputStream zipOutput = null;
        try {
            zipOutput = (ZipArchiveOutputStream) new ArchiveStreamFactory()
                    .createArchiveOutputStream(ArchiveStreamFactory.ZIP, outputStream);
            zipOutput.setEncoding("utf-8");
            zipOutput.setUseZip64(Zip64Mode.AsNeeded);
            Collection<File> files = FileUtils.listFilesAndDirs(dir, TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);

            for (File file : files) {
                InputStream in = null;
                try {
                    if (file.getPath().equals(dir.getPath())) {
                        continue;
                    }
                    String relativePath = StringUtils.replace(file.getPath(), dir.getPath() + File.separator, "");
                    ZipArchiveEntry entry = new ZipArchiveEntry(file, relativePath);
                    zipOutput.putArchiveEntry(entry);
                    if (file.isDirectory()) {
                        continue;
                    }

                    in = new FileInputStream(file);
                    IOUtils.copy(in, zipOutput);
                    zipOutput.closeArchiveEntry();
                } finally {
                    if (in != null) {
                        IOUtils.closeQuietly(in);
                    }
                }
            }
            zipOutput.finish();
        } finally {
            IOUtils.closeQuietly(zipOutput);
        }
    }



}

