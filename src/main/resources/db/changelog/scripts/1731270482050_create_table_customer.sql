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







