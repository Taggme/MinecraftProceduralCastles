Minecraft Procedural Castle Generation
======================================

Castle Generator is a three dimensional prodedural generator using Minecraft as a graphics/physics engine. Generation is preformed by finding a valid location to generate the entrence and creating all rooms recursively from that point. A bounding box is create to prevent rooms from sprawling out in any direction infinitely. Rooms are created as extensions of the "CastleChunk" class and are all the same size and shape when generated to make for easy cell generation. Rooms can either be terminating rooms or rooms that continue the flow of generation that create new rooms in any of the four cardinal directions or straight up via "Ladder" rooms. 

![Outside of generated structure](img/Sample1.png?raw=true)


This compartamental design allows for easy creation of new rooms to extend the behavior of the generator. All rooms require are code to generate doors, spawn nearby rooms, and a finisher function that preforms post generation cleanup and decorations. As diffrent type of rooms are created the emergent structure becomes vastly more complex then the sum of it's parts leading to interesting layouts that vary wildly form generation to generation. 

![Inside of generated structure](img/Sample2.png?raw=true)

Requirements
============

Requires Minecraft 1.7.2
