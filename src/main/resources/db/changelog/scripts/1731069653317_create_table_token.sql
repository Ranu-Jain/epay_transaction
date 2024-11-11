CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE token (
    id UUID NOT NULL DEFAULT UUID_GENERATE_V4(),
    merchant_id CHARACTER(255) NOT NULL,
    token_type CHARACTER(50) NOT NULL,
    generated_token CHARACTER(200),
    token_expiry_time TIMESTAMP,
    is_token_valid BOOLEAN DEFAULT false,
    failed_reason CHARACTER(100),
    remarks CHARACTER(100),
    expired_at TIMESTAMP,
    status CHARACTER(20),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    created_by CHARACTER(200),
    updated_by CHARACTER(200)
);

CREATE TABLE token_audit (
    id UUID NOT NULL DEFAULT UUID_GENERATE_V4(),
    token_id CHARACTER(36) NOT NULL,
    action_type CHARACTER(50) NOT NULL
);

CREATE TABLE merchant_order_info (
    id UUID NOT NULL DEFAULT UUID_GENERATE_V4(),
    status CHARACTER(50),
    order_hash CHARACTER(50),
    merchant_token_id CHARACTER(36),
    merchant_id CHARACTER(50),
    merchant_customer_id CHARACTER(36),
    country_id CHARACTER(50),
    currency_id CHARACTER(50),
    amount CHARACTER,
    order_ref_num CHARACTER(255),
    operation_mode CHARACTER(50),
    txn_mode CHARACTER(50),
    payment_mode CHARACTER(50),
    access_mode CHARACTER(50),
    order_status CHARACTER(50),
    order_request_count CHARACTER,
    callback_url CHARACTER(255),
    failed_reason CHARACTER(255),
    system_ip CHARACTER(255),
    geo_location CHARACTER(255),
    system_details CHARACTER(50),
    order_generation_mode CHARACTER(50),
    other_details CHARACTER(1000),
    expiry TIMESTAMP,
    tpv CHARACTER(1000)
);


CREATE INDEX idx_merchant_id ON merchant_order_info (merchant_id);