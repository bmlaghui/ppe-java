#------------------------------------------------------------
#        Script MySQL.
#------------------------------------------------------------


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
# Table: Candidat
#------------------------------------------------------------

CREATE TABLE Candidat(
        id             Int  Auto_increment  NOT NULL ,
        id_Competition Int NOT NULL
	,CONSTRAINT Candidat_PK PRIMARY KEY (id)

	,CONSTRAINT Candidat_Competition_FK FOREIGN KEY (id_Competition) REFERENCES Competition(id)
)ENGINE=InnoDB;


#------------------------------------------------------------
# Table: Personne
#------------------------------------------------------------

CREATE TABLE Personne(
        id_Candidat    Int NOT NULL ,
        id             Int NOT NULL ,
        Prenom         Varchar (50) NOT NULL ,
        Mail           Varchar (50) NOT NULL ,
        id_Competition Int NOT NULL
	,CONSTRAINT Personne_PK PRIMARY KEY (id_Candidat,id)

	,CONSTRAINT Personne_Candidat_FK FOREIGN KEY (id_Candidat) REFERENCES Candidat(id)
	,CONSTRAINT Personne_Competition0_FK FOREIGN KEY (id_Competition) REFERENCES Competition(id)
)ENGINE=InnoDB;


#------------------------------------------------------------
# Table: Equipe
#------------------------------------------------------------

CREATE TABLE Equipe(
        id_Candidat    Int NOT NULL ,
        id             Int NOT NULL ,
        Nom            Varchar (50) NOT NULL ,
        id_Competition Int NOT NULL
	,CONSTRAINT Equipe_PK PRIMARY KEY (id_Candidat,id)

	,CONSTRAINT Equipe_Candidat_FK FOREIGN KEY (id_Candidat) REFERENCES Candidat(id)
	,CONSTRAINT Equipe_Competition0_FK FOREIGN KEY (id_Competition) REFERENCES Competition(id)
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

