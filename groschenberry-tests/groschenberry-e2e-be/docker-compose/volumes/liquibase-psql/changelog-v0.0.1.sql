--liquibase formatted sql

--changeset mditina:1 labels:v0.0.1
CREATE TYPE "country_type" AS ENUM ('Australia', 'Belarus', 'Great Britain', 'Russia');
CREATE TYPE "currency_type" AS ENUM ('RUB', 'EUR', 'USD', 'CNY');
--CREATE TYPE "nominal_type" AS ENUM ('1', '2', '5', '10', '15', '20', '25', '50', '100');

CREATE TABLE "cibs" (
	"id" text primary key constraint cibs_id_length_ctr check (length("id") < 64),
	"title" text constraint cibs_title_length_ctr check (length(title) < 128),
	"description" text constraint cibs_description_length_ctr check (length(description) < 4096),
	"country" country_type,
	"currency" currency_type,
	"nominal" integer,
	"material" text constraint cibs_material_length_ctr check (length(material) < 255),
	"diameter" decimal,
	"start_year" text constraint cibs_start_year_length_ctr check (length(start_year) = 4),
	"stop_year" text constraint cibs_stop_year_length_ctr check (length(stop_year) = 4),
	"lock" text not null constraint cibs_lock_length_ctr check (length(lock) < 64)
);

CREATE INDEX cibs_country_idx on "cibs" using hash ("country");

CREATE INDEX cibs_currency_idx on "cibs" using hash ("currency");

CREATE TABLE "cids" (
	"id" text primary key constraint cids_id_length_ctr check (length("id") < 64),
	"description" text constraint cids_description_length_ctr check (length(description) < 4096),
	"mint" text constraint cids_mint_length_ctr check (length(mint) < 255),
	"copies" integer,
	"issue_year" text constraint cids_issue_year_length_ctr check (length(issue_year) = 4),
	"cib_id" text not null constraint cids_cib_id_length_ctr check (length(cib_id) < 64),
	--"cib_id" text not null foreign key references cibs (id) constraint cids_cib_id_length_ctr check (length(lock) < 64)
	"lock" text not null constraint cids_lock_length_ctr check (length(lock) < 64)
);

CREATE INDEX cids_cib_id_idx on "cids" using hash ("cib_id");