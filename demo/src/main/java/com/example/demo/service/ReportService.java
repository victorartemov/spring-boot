package com.example.demo.service;

import com.example.demo.model.Agent;

import java.util.Date;
import java.util.List;

public interface ReportService {
    List<Agent> getTopFiveAgentsWithinDateRange(Date startDate, Date endDate);
}
