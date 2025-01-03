package org.example.zahra.springauthentification.Web.Controllers;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BaseController<E, ID, R> {
    ResponseEntity<R> create(E entity);

    ResponseEntity<R> update(ID id, E entity);

    ResponseEntity<R> getById(ID id);

    ResponseEntity<List<R>> getAll();

    ResponseEntity<Void> delete(ID id);
}
