package com.fer.hr.product.controller;

import com.fer.hr.product.dto.ProductResponse;
import com.fer.hr.product.model.*;
import com.fer.hr.product.service.ProductService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/selects")
@AllArgsConstructor
public class SelectController {

    private final ProductService productService;

    @GetMapping("/brands")
    public ResponseEntity<List<Brand>> getBrands() {
        List<Brand> brands = productService.getBrands();

        if(brands.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(brands, HttpStatus.OK);
    }

    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getCategories() {
        List<Category> categories = productService.getCategories();

        if(categories.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/types")
    public ResponseEntity<List<Type>> getTypes() {
        List<Type> types = productService.getTypes();

        if(types.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(types, HttpStatus.OK);
    }

    @GetMapping("/notes")
    public ResponseEntity<List<Note>> getNotes() {
        List<Note> notes = productService.getNotes();

        if(notes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(notes, HttpStatus.OK);
    }

    @GetMapping("/note-types")
    public ResponseEntity<List<NoteType>> getNoteTypes() {
        List<NoteType> noteTypes = productService.getNoteTypes();

        if(noteTypes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(noteTypes, HttpStatus.OK);
    }
}
