package main;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {

	Clip clip;
	URL soundURL[] = new URL[30];
	
	public Sound() {
		soundURL[0] = getClass().getResource("/sound/Tranquil.wav");
		soundURL[1] = getClass().getResource("/sound/Key.wav");
		soundURL[2] = getClass().getResource("/sound/Item.wav");
		soundURL[3] = getClass().getResource("/sound/Door.wav");
		soundURL[4] = getClass().getResource("/sound/Mortiduellum_Overworld_(Intro)_8-bit.wav");
		soundURL[5] = getClass().getResource("/sound/Mortiduellum_Overworld_(Game)_8-bit.wav");

	}
	
	public void setFile(int i) {
		try {
			
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
			clip = AudioSystem.getClip();
			clip.open(ais);
			
		}catch(Exception e) {
			
		}
	}
	public void play() {
		
		clip.start();
		
	}
	public void loop() {
		
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	public void stop() {
		
		clip.stop();
	}
}
