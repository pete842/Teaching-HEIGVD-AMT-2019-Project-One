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
		foreign key (`media_id`) references `medias`(`id`)
	);

