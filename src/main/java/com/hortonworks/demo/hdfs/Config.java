package com.hortonworks.demo.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.security.UserGroupInformation;

/**
 * Created by acesir on 8/25/15.
 */
public class Config {
    Configuration conf = new Configuration();
    private String _hdfsUri;

    public Config(String hdfsUri){
        System.setProperty("HADOOP_USER_NAME", "hdfs");
        _hdfsUri = hdfsUri;
    }

    public Configuration get() {

        conf.set("fs.defaultFS", _hdfsUri);
        conf.set("hadoop.job.ugi", "hdfs");

        System.setProperty("HADOOP_USER_NAME", "hdfs");

        conf.set("fs.hdfs.impl", org.apache.hadoop.hdfs.DistributedFileSystem.class.getName());
        conf.set("fs.file.impl", org.apache.hadoop.fs.LocalFileSystem.class.getName());

        return conf;
    }

    public UserGroupInformation getHdfsUgi() {
        UserGroupInformation ugi = UserGroupInformation.createRemoteUser("hdfs");
        return ugi;
    }
}
