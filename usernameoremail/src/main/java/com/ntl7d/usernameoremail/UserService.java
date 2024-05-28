package com.ntl7d.usernameoremail;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public CreateUserResponse createUser(CreateUserRequest request) {
        User user = User.builder().username(request.username()).email(request.email())
                .password(request.password()).build();
        userRepository.save(user);
        return new CreateUserResponse(user.getUsername(), user.getEmail());
    }

    public GetUserResponse getUsernameOrEmail(String usernameOrEmail) {
        User user = userRepository.findByUsernameOrEmail(usernameOrEmail).orElseThrow();

        if (usernameOrEmail.equals(user.getEmail())) {
            return new GetUserResponse(true, null, usernameOrEmail);
        } else if (usernameOrEmail.equals(user.getUsername())) {
            return new GetUserResponse(true, usernameOrEmail, null);
        } else {
            return new GetUserResponse(false, null, null);
        }


    }

}
