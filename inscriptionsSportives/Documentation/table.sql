#------------------------------------------------------------
#        Script MySQL.
#------------------------------------------------------------


#------------------------------------------------------------
# Table: Candidat
#------------------------------------------------------------

CREATE TABLE Candidat(
        id  Int  Auto_increment  NOT NULL ,
        nom Varchar (50) NOT NULL
	,CONSTRAINT Candidat_PK PRIMARY KEY (id)
)ENGINE=InnoDB;


#------------------------------------------------------------
# Table: Personne
#------------------------------------------------------------

CREATE TABLE Personne(
        id_Candidat Int NOT NULL ,
        id          Int NOT NULL ,
        Prenom      Varchar (50) NOT NULL ,
        Mail        Varchar (50) NOT NULL ,
        nom         Varchar (50) NOT NULL
	,CONSTRAINT Personne_PK PRIMARY KEY (id_Candidat,id)

	,CONSTRAINT Personne_Candidat_FK FOREIGN KEY (id_Candidat) REFERENCES Candidat(id)
)ENGINE=InnoDB;


#------------------------------------------------------------
# Table: Equipe
#------------------------------------------------------------

CREATE TABLE Equipe(
        id_Candidat  Int NOT NULL ,
        id           Int NOT NULL ,
        Nom          Varchar (50) NOT NULL ,
        nom_Candidat Varchar (50) NOT NULL
	,CONSTRAINT Equipe_PK PRIMARY KEY (id_Candidat,id)

	,CONSTRAINT Equipe_Candidat_FK FOREIGN KEY (id_Candidat) REFERENCES Candidat(id)
)ENGINE=InnoDB;


#------------------------------------------------------------
# Table: Competition
#------------------------------------------------------------

CREATE TABLE Competition(
        id           Int  Auto_increment  NOT NULL ,
        Nom          Varchar (50) NOT NULL ,
        Date_cloture Date NOT NULL ,
        EnEquipe     Bool NOT NULL
	,CONSTRAINT Competition_PK PRIMARY KEY (id)
)ENGINE=InnoDB;


#------------------------------------------------------------
# Table: APPARTENIR
#------------------------------------------------------------

CREATE TABLE APPARTENIR(
        id_Candidat        Int NOT NULL ,
        id                 Int NOT NULL ,
        id_Candidat_Equipe Int NOT NULL ,
        id_Equipe          Int NOT NULL
	,CONSTRAINT APPARTENIR_PK PRIMARY KEY (id_Candidat,id,id_Candidat_Equipe,id_Equipe)

	,CONSTRAINT APPARTENIR_Personne_FK FOREIGN KEY (id_Candidat,id) REFERENCES Personne(id_Candidat,id)
	,CONSTRAINT APPARTENIR_Equipe0_FK FOREIGN KEY (id_Candidat_Equipe,id_Equipe) REFERENCES Equipe(id_Candidat,id)
)ENGINE=InnoDB;


#------------------------------------------------------------
# Table: INSCRIRE
#------------------------------------------------------------

CREATE TABLE INSCRIRE(
        id          Int NOT NULL ,
        id_Candidat Int NOT NULL
	,CONSTRAINT INSCRIRE_PK PRIMARY KEY (id,id_Candidat)

	,CONSTRAINT INSCRIRE_Competition_FK FOREIGN KEY (id) REFERENCES Competition(id)
	,CONSTRAINT INSCRIRE_Candidat0_FK FOREIGN KEY (id_Candidat) REFERENCES Candidat(id)
)ENGINE=InnoDB;

