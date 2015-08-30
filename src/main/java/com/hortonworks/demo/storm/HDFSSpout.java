package com.hortonworks.demo.storm;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import org.apache.commons.io.FilenameUtils;
import org.apache.hadoop.hdfs.DFSInotifyEventInputStream;
import org.apache.hadoop.hdfs.client.HdfsAdmin;
import org.apache.hadoop.hdfs.inotify.Event;
import org.apache.hadoop.hdfs.inotify.EventBatch;
import com.hortonworks.demo.hdfs.Config;
import java.net.URI;
import java.security.PrivilegedExceptionAction;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * Created by acesir on 8/25/15.
 */
public class HDFSSpout extends BaseRichSpout {

    private SpoutOutputCollector _collector;
    //private static Logger _log = Logger.getLogger(HdfsSpout.class);
    private String _hdfsUri;
    private String _hdfsDirectory;

    public HDFSSpout(String hdfsUri, String hdfsDirectory) {
        _hdfsUri = hdfsUri;
        _hdfsDirectory = hdfsDirectory;
    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("hdfs-path"));
    }

    public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector collector) {
        _collector = collector;
    }

    public void fail(Object msgId) {
        //_log.error("Failed processing: " + (String) msgId);
        System.out.println("Failed processing: " + (String) msgId);
    }

    public void ack(Object msgId) {
        //_log.info("Completed processing: " + (String) msgId);
        System.out.println("Completed processing: " + (String) msgId);
    }

    public void nextTuple() {
        try {
            HdfsAdmin admin = new HdfsAdmin(new URI(_hdfsUri + _hdfsDirectory), new Config(_hdfsUri).get());
            final DFSInotifyEventInputStream eventStream = admin.getInotifyEventStream();

            new com.hortonworks.demo.hdfs.Config(_hdfsUri).getHdfsUgi().doAs(new PrivilegedExceptionAction<Void>() {
                public Void run() throws Exception {
                    while (true) {
                        EventBatch events = eventStream.take();
                        for (Event event : events.getEvents()) {
                            //System.out.println("event type = " + event.getEventType());
                            switch (event.getEventType()) {
                                case CREATE:
                                    Event.CreateEvent createEvent = (Event.CreateEvent) event;
                                    String newFile = createEvent.getPath().replace("._COPYING_", "");

                                    if (createEvent.getPath().contains(_hdfsDirectory)) {
                                        //utils.ReadFile(createEvent.getPath().replace("._COPYING_", ""));
                                        _collector.emit(new Values(newFile));
                                        //Date date = Calendar.getInstance().getTime();
                                        //String strUUID = new SimpleDateFormat("yyyyMMddhhmmssSSS").format(date);
                                        //String fileExt = FilenameUtils.getExtension(newFile);
                                    }
                                    break;
                                default:
                                    break;
                            }
                        }
                    }
                }
            });
            Thread.sleep(1000);
        }
        catch (Exception ex) {

        }

    }
}
