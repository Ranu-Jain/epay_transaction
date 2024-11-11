CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE customer
    (
	 id UUID NOT NULL DEFAULT UUID_GENERATE_V4(),
	 merchant_id CHARACTER(100),
	 customer_id CHARACTER(100),
	 name CHARACTER(100),
	 email CHARACTER(100),
	 phone_number CHARACTER(20),
	 gst_in CHARACTER(18),
	 gst_in_address CHARACTER(500),
	 address1 CHARACTER(100),
	 address2 CHARACTER(100),
	 city CHARACTER(100),
	 state CHARACTER(100),
	 country CHARACTER(100),
	 pincode CHARACTER(10),
	 status CHARACTER(1),
	 created_at TIMESTAMP NOT NULL,
     updated_at TIMESTAMP NOT NULL,
     created_by CHARACTER(200),
     updated_by CHARACTER(200)
   )
