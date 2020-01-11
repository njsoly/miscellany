CREATE TABLE suit (
    name varchar(50)  NOT NULL CONSTRAINT pk_suit PRIMARY KEY
);

INSERT INTO public.suit ("name") VALUES ('Spades'), ('Diamonds'), ('Clubs'), ('Hearts');

ALTER TABLE playing_card 
ADD CONSTRAINT fk_playing_card_suit 
FOREIGN KEY (suit_name) 
REFERENCES suit (name) NOT VALID;