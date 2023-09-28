package dev.hashnode.rubenscheedler.householdtaskmanager.domain.model.value;

import lombok.Builder;
import lombok.NonNull;

@Builder
public record Assignee(@NonNull String nickname) {
}
