package org.example.zahra.springauthentification.Web.Controllers.Impl;

import org.example.zahra.springauthentification.Exceptions.ResourceNotFoundException;
import org.example.zahra.springauthentification.Exceptions.BadRequestException;
import org.example.zahra.springauthentification.Exceptions.ValidationException;
import org.example.zahra.springauthentification.Exceptions.UnauthorizedException;
import org.example.zahra.springauthentification.Web.Controllers.BaseController;
import org.example.zahra.springauthentification.Web.Filters.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;
import java.util.stream.Collectors;

public abstract class BaseControllerImpl<E, ID, R> implements BaseController<E, ID, R> {

    private final org.example.zahra.springauthentification.Services.BaseService<E, ID> service;
    private final org.example.zahra.springauthentification.Mappers.GenericMapper<E, ?, R> mapper;

    public BaseControllerImpl(org.example.zahra.springauthentification.Services.BaseService<E, ID> service,
                              org.example.zahra.springauthentification.Mappers.GenericMapper<E, ?, R> mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @Override
    @PostMapping
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
    @PutMapping
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
    @GetMapping
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
    @DeleteMapping
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
