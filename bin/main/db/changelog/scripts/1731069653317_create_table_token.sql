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

CREATE TABLE orders(
    id RAW(16) DEFAULT SYS_GUID() PRIMARY KEY NOT NULL,
    merchant_id VARCHAR2(50 CHAR),
    customer_id VARCHAR2(50 CHAR),
    currency_code VARCHAR2(50 CHAR),
    order_amount NUMBER,
    order_ref_number VARCHAR2(255 CHAR),
    sbi_order_ref_number VARCHAR2(255 CHAR),
    status VARCHAR2(50 CHAR),
    other_details CLOB,
    expiry NUMBER,
    multi_accounts CLOB,
    payment_mode VARCHAR2(50 CHAR),
    order_hash VARCHAR2(50 CHAR),
    created_by VARCHAR2(50 CHAR),
    updated_by VARCHAR2(50 CHAR),
    created_date NUMBER,
    updated_date NUMBER
);

CREATE INDEX idx_merchant_id ON order (merchant_id);

INSERT INTO order (
    status,
    order_hash,
    merchant_id,
    sbi_order_ref_number,
    expiry
) VALUES (
    'CREATED',
    'ff382163eaaa135afd90735',
    '1122323',
    'sbiorder1234',
    10
);

commit;