drop table token;
drop table token_audit;
drop table merchant_order_info;

CREATE TABLE token (
    id RAW(16) DEFAULT SYS_GUID() PRIMARY KEY NOT NULL,
    merchant_id VARCHAR2(255 CHAR) NOT NULL,
    token_type VARCHAR2(50 CHAR) NOT NULL,
    generated_token VARCHAR2(2000 CHAR),
    token_expiry_time NUMBER,
    is_token_valid NUMBER(1) NOT NULL,
    failed_reason VARCHAR2(100 CHAR),
    remarks VARCHAR2(100 CHAR),
    expired_at NUMBER,
    status VARCHAR2(20 CHAR),
    created_at NUMBER NOT NULL,
    updated_at NUMBER NOT NULL,
    created_by VARCHAR2(200 CHAR),
    updated_by VARCHAR2(200 CHAR)
);

CREATE TABLE token_audit (
    id RAW(16) DEFAULT SYS_GUID() PRIMARY KEY NOT NULL,
    token_id RAW(16) NOT NULL,
    action_type VARCHAR2(50 CHAR) NOT NULL
);

CREATE TABLE merchant_order_info(
    id RAW(16) DEFAULT SYS_GUID() PRIMARY KEY NOT NULL,
    status VARCHAR2(50 CHAR),
    order_hash VARCHAR2(50 CHAR),
    merchant_token_id RAW(16),
    merchant_id VARCHAR2(50 CHAR),
    merchant_customer_id RAW(16),
    country_id VARCHAR2(50 CHAR),
    currency_id VARCHAR2(50 CHAR),
    amount NUMBER,
    order_ref_num VARCHAR2(255 CHAR),
    sbi_order_ref_num VARCHAR2(255 CHAR),
    operation_mode VARCHAR2(50 CHAR),
    txn_mode VARCHAR2(50 CHAR),
    payment_mode VARCHAR2(50 CHAR),
    access_mode VARCHAR2(50 CHAR),
    order_status VARCHAR2(50 CHAR),
    order_request_count NUMBER,
    callback_url VARCHAR2(255 CHAR),
    failed_reason VARCHAR2(255 CHAR),
    system_ip VARCHAR2(255 CHAR),
    geo_location VARCHAR2(255 CHAR),
    system_details VARCHAR2(50 CHAR),
    order_generation_mode VARCHAR2(50 CHAR),
    other_details CLOB,
    expiry NUMBER,
    tpv CLOB,
    token VARCHAR2(2000 CHAR)
);


CREATE INDEX idx_merchant_id ON merchant_order_info (merchant_id);


INSERT INTO merchant_order_info (
    status,
    order_hash,
    merchant_id,
    sbi_order_ref_num,
    expiry
) VALUES (
    'ACTIVE',
    'ff382163eaaa135afd90735',
    '1122323',
    'sbiorder1234',
    10
);

commit;