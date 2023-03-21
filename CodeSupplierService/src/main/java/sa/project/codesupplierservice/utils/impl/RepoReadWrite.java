package sa.project.codesupplierservice.utils.impl;

import sa.project.codesupplierservice.utils.ICodeReadWrite;

import java.io.IOException;

public class RepoReadWrite implements ICodeReadWrite {
    @Override
    public String readFromSource(String sourceName) throws IOException {
        // return restTemplate.getForObject(gitUrl, String.class);
        return null;
    }

    @Override
    public String writeProcessedCode(String code, String filename) {
        return null;
    }
}
