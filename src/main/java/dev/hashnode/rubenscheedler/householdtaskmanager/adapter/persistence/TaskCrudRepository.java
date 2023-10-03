package dev.hashnode.rubenscheedler.householdtaskmanager.adapter.persistence;

import dev.hashnode.rubenscheedler.householdtaskmanager.adapter.persistence.entity.TaskEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TaskCrudRepository extends CrudRepository<TaskEntity, UUID> {
}
