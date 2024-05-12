package com.learn.techplatform.vietqr_bank.modals;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VietQrDataResponse {
    private String qrCode;
    private String qrDataURL;
}
