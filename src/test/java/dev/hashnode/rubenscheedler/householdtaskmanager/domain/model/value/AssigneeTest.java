package dev.hashnode.rubenscheedler.householdtaskmanager.domain.model.value;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class AssigneeTest {
    @Test
    void assignee_nicknameNull_raisesException() {
        assertThatThrownBy(() -> Assignee.builder().build()).isInstanceOf(NullPointerException.class);
    }
}
