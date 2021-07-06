package com.test.demo.controller;

import com.test.demo.SpringBootTestDemoApplication;
import com.test.demo.controller.dto.CounterResponse;
import com.test.demo.controller.dto.CounterSearchRequest;
import com.test.demo.exception.CounterException;
import com.test.demo.model.TextInfo;
import com.test.demo.service.CounterService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.*;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping(path = "/counter-api")
public class CounterController {
    private static final Logger logger = LogManager.getLogger(CounterController.class);

    private static final CsvPreference PIPE_DELIMITED = new CsvPreference.Builder('"', '|', "\n").build();

    @Autowired
    private CounterService counterService;

    @PostMapping(path = "/search", produces = "application/json")
    public ResponseEntity<CounterResponse> searchTextCount(@RequestBody CounterSearchRequest counterSearchRequest) throws Exception {

        List<String> searchList = counterSearchRequest.getSearchText();
        List<Map<String, Integer>> counts = new ArrayList<>();
        logger.debug("Search list provided %s", searchList);
        if(!searchList.isEmpty()) {
            Map<String, Long> couterMap = counterService.getCompleteTextList().stream().collect(Collectors.groupingBy(e -> e, Collectors.counting()));

            searchList.forEach(str -> {
                Map<String, Integer> internalValMap;
                if (couterMap.containsKey(str)) {
                    internalValMap = new HashMap<>();
                    internalValMap.put(str, couterMap.get(str).intValue());

                } else {
                    internalValMap = new HashMap<>();
                    internalValMap.put(str, 0);

                }
                if (internalValMap != null) {
                    counts.add(internalValMap);
                }
            });
        }else{
            logger.error("Search list is empty");
            throw new CounterException("Bad Request");
        }
        return ResponseEntity.ok(CounterResponse.builder().counts(counts).build());

    }

    @GetMapping(path = "/top/{displayCount}", produces = "text/csv")
    public void displayTextCount(@PathVariable Integer displayCount, HttpServletResponse response) throws IOException, ServletRequestBindingException {
        ICsvBeanWriter csvWriter = null;
        try {
            response.setContentType("text/csv;charset=utf-8");
            csvWriter = new CsvBeanWriter(response.getWriter(), PIPE_DELIMITED);
            String[] header = {"Name", "Count"};
            if (displayCount > 0) {
                List<TextInfo> infos = counterService.getTextCountList().stream()
                        .sorted(comparing(TextInfo::getCount, comparing(Math::abs)).reversed())
                        .limit(displayCount)
                        .collect(toList());
                logger.debug("Text info retrieved %s", infos);
                for (TextInfo textInfo : infos) {
                    csvWriter.write(textInfo, header);
                }

            } else {
                logger.debug("Invalid request provided");
                String noReturned = "Invalid Request";
                csvWriter.write(new TextInfo(noReturned, null), header);

            }
        } finally {
            if (csvWriter != null) {
                csvWriter.close();
            }
        }

    }

}
