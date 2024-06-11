package com.fer.hr.webshopassistant.config;

import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingClient;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Configuration
public class RagConfiguration {

    @Value("classpath:/docs/webshop-context.txt")
    private Resource context;

    @Value("vectorstore.json")
    private String vectorStoreName;

    @Bean
    SimpleVectorStore simpleVectorStore(EmbeddingClient embeddingClient) {
        //Define SimpleVectorStore object with the client for embedding the context documents
        SimpleVectorStore simpleVectorStore = new SimpleVectorStore(embeddingClient);
        File vectorStoreFile = getVectorStoreFile();

        //Check if the vector store file already exists
        if(vectorStoreFile.exists()) {
            //Load pre-processed vectors
            simpleVectorStore.load(vectorStoreFile);
        }
        else {
            //Create TextReader object for web shop context path
            TextReader textReader = new TextReader(context);
            textReader.getCustomMetadata().put("filename", "context.txt");

            //Read documents from the context
            List<Document> documents = textReader.get();

            TokenTextSplitter tokenTextSplitter = new TokenTextSplitter();
            //Split documents into tokens (words, phrases, etc.)
            List<Document> splitDocuments = tokenTextSplitter.apply(documents);

            //Add tokenized documents to the vector store
            simpleVectorStore.add(splitDocuments);
            //Save the processed vector store to a file
            simpleVectorStore.save(vectorStoreFile);
        }
        return simpleVectorStore;
    }

    private File getVectorStoreFile() {
        Path path = Paths.get("src", "main", "resources", "data");

        String absolutePath = path.toFile().getAbsolutePath() + "/" + vectorStoreName;

        return new File(absolutePath);
    }
}
