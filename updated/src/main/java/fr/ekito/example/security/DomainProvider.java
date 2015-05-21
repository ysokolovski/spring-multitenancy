package fr.ekito.example.security;

import fr.ekito.example.domain.Domain;

import java.util.Optional;

/**
 * Created by ysokolov on 5/20/2015.
 */
public interface DomainProvider {
    Optional<Domain> getCurrentDomain();
}
