package com.kraya.platform.controller;

import com.kraya.platform.exception.ResourceNotFoundException;
import com.kraya.platform.exception.InvalidInputException;
import com.kraya.platform.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public abstract class GenericController<T> {

    protected abstract UserService getService();

    @GetMapping
    public ResponseEntity<List<T>> getAll() {
        List<T> items = getService().findAll();
        return ResponseEntity.ok(items);
    }

    @GetMapping("/{id}")
    public ResponseEntity<T> getById(@PathVariable Long id) {
        T item = getService().findById(id);
        if (item == null) {
            throw new ResourceNotFoundException("Resource not found with ID: " + id);
        }
        return ResponseEntity.ok(item);
    }

    @PostMapping
    public ResponseEntity<T> create(@RequestBody T item) {
        if (item == null) {
            throw new InvalidInputException("Invalid input: resource cannot be null");
        }
        T createdItem = getService().create(item);
        return ResponseEntity.status(201).body(createdItem);
    }

    @PutMapping("/{id}")
    public ResponseEntity<T> update(@PathVariable Long id, @RequestBody T item) {
        if (item == null) {
            throw new InvalidInputException("Invalid input: resource cannot be null");
        }
        T updatedItem = getService().update(id, item);
        return ResponseEntity.ok(updatedItem);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        getService().delete(id);
        return ResponseEntity.noContent().build();
    }
}
