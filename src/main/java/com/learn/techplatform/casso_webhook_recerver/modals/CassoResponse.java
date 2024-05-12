package com.learn.techplatform.casso_webhook_recerver.modals;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CassoResponse {
    public int error;
    public List<CassoData> data;
}
