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

load data local infile 'out.csv' into table `medias` fields terminated by '\t' lines terminated by '\n' (`title`, @release, `duration`, `main_genre`, `rating`) set `release` = FROM_UNIXTIME(@release);

drop procedure if exists insertMediaUser;
DELIMITER //

CREATE PROCEDURE insertMediaUser()
BEGIN
    DECLARE counter INT default 0;
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
//
DELIMITER ;

call insertMediaUser;

