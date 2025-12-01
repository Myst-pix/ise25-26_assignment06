package de.seuhd.campuscoffee.domain.model;

import lombok.Builder;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import java.time.LocalDateTime;
import java.io.Serial;
import java.io.Serializable;

@Builder(toBuilder = true)
public record User (
        @Nullable Long id,
        @Nullable LocalDateTime createdAt,
        @Nullable LocalDateTime updatedAt,
        @NonNull String loginName,
        @NonNull String emailAddress,
        @NonNull String firstName,
        @NonNull String lastName
) implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
}
