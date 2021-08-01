CREATE TABLE short_url
(
    id   int AUTO_INCREMENT NOT NULL,
    long_url text NOT NULL,
    short_url varchar(255) NOT NULL,
    hit_counter int NOT NULL DEFAULT 0,
    create_counter int NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    UNIQUE (short_url)
);