package com.epay.transaction.repositary;


import com.epay.transaction.entity.AggMerchantRfcRuleConfig;
import com.epay.transaction.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;



@Repository
public interface AggMerchantRfcRuleConfigRepo extends JpaRepository<AggMerchantRfcRuleConfig, String> {

    Optional<AggMerchantRfcRuleConfig> findByRfcRuleId(String rfcRuleId);
    Optional<AggMerchantRfcRuleConfig> findByMerchantId(String merchantId);

}