package nobel.springsecurity.demo.security;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static nobel.springsecurity.demo.security.UserPermission.*;

public enum UserRole {
    STUDENT(new HashSet<>()),
    ADMIN(new HashSet<>(Arrays.asList(COURSE_READ, COURSE_WRITE, STUDENT_READ, STUDENT_WRITE))),
    ADMINTRAINEE(new HashSet<>(Arrays.asList(COURSE_READ, STUDENT_READ)));


    private final Set<UserPermission> permissions;

    UserRole(Set<UserPermission> permissions) {
        this.permissions = permissions;
    }
}
