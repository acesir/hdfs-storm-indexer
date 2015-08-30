package com.hortonworks.demo.storm;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;
import com.hortonworks.demo.solr.Solr;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.util.Map;

/**
 * Created by acesir on 8/25/15.
 */
public class SolrBolt extends BaseRichBolt {
    private OutputCollector _collector;
    private String _hdfsUri;
    private String _solrHost;


    public void prepare(Map map, TopologyContext topologyContext, OutputCollector collector) {
        _collector = collector;
        _hdfsUri = (String)map.get("hdfsUri");
        _solrHost = (String)map.get("solrHost");
    }

    private void DocumentIndex(String file) {
        Solr solr = new Solr(_solrHost, _hdfsUri);
        solr.SolrCellIndex(file, "1");
    }

    public void execute(Tuple tuple) {

        try {
            DocumentIndex(tuple.getValueByField("hdfs-path").toString());
            _collector.ack(tuple);

            String fileExt = FilenameUtils.getExtension(tuple.getValueByField("hdfs-path").toString());
            if (fileExt.equals("png")) {
                _collector.emit("tesseract-stream", tuple, new Values(tuple.getValueByField("hdfs-path")));
            }
        }
        catch (Exception ex) {
            _collector.fail(tuple);
            System.out.println(ex.toString());
        }
    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declareStream("tesseract-stream", new Fields("image"));
        declarer.declare(new Fields("solr"));
    }
}
