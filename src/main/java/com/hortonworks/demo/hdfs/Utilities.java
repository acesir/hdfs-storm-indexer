package com.hortonworks.demo.hdfs;

import org.apache.commons.io.IOUtils;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

/**
 * Created by acesir on 8/25/15.
 */
public class Utilities {
    private String _hdfsUri;

    public Utilities(String hdfsUri) {
        _hdfsUri = hdfsUri;
    }


    public byte[] ReadHdfsFile(String path) {
        byte[] fileBytes = null;
        try {
            Config hdfs = new Config(_hdfsUri);
            FileSystem fs = FileSystem.get(hdfs.get());
            Path hdfsPath = new Path(path);

            FSDataInputStream fsi = new FSDataInputStream(fs.open(hdfsPath));

            fileBytes = IOUtils.toByteArray(fsi);

        } catch (Exception ex) {
            System.out.println("ReadHdfsFile: " + ex.toString());
        } finally {
            return fileBytes;
        }
    }
}
