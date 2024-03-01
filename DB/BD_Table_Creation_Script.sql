CREATE TABLE Maregraph (
  ID_Maregraph INTEGER PRIMARY KEY,
  Location TEXT,
  URL TEXT,
  User TEXT,
  Password TEXT
);
CREATE TABLE Institution (
  ID_Institution INTEGER PRIMARY KEY,
  Name TEXT,
  Sigla TEXT,
  Topic TEXT,
  CONSTRAINT unique_name UNIQUE (Topic)
);
CREATE TABLE Analyze (
  ID_Analyze INTEGER PRIMARY KEY,
  ID_Maregraph INTEGER,
  ID_Institution INTEGER,
  Name TEXT,
  FOREIGN KEY (ID_Maregraph) REFERENCES Maregraph(ID_Maregraph),
  FOREIGN KEY (ID_Institution) REFERENCES Institution(ID_Institution),
  CONSTRAINT unique_name UNIQUE (ID_Maregraph, ID_Institution)
);

