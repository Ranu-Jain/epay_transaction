package com.epay.transaction.repositary;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface AggMerchantRfcRuleConfigRepo extends JpaRepository<AggMerchantRfcRuleConfig, String> {

    Optional<AggMerchantRfcRuleConfig> findByRfcRuleId(String rfcRuleId);
    Optional<AggMerchantRfcRuleConfig> findByMerchantId(String merchantId);

}