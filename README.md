# Storm HDFS File Indexer

* Spout listens for HDFS iNotify streams in target directory
* SolrBolt pushes binary data stream for indexing in Solr
* HBaseBolt stores raw image with linked ID between Solr
* TesseractBolt parses images text and updates Solr document
* ASP .NET WebApp to talk to HBase and Solr for uploading as well as retrieinvg and searching raw file content

![ScreenShot](https://raw.github.com/acesir/hdfs-storm-indexer/master/architecture.png)
