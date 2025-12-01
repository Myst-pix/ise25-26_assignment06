package de.seuhd.campuscoffee.api.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import de.seuhd.campuscoffee.api.dtos.UserDto;
import de.seuhd.campuscoffee.api.mapper.UserDtoMapper;
import de.seuhd.campuscoffee.domain.ports.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import java.util.List;

@Tag(name = "Users", description = "Operations related to user management.")
@Controller
@RequestMapping("/api/users")
@Slf4j
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserDtoMapper userDtoMapper;

    @GetMapping("")
    public ResponseEntity<List<UserDto>> getAll() {
        log.debug("GET /api/users");
        return ResponseEntity.ok(
                userService.getAll().stream()
                        .map(userDtoMapper::fromDomain)
                        .toList()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getById(@PathVariable Long id) {
        log.debug("GET /api/users/{}", id);
        return ResponseEntity.ok(
                userDtoMapper.fromDomain(userService.getById(id))
        );
    }

    @GetMapping("/filter")
    public ResponseEntity<UserDto> getByLoginName(@RequestParam String loginName) {
        log.debug("GET /api/users/filter?loginName={}", loginName);
        return ResponseEntity.ok(
                userDtoMapper.fromDomain(userService.getByLoginName(loginName))
        );
    }

    @PostMapping("")
    public ResponseEntity<UserDto> create(@Valid @RequestBody UserDto dto) {
        log.debug("POST /api/users");
        var created = userService.upsert(userDtoMapper.toDomain(dto));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userDtoMapper.fromDomain(created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> update(
            @PathVariable Long id,
            @Valid @RequestBody UserDto dto
    ) {
        log.debug("PUT /api/users/{}", id);

        if (!id.equals(dto.id())) {
            throw new IllegalArgumentException("Path ID and DTO ID do not match");
        }

        var updated = userService.upsert(userDtoMapper.toDomain(dto));
        return ResponseEntity.ok(userDtoMapper.fromDomain(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.debug("DELETE /api/users/{}", id);
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
