package com.example.demo.service.impl;

import com.example.demo.model.Agent;
import com.example.demo.service.AgentService;
import com.example.demo.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private AgentService agentService;

    @Override
    public List<Agent> getTopFiveAgentsWithinDateRange(Date startDate, Date endDate) {
        List<Agent> agents = agentService.findAll();

        return agents.stream()
                .sorted(Comparator.comparing(a -> -a.getSumOfSoldPropertiesForGivenPeriod(startDate, endDate)))
                .limit(5)
                .collect(Collectors.toList());
    }
}
