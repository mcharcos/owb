Introduction
============
[Orphanage Without Borders (OWB)](http://orphanagewithoutborders.org) is a volunteer non-profit organization aiming to improve the live quality of children in orphanages and abandoned children.

- OWB is an application made in GWT + Java that aims to increase collaboration between NGOs and improve the efficiency of the work to the cause of orphan children.
- It would also be a portal for companies and individuals to participate or contribute in different ways (donations, volunteering, fund-raising).
- In addition, it would allow to share information about the state and needs of orphanages and previous experiences of projects that helped to improve the quality of orphanages.
- We intend to make the interface friendly and amusing by creating a simple game that would allow the users to gain points as they participate and see the status at all time of their contribution.
- The project is very challenging, not only technically but conceptually since behind there is a whole plan to allow orphanages to acquire minimum standards of life (something that every child deserve).
- The application is based on the Model-View-Presenter so we can separate the development work at the back-end from the graphical design. The project includes refining our current rough design and ideas, development and testing. Our philosophy is based on the enthusiasm of helping others. If you believe in the cause of helping orphan children and you love challenging technical projects we would love to work with you.

Installation Tools
===================
1. Install Eclipse 
        a- Go to http://www.eclipse.org/downloads/ and download the software
	b- Follow the wizard instructions when installing
2. Install GWT and App Engine plug-ins
        a- Open eclipse and browse to Help > Install New Software > Put the url http://dl.google.com/eclipse/plugin/4.2.
        b- Follow the instructions at https://developers.google.com/eclipse/docs/install-eclipse-4.24. 
	   IN SUMMARY:
		* Type 'Google' in the search term and select
		  + Developer Tools
		  + Google plugin for eclipse
		  + GWT designer for GPE
		  + SDKs
                * Now type in "GWT" and select
		  + GWT Designer Core
		  + GWT Designer Editor
		  + GWT Designer GPE
                * Install the plugins.

Installation Code
===================
1. Clone this repository:
	a- Create in your filesystem a new directory (let's call it "owb-repo"). 
	b- Go to owb-repo.
	c- Clone the repository from mcharcos/owb. In shell command call "git clone https://github.com/mcharcos/owb.git"
	d- Now you have a bunch of directories and files under owb-repo/owb.
2. Create a project with the code
        a- Create a new directory that you will use as workspace (let's call it "owb-workspace")
	b- Go to File > Import > General > Existing Projects Into Workspace
	c- Press Browse in Select Root Directory and go to owb-repo/owb
	d- Press Finish. NOTE: Don't check "Copy Project into Workspace". This will copy the files into owb-workspace and you won't be able to commit and push the changes into the repository.
3. Code, play and improve the code. Enjoy!

Push changes
=============
1. Got to owb-repo/owb
2. git add, commit and push the changes. See http://git-scm.com/documentation, Chapter 2 "Git Basics".

Demo
====
Currently deployed at http://karmagochi.appspot.com/

Design Description
==================
- The approach of this application is a Model-View-Presenter.
- The global picture is as follow:
 * client packages contain the front end.
 * server packages contain the back end.
 * shared packages are visible to the front and back end.

