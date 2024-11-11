--liquibase formatted sql
--changeset Ranjan:0
 CREATE TABLE "PAYAGGTRANSCTION"."merchant_Customer"
    (
	"id" NUMBER(19,0),
	"merchant_id" VARCHAR2(100 CHAR),
	"customer_id" VARCHAR2(100 CHAR),
	"name" VARCHAR2(100 CHAR),
	"email" VARCHAR2(100 CHAR),
	"phone_number" VARCHAR2(20 CHAR),
	"gstin" VARCHAR2(18 CHAR),
	"gstin_address"	VARCHAR2(500 CHAR),
	"address1" VARCHAR2(100 CHAR),
	"address2" VARCHAR2(100 CHAR), 
	"city" VARCHAR2(100 CHAR), 
	"state" VARCHAR2(100 CHAR), 
	"country" VARCHAR2(100 CHAR), 
	"pincode" VARCHAR2(10 CHAR), 
	"active_status" VARCHAR2(1 CHAR), 
	"created_by" VARCHAR2(100 CHAR), 
	"updated_by" VARCHAR2(100 CHAR),
	"created_date" NUMBER(19,0), 
	"updated_date" NUMBER(19,0)
   )
