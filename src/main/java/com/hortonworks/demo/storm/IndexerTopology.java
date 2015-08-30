package com.hortonworks.demo.storm;

import backtype.storm.topology.TopologyBuilder;
import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.utils.Utils;
import com.hortonworks.demo.solr.Solr;

/**
 * Created by acesir on 8/25/15.
 */
public class IndexerTopology {
    public static void main( String[] args) {

        Config conf = new Config();
        conf.put("hdfsUri", "hdfs://adis-dal01.cloud.hortonworks.com:8020");
        conf.put("hdfsPath", "/falcon");
        conf.put("solrHost", "http://adis-dal06.cloud.hortonworks.com:8983/solr/fiserv");
        conf.put("zooKeeper", "adis-dal01.cloud.hortonworks.com,adis-dal02.cloud.hortonworks.com,adis-dal03.cloud.hortonworks.com");

        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("hdfs", new HDFSSpout("hdfs://node1.hadoop.com:8020", "/falcon"), 1);
        builder.setBolt("hbase", new HBaseBolt(), 5).shuffleGrouping("hdfs");
        builder.setBolt("solr", new SolrBolt(), 5).shuffleGrouping("hdfs");
        builder.setBolt("tesseract", new TesseractBolt(),5).shuffleGrouping("solr", "tesseract-stream");

        conf.setDebug(true);

        // never timeout
        conf.setMessageTimeoutSecs(Integer.MAX_VALUE);

        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology("test", conf, builder.createTopology());
        Utils.sleep(2000);
        //cluster.killTopology("test");
        //cluster.shutdown();

    }
}
