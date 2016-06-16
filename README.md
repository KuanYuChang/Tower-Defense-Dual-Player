# SoftwareStudioFinalProject

##Tower Defense

###Incentive:
Language Learning is popular nowadays but the most important part, vocabulary memorizing is tedious. 
We guess the main reason is that it’s boring for memorizing vocabularies ourselves, but it would be interesting to compete with others. Thus, we want to combine games and vocabulary memorizing together to make learning interesting.

###Goal:
We want to build a software combining vocabulary memorizing and tower defense game, and intend to enhance students’ ability of
memorizing vocabularies and strategy thinking.

###Playing method:
Two player game. Hire two kinds of soldiers to defend your tower, and doing the vol. quiz in the meanwhile.

###Design pattern:
We divided the software as four parts, Client GUI, Client structure, Server and Vol. quiz. 
We applied MVC for implementing user interfaces on computers.
* Model is implemented by Server.
* Controller and View are implemented by Client
* Vol. quiz’s answer is sent to Server by Client Controller.

###Application techniques:
We import ani, minim, processing to complete our implementation.
* Ani is used to display animation such as soldiers’ moving and attacking.
* Minim is used to play sound effect when answer the Vol. quiz.
* Processing is used to display all of the elements on the screen, including soldiers, tower, button, etc.   
