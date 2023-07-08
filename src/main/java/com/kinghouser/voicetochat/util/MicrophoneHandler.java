package com.kinghouser.voicetochat.util;

import com.kinghouser.voicetochat.client.VoiceToChatClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import org.vosk.Model;
import org.vosk.Recognizer;

import javax.sound.sampled.*;
import java.io.IOException;


public class MicrophoneHandler {

    private AudioFormat format;
    private Line.Info info;
    private TargetDataLine microphone;
    public Recognizer recognizer;

    public String result = "";

    public MicrophoneHandler() {}

    public void init() {
        try {
            format = new AudioFormat(16000f, 16, 1, true, false);
            info = new DataLine.Info(TargetDataLine.class, format);
            microphone = (TargetDataLine) AudioSystem.getLine(info);
            Model model = new Model(VoiceToChatClient.voskPath);
            recognizer = new Recognizer(model, 16000);
        } catch (IOException | LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }

    public void start() {
        try {
            recognizer.reset();
            result = "";

            microphone.open(format);
            microphone.start();

            microphone.flush();

            int numBytesRead;
            int CHUNK_SIZE = 4096;
            int bytesRead = 0;

            byte[] b = new byte[4096];

            while (bytesRead <= 100000000 && VoiceToChatClient.active) {
                if (MinecraftClient.getInstance().isPaused()) continue;
                numBytesRead = microphone.read(b, 0, CHUNK_SIZE);

                bytesRead += numBytesRead;

                if (recognizer.acceptWaveForm(b, numBytesRead)) {
                    String result = recognizer.getResult();
                    result = result.substring(14, result.length() - 3);
                    if (!result.equals("")) this.result = result;
                }
                else {
                    String partialResult = recognizer.getPartialResult();
                    partialResult = partialResult.substring(17, partialResult.length() - 3);
                    if (!partialResult.equals("")) result = partialResult;
                }
            }
            if (MinecraftClient.getInstance().player != null) MinecraftClient.getInstance().player.sendMessage(Text.of(""), true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        microphone.stop();
        microphone.close();
        result = "";
    }
}