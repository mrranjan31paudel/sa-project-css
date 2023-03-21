package sa.project.codesupplierservice.service;

public interface ICodeSupplierService {
    String getCDSCode(String topic);
    String getSSCode(String topic1, String topic2);
    String getRSCode(String topics);
}
