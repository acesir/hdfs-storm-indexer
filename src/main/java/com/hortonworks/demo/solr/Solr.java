package com.hortonworks.demo.solr;

import org.apache.commons.io.FilenameUtils;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.ContentStreamUpdateRequest;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.util.ContentStreamBase;
import com.hortonworks.demo.hdfs.Utilities;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by acesir on 8/25/15.
 */
public class Solr {

    private String _solrHost;
    private SolrClient _solrClient;
    private String _hdfsUri;

    public Solr (String solrHost, String hdfsUri) {

        _solrHost = solrHost;
        _hdfsUri = hdfsUri;
    }

    public void SolrCellIndex(String filename, String UUID) {

        _solrClient = new HttpSolrClient(_solrHost);
        try {

            ContentStreamUpdateRequest req = new ContentStreamUpdateRequest("/update/extract");

            ContentStreamBase.ByteArrayStream fileStream = new ContentStreamBase.ByteArrayStream(new Utilities(_hdfsUri).ReadHdfsFile(filename), "test");
            req.setParam("literal.id", FilenameUtils.getName(filename));
            req.setParam("literal.resourcename", filename);
            req.addContentStream(fileStream);
            UpdateResponse process = req.process(_solrClient);
            process.getStatus();
            _solrClient.commit();
        }
        catch (Exception ex) {
            System.out.println("SolrCellIndex: " + ex.toString());
        }
    }

    public void UpdateDocument(String fileName, String content) {
        _solrClient = new HttpSolrClient(_solrHost);
        try {
            SolrInputDocument sdoc = new SolrInputDocument();
            sdoc.addField("id", fileName);
            Map<String, Object> fieldModifier = new HashMap(1);
            fieldModifier.put("set", content);
            sdoc.addField("content", fieldModifier);

            _solrClient.add(sdoc);
            _solrClient.commit();
            _solrClient.close();
        }
        catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }
}
