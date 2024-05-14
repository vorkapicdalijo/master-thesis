package com.fer.hr.webshopassistant.controller;

import com.fer.hr.webshopassistant.model.ChatResponse;
import com.fer.hr.webshopassistant.service.AssistantService;
import org.springframework.ai.chat.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/assistant")
public class AssistantController {

    private final AssistantService assistantService;

    public AssistantController(AssistantService assistantService) {
        this.assistantService = assistantService;
    }

    @GetMapping("/chat")
    public ChatResponse chat(@RequestParam(value="message", defaultValue = "Hi! Who are you") String message) {
        return assistantService.chat(message);
    }



}
