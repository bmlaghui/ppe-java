
# PPE-JAVA | Inscriptions Sportives
***

## Mise en situation 
Cette situation professionnelle est réalisée dans le contexte de la M2L, ou la maison des ligues de Lorraine. Sa mission est de fournir aux ligues sportives différents services. De plus, elle gère l’organisation de compétitions et ainsi la gestion des équipes et des personnes y participant. 

## Technologie 

Cette Apllication JAVA est un fork du projet [inscriptionsSportives](https://github.com/alexandreMesle/inscriptionsSportives).

inscriptionsSportives est un projet java qui a été imposé comme base de départ pour notre projet.

De plus il nous été imposé d'utilisé [Hibernate](https://hibernate.org/) comme ORM. 

Le dépot principal de l'application est accessible [ici](https://github.com/taoberquer/inscriptionsSportives)

## Installation 

#### Prérequis
* JAVA
* PostgreSQL
* [Maven](https://maven.apache.org/)
* [CommandLine](https://github.com/alexandreMesle/CommandLine) 

#### Procédure
- Une fois téléchargé CommandLine accolé le au dossier InscriptionsSportives et vérifiez que le lien dans le Build Path est fonctionnel.

- Lancez un "update project" de maven.

- Dans le dossier `src/main/ressources` copiez le fichier `hibernate.cfg.xml.example` en `hibernate.cfg.xml`.

- Configurez le fichier `hibernate.cfg.xml` pour qu'il puisse avoir accès à la base de donnée PostgreSQL.
## Auteurs

* **Tao Berquer** - [www.taoberquer.fr](https://www.taoberquer.fr/)
* **Alexandre Kaprielian** - [www.alexandre-kaprielian.com](www.alexandre-kaprielian.com)
* **Brahim Mlaghui** - [www.mlaghuibrahim.com](http://www.mlaghuibrahim.com/)
