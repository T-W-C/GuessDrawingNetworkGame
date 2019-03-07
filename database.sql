create table if not exists player
(
	pid serial not null,
	username varchar not null
		constraint player_pk
			primary key,
	password varchar(600) not null,
	email varchar not null,
	rank integer default 1 not null,
	totalscore integer default 0 not null
);

alter table player owner to group1;

create unique index player_email_uindex
	on player (email);

create unique index player_username_uindex
	on player (username);

create unique index player_pid_uindex
	on player (pid);

create table if not exists match
(
	mid serial not null
		constraint match_pk
			primary key,
	timestamp timestamp not null,
	gamemode integer not null
);

alter table match owner to group1;

create unique index match_mid_uindex
	on match (mid);

create table if not exists game
(
	pid integer not null
		constraint match___fk
			references match
		constraint player__fk
			references player (pid),
	mid integer not null,
	score integer not null,
	"placing" integer not null
);

alter table game owner to group1;

create table if not exists word
(
	word varchar not null
);

alter table word owner to group1;

create unique index wrod_word_uindex
	on word (word);

create table if not exists achievement
(
	aid serial not null
		constraint achievement_pk
			primary key,
	achievementname varchar not null,
	category varchar not null
);

alter table achievement owner to group1;

create unique index achievement_achievementname_uindex
	on achievement (achievementname);

create unique index achievement_aid_uindex
	on achievement (aid);

create table if not exists achievementunlocked
(
	aid integer
		constraint achievementunlocked_achievement__fk
			references achievement,
	pid integer
		constraint achievementunlocked_player_pid_fk
			references player (pid),
	timestamp timestamp
);

alter table achievementunlocked owner to group1;