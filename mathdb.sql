drop table if exists questions;
drop table if exists keywords;
drop table if exists maps;

create table questions (
	id INT NOT NULL,
	question VARCHAR (50) NOT NULL,
	PRIMARY KEY (id)
);

create table keywords (
	id INT NOT NULL,
	keyword VARCHAR (20) NOT NULL,
	PRIMARY KEY (id)
);

create table maps (
	id INT NOT NULL,
	q_id INT NOT NULL,
	k_id INT NOT NULL,
	PRIMARY KEY (id)
);