package com.learn.techplatform.casso_webhook_recerver.modals;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CassoData {
    public int id;
    public String when;
    public int amount;
    public String description;
    public int cusum_balance;
    public String tid;
    public String bankName;
    public String bankAbbreviation;
    public String subAccId;
    public String bank_sub_acc_id;
    public String virtualAccount;
    public String virtualAccountName;
    public String corresponsiveName;
    public String corresponsiveAccount;
    public String corresponsiveBankId;
    public String corresponsiveBankName;
}
