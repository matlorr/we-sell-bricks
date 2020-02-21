DROP TABLE IF EXISTS bestellung;

CREATE TABLE bestellung
(
    bestellnr integer PRIMARY KEY,
    datum     text,
    kunde     integer DEFAULT NULL
);

DROP TABLE IF EXISTS kunde;
CREATE TABLE kunde
(
    kundennr integer PRIMARY KEY,
    name     text,
    plz      integer DEFAULT NULL,
    stadt    text,
    strasse  text,
    lng      double  DEFAULT NULL,
    lat      double  DEFAULT NULL
);

DROP TABLE IF EXISTS position;
CREATE TABLE position
(
    id         integer PRIMARY KEY,
    bestellung_dto integer REFERENCES bestellung (bestellnr),
    anzahl     integer DEFAULT NULL,
    produkt    integer DEFAULT NULL
);

DROP TABLE IF EXISTS produkt;
CREATE TABLE produkt
(
    id           integer PRIMARY KEY,
    gewicht      text,
    hoehe        text,
    breite       text,
    tiefe        text,
    preis        text,
    produkt      text,
    beschreibung text,
    bestand      INTEGER
) ;
