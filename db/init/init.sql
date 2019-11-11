create database if not exists `libmovie`;
use `libmovie`;

create table if not exists `medias`
	(
		`id` int unsigned not null auto_increment,
        `title` varchar(250) not null,
        `release` timestamp,
        `duration` int unsigned,
        `main_genre` varchar(250),
        `rating` tinyint unsigned,
        primary key (`id`)
	);
    
create table if not exists `users`
	(
		`id` int unsigned not null auto_increment,
        `username` varchar(250) not null,
        `password` varchar(250) not null,
        `firstname` varchar(250) not null,
        `lastname` varchar(250) not null,
        `email` varchar(250) not null,
        `member_since` timestamp not null default current_timestamp,
        primary key (`id`),
        constraint `username_uq` unique (`username`),
        constraint `email_uq` unique (`email`)
	);
    
create table if not exists `media_user`
	(
		`id` int unsigned not null auto_increment,
        `user_id` int unsigned not null,
        `media_id` int unsigned not null ,
        `rating` tinyint unsigned,
        `watched` timestamp null default null,
        primary key (`id`),
		foreign key (`user_id`) references `users`(`id`),
		foreign key (`media_id`) references `medias`(`id`),
        constraint uq_association unique (`user_id`, `media_id`)
);

use `libmovie`;

insert into `users` 
(
    `username`,
    `password`,
    `firstname`,
    `lastname`,
    `email`
)
values
(
    "pete842",
    "totem",
    "Pierre",
    "Kohler",
    "pete842@mail.com"
),
(
    "jzaehrin",
    "totem",
    "Jonathan",
    "Zaehringer",
    "jzaehrin@mail.com"
),
(
    "capito27",
    "totem",
    "Filipe",
    "Fortunato",
    "capito27@mail.com"
);

load data local infile '/docker-entrypoint-initdb.d/out.csv' into table `medias` fields terminated by '\t' lines terminated by '\n' (`title`, @release, `duration`, `main_genre`, `rating`) set `release` = FROM_UNIXTIME(@release);

drop procedure if exists insertMediaUser;
DELIMITER //

CREATE PROCEDURE insertMediaUser()
BEGIN
    DECLARE counter INT default 0;
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION SET counter = counter - 1;
    BEGIN
        WHILE counter < 10000 DO
                set counter = counter + 1;
                insert into `media_user`
                (
                    `user_id`,
                    `media_id`
                )
                values
                (
                    1,
                    floor(rand()*(1000000-1+1))+1
                );

                insert into `media_user`
                (
                    `user_id`,
                    `media_id`,
                    `rating`,
                    `watched`
                )
                values
                (
                    1,
                    floor(rand()*(1000000-1+1))+1,
                    floor(rand()*(100-1+1))+1,
                    now() - interval floor(rand() * 365) day
                );
            END WHILE;
    END;
END;
//
DELIMITER ;

call insertMediaUser;

