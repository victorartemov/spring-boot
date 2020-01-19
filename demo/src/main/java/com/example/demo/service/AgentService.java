package com.example.demo.service;

import com.example.demo.model.Agent;

import java.util.List;

public interface AgentService {
    Agent createAgent(Agent agent);

    Agent updateAgent(Agent agent);

    void deleteAgent(Agent agent);

    Agent findById(Long id);

    List<Agent> findAll();
}
