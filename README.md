# ![eLearningQA](https://github.com/RobertoArastiBlanco/eLearningQA/blob/main/Memo/plantillaLatex-master/img/FullLogo.png?raw=true)

[![Java CI with Maven](https://github.com/bae1001/eLearningQA/actions/workflows/maven.yml/badge.svg)](https://github.com/bae1001/eLearningQA/actions/workflows/maven.yml)
[![](https://img.shields.io/github/deployments/RobertoArastiBlanco/eLearningQA/elearningqa?label=Heroku%20deployment&style=plastic)](https://elearningqa.herokuapp.com)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=bae1001_eLearningQA&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=bae1001_eLearningQA)
## Description

eLearningQA is a web app that uses Moodle's web service 
API to retrieve information and runs a set of checks
while applying an e-learning quality framework to
assess the quality of instructional design on 
Moodle courses (I am not a Moodle Partner). 
This app is intended to be used by 
teachers using Moodle.


## Features

- Compatible with Moodle v3.8 onwards
- Allows the user to choose which Moodle host to connect to when logging in
- 23 checks on different aspects of a course, such as checking if assignments are graded on time, checking whether files are up to date or checking the correctness of the quizzes indexes.
- Generates reports showing the checks' results and additional information concerning the quality framework
- Shows alerts explaining the causes of check failures, occasionally with links to take you to the source of the problem
- Allows the user to export reports to a Excel file.
- Graphs that show the evolution of the quality of courses

## Snapshots
Login page
![Login page](https://github.com/RobertoArastiBlanco/eLearningQA/blob/main/Memo/plantillaLatex-master/img/Login.PNG?raw=true)
Main page
![Main page](https://github.com/RobertoArastiBlanco/eLearningQA/blob/main/Memo/plantillaLatex-master/img/ListaCursos.PNG?raw=true)
Report page
![Phase report](https://github.com/RobertoArastiBlanco/eLearningQA/blob/main/Memo/plantillaLatex-master/img/InformeFases.PNG?raw=true)
![Evolution graph](https://github.com/RobertoArastiBlanco/eLearningQA/blob/main/Memo/plantillaLatex-master/img/Evolucion.PNG?raw=true)


# Docker in eLearningQA

ElearningQA has the capability to be deployed in containers using Docker. This makes very easy the deployment of the application in any environment. In order to execute eLearningQA with Docker it is necessary to dispose of [Docker dekstop](https://docs.docker.com/get-docker/) installed on the enviornment. 

## Execution steps
1. Execute the following command in a terminal opened in the location of the project's Dockerfile.
```
mvn clean install
```
2. Open Docker Desktop
3. In order to create the image execute the following command. Again this command should be execute in a terminal opened in the location of the project's Dockerfile.
```
docker build -t <imageName> .
```
4. To run the image execute:
```
run -p <outSidePort>:8080 <imageName>
```
Once the last step is made it will be possible to monitor the application from Docker Desktop.


## Authors

- [Roberto Arasti Blanco](https://www.github.com/RobertoArastiBlanco)
- [Bilal Azar El Mourabit](https://github.com/bae1001)

## Mentors

- [Carlos López Nozal](https://www.github.com/clopezno)
- [Raúl Marticorena Sánchez](https://www.github.com/rmartico)


