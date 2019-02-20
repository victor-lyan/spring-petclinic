INSERT INTO users VALUES (1, 'James', 'Carter', 'j_carter@gmail.com', '$2a$10$5IGGLE6ICNCirp9ZM.fsZeSERWyvqb/AH7LeS58n8ldp20zxjMPPO', true) ON CONFLICT DO NOTHING;
INSERT INTO users VALUES (2, 'Helen', 'Leary', 'helen.leary@yahoo.com', '$2a$10$5IGGLE6ICNCirp9ZM.fsZeSERWyvqb/AH7LeS58n8ldp20zxjMPPO', true) ON CONFLICT DO NOTHING;
INSERT INTO users VALUES (3, 'Linda', 'Douglas', 'lindi_star@gmail.com', '$2a$10$5IGGLE6ICNCirp9ZM.fsZeSERWyvqb/AH7LeS58n8ldp20zxjMPPO', true) ON CONFLICT DO NOTHING;
INSERT INTO users VALUES (4, 'Rafael', 'Ortega', 'rafa_ortega@mail.com', '$2a$10$5IGGLE6ICNCirp9ZM.fsZeSERWyvqb/AH7LeS58n8ldp20zxjMPPO', true) ON CONFLICT DO NOTHING;
INSERT INTO users VALUES (5, 'Henry', 'Stevens', 'ohenry@gmail.com', '$2a$10$5IGGLE6ICNCirp9ZM.fsZeSERWyvqb/AH7LeS58n8ldp20zxjMPPO', true) ON CONFLICT DO NOTHING;
INSERT INTO users VALUES (6, 'Sharon', 'Jenkins', 'sharon_slut333@yandex.ru', '$2a$10$5IGGLE6ICNCirp9ZM.fsZeSERWyvqb/AH7LeS58n8ldp20zxjMPPO', true) ON CONFLICT DO NOTHING;

INSERT INTO users VALUES (7, 'George', 'Franklin', 'gf_beaver@mail.com', '$2a$10$5IGGLE6ICNCirp9ZM.fsZeSERWyvqb/AH7LeS58n8ldp20zxjMPPO', true) ON CONFLICT DO NOTHING;
INSERT INTO users VALUES (8, 'Betty', 'Davis', 'bettydavis@gmail.com', '$2a$10$5IGGLE6ICNCirp9ZM.fsZeSERWyvqb/AH7LeS58n8ldp20zxjMPPO', true) ON CONFLICT DO NOTHING;
INSERT INTO users VALUES (9, 'Eduardo', 'Rodriquez', 'edu_r74@gmail.com', '$2a$10$5IGGLE6ICNCirp9ZM.fsZeSERWyvqb/AH7LeS58n8ldp20zxjMPPO', true) ON CONFLICT DO NOTHING;
INSERT INTO users VALUES (10, 'Harold', 'Davis', 'haroldinho@mail.com', '$2a$10$5IGGLE6ICNCirp9ZM.fsZeSERWyvqb/AH7LeS58n8ldp20zxjMPPO', true) ON CONFLICT DO NOTHING;
INSERT INTO users VALUES (11, 'Peter', 'McTavish', 'fuckerman784@gmail.com', '$2a$10$5IGGLE6ICNCirp9ZM.fsZeSERWyvqb/AH7LeS58n8ldp20zxjMPPO', true) ON CONFLICT DO NOTHING;
INSERT INTO users VALUES (12, 'Jean', 'Coleman', 'jeany_beany@yahoo.com', '$2a$10$5IGGLE6ICNCirp9ZM.fsZeSERWyvqb/AH7LeS58n8ldp20zxjMPPO', true) ON CONFLICT DO NOTHING;
INSERT INTO users VALUES (13, 'Jeff', 'Black', 'black.dildo@mail.com', '$2a$10$5IGGLE6ICNCirp9ZM.fsZeSERWyvqb/AH7LeS58n8ldp20zxjMPPO', true) ON CONFLICT DO NOTHING;
INSERT INTO users VALUES (14, 'Maria', 'Escobito', 'masha.prostokvasha@mail.ru', '$2a$10$5IGGLE6ICNCirp9ZM.fsZeSERWyvqb/AH7LeS58n8ldp20zxjMPPO', true) ON CONFLICT DO NOTHING;
INSERT INTO users VALUES (15, 'David', 'Schroeder', '3120788@mail.com', '$2a$10$5IGGLE6ICNCirp9ZM.fsZeSERWyvqb/AH7LeS58n8ldp20zxjMPPO', true) ON CONFLICT DO NOTHING;
INSERT INTO users VALUES (16, 'Carlos', 'Estaban', 'mrcarlos@mail.com', '$2a$10$5IGGLE6ICNCirp9ZM.fsZeSERWyvqb/AH7LeS58n8ldp20zxjMPPO', true) ON CONFLICT DO NOTHING;

INSERT INTO vets VALUES (1, 'MONDAY, WEDNESDAY, FRIDAY', '10:00 - 15:00') ON CONFLICT DO NOTHING;
INSERT INTO vets VALUES (2, 'MONDAY, WEDNESDAY, FRIDAY, SATURDAY', '09:00 - 17:00') ON CONFLICT DO NOTHING;
INSERT INTO vets VALUES (3, 'TUESDAY, THURSDAY, SATURDAY', '08:00 - 14:00') ON CONFLICT DO NOTHING;
INSERT INTO vets VALUES (4, 'WEDNESDAY, SUNDAY', '13:00 - 18:00') ON CONFLICT DO NOTHING;
INSERT INTO vets VALUES (5, 'EVERYDAY', '10:00 - 14:00') ON CONFLICT DO NOTHING;
INSERT INTO vets VALUES (6, 'MONDAY, WEDNESDAY, FRIDAY, SUNDAY', '07:00 - 18:00') ON CONFLICT DO NOTHING;

INSERT INTO specialties VALUES (1, 'radiology') ON CONFLICT DO NOTHING;
INSERT INTO specialties VALUES (2, 'surgery') ON CONFLICT DO NOTHING;
INSERT INTO specialties VALUES (3, 'dentistry') ON CONFLICT DO NOTHING;

INSERT INTO vet_specialties VALUES (1, 2) ON CONFLICT DO NOTHING;
INSERT INTO vet_specialties VALUES (2, 1) ON CONFLICT DO NOTHING;
INSERT INTO vet_specialties VALUES (3, 2) ON CONFLICT DO NOTHING;
INSERT INTO vet_specialties VALUES (3, 3) ON CONFLICT DO NOTHING;
INSERT INTO vet_specialties VALUES (4, 2) ON CONFLICT DO NOTHING;
INSERT INTO vet_specialties VALUES (5, 1) ON CONFLICT DO NOTHING;
INSERT INTO vet_specialties VALUES (6, 3) ON CONFLICT DO NOTHING;
INSERT INTO vet_specialties VALUES (6, 2) ON CONFLICT DO NOTHING;
INSERT INTO vet_specialties VALUES (6, 1) ON CONFLICT DO NOTHING;

INSERT INTO pet_types VALUES (1, 'cat') ON CONFLICT DO NOTHING;
INSERT INTO pet_types VALUES (2, 'dog') ON CONFLICT DO NOTHING;
INSERT INTO pet_types VALUES (3, 'lizard') ON CONFLICT DO NOTHING;
INSERT INTO pet_types VALUES (4, 'snake') ON CONFLICT DO NOTHING;
INSERT INTO pet_types VALUES (5, 'bird') ON CONFLICT DO NOTHING;
INSERT INTO pet_types VALUES (6, 'hamster') ON CONFLICT DO NOTHING;

INSERT INTO owners VALUES (7, '110 W. Liberty St.', 'Madison', '6085551023') ON CONFLICT DO NOTHING;
INSERT INTO owners VALUES (8, '638 Cardinal Ave.', 'Sun Prairie', '6085551749') ON CONFLICT DO NOTHING;
INSERT INTO owners VALUES (9, '2693 Commerce St.', 'McFarland', '6085558763') ON CONFLICT DO NOTHING;
INSERT INTO owners VALUES (10, '563 Friendly St.', 'Windsor', '6085553198') ON CONFLICT DO NOTHING;
INSERT INTO owners VALUES (11, '2387 S. Fair Way', 'Madison', '6085552765') ON CONFLICT DO NOTHING;
INSERT INTO owners VALUES (12, '105 N. Lake St.', 'Monona', '6085552654') ON CONFLICT DO NOTHING;
INSERT INTO owners VALUES (13, '1450 Oak Blvd.', 'Monona', '6085555387') ON CONFLICT DO NOTHING;
INSERT INTO owners VALUES (14, '345 Maple St.', 'Madison', '6085557683') ON CONFLICT DO NOTHING;
INSERT INTO owners VALUES (15, '2749 Blackhawk Trail', 'Madison', '6085559435') ON CONFLICT DO NOTHING;
INSERT INTO owners VALUES (16, '2335 Independence La.', 'Waunakee', '6085555487') ON CONFLICT DO NOTHING;

INSERT INTO pets VALUES (1, 'Leo', '2000-09-07', 1, 7) ON CONFLICT DO NOTHING;
INSERT INTO pets VALUES (2, 'Basil', '2002-08-06', 6, 8) ON CONFLICT DO NOTHING;
INSERT INTO pets VALUES (3, 'Rosy', '2001-04-17', 2, 9) ON CONFLICT DO NOTHING;
INSERT INTO pets VALUES (4, 'Jewel', '2000-03-07', 2, 9) ON CONFLICT DO NOTHING;
INSERT INTO pets VALUES (5, 'Iggy', '2000-11-30', 3, 10) ON CONFLICT DO NOTHING;
INSERT INTO pets VALUES (6, 'George', '2000-01-20', 4, 11) ON CONFLICT DO NOTHING;
INSERT INTO pets VALUES (7, 'Samantha', '1995-09-04', 1, 12) ON CONFLICT DO NOTHING;
INSERT INTO pets VALUES (8, 'Max', '1995-09-04', 1, 12) ON CONFLICT DO NOTHING;
INSERT INTO pets VALUES (9, 'Lucky', '1999-08-06', 5, 13) ON CONFLICT DO NOTHING;
INSERT INTO pets VALUES (10, 'Mulligan', '1997-02-24', 2, 14) ON CONFLICT DO NOTHING;
INSERT INTO pets VALUES (11, 'Freddy', '2000-03-09', 5, 15) ON CONFLICT DO NOTHING;
INSERT INTO pets VALUES (12, 'Lucky', '2000-06-24', 2, 16) ON CONFLICT DO NOTHING;
INSERT INTO pets VALUES (13, 'Sly', '2002-06-08', 1, 16) ON CONFLICT DO NOTHING;

INSERT INTO visits VALUES (1, 7, 6, '2010-03-04 12:00:00', 'rabies shot') ON CONFLICT DO NOTHING;
INSERT INTO visits VALUES (2, 8, 3, '2011-03-04 10:30:00', 'rabies shot') ON CONFLICT DO NOTHING;
INSERT INTO visits VALUES (3, 8, 1, '2009-06-04 17:00:00', 'neutered') ON CONFLICT DO NOTHING;
INSERT INTO visits VALUES (4, 1, 4, '2008-09-04 13:45:00', 'spayed') ON CONFLICT DO NOTHING;


INSERT INTO roles VALUES (1, 'OWNER') ON CONFLICT DO NOTHING;
INSERT INTO roles VALUES (2, 'VET') ON CONFLICT DO NOTHING;

INSERT INTO user_roles VALUES (1, 2) ON CONFLICT DO NOTHING;
INSERT INTO user_roles VALUES (2, 2) ON CONFLICT DO NOTHING;
INSERT INTO user_roles VALUES (3, 2) ON CONFLICT DO NOTHING;
INSERT INTO user_roles VALUES (4, 2) ON CONFLICT DO NOTHING;
INSERT INTO user_roles VALUES (5, 2) ON CONFLICT DO NOTHING;
INSERT INTO user_roles VALUES (6, 2) ON CONFLICT DO NOTHING;
INSERT INTO user_roles VALUES (7, 1) ON CONFLICT DO NOTHING;
INSERT INTO user_roles VALUES (8, 1) ON CONFLICT DO NOTHING;
INSERT INTO user_roles VALUES (9, 1) ON CONFLICT DO NOTHING;
INSERT INTO user_roles VALUES (10, 1) ON CONFLICT DO NOTHING;
INSERT INTO user_roles VALUES (11, 1) ON CONFLICT DO NOTHING;
INSERT INTO user_roles VALUES (12, 1) ON CONFLICT DO NOTHING;
INSERT INTO user_roles VALUES (13, 1) ON CONFLICT DO NOTHING;
INSERT INTO user_roles VALUES (14, 1) ON CONFLICT DO NOTHING;
INSERT INTO user_roles VALUES (15, 1) ON CONFLICT DO NOTHING;
INSERT INTO user_roles VALUES (16, 1) ON CONFLICT DO NOTHING;
