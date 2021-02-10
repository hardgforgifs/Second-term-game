# pixel-boat

Originally developed by ENG1-Team-12, continued by ENG1-Team-10.

This repository is for developing the single player, Java-based game inspired by the Annual Dragon Boat Race in York.

## Website

Team 10 Assessment Website
Please click the link: https://hardgforgifs.github.io/assessment-2/

## Developing (IntelliJ IDEA)

After cloning the repo you will want to open it in the IDE.

Then you will want to open `Edit Run/Debug Configurations` available on the top bar.
Add a new `application` then you will want to configure it with the following options:

Main class: `com.hardgforgif.dragonboatracing.desktop.DesktopLauncher`  
Working directory: `complete path to cloned repo\game\desktop\src\assets`  
Use classpath of module: `game.desktop.main`  

Then select the new configuration, and you will be able to run it.

## Testing (IntelliJ IDEA)

> CI will run unit tests on every commit to main and every pull request

The tests can be run all at once with the gradle task in build.gradle from the tests subproject(tests folder)
Otherwise run 1 by 1, or class by class via the test classes.

You can also run the tests through gradle:

`gradlew tests:test`

It will produce a HTML report.

## Releasing

> CI will make a release build for every commit to main

`gradlew desktop:dist`

## Documentation

### Assessment 2

* [Change Report](https://raw.githubusercontent.com/hardgforgifs/assessment-2/main/assessment2/Change2.pdf)
* [Implementation](https://raw.githubusercontent.com/hardgforgifs/assessment-2/main/assessment2/Impl2.pdf)
* [Testing](https://raw.githubusercontent.com/hardgforgifs/assessment-2/main/assessment2/Test2.pdf)
* [Continuous Integration](https://raw.githubusercontent.com/hardgforgifs/assessment-2/main/assessment2/CI2.pdf)

### Assessment 1

* [User Requirements](https://raw.githubusercontent.com/hardgforgifs/assessment-2/main/assessment2/Req1.pdf)
* [Architecture](https://raw.githubusercontent.com/hardgforgifs/assessment-2/main/assessment2/Arch1.pdf)
* [Method selection and planning](https://raw.githubusercontent.com/hardgforgifs/assessment-2/main/assessment2/Plan1.pdf)
* [Risk assessment and mitigation](https://raw.githubusercontent.com/hardgforgifs/assessment-2/main/assessment2/Risk1.pdf)

## Inspiration

Inspiration: https://www.yorkrotary.co.uk/dragon-boat-challenge

## Authors

Contributors names and contact info

University of York Engineering 1 Cohort 2 Team 10:

* [Bowen Lyu](https://github.com/stormlyu)
* [Dragos Stoican](https://github.com/DragosStoican)
* [Rhys Milling](https://github.com/rmil)
* [Sam Plane](https://github.com/CitricAmoeba)
* [Quentin rothman](https://github.com/SaltyRex)

University of York Engineering 1 Cohort 2 Team 12:

* [Umer Fakher](https://github.com/UmerFakher)
* [James Frost](https://github.com/Fritzbox2000)
* [William Walton](https://github.com/wpw503)
* [Richard Liiv](https://github.com/sumsare)
* [Olly Wortley](https://github.com/orw511)
* [Joe Cambridge](https://github.com/JoeCambridge)

## References

* LibGDX Java Game development library: https://libgdx.badlogicgames.com/
* Software Engineering Skills Development: I. Sommerville, Software Engineering, Pearson Education, 2008
