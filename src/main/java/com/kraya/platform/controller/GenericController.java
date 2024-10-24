package com.kraya.platform.controller;

import com.kraya.platform.dto.UserRegistrationRequest;
import com.kraya.platform.dto.UserUpdateRequest;
import com.kraya.platform.exception.ResourceNotFoundException;
import com.kraya.platform.exception.InvalidInputException;
import com.kraya.platform.model.User;
import com.kraya.platform.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * GenericController provides common CRUD operations for entities.
 */
public abstract class GenericController<T> {

    /**
     * Abstract method to get the specific service for the entity.
     *
     * @return the specific service for the entity
     */
    protected abstract UserService getService();

    /**
     * Retrieves all resources.
     *
     * @return a list of resources
     */
    @GetMapping
    public ResponseEntity<List<T>> getAll() {
        List<T> items = (List<T>) getService().findAll();
        return ResponseEntity.ok(items);
    }

    /**
     * Retrieves a resource by ID.
     *
     * @param id the ID of the resource to retrieve
     * @return the requested resource
     */
    @GetMapping("/{id}")
    public ResponseEntity<T> getById(@PathVariable Long id) {
        T item = (T) getService().findById(id);
        if (item == null) {
            throw new ResourceNotFoundException("Resource not found with ID: " + id);
        }
        return ResponseEntity.ok(item);
    }

    /**
     * Creates a new resource.
     *
     * @param item the resource to create
     * @return the created resource
     */
    @PostMapping
    public ResponseEntity<T> create(@RequestBody T item) {
        if (item == null) {
            throw new InvalidInputException("Invalid input: resource cannot be null");
        }
        T createdItem = (T) getService().create((UserRegistrationRequest) item);
        return ResponseEntity.status(201).body(createdItem);
    }

    /**
     * Updates an existing resource.
     *
     * @param id   the ID of the resource to update
     * @param item the updated resource data
     * @return the updated resource
     */
    @PutMapping("/{id}")
    public ResponseEntity<T> update(@PathVariable Long id, @RequestBody T item) {
        if (item == null) {
            throw new InvalidInputException("Invalid input: resource cannot be null");
        }
        T updatedItem = (T) getService().update(id, (UserUpdateRequest) item);
        return ResponseEntity.ok(updatedItem);
    }

    /**
     * Deletes a resource by ID.
     *
     * @param id the ID of the resource to delete
     * @return ResponseEntity indicating the result of the operation
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        getService().delete(id);
        return ResponseEntity.noContent().build();
    }
}
