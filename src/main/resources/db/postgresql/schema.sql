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

/* 
 * we need to do this, because we insert some initial data with hardcoded ids
 * without this if we insert some data manually, there will be an auto-increment error 
*/
ALTER SEQUENCE users_id_seq RESTART WITH 100;

CREATE TABLE IF NOT EXISTS "roles" (
	"id" serial NOT NULL PRIMARY KEY,
	"name" varchar(50) NOT NULL
);

ALTER SEQUENCE roles_id_seq RESTART WITH 100;

CREATE TABLE IF NOT EXISTS "user_roles" (
	"user_id" int4 NOT NULL,
	"role_id" int4 NOT NULL,
	CONSTRAINT unique_user_and_role_ids UNIQUE (user_id,role_id),
	FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
	FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS "owners" (
	"id" int4 NOT NULL PRIMARY KEY,
	"address" varchar(255),
	"city" varchar(50),
	"phone" varchar(50),
	FOREIGN KEY (id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS "pet_types" (
	"id" serial NOT NULL PRIMARY KEY,
	"name" varchar(50) NOT NULL
);

ALTER SEQUENCE pet_types_id_seq RESTART WITH 100;

CREATE TABLE IF NOT EXISTS "pets" (
	"id" serial NOT NULL PRIMARY KEY,
	"name" varchar(100) NOT NULL,
	"birth_date" DATE,
	"type_id" int4 NOT NULL,
	"owner_id" int4 NOT NULL,
	FOREIGN KEY (type_id) REFERENCES pet_types(id) ON DELETE CASCADE,
	FOREIGN KEY (owner_id) REFERENCES owners(id) ON DELETE CASCADE
);

ALTER SEQUENCE pets_id_seq RESTART WITH 100;

CREATE TABLE IF NOT EXISTS "vets" (
	"id" int4 NOT NULL PRIMARY KEY,
	"working_days" varchar(255),
	"working_hours" varchar(50),
	FOREIGN KEY (id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS "specialties" (
	"id" serial NOT NULL PRIMARY KEY,
	"name" varchar NOT NULL
);

ALTER SEQUENCE specialties_id_seq RESTART WITH 100;

CREATE TABLE IF NOT EXISTS "vet_specialties" (
	"vet_id" int4 NOT NULL,
	"specialty_id" int4 NOT NULL,
	FOREIGN KEY (vet_id) REFERENCES vets(id) ON DELETE CASCADE,
  FOREIGN KEY (specialty_id) REFERENCES specialties(id) ON DELETE CASCADE,
  CONSTRAINT unique_ids UNIQUE (vet_id,specialty_id)
);

CREATE TABLE IF NOT EXISTS "visits" (
	"id" serial NOT NULL PRIMARY KEY,
	"pet_id" int4 NOT NULL,
	"vet_id" int4 NOT NULL,
	"visit_date" TIMESTAMP NOT NULL,
	"description" TEXT,
	"is_finished" BOOLEAN NOT NULL DEFAULT 'false',
	"created" TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	"modified" TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	FOREIGN KEY (vet_id) REFERENCES vets(id) ON DELETE CASCADE,
	FOREIGN KEY (pet_id) REFERENCES pets(id) ON DELETE CASCADE
);

ALTER SEQUENCE visits_id_seq RESTART WITH 100;
