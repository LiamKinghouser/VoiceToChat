package com.kinghouser.voicetochat.client;

import com.kinghouser.voicetochat.VoiceToChat;
import com.kinghouser.voicetochat.client.event.EventHandler;
import com.kinghouser.voicetochat.util.MicrophoneHandler;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

import java.util.List;

public class VoiceToChatClient implements ClientModInitializer {

    public static boolean active = false;

    private static final String configPath = FabricLoader.getInstance().getConfigDirectory().getPath();

    public static String voskPath = configPath + "/lib/vosk/vosk-model-small-en-us-0.15";
    private static final String libvoskPath = configPath + "/lib/vosk/libvosk.dylib";

    public static MicrophoneHandler microphoneHandler;

    public static KeyBinding startRecordingKeyBinding;
    public static KeyBinding stopRecordingKeyBinding;
    public static KeyBinding clearRecordingKeyBinding;

    @Override
    public void onInitializeClient() {
        try {
            System.load(libvoskPath);
        } catch (Exception e) {
            e.printStackTrace();
        }

        microphoneHandler = new MicrophoneHandler();

        startRecordingKeyBinding = new KeyBinding("key.voicetochat.start_recording", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_O, "key.category.voicetochat");
        KeyBindingHelper.registerKeyBinding(startRecordingKeyBinding);

        stopRecordingKeyBinding = new KeyBinding("key.voicetochat.stop_recording", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_M, "key.category.voicetochat");
        KeyBindingHelper.registerKeyBinding(stopRecordingKeyBinding);

        clearRecordingKeyBinding = new KeyBinding("key.voicetochat.clear_recording", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_K, "key.category.voicetochat");
        KeyBindingHelper.registerKeyBinding(clearRecordingKeyBinding);

        EventHandler.register();
    }

    public static void renderHud(MatrixStack matrices, float tickDelta) {
        if (active) {
            Identifier imageTexture = new Identifier(VoiceToChat.MOD_ID, "textures/icon/microphone.png");
            RenderSystem.setShaderTexture(0, imageTexture);
            DrawableHelper.drawTexture(matrices, 0, 0, 0, 0, 32, 32, 32, 32);
            drawWrappedText(matrices, Text.literal(microphoneHandler.result), 32, 6, 0xFFD700);
        }
    }

    private static void drawWrappedText(MatrixStack matrices, Text text, int x, int y, int color) {
        MinecraftClient client = MinecraftClient.getInstance();
        int maxWidth = client.getWindow().getScaledWidth() - 64;
        List<OrderedText> lines = client.textRenderer.wrapLines(text, maxWidth);

        for (int i = 0; i < lines.size(); i++) {
            OrderedText line = lines.get(i);
            int lineHeight = client.textRenderer.fontHeight;

            if (i > 0) {
                lineHeight += 2;
            }

            int lineY = y + (lineHeight * i);
            client.textRenderer.drawWithShadow(matrices, line, x, lineY, color);
        }
    }
}