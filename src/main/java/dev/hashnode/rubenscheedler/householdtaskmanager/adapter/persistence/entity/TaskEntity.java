package dev.hashnode.rubenscheedler.householdtaskmanager.adapter.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.NonNull;

import java.util.UUID;

@Entity(name="task")
public class TaskEntity {
    @NonNull
    @Id
    UUID id;
    @NonNull
    String description;
    String assignee;
    boolean completed;
}
