# VoiceToChat

Minecraft 1.19.4 Fabric Mod that allows the player to send chat messages with their voice.

## Setup

Download the Mod at: https://github.com/LiamKinghouser/VoiceToChat/releases/download/1.0/VoiceToChat-1.0-SNAPSHOT.jar

Download lib.zip: https://github.com/LiamKinghouser/VoiceToChat/releases/download/1.0/lib.zip

Put the Mod jar file into your Mod folder. 

Extract lib.zip, and put the lib folder into the "config" folder of your Minecraft folder.

Make sure to read the following for specifics. 

## Keybinds

The Mod adds 3 keybinds to the game to control the microphone. 

Default keybinds:

"O": Start Recording

"M": Stop Recording and Send Current Text

"K": Clear Current Recording

All keybinds are configurable.

## Microphone Permissions

This Mod requires use of the microphone permission. Depending on your Minecraft client, the Mod may or may not be able to request permission. If the Mod is not picking up any audio, this is the most likely problem. 

This Mod has only been tested on macOS M1 Ventura 13.1. 

MultiMC seems to correctly request microphone access, but the default Minecraft client does not. Therefore, if you are having issues with microphone access, it is recommended to use MultiMC or a derivative such as Prism Launcher.

## Vosk

This Mod uses Vosk, a speech to text API.

To use Vosk, a native library is required. Because this Mod has only been tested on macOS M1 Ventura 13.1, the provided libvosk.dylib has been built for the ARM64 architecture. You will need to build your own native library that supports your system architecture. The lib.zip file contains the libvosk.dylib built for ARM64 and a small English Vosk model.

The Vosk model folder must be named "vosk-model-small-en-us-0.15" for the Mod to work. A more optimized config system may be added in the future.

The file system for the libraries should follow this format:

- Native Library: (Minecraft folder)/config/lib/vosk/libvosk.dylib
- Vosk Model: (Minecraft folder)/config/lib/vosk/vosk-model-small-en-us-0.15
