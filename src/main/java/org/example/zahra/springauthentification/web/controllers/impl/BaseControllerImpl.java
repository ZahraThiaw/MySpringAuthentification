package org.example.zahra.springauthentification.web.controllers.impl;

import org.example.zahra.springauthentification.exceptions.ResourceNotFoundException;
import org.example.zahra.springauthentification.exceptions.BadRequestException;
import org.example.zahra.springauthentification.exceptions.ValidationException;
import org.example.zahra.springauthentification.exceptions.UnauthorizedException;
import org.example.zahra.springauthentification.web.controllers.BaseController;
import org.example.zahra.springauthentification.filters.ApiResponse;
import org.example.zahra.springauthentification.web.dtos.mappers.GenericMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

public abstract class BaseControllerImpl<E, ID, R> implements BaseController<E, ID, R> {

    private final org.example.zahra.springauthentification.services.BaseService<E, ID> service;
    private final GenericMapper<E, ?, R> mapper;

    public BaseControllerImpl(org.example.zahra.springauthentification.services.BaseService<E, ID> service,
                              GenericMapper<E, ?, R> mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @Override
    public ResponseEntity<ApiResponse<R>> create(E entity) {
        try {
            E createdEntity = service.create(entity);
            R responseDto = mapper.toResponseDto(createdEntity);
            ApiResponse<R> apiResponse = new ApiResponse<>(true, "Entity created successfully", responseDto, null, "SUCCESS", HttpStatus.CREATED.value());
            return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
        } catch (ValidationException e) {
            ApiResponse<R> apiResponse = new ApiResponse<>(false, "Validation error: " + e.getMessage(), null, List.of(e.getMessage()), "VALIDATION_ERROR", HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(apiResponse);
        } catch (BadRequestException e) {
            ApiResponse<R> apiResponse = new ApiResponse<>(false, "Bad request: " + e.getMessage(), null, List.of(e.getMessage()), "BAD_REQUEST", HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(apiResponse);
        } catch (Exception e) {
            ApiResponse<R> apiResponse = new ApiResponse<>(false, "Internal server error: " + e.getMessage(), null, List.of(e.getMessage()), "INTERNAL_ERROR", HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
        }
    }

    @Override
    public ResponseEntity<ApiResponse<R>> update(ID id, E entity) {
        try {
            E updatedEntity = service.update(id, entity);
            R responseDto = mapper.toResponseDto(updatedEntity);
            ApiResponse<R> apiResponse = new ApiResponse<>(true, "Entity updated successfully", responseDto, null, "SUCCESS", HttpStatus.OK.value());
            return ResponseEntity.ok(apiResponse);
        } catch (ResourceNotFoundException e) {
            ApiResponse<R> apiResponse = new ApiResponse<>(false, "Entity not found with ID: " + id, null, List.of(e.getMessage()), "NOT_FOUND", HttpStatus.NOT_FOUND.value());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
        } catch (ValidationException e) {
            ApiResponse<R> apiResponse = new ApiResponse<>(false, "Validation error: " + e.getMessage(), null, List.of(e.getMessage()), "VALIDATION_ERROR", HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(apiResponse);
        } catch (Exception e) {
            ApiResponse<R> apiResponse = new ApiResponse<>(false, "Internal server error: " + e.getMessage(), null, List.of(e.getMessage()), "INTERNAL_ERROR", HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
        }
    }

    @Override
    public ResponseEntity<ApiResponse<R>> getById(ID id) {
        try {
            E foundEntity = service.getById(id);
            R responseDto = mapper.toResponseDto(foundEntity);
            ApiResponse<R> apiResponse = new ApiResponse<>(true, "Entity retrieved successfully", responseDto, null, "SUCCESS", HttpStatus.OK.value());
            return ResponseEntity.ok(apiResponse);
        } catch (ResourceNotFoundException e) {
            ApiResponse<R> apiResponse = new ApiResponse<>(false, "Entity not found with ID: " + id, null, List.of(e.getMessage()), "NOT_FOUND", HttpStatus.NOT_FOUND.value());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
        } catch (Exception e) {
            ApiResponse<R> apiResponse = new ApiResponse<>(false, "Internal server error: " + e.getMessage(), null, List.of(e.getMessage()), "INTERNAL_ERROR", HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
        }
    }

    @Override
    @GetMapping
    public ResponseEntity<ApiResponse<List<R>>> getAll() {
        try {
            List<E> allEntities = service.getAll();
            List<R> responseDtos = allEntities.stream().map(mapper::toResponseDto).collect(Collectors.toList());
            ApiResponse<List<R>> apiResponse = new ApiResponse<>(true, "Entities retrieved successfully", responseDtos, null, "SUCCESS", HttpStatus.OK.value());
            return ResponseEntity.ok(apiResponse);
        } catch (Exception e) {
            ApiResponse<List<R>> apiResponse = new ApiResponse<>(false, "Internal server error: " + e.getMessage(), null, List.of(e.getMessage()), "INTERNAL_ERROR", HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
        }
    }

    @Override
    public ResponseEntity<ApiResponse<Void>> delete(ID id) {
        try {
            service.delete(id);
            ApiResponse<Void> apiResponse = new ApiResponse<>(true, "Entity deleted successfully", null, null, "SUCCESS", HttpStatus.NO_CONTENT.value());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(apiResponse);
        } catch (ResourceNotFoundException e) {
            ApiResponse<Void> apiResponse = new ApiResponse<>(false, "Entity not found with ID: " + id, null, List.of(e.getMessage()), "NOT_FOUND", HttpStatus.NOT_FOUND.value());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
        } catch (UnauthorizedException e) {
            ApiResponse<Void> apiResponse = new ApiResponse<>(false, "Unauthorized to delete entity with ID: " + id, null, List.of(e.getMessage()), "UNAUTHORIZED", HttpStatus.FORBIDDEN.value());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(apiResponse);
        } catch (Exception e) {
            ApiResponse<Void> apiResponse = new ApiResponse<>(false, "Internal server error: " + e.getMessage(), null, List.of(e.getMessage()), "INTERNAL_ERROR", HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
        }
    }
}
