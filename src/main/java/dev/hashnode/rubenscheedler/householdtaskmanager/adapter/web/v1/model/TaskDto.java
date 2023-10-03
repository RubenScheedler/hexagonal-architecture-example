package dev.hashnode.rubenscheedler.householdtaskmanager.adapter.web.v1.model;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.UUID;

@Data
@Builder
public class TaskDto {
    @NonNull UUID id;
    @NonNull String description;
    String assignee;
}
