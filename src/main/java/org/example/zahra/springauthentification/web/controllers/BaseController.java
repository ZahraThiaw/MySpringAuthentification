package org.example.zahra.springauthentification.web.controllers;

import org.example.zahra.springauthentification.filters.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BaseController<E, ID, R> {

    ResponseEntity<ApiResponse<R>> create(E entity);

    ResponseEntity<ApiResponse<R>> update(ID id, E entity);

    ResponseEntity<ApiResponse<R>> getById(ID id);

    ResponseEntity<ApiResponse<List<R>>> getAll();

    ResponseEntity<ApiResponse<Void>> delete(ID id);
}
