USE Funn;

CREATE TABLE person (
    id INT PRIMARY KEY,
    navn VARCHAR(255),
    tlf VARCHAR(12),
    e_post VARCHAR(255)
);

CREATE TABLE museum (
    id INT PRIMARY KEY,
    navn VARCHAR(255),
    sted VARCHAR(255)
);

CREATE TABLE vaapen (
    id INT PRIMARY KEY,
    funnsted VARCHAR(255),
    finner_id INT,
    funntidspunkt DATETIME,
    antatt_aarstall INT,
    museum_id INT,
    type VARCHAR(100),
    materiale VARCHAR(100),
    vekt INT,
    FOREIGN KEY (finner_id) REFERENCES Person(id),
    FOREIGN KEY (museum_id) REFERENCES Museum(id)
);

CREATE TABLE smykke (
    id INT PRIMARY KEY,
    funnsted VARCHAR(255),
    finner_id INT,
    funntidspunkt DATETIME,
    antatt_aarstall INT,
    museum_id INT,
    Type VARCHAR(100),
    verdiestimat INT,
    filnavn VARCHAR(255),
    FOREIGN KEY (finner_id) REFERENCES Person(id),
    FOREIGN KEY (museum_id) REFERENCES Museum(id)
);

CREATE TABLE mynt (
    id INT PRIMARY KEY,
    funnsted VARCHAR(255),
    finner_id INT,
    funntidspunkt DATETIME,
    antatt_aarstall INT,
    museum_id INT,
    diameter INT,
    metall VARCHAR(100),
    FOREIGN KEY (finner_id) REFERENCES Person(id),
    FOREIGN KEY (museum_id) REFERENCES Museum(id)
);
