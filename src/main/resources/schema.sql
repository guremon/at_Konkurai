CREATE TABLE user (
    id int(10) NOT NULL AUTO_INCREMENT,
    username varchar(30) NOT NULL,
    password varchar(30) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE item (
    id int(10) NOT NULL,
    itemId int(10) NOT NULL AUTO_INCREMENT,
    itemname varchar(40) NOT NULL,
    category int(1) NOT NULL,
    total_amount int(4),
    remaining_amount int(4),
    unit int(2),
    amount_to_use int(4),
    amount_score int(3),
    frequency_of_use int(3),
    stock int(3),
    registeredDateTime datetime NOT NULL,
    lastModifiedDateTime datetime NOT NULL,
    PRIMARY KEY (itemId, id),
    FOREIGN KEY (id) REFERENCES user(id)
);