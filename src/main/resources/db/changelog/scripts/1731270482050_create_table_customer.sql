CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE customer
    (
	 id UUID NOT NULL DEFAULT UUID_GENERATE_V4(),
	 merchant_id VARCHAR2(100 CHAR),
	 customer_id VARCHAR2(100 CHAR),
	 name VARCHAR2(100 CHAR),
	 email VARCHAR2(100 CHAR),
	 phone_number VARCHAR2(20 CHAR),
	 gst_in VARCHAR2(18 CHAR),
	 gst_in_address VARCHAR2(500 CHAR),
	 address1 VARCHAR2(100 CHAR),
	 address2 VARCHAR2(100 CHAR),
	 city VARCHAR2(100 CHAR),
	 state VARCHAR2(100 CHAR),
	 country VARCHAR2(100 CHAR),
	 pincode VARCHAR2(10 CHAR),
	 status VARCHAR2(1 CHAR),
	 created_at TIMESTAMP NOT NULL,
     updated_at TIMESTAMP NOT NULL,
     created_by CHARACTER(200),
     updated_by CHARACTER(200)
   )
