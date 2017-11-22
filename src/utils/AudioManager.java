package utils;

import java.applet.Applet;
import java.applet.AudioClip;

public class AudioManager {

    private final AudioClip death;
    private final AudioClip eatFruit;
    private final AudioClip eatGhost;
    private final AudioClip extraLife;
    private final AudioClip intro;
    private final AudioClip siren;
    
    private boolean playingSiren;
    
    public AudioManager() {
        death = Applet.newAudioClip(getClass().getResource("/audio/death.wav"));
        eatFruit = Applet.newAudioClip(getClass().getResource("/audio/eatfruit.wav"));
        eatGhost = Applet.newAudioClip(getClass().getResource("/audio/eatghost.wav"));
        extraLife = Applet.newAudioClip(getClass().getResource("/audio/extralife.wav"));
        intro = Applet.newAudioClip(getClass().getResource("/audio/intro.wav"));
        siren = Applet.newAudioClip(getClass().getResource("/audio/siren.wav"));
        playingSiren = false;
    }
    
    public void playDeath() {
        death.play();
    }
    
    public void playEatFruit() {
        eatFruit.play();
    }
    
    public void playEatGhost() {
        eatGhost.play();
    }
    
    public void playExtraLife() {
        extraLife.play();
    }
    
    public void playIntro() {
        intro.play();
    }
    
    public void startSiren() {
        if (!playingSiren) {
            siren.loop();
            playingSiren = true;
        }
    }
    
    public void stopSiren() {
        siren.stop();
        playingSiren = false;
    }
    
}
