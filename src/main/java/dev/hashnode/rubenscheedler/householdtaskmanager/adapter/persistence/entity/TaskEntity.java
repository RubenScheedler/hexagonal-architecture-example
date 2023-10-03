package dev.hashnode.rubenscheedler.householdtaskmanager.adapter.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.util.UUID;

@Data
@Builder
@Entity(name="task")
@NoArgsConstructor // for ORM/Spring repo
@AllArgsConstructor // for @Builder
public class TaskEntity {
    @NonNull
    @Id
    UUID id;
    @NonNull
    String description;
    String assignee;
    boolean completed;
}
