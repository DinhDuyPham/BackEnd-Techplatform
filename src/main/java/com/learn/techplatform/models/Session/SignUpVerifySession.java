package com.learn.techplatform.models.Session;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
@AllArgsConstructor
public class SignUpVerifySession {
    private String userId;
    private String passcode;
}
