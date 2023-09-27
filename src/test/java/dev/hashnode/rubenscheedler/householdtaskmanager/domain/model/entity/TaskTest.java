package dev.hashnode.rubenscheedler.householdtaskmanager.domain.model.entity;

import dev.hashnode.rubenscheedler.householdtaskmanager.domain.model.entity.Task;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.model.value.id.TaskId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class TaskTest {
    @Test
    void task_idNull_raisesException() {
        assertThatThrownBy(() -> Task.builder()
                // no id
                .description("fix the lamp")
                .build()
        )
        .isInstanceOf(NullPointerException.class);
    }

    @Test
    void task_descriptionNull_raisesException() {
        assertThatThrownBy(() -> Task.builder()
                .id(new TaskId(UUID.randomUUID()))
                // no description
                .build()
        )
        .isInstanceOf(NullPointerException.class);
    }
}
