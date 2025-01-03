package org.example.zahra.springauthentification.Web.Controllers.Impl;

import org.example.zahra.springauthentification.Exceptions.ResourceNotFoundException;
import org.example.zahra.springauthentification.Exceptions.BadRequestException;
import org.example.zahra.springauthentification.Exceptions.ValidationException;
import org.example.zahra.springauthentification.Exceptions.UnauthorizedException;
import org.example.zahra.springauthentification.Web.Controllers.BaseController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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
    public ResponseEntity<R> create(E entity) {
        try {
            E createdEntity = service.create(entity);
            return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponseDto(createdEntity));
        } catch (ValidationException e) {
            throw new ValidationException("Erreur de validation : " + e.getMessage());
        } catch (BadRequestException e) {
            throw new BadRequestException("Requête invalide : " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Erreur interne lors de la création de l'entité : " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<R> update(ID id, E entity) {
        try {
            E updatedEntity = service.update(id, entity);
            return ResponseEntity.ok(mapper.toResponseDto(updatedEntity));
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Entité non trouvée avec l'ID : " + id);
        } catch (ValidationException e) {
            throw new ValidationException("Erreur de validation : " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Erreur interne lors de la mise à jour de l'entité : " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<R> getById(ID id) {
        try {
            E foundEntity = service.getById(id);
            return ResponseEntity.ok(mapper.toResponseDto(foundEntity));
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Entité non trouvée avec l'ID : " + id);
        } catch (Exception e) {
            throw new RuntimeException("Erreur interne lors de la récupération de l'entité : " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<List<R>> getAll() {
        try {
            List<E> allEntities = service.getAll();
            return ResponseEntity.ok(allEntities.stream()
                    .map(mapper::toResponseDto)
                    .collect(Collectors.toList()));
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
