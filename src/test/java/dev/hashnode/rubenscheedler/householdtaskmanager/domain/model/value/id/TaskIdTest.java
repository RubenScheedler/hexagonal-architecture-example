package dev.hashnode.rubenscheedler.householdtaskmanager.domain.model.value.id;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class TaskIdTest {
    @Test
    void taskId_idNull_raisesException() {
        assertThatThrownBy(() -> new TaskId(null)).isInstanceOf(NullPointerException.class);
    }
}
