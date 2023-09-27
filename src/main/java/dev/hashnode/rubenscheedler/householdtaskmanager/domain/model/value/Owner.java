package dev.hashnode.rubenscheedler.householdtaskmanager.domain.model.value;

import lombok.Builder;
import lombok.NonNull;

@Builder
public record Owner(@NonNull String nickname) {
}
