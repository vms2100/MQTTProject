-- language: sql
-- Insert Maregraph data
INSERT INTO Maregraph (Location, URL, User, Password) 
VALUES 
    ('Cascais', '10.0.7.45:1833', '', ''),
    ('Faro', '10.0.7.46:1833', '', '');

-- Insert Institution data
INSERT INTO Institution (Name, Sigla, Topic) 
VALUES 
    ('(Space & Earth Geodetic Analysis Lab', 'SEGAL', 'Maregrafo'),
    ('Instituto Portugues do Mar e da Atmosfera', 'IPMA', 'ipmatopics'),
    ('Uk Met Office', 'UKMET', 'ukmettopics');

-- Insert Analyze data
INSERT INTO Analyze (ID_Maregraph, ID_Institution, Name) 
VALUES 
    (1, 1, 'Faro'),
    (2, 1, 'Cascais'),
    (1, 2, 'FaroIPMA'),
    (2, 2, 'CascaisIPMA'),
    (2, 3, 'FaroUkData');

