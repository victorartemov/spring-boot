package com.example.demo.service.impl;

import com.example.demo.model.Agent;
import com.example.demo.repository.AgentRepository;
import com.example.demo.service.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgentServiceImpl implements AgentService {

    @Autowired
    private AgentRepository agentRepository;

    @Override
    public Agent createAgent(Agent agent) {
        return agentRepository.save(agent);
    }

    @Override
    public Agent updateAgent(Agent agent) {
        return agentRepository.save(agent);
    }

    @Override
    public void deleteAgent(Agent agent) {
        agentRepository.delete(agent);
    }

    @Override
    public Agent findById(Long id) {
        return agentRepository.findById(id).orElse(null);
    }

    @Override
    public List<Agent> findAll() {
        return (List<Agent>) agentRepository.findAll();
    }
}
