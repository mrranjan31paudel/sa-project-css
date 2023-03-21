package sa.project.codesupplierservice.service.impl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sa.project.codesupplierservice.service.ICodeSupplierService;
import sa.project.codesupplierservice.utils.ICodeReadWrite;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class CodeSupplierService implements ICodeSupplierService {

    private final ICodeReadWrite codeReadWrite;

    @Value("${css.cds.src}")
    private String cdsSrc;

    @Value("${css.ss.src}")
    private String ssSrc;

    @Value("${css.rs.src}")
    private String rsSrc;

    @Override
    public String getCDSCode(String topic) {
        try {
            String code = codeReadWrite.readFromSource(cdsSrc);
            String newCode = code.replace("$$topic$$", topic);
            return newCode;
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Error!");
        }
    }

    @Override
    public String getSSCode(String topic1, String topic2) {
        try {
        String code = codeReadWrite.readFromSource(ssSrc);
        String newCode = code.replace("$$topic1$$", topic1);
        newCode = newCode.replace("$$topic2$$", topic2);
        return newCode;
        }
            catch (IOException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Error!");
        }
    }

    @Override
    public String getRSCode(String topics) {
        try {
            String code = codeReadWrite.readFromSource(rsSrc);
            String[] topicArr = topics.split(" *, *");

            StringBuilder sb = new StringBuilder("\"");
            for (int i = 0; i < topicArr.length - 1; i++) {
                sb.append(topicArr[i]).append("\",\"");
            }
            sb.append(topicArr[topicArr.length - 1]).append("\"");

            return code.replace("\"$$topics$$\"", sb.toString());
        }
            catch (IOException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Error!");
        }
    }
}
