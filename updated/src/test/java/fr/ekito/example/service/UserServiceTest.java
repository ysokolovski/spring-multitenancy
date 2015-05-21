package fr.ekito.example.service;

import fr.ekito.example.Application;
import fr.ekito.example.MultitenantMongoTemplate;
import fr.ekito.example.config.MongoConfiguration;
import fr.ekito.example.domain.Domain;
import fr.ekito.example.domain.User;
import fr.ekito.example.repository.DomainRepository;
import fr.ekito.example.repository.UserRepository;
import fr.ekito.example.security.DomainProvider;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.inject.Inject;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;

/**
 * Test class for the UserResource REST controller.
 *
 * @see UserService
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@Import(MongoConfiguration.class)
public class UserServiceTest {

    @Inject
    private UserRepository userRepository;

    @Inject
    private UserService userService;

    @Inject
    MultitenantMongoTemplate multitenantMongoTemplate;

    @Inject
    private DomainRepository domainRepository;

    @Mock
    private DomainProvider domainProvider;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindNotActivatedUsersByCreationDateBefore() {

        final Domain test = domainRepository.findAll().get(0);
        when(domainProvider.getCurrentDomain()).thenReturn(Optional.of(test));

        multitenantMongoTemplate.setDomainProvider(domainProvider);

        userService.removeNotActivatedUsers();
        DateTime now = new DateTime();
        List<User> users = userRepository.findNotActivatedUsersByCreationDateBefore(now.minusDays(3));
        assertThat(users).isEmpty();
    }
}
