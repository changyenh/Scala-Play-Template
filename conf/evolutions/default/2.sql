# --- !Ups

CREATE TABLE "oauth_client" (
  "client_id" UUID PRIMARY KEY,
  "client_secret" VARCHAR(255) NOT NULL,
	"user_id" UUID NOT NULL,
	"redirect_uri" VARCHAR(255),
	"scope" VARCHAR(255) NOT NULL,
	"grant_type" VARCHAR(50) NOT NULL,
	"created_at" TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE "oauth_authorization_code" (
	"key" SERIAL PRIMARY KEY,
	"user_id" UUID NOT NULL,
	"client_id" UUID NOT NULL,
	"auth_code" VARCHAR(255) NOT NULL UNIQUE,
	"redirect_uri" VARCHAR(255) NOT NULL,
	"created_at" TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE "oauth_access_token" (
	"key" SERIAL PRIMARY KEY,
	"user_id" UUID NOT NULL,
	"client_id" UUID NOT NULL,
	"access_token" VARCHAR(255) NOT NULL UNIQUE,
	"refresh_token" VARCHAR(255) NOT NULL UNIQUE,
	"scope" VARCHAR(255) NOT NULL,
	"created_at" TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

# --- !Downs