This is a simple library written in Java for creating a Rock-Paper-Scissors alike command line game.

1. How to build
    Type 'mvn clean install' to build target/rockpaperscissors-1.0.jar'

2. How to run
    Type 'java -jar rockpaperscissors-1.0.jar' to run the game.
The Main class file is 'RockPaperScissors.class'.

3. How to play
    The following commands are available to type in command line:

> rock
    Throw Rock

> paper
    Throw Paper

> scissors
    Throw Scissors

> mode playerVsComputer
    Play versus Computer.

> mode computerVsComputer
    Watch how one Computer plays instead of you versus another Computer. You can just press <Enter> to see one more round.

> reset
    Reset all the counters and get to the initial state. 

> quit
 Quit the game.

4. How to extend
    To create a new weapon, create a class inherited from AbstractWeapon and pass an instance in the GameModel constructor. Override the bittenBy method
to specify the behavior of the weapon.

    To create a new command, create a class inherited from AbstractCommand and pass an instance in the GameModel constructor. Override the execute method
to specify the functionality.

