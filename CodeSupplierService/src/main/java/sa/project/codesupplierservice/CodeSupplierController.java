package sa.project.codesupplierservice;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sa.project.codesupplierservice.service.ICodeSupplierService;

import java.util.Arrays;

@RestController
@RequiredArgsConstructor
public class CodeSupplierController {

    private final ICodeSupplierService codeSupplierService;

    /**
     * Endpoint: Change Detector Service
     * GET /cds?topic=topicName
     * @param topic
     * @return
     */
    @GetMapping("/cds")
    public ResponseEntity<String> getCDSCode(@RequestParam String topic) {
        return new ResponseEntity<>(codeSupplierService.getCDSCode(topic), HttpStatus.OK);
    }

    /**
     * Endpoint: Scoring Service
     * GET /ss?topic1=t1&topic2=t2
     * @param topic1
     * @param topic2
     * @return
     */
    @GetMapping("/ss")
    public ResponseEntity<String> getSSCode(@RequestParam String topic1, @RequestParam String topic2) {
        return new ResponseEntity<>(codeSupplierService.getSSCode(topic1, topic2), HttpStatus.OK);
    }

    /**
     * Endpoint: Reporting Service
     * GET /rs?topics=t1,t2
     * @param topics
     * @return
     */
    @GetMapping("/rs")
    public ResponseEntity<String> getRSCode(@RequestParam String topics) {
        return new ResponseEntity<>(codeSupplierService.getRSCode(topics), HttpStatus.OK);
    }
}
