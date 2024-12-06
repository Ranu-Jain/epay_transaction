--liquibase formatted sql
--changeset Ranjan:1
CREATE TABLE customer
    (
	 id RAW(16) PRIMARY KEY NOT NULL ,
	 merchant_id VARCHAR2(100),
	 customer_id VARCHAR2(100),
	 customer_name VARCHAR2(100),
	 email VARCHAR2(100),
	 phone_number VARCHAR2(20),
	 gst_in VARCHAR2(18),
	 gst_in_address VARCHAR2(500),
	 address1 VARCHAR2(100),
	 address2 VARCHAR2(100),
	 city VARCHAR2(100),
	 state VARCHAR2(100),
	 country VARCHAR2(100),
	 pincode VARCHAR2(10),
	 status VARCHAR2(1),
	 created_date NUMBER NOT NULL,
     updated_date NUMBER NOT NULL,
     created_by VARCHAR2(200),
     updated_by VARCHAR2(200)
   )

CREATE TABLE Transaction (
    id                 RAW(16) DEFAULT SYS_GUID() PRIMARY KEY,
    merchant_order_id  VARCHAR2(100),
    merchant_token_id  VARCHAR2(100),
    atrn_num           VARCHAR2(255),
    reference_number   VARCHAR2(255),
    pay_mode           VARCHAR2(50),
    payment_mode_details CLOB,
    payment_status     VARCHAR2(50),
    txn_request_count  NUMBER,
    fail_reason        VARCHAR2(255),
    debit_amt          NUMBER(15,2),
    gstin              VARCHAR2(50),
    channel_bank       VARCHAR2(100),
    order_ref_num      VARCHAR2(255),
    settlement_status  VARCHAR2(50),
    refund_status      VARCHAR2(50),
    cancellation_status VARCHAR2(50),
    cin                VARCHAR2(50),
    push_response      CLOB,
    created_by         VARCHAR2(255),
    updated_by         VARCHAR2(255),
    created_date       NUMBER NOT NULL,
    updated_date       NUMBER NOT NULL,
    status             VARCHAR2(50)
);





