package com.hortonworks.demo.storm;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import com.hortonworks.demo.solr.Solr;

import java.util.Map;

/**
 * Created by acesir on 8/25/15.
 */
public class HBaseBolt extends BaseRichBolt {
    private OutputCollector _collector;
    private Solr solr;

    public void prepare(Map map, TopologyContext topologyContext, OutputCollector collector) {

        _collector = collector;
    }

    public void execute(Tuple tuple) {
        try {

            _collector.ack(tuple);

        }
        catch (Exception ex) {
            _collector.fail(tuple);
        }
    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("output_path"));
    }
}
