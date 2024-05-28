package com.ntl7d.usernameoremail;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;



@RestController
@RequestMapping(name = "test")
@RequiredArgsConstructor
public class UsernameOrEmailController {

    private final UserService userService;

    @PostMapping("add")
    public ResponseEntity<CreateUserResponse> createUser(@RequestBody CreateUserRequest request) {
        return ResponseEntity.ok(userService.createUser(request));
    }



    @PostMapping("view")
    public ResponseEntity<GetUserResponse> usernameOrEmail(@RequestBody String usernameOrEmail) {
        return ResponseEntity.ok(userService.getUsernameOrEmail(usernameOrEmail));
    }

}
