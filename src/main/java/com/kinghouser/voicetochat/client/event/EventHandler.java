package com.kinghouser.voicetochat.client.event;

import com.kinghouser.voicetochat.VoiceToChat;
import com.kinghouser.voicetochat.client.VoiceToChatClient;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;

public class EventHandler {

    public static MicrophoneHandlerThread microphoneHandlerThread;

    public static void register() {
        ClientLifecycleEvents.CLIENT_STARTED.register(EventHandler::handelClientStartEvent);
        ClientTickEvents.END_CLIENT_TICK.register(EventHandler::handleEndClientTickEvent);
        ClientTickEvents.START_CLIENT_TICK.register(EventHandler::handleStartClientTickEvent);

        HudRenderCallback.EVENT.register(VoiceToChatClient::renderHud);
    }

    private static void handelClientStartEvent(MinecraftClient client) {
        VoiceToChat.LOGGER.info("Loading acoustic model from " + VoiceToChatClient.voskPath);
        try {
            VoiceToChatClient.microphoneHandler.init();
            VoiceToChat.LOGGER.info("Acoustic model loaded successfully!");
        } catch (Exception e1) {
            VoiceToChat.LOGGER.error(e1.getMessage());
        }
    }

    private static void handleStartClientTickEvent(MinecraftClient client) {
        if (client.player != null && VoiceToChatClient.startRecordingKeyBinding.isPressed() && !VoiceToChatClient.active) {
            microphoneHandlerThread = new MicrophoneHandlerThread();
            microphoneHandlerThread.start();

            VoiceToChatClient.active = true;
        }
        if (client.player != null && VoiceToChatClient.clearRecordingKeyBinding.isPressed() && VoiceToChatClient.active) {
            microphoneHandlerThread.clearRecording();
        }
    }

    private static void handleEndClientTickEvent(MinecraftClient client) {
        if (client.player != null && VoiceToChatClient.stopRecordingKeyBinding.isPressed() && VoiceToChatClient.active && VoiceToChatClient.microphoneHandler != null) {
            VoiceToChatClient.active = false;
            if (VoiceToChatClient.microphoneHandler.result != null && !VoiceToChatClient.microphoneHandler.result.equals("")) client.player.networkHandler.sendChatMessage(VoiceToChatClient.microphoneHandler.result);
            microphoneHandlerThread.stopMicrophone();
        }
    }

    public static class MicrophoneHandlerThread extends Thread {

        public MicrophoneHandlerThread() {}

        public void run() {
            VoiceToChatClient.microphoneHandler.start();
        }

        public void stopMicrophone() {
            VoiceToChatClient.microphoneHandler.stop();
        }

        public void clearRecording() {
            VoiceToChatClient.microphoneHandler.result = "";
        }
    }
}