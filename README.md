# VoiceToChat

(In Progress)

Minecraft 1.19.4 Fabric Mod that allows the player to send chat messages with their voice.

## Setup:

Download the latest release at: 

Download lib.zip (contains libvosk.dylib (vosk native library) and vosk-model-small-en-us-0.15): 

Put the Mod jar file into your Mod folder. 

Extract lib.zip, and put the lib folder into the "config" folder of your Minecraft folder.

Make sure to read the following for specifics. 

## Keybinds

The Mod adds 3 keybinds to the game to control the microphone. 

Default keybinds:

"O": Start Recording

"M": Stop Recording and Send Current Text

"K": Clear Current Recording

## Microphone Permissions

This Mod requires use of the Microphone permission. Depending on your Minecraft client, the Mod may or may not be able to request permission. If the Mod is not picking up any audio, this is the most likely problem. 

This Mod has only been tested on MacOS M1 Ventura 13.1. 

MultiMC seems to correctly request Microphone access, but the default Minecraft client does not. This is a complicated issue and if you are having problems with it please make a new issue and I will do my best to help you. 

## Vosk

Vosk is a speech to text API. 

To use Vosk, a native library is required. Because this Mod has only been tested on MacOS M1 Ventura 13.1, the provided libvosk.dylib has been built for the ARM64 architecture. You will need to build your own native library that supports your system architecture. At this time I am not offering any tutorials on how to do this, but all of the information is available online. 

You may consider consulting ChatGPT for assistance if you cannot figure out how to do build the native library, as it has been helpful to me while creating this project. 

--------------------------------------------

The Vosk model folder must be named "vosk-model-small-en-us-0.15" for the Mod to work. A more optimized config system may be added in the future.

--------------------------------------------

The file system for the libraries should follow this format:

Native library: <Minecraft folder>/config/lib/vosk/libvosk.dylib
Vosk Model: <Minecraft folder>/config/lib/vosk/vosk-model-small-en-us-0.15
