package sa.project.codesupplierservice.service;

import java.io.File;
import java.io.IOException;

public interface ICodeSupplierService {
    void getCDSCode(String topic, File workDir) throws IOException;
    void getSSCode(String topic1, String topic2, File workDir) throws IOException;
    void getRSCode(String topics, File workDir) throws IOException;

    File getCode(String serviceName, String topics) throws IOException;
}
