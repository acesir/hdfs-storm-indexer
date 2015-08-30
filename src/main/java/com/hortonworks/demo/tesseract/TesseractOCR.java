package com.hortonworks.demo.tesseract;

import net.sourceforge.tess4j.Tesseract;
import org.apache.hadoop.security.UserGroupInformation;
import com.hortonworks.demo.hdfs.Utilities;
import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;

/**
 * Created by acesir on 8/25/15.
 */
public class TesseractOCR {
    private String _hdfsUri;

    public TesseractOCR(String hdfsUri) {
        _hdfsUri = hdfsUri;
    }
    public String Parse(String imageFile) {
        UserGroupInformation ugi = UserGroupInformation.createRemoteUser("root");
        String pngImageDoc = "";

        Tesseract instance = Tesseract.getInstance();
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(new Utilities(_hdfsUri).ReadHdfsFile(imageFile));
            pngImageDoc = instance.doOCR(ImageIO.read(bais));

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return pngImageDoc;
    }
}
