package com.epay.transaction.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "AGGMERCHANTRFCRULECONFIG")
public class AggMerchantRfcRuleConfig {

    @Id
    @Column(name = "RFCRULEID")
    private String rfcRuleId;

    @Column(name = "AGGREGATORID")
    private String aggregatorId;

    @Column(name = "MERCHANTID")
    private String merchantId;

    @Column(name = "MERCHANTCATEGORYCODE")
    private String merchantCategoryCode;

    @Column(name = "BUSINESSCATEGORY")
    private String businessCategory;

    @Column(name = "OPERATINGMODEID")
    private String operatingModeId;

    @Column(name = "COUNTRYCODE")
    private String countryCode;

    @Column(name = "CURRENCYCODE")
    private String currencyCode;

    /*   @Column(name = "THRESHOLDAMOUNT", precision = 17, scale = 4)
    private Double thresholdAmount;
  */

    @Column(name = "THRESHOLDAMOUNT")
    private Double thresholdAmount;

    @Column(name = "THRESHOLDAMOUNTACTION")
    private String thresholdAmountAction;

    @Column(name = "NEGATIVECARDACTION")
    private String negativeCardAction;

    @Column(name = "NEGATIVEIPACTION")
    private String negativeIpAction;

    @Column(name = "NEGATIVECOUNTRYACTION")
    private String negativeCountryAction;

    @Column(name = "NEGATIVESTATEACTION")
    private String negativeStateAction;

    @Column(name = "NEGATIVECITYACTION")
    private String negativeCityAction;

    @Column(name = "NEGATIVENAMEACTION")
    private String negativeNameAction;

    @Column(name = "NEGATIVEEMAILACTION")
    private String negativeEmailAction;

    @Column(name = "NEGATIVECONTACTNOACTION")
    private String negativeContactNoAction;

    @Column(name = "SDNLISTACION")
    private String sdnListAction;

    @Column(name = "OFACLISTACTION")
    private String ofacListAction;

    @Column(name = "PEPACTION")
    private String pepAction;

    @Column(name = "DAYCARDTXNCOUNT")
    private String dayCardTxnCount;

    @Column(name = "DAYCARDTXNCOUNTACTION")
    private String dayCardTxnCountAction;

    @Column(name = "DORMANCYDAYS")
    private String dormancyDays;

    @Column(name = "DORMANCYACTION")
    private String dormancyAction;

    @Column(name = "BINMODE")
    private String binMode;

    @Column(name = "BINMODEACTION")
    private String binModeAction;

    @Column(name = "ISACTIVE")
    private String isActive;

    @Column(name = "INSTRUCTIONTYPE")
    private String instructionType;

    @Column(name = "RECORDSTATUS")
    private String recordStatus;

    @Column(name = "CREATIONDATE")
    private Date creationDate;

    @Column(name = "CREATEDBY")
    private String createdBy;

    @Column(name = "CREATEDBYSESSIONID")
    private String createdBySessionId;

    @Column(name = "MODIFIEDDATE")
    private Date modifiedDate;

    @Column(name = "MODIFIEDBY")
    private String modifiedBy;

    @Column(name = "MODIFIEDBYSESSIONID")
    private String modifiedBySessionId;

   /* @Column(name = "DAYCARDTXNAMOUNT", precision = 17, scale = 4)
    private Double dayCardTxnAmount;
   */

    @Column(name = "DAYCARDTXNAMOUNT")
    private Double dayCardTxnAmount;

    @Column(name = "DAYCARDTXNAMOUNTACTION")
    private String dayCardTxnAmountAction;





/*
    @Id
    @Column(name = "RFCRULEID", length = 10)
    private String rfcRuleId;

    @Column(name = "AGGREGATORID", length = 10)
    private String aggregatorId;

    @Column(name = "MERCHANTID", length = 10)
    private String merchantId;

    @Column(name = "MERCHANTCATEGORYCODE", length = 10)
    private String merchantCategoryCode;

    @Column(name = "BUSINESSCATEGORY", length = 10)
    private String businessCategory;

    @Column(name = "OPERATINGMODEID", length = 10)
    private String operatingModeId;

    @Column(name = "COUNTRYCODE", length = 10)
    private String countryCode;

    @Column(name = "CURRENCYCODE", length = 10)
    private String currencyCode;

    @Column(name = "THRESHOLDAMOUNT", precision = 17, scale = 4)
    private Double thresholdAmount;



    @Column(name = "THRESHOLDAMOUNTACTION", length = 10)
    private String thresholdAmountAction;

    @Column(name = "NEGATIVECARDACTION", length = 10)
    private String negativeCardAction;

    @Column(name = "NEGATIVEIPACTION", length = 10)
    private String negativeIpAction;

    @Column(name = "NEGATIVECOUNTRYACTION", length = 10)
    private String negativeCountryAction;

    @Column(name = "NEGATIVESTATEACTION", length = 10)
    private String negativeStateAction;

    @Column(name = "NEGATIVECITYACTION", length = 10)
    private String negativeCityAction;

    @Column(name = "NEGATIVENAMEACTION", length = 10)
    private String negativeNameAction;

    @Column(name = "NEGATIVEEMAILACTION", length = 10)
    private String negativeEmailAction;

    @Column(name = "NEGATIVECONTACTNOACTION", length = 10)
    private String negativeContactNoAction;

    @Column(name = "SDNLISTACION", length = 10)
    private String sdnListAction;

    @Column(name = "OFACLISTACTION", length = 10)
    private String ofacListAction;

    @Column(name = "PEPACTION", length = 10)
    private String pepAction;

    @Column(name = "DAYCARDTXNCOUNT", length = 10)
    private String dayCardTxnCount;

    @Column(name = "DAYCARDTXNCOUNTACTION", length = 10)
    private String dayCardTxnCountAction;

    @Column(name = "DORMANCYDAYS", length = 10)
    private String dormancyDays;

    @Column(name = "DORMANCYACTION", length = 10)
    private String dormancyAction;

    @Column(name = "BINMODE", length = 10)
    private String binMode;

    @Column(name = "BINMODEACTION", length = 10)
    private String binModeAction;

    @Column(name = "ISACTIVE", length = 1)
    private String isActive;

    @Column(name = "INSTRUCTIONTYPE", length = 1)
    private String instructionType;

    @Column(name = "RECORDSTATUS", length = 1)
    private String recordStatus;

    @Column(name = "CREATIONDATE")
    private Date creationDate;

    @Column(name = "CREATEDBY", length = 100)
    private String createdBy;

    @Column(name = "CREATEDBYSESSIONID", length = 100)
    private String createdBySessionId;

    @Column(name = "MODIFIEDDATE")
    private Date modifiedDate;

    @Column(name = "MODIFIEDBY", length = 100)
    private String modifiedBy;

    @Column(name = "MODIFIEDBYSESSIONID", length = 100)
    private String modifiedBySessionId;

  @Column(name = "DAYCARDTXNAMOUNT", precision = 17, scale = 4)
    private Double dayCardTxnAmount;




    @Column(name = "DAYCARDTXNAMOUNTACTION", length = 10)
    private String dayCardTxnAmountAction;

    */
}