package dev.hashnode.rubenscheedler.householdtaskmanager.domain.model.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class User {
    @NonNull
    String nickname;
    boolean isParent;
    boolean isFamilyMember;
}
