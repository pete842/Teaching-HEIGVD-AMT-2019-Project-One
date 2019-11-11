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
    "$31$5$8f91zmgQBh7LUAM1-29me1KhHFQJekxK1872YYzNO2s",
    "Pierre",
    "Kohler",
    "pete842@mail.com"
),
(
    "jzaehrin",
    "$31$5$8f91zmgQBh7LUAM1-29me1KhHFQJekxK1872YYzNO2s",
    "Jonathan",
    "Zaehringer",
    "jzaehrin@mail.com"
),
(
    "capito27",
    "$31$5$8f91zmgQBh7LUAM1-29me1KhHFQJekxK1872YYzNO2s",
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

