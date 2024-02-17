package com.sangpt.teamchatspring.domain.entities.enums;

import lombok.Data;

@Data
public class MemberRole {
    public static String ADMIN = "ADMIN";
    public static String MODERATOR = "MODERATOR";
    public static String GUEST = "GUEST";
}
