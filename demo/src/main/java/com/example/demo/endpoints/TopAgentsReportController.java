package com.example.demo.endpoints;

import com.example.demo.model.Agent;
import com.example.demo.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
public class TopAgentsReportController {

    @Autowired
    private ReportService reportService;

    //http://localhost:8080/top5report/?startDate=17/07/2019&endDate=15/10/2020 for example
    @GetMapping("/top5report")
    public List<Agent> getTop5AgentsReport(@RequestParam String startDate, @RequestParam String endDate) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        try {
            Date start = dateFormat.parse(startDate);
            Date end = dateFormat.parse(endDate);

            return reportService.getTopFiveAgentsWithinDateRange(start, end);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }
}
