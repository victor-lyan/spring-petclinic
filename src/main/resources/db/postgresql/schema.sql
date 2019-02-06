CREATE TABLE IF NOT EXISTS "users" (
	"id" serial NOT NULL PRIMARY KEY,
	"first_name" varchar(50) NOT NULL,
	"last_name" varchar(50) NOT NULL,
	"email" varchar(50) NOT NULL UNIQUE,
	"password" varchar(100) NOT NULL,
	"is_active" BOOLEAN NOT NULL DEFAULT 'false',
	"activation_code" varchar(100),
	"created" TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	"modified" TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_users_last_name ON users (last_name);
CREATE INDEX IF NOT EXISTS idx_users_first_name ON users (first_name);

CREATE TABLE IF NOT EXISTS "roles" (
	"id" serial NOT NULL PRIMARY KEY,
	"name" varchar(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS "user_roles" (
	"user_id" int4 NOT NULL,
	"role_id" int4 NOT NULL,
	FOREIGN KEY (user_id) REFERENCES users(id),
	FOREIGN KEY (role_id) REFERENCES roles(id)
);

CREATE TABLE IF NOT EXISTS "owners" (
	"id" serial NOT NULL PRIMARY KEY,
	"user_id" int4 NOT NULL,
	"address" varchar(255),
	"city" varchar(50),
	"phone" varchar(50),
	FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS "pet_types" (
	"id" serial NOT NULL PRIMARY KEY,
	"name" varchar(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS "pets" (
	"id" serial NOT NULL PRIMARY KEY,
	"name" varchar(100) NOT NULL,
	"birthdate" DATE,
	"type_id" int4 NOT NULL,
	"owner_id" int4 NOT NULL,
	FOREIGN KEY (type_id) REFERENCES pet_types(id),
	FOREIGN KEY (owner_id) REFERENCES owners(id)
);

CREATE TABLE IF NOT EXISTS "vets" (
	"id" serial NOT NULL PRIMARY KEY,
	"user_id" int4 NOT NULL
);

CREATE TABLE IF NOT EXISTS "vet_specialties" (
	"vet_id" int4 NOT NULL,
	"specialty_id" int4 NOT NULL,
	FOREIGN KEY (vet_id) REFERENCES vets(id),
  FOREIGN KEY (specialty_id) REFERENCES specialties(id),
  CONSTRAINT unique_ids UNIQUE (vet_id,specialty_id)
);

CREATE TABLE IF NOT EXISTS "specialties" (
	"id" serial NOT NULL PRIMARY KEY,
	"name" varchar NOT NULL
);

CREATE TABLE IF NOT EXISTS "visits" (
	"id" serial NOT NULL PRIMARY KEY,
	"pet_id" int4 NOT NULL,
	"vet_id" int4 NOT NULL,
	"visit_date" TIMESTAMP NOT NULL,
	"description" TEXT,
	"is_finished" BOOLEAN NOT NULL DEFAULT 'false',
	"created" TIMESTAMP NOT NULL DEFAULT 'CURRENT_TIMESTAMP',
	"modified" TIMESTAMP NOT NULL DEFAULT 'CURRENT_TIMESTAMP',
	FOREIGN KEY (vet_id) REFERENCES vets(id),
	FOREIGN KEY (pet_id) REFERENCES pets(id)
);
