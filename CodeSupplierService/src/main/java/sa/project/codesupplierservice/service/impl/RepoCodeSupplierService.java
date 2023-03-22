package sa.project.codesupplierservice.service.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sa.project.codesupplierservice.service.ICodeSupplierService;
import sa.project.codesupplierservice.utils.ICodeReadWrite;
import sa.project.codesupplierservice.utils.IZipper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
@RequiredArgsConstructor
public class RepoCodeSupplierService implements ICodeSupplierService {

    private final ICodeReadWrite codeReadWrite;
    private final IZipper zipper;

    private long currentTimeStamp;

    @Value("${css.cds.src}")
    private String cdsSrc;

    @Value("${css.ss.src}")
    private String ssSrc;

    @Value("${css.rs.src}")
    private String rsSrc;

    @Value("${zipDir}")
    private String zipDir;

    @Value("${codeDir}")
    private String codeDir;

    @Value("${zipOutDir}")
    private String zipOutDir;

    private void downloadZip(String serviceName, String dest) throws IOException {
        String srcUrl = "";
        if (serviceName.equals("cds"))
            srcUrl = cdsSrc;
        else if (serviceName.equals("ss"))
            srcUrl = ssSrc;
        else
            srcUrl = rsSrc;

        try(FileOutputStream fileOutputStream = new FileOutputStream(dest)) {
            ReadableByteChannel readableByteChannel = Channels.newChannel(new URL(srcUrl).openStream());
            fileOutputStream.getChannel().transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
        }
    }

    @Override
    public void getCDSCode(String topic, File workDir) throws IOException {
        // code regeneration in file // Remaining
    }

    @Override
    public void getSSCode(String topic1, String topic2, File workDir) throws IOException {
//        try {
//            String code = codeReadWrite.readFromSource(ssSrc);
//            String newCode = code.replace("$$topic1$$", topic1);
//            newCode = newCode.replace("$$topic2$$", topic2);
//            return newCode;
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Error!");
//        }
    }

    @Override
    public void getRSCode(String topics, File workDir) throws IOException {
//        try {
//            String code = codeReadWrite.readFromSource(rsSrc);
//            String[] topicArr = topics.split(" *, *");
//
//            StringBuilder sb = new StringBuilder("\"");
//            for (int i = 0; i < topicArr.length - 1; i++) {
//                sb.append(topicArr[i]).append("\",\"");
//            }
//            sb.append(topicArr[topicArr.length - 1]).append("\"");
//
//            return code.replace("\"$$topics$$\"", sb.toString());
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Error!");
//        }
    }

    @Override
    public File getCode(String serviceName, String topics) throws IOException {
        if (!serviceName.matches("^(cds)|(ss)|(rs)$"))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Allowed service names: cds, ss, rs!");

        String[] topicArr = topics.split(" *, *");
        currentTimeStamp = getCurrentTimeStamp();
        String zipSrc = zipDir + "/" + serviceName + "_" + currentTimeStamp + ".zip";
        String extractSrc = codeDir + "/" + serviceName + "_" + currentTimeStamp;
        String exportSrc = zipOutDir + "/" + serviceName + "_" + currentTimeStamp;
        File extractDir = new File(extractSrc);
        if (!extractDir.mkdir())
            throw new IOException("Failed to create directory: " + extractDir);

        downloadZip(serviceName, zipSrc);
        zipper.unZip(zipSrc, extractSrc);
        switch (serviceName) {
            case "cds" -> getCDSCode(topics, extractDir);
            case "ss" -> getSSCode(topicArr[0], topicArr[1], extractDir);
            case "rs" -> getRSCode(topics, extractDir);
        };
        File[] filesInExtractDir = extractDir.listFiles();
        if (filesInExtractDir == null || filesInExtractDir.length == 0)
            throw new IOException("Empty Extract Dir!");
        zipper.zipDir(filesInExtractDir[0].getAbsolutePath(), exportSrc);

        return new File(exportSrc);
    }

    private long getCurrentTimeStamp() {
        LocalDateTime dateTime = LocalDateTime.now();
        ZoneId zoneId = ZoneId.systemDefault();
        return dateTime.atZone(zoneId).toEpochSecond();
    }
}
