package sa.project.codesupplierservice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sa.project.codesupplierservice.service.ICodeSupplierService;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CodeSupplierController {

    private final ICodeSupplierService codeSupplierService;

    /**
     * Endpoint: Change Detector Service
     * GET /cds?topic=topicName
     * @param topic
     * @return
     */
    @GetMapping("/cds")
    public ResponseEntity<String> getCDSCode(@RequestParam String topic) { //ResponseEntity<String> replace for String
        LocalDateTime localDateTime = LocalDateTime.now();
        log.info("Change Detector Service Log:  Date is " + localDateTime + " , Topic is : " + topic );
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
        LocalDateTime localDateTime = LocalDateTime.now();
        log.info("Score Service Log: Date is " + localDateTime + " , Topic1 is : " + topic1 + " , Topic2 is : " + topic2);
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
        LocalDateTime localDateTime = LocalDateTime.now();
        log.info(" Reporting Service Log:  Date is " + localDateTime + " , Topic is : " + topics );
        return new ResponseEntity<>(codeSupplierService.getRSCode(topics), HttpStatus.OK);
    }
}
