package org.example.zahra.springauthentification.Web.Controllers;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface BaseController<E, ID> {

    ResponseEntity<E> create(@RequestBody E entity);

    ResponseEntity<E> update(@PathVariable ID id, @RequestBody E entity);

    ResponseEntity<E> getById(@PathVariable ID id);

    ResponseEntity<List<E>> getAll();

    ResponseEntity<Void> delete(@PathVariable ID id);
}
