package org.example.zahra.springauthentification.Web.Controllers.Impl;

import org.example.zahra.springauthentification.Web.Controllers.BaseController;
import org.example.zahra.springauthentification.Exceptions.ResourceNotFoundException;
import org.example.zahra.springauthentification.Exceptions.BadRequestException;
import org.example.zahra.springauthentification.Exceptions.ValidationException;
import org.example.zahra.springauthentification.Exceptions.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public abstract class BaseControllerImpl<E, ID> implements BaseController<E, ID> {

    private final org.example.zahra.springauthentification.Services.BaseService<E, ID> service;

    public BaseControllerImpl(org.example.zahra.springauthentification.Services.BaseService<E, ID> service) {
        this.service = service;
    }

    @Override
    public ResponseEntity<E> create(E entity) {
        try {
            E createdEntity = service.create(entity);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdEntity);
        } catch (ValidationException e) {
            throw new ValidationException("Erreur de validation : " + e.getMessage());
        } catch (BadRequestException e) {
            throw new BadRequestException("Requête invalide : " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Erreur interne lors de la création de l'entité : " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<E> update(ID id, E entity) {
        try {
            E updatedEntity = service.update(id, entity);
            return ResponseEntity.ok(updatedEntity);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Entité non trouvée avec l'ID : " + id);
        } catch (ValidationException e) {
            throw new ValidationException("Erreur de validation : " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Erreur interne lors de la mise à jour de l'entité : " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<E> getById(ID id) {
        try {
            E foundEntity = service.getById(id);
            return ResponseEntity.ok(foundEntity);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Entité non trouvée avec l'ID : " + id);
        } catch (Exception e) {
            throw new RuntimeException("Erreur interne lors de la récupération de l'entité : " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<List<E>> getAll() {
        try {
            List<E> allEntities = service.getAll();
            return ResponseEntity.ok(allEntities);
        } catch (Exception e) {
            throw new RuntimeException("Erreur interne lors de la récupération des entités : " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<Void> delete(ID id) {
        try {
            service.delete(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Entité non trouvée avec l'ID : " + id);
        } catch (UnauthorizedException e) {
            throw new UnauthorizedException("Non autorisé à supprimer l'entité avec l'ID : " + id);
        } catch (Exception e) {
            throw new RuntimeException("Erreur interne lors de la suppression de l'entité : " + e.getMessage());
        }
    }
}
