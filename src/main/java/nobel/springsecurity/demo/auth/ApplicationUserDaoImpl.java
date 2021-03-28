package nobel.springsecurity.demo.auth;

import nobel.springsecurity.demo.security.UserRole;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository("fake")
public class ApplicationUserDaoImpl implements ApplicationUserDao{
    private List<ApplicationUser> applicationUsers;
    private final PasswordEncoder passwordEncoder;

    public ApplicationUserDaoImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        applicationUsers = new ArrayList<>();
        applicationUsers.add(new ApplicationUser(UserRole.STUDENT.getGrantedAuthorities(),
                "student",
                passwordEncoder.encode("123"),
                true,
                true,
                true,
                true));

        applicationUsers.add(new ApplicationUser(UserRole.ADMINTRAINEE.getGrantedAuthorities(),
                "tom",
                passwordEncoder.encode("123"),
                true,
                true,
                true,
                true));

        applicationUsers.add(new ApplicationUser(UserRole.ADMIN.getGrantedAuthorities(),
                "ahmed",
                passwordEncoder.encode("123"),
                true,
                true,
                true,
                true));
    }

    @Override
    public Optional<ApplicationUser> selectApplicationUserByUsername(String username) {
        return applicationUsers.stream().filter(user -> user.getUsername().equals(username)).findFirst();
    }
}
