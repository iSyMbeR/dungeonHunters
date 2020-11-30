package com.dungeonhunters.dungeonhunters.controller;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine.Info;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

import static javax.sound.sampled.AudioSystem.getAudioInputStream;
import static javax.sound.sampled.AudioFormat.Encoding.PCM_SIGNED;

public class MusicController {

    public static void main(String[] args) {
        getMusic("start");
    }
    public static void getMusic(String name){
        final MusicController player = new MusicController();
        switch (name){
            case "areaClear":
                player.play("src\\main\\java\\com\\dungeonhunters\\dungeonhunters\\MusicEffect\\areaClear.mp3");
                break;
            case "bossDefeat":
                player.play("src\\main\\java\\com\\dungeonhunters\\dungeonhunters\\MusicEffect\\bossDefeat.mp3");
                break;
            case "death":
                player.play("src\\main\\java\\com\\dungeonhunters\\dungeonhunters\\MusicEffect\\death.mp3");
                break;
            case "enemyGun":
                player.play("src\\main\\java\\com\\dungeonhunters\\dungeonhunters\\MusicEffect\\enemyGun.mp3");
                break;
            case "gameOver":
                player.play("src\\main\\java\\com\\dungeonhunters\\dungeonhunters\\MusicEffect\\gameOver.mp3");
                break;
            case "start":
                player.play("src\\main\\java\\com\\dungeonhunters\\dungeonhunters\\MusicEffect\\start.mp3");
                break;
            case "lifeUp":
                player.play("src\\main\\java\\com\\dungeonhunters\\dungeonhunters\\MusicEffect\\lifeUp.mp3");
                break;
            default:
                System.out.println("Music not found");
        }

    }

    public void play(String filePath) {
        final File file = new File(filePath);

        try (final AudioInputStream in = getAudioInputStream(file)) {

            final AudioFormat outFormat = getOutFormat(in.getFormat());
            final Info info = new Info(SourceDataLine.class, outFormat);

            try (final SourceDataLine line =
                         (SourceDataLine) AudioSystem.getLine(info)) {

                if (line != null) {
                    line.open(outFormat);
                    line.start();
                    stream(getAudioInputStream(outFormat, in), line);
                    line.drain();
                    line.stop();
                }
            }

        } catch (UnsupportedAudioFileException
                | LineUnavailableException
                | IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private AudioFormat getOutFormat(AudioFormat inFormat) {
        final int ch = inFormat.getChannels();

        final float rate = inFormat.getSampleRate();
        return new AudioFormat(PCM_SIGNED, rate, 16, ch, ch * 2, rate, false);
    }

    private void stream(AudioInputStream in, SourceDataLine line)
            throws IOException {
        final byte[] buffer = new byte[4096];
        for (int n = 0; n != -1; n = in.read(buffer, 0, buffer.length)) {
            line.write(buffer, 0, n);
        }
    }
}