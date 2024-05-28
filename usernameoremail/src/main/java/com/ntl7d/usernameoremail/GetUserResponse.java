package com.ntl7d.usernameoremail;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record GetUserResponse(boolean success, String username, String email) {
}
