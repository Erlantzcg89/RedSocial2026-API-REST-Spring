
package com.example.redsocial2026.service;

import com.example.redsocial2026.model.Topic;
import com.example.redsocial2026.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicService {

    @Autowired
    private TopicRepository topicRepository;

    public Topic buscarPorId(Long id) {
        return topicRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Topic no encontrado con id: " + id));
    }

    public List<Topic> obtenerTodos() {
        return topicRepository.findAll();
    }
    
    public Topic guardarTopic(Topic topic) {
        return topicRepository.save(topic);
    }
}
