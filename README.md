# RateChill Read Me

RateChill is an application that allows students to easily give an 
evaluation of their last two lectures in a course. NTNU Courses are 
automatically fetched from the IME API so that students can add courses 
they are currently taking. Professors can log in with their NTNU username 
and see all evaluations represented in different graphs.


## Installation

### For all OS
Running the RateChill.jar requires having Java SE Runtime Environment 1.8
installed. This can be downloaded from Oracle's websites
(http://www.oracle.com/technetwork/java/javase/downloads/).

Using the application requires connection to the NTNU network.
If not connected to eduroam on NTNU campus, VPN must be used. 
Read more about installing VPN on NTNU's websites
(https://innsida.ntnu.no/wiki/-/wiki/English/Install+VPN).

### For windows only
Windows users can run the RateChill.jar or the RateChill.exe. RateChill.jar
requires having Java SE Runtime Environment 1.8 installed. The bin
folder is required to run the RateChill.exe, so do not delete this.


## Usage
Ensure that Java SE Runtime Environment 1.8 is installed, and that the
device is connected to the NTNU network. 
Run the RateChill.jar in order to start the application.
For an in-depth non-technical user guide, see UserGuide.pdf.


## Editing source code
We used Eclipse as IDE. The source code can be opened as a Java project
or a Gradle project. To build the project it must be opened as a Gradle
project.

### Import as Gradle Project
This required having Gradle installed.
In Eclipse, click

	File -> Import -> Gradle -> Existing Gradle Project

Click next and set "Project root directory"
to the RateChill folder. Click Finish. 

### Import as Java Project
In Eclipse, click

	File -> New -> Java Project

Uncheck "Use default location" and set "Location" to the RateChill
folder. Click Finish.

## Building the project
Gradle is used to build the project and can be downloaded 
[here](https://gradle.org/install).
An Eclipse Gradle plugin can be installed from the marketplace.
In order to build, locate the build task in Gradle Tasks.
This will assemble and test the project. Make sure connection to the 
NTNU network is established, as this is required by the database tests. 


## Running tests
JUnit tests are run and JaCoCo test report is generated when building.
The test summary can then be located at build/reports/tests/index.html
The test coverage can be located at build/jacocoHtml/index.html

To run tests individually, locate the test class in question in
src/test/java and run as JUnit test.
DBControllerTest requires NTNU network connection.

