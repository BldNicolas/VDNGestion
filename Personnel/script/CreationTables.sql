CREATE DATABASE IF NOT EXISTS VDNGestion;
USE VDNGestion;

CREATE TABLE employe (
    id_employe INT PRIMARY KEY AUTO_INCREMENT,
    id_ligue INT,
    droit BOOLEAN NOT NULL,
    nom VARCHAR(256) NOT NULL,
    prenom VARCHAR(256) NOT NULL,
    password VARCHAR(256) NOT NULL,
    mail VARCHAR(256) NOT NULL,
    date_arrive DATE NOT NULL,
    date_depart DATE
);

CREATE TABLE ligue (
    id_ligue INT PRIMARY KEY AUTO_INCREMENT,
    id_admin INT,
    nom VARCHAR(256) NOT NULL,
    FOREIGN KEY (id_admin) REFERENCES employe (id_employe)
);

ALTER TABLE employe
ADD FOREIGN KEY (id_ligue) REFERENCES ligue(id_ligue);
