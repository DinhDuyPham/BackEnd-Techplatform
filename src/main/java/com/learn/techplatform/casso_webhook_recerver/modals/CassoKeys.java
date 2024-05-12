package com.learn.techplatform.casso_webhook_recerver.modals;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CassoKeys {
    private String courseCode;
    private String username;
}
