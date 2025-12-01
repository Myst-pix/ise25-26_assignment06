package de.seuhd.campuscoffee.domain.impl;

import de.seuhd.campuscoffee.domain.ports.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import de.seuhd.campuscoffee.domain.model.User;
import de.seuhd.campuscoffee.domain.ports.UserDataService;
import org.jspecify.annotations.NonNull;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserDataService userDataService;

    @Override
    public void clear() {
        log.info("Clearing all users");
        userDataService.clear();
    }

    @Override
    public @NonNull List<User> getAll() {
        List<User> users = userDataService.getAll();
        log.debug("Loaded {} users", users.size());
        return users;
    }

    @Override
    public @NonNull User getById(@NonNull Long id) {
        Objects.requireNonNull(id, "id must not be null");
        log.debug("Loading user with id {}", id);
        return userDataService.getById(id);
    }

    @Override
    public @NonNull User getByLoginName(@NonNull String loginName) {
        Objects.requireNonNull(loginName, "loginName must not be null");
        log.debug("Loading user with loginName {}", loginName);
        return userDataService.getByLoginName(loginName);
    }

    @Override
    public @NonNull User upsert(@NonNull User user) {
        Objects.requireNonNull(user, "user must not be null");

        if (user.id() == null) {
            log.info("Creating new user with loginName {}", user.loginName());
        } else {
            log.info("Updating user with id {}", user.id());
            userDataService.getById(user.id());
        }

        User saved = userDataService.upsert(user);
        log.debug("User with id {} successfully saved", saved.id());
        return saved;
    }

    @Override
    public void delete(@NonNull Long id) {
        Objects.requireNonNull(id, "id must not be null");
        log.info("Deleting user with id {}", id);
        userDataService.delete(id);
    }
}
