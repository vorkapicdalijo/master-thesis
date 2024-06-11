package com.fer.hr.webshopassistant.service;

import com.fer.hr.webshopassistant.model.ChatResponse;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class AssistantService {

    // ChatClient bean for interacting with the chat engine
    private final ChatClient chatClient;

    // VectorStore bean for interacting with the vector store
    private final VectorStore vectorStore;

    // Path to the assistant prompt template
    @Value("classpath:/prompts/assistant-prompt-template.st")
    private Resource assistantPromptTemplate;

    public AssistantService(ChatClient chatClient, VectorStore vectorStore) {
        this.chatClient = chatClient;
        this.vectorStore = vectorStore;
    }

    public ChatResponse chat(String message) {
        // Run the vector store similarity search for similar context documents based on the user message
        List<Document> similarDocuments = vectorStore.similaritySearch(
                SearchRequest.query(message) // Build a search request with the user message
                        .withTopK(2)); // Retrieve the top 2 most similar documents

        // Extract the content (text) of the retrieved documents
        List<String> contentList = similarDocuments
                .stream()
                .map(Document::getContent).toList();

        // Load the assistant prompt template
        PromptTemplate promptTemplate = new PromptTemplate(assistantPromptTemplate);

        // Create a map to store prompt parameters
        Map<String, Object> promptParameters = new HashMap<>();
        promptParameters.put("input", message);
        promptParameters.put("documents", String.join("\n", contentList));

        // Create a prompt object using the prompt template and parameters
        Prompt prompt = promptTemplate.create(promptParameters);

        // Create a ChatResponse object to hold the response message
        ChatResponse chatResponse = new ChatResponse();

        // Use the OpenAI ChatClient to call the chat engine with the generated prompt template
        String answer = chatClient.call(prompt).getResult().getOutput().getContent();
        chatResponse.setMessage(answer);

        // Return the ChatResponse object containing the generated answer
        return chatResponse;

    }
}
