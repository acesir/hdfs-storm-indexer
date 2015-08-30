package com.hortonworks.demo.storm;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Tuple;
import com.hortonworks.demo.solr.Solr;
import com.hortonworks.demo.tesseract.TesseractOCR;
import org.apache.commons.io.FilenameUtils;

import java.util.Map;

/**
 * Created by acesir on 8/26/15.
 */
public class TesseractBolt extends BaseRichBolt {
    private OutputCollector _collector;
    private String _solrHost;
    private String _hdfsUri;

    public void prepare(Map map, TopologyContext topologyContext, OutputCollector collector) {
        _collector = collector;
        _hdfsUri = (String)map.get("hdfsUri");
        _solrHost = (String)map.get("solrHost");
    }

    private void DocumentUpdate(String imageFile) {
        Solr solr = new Solr(_solrHost, _hdfsUri);
        TesseractOCR tesseract = new TesseractOCR(_hdfsUri);
        solr.UpdateDocument(FilenameUtils.getName(imageFile), tesseract.Parse(imageFile));
    }


    public void execute(Tuple tuple) {
        try {
            System.out.println("Got emited SolrBolt stream: " + tuple.getValueByField("image").toString());
            DocumentUpdate(tuple.getValueByField("image").toString());
            _collector.ack(tuple);

        }
        catch (Exception ex) {
            _collector.fail(tuple);
        }
    }

    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {

    }
}
