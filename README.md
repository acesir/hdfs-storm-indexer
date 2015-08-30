# hdfs-storm-indexer

Storm HDFS File Indexer.

Spout listens for HDFS iNotify streams in target directory.
SolrBolt pushes binary data stream for indexing in Solr.
HBaseBolt stores raw image with linked ID between Solr.
TesseractBolt parses images text and updates Solr document.
