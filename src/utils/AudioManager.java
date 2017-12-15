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
    private final AudioClip waka;
    private final AudioClip win;
    private final AudioClip powerPelletSiren;
    
    private boolean playingSiren;
    private boolean playingWaka;
    private boolean playingPelletSiren;
    
    public AudioManager() {
        death = Applet.newAudioClip(getClass().getResource("/audio/death.wav"));
        eatFruit = Applet.newAudioClip(getClass().getResource("/audio/eatfruit.wav"));
        eatGhost = Applet.newAudioClip(getClass().getResource("/audio/eatghost.wav"));
        extraLife = Applet.newAudioClip(getClass().getResource("/audio/extralife.wav"));
        intro = Applet.newAudioClip(getClass().getResource("/audio/intro.wav"));
        siren = Applet.newAudioClip(getClass().getResource("/audio/siren.wav"));
        waka = Applet.newAudioClip(getClass().getResource("/audio/waka.wav"));
        win = Applet.newAudioClip(getClass().getResource("/audio/win.wav"));        
        powerPelletSiren = Applet.newAudioClip(getClass().getResource("/audio/powerpelletsiren.wav"));
        playingSiren = false;
        playingWaka = false;
        playingPelletSiren = false;
    
    }
    
    // para todos os sons
    public void stopAll() {
        death.stop();
        eatFruit.stop();
        eatGhost.stop();
        extraLife.stop();
        intro.stop();
        siren.stop();
        waka.stop();
        win.stop();
        powerPelletSiren.stop();
    }
    
    public void startPelletSong(){
        if (!playingPelletSiren) {
            powerPelletSiren.loop();
            playingPelletSiren = true;
        }
    }
    
    public void stopPelletSong (){
         powerPelletSiren.stop();
         playingPelletSiren = false;
    }
            
    public void startWaka() {
        if (!playingWaka) {
            waka.loop();
            playingWaka = true;
        }
    }
    
    public void stopWaka() {
        waka.stop();
        playingWaka = false;
       
    }
    
    public void playWin() {
        win.play();
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
