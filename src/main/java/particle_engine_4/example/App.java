/*
 * Sophie Knox
 * Particle Engine 4
 * 10/11/24
 * This project creates three sublasses of particles: an alien spaceship, cow, and stars.
 * Stars and cows bounce off screen. Spaceship is confined to x bounds. Cows collide with eachother
 * This class acts like main and has minimal code in it to ensure encapsulation
 * 
 * I am attempting extra credit
 * Goal of game: Shoot all the stars. Each star shot is a point. If you shoot a cow you automatically loose.
 * Left and right arrows contol spaceship's x position
 * spacebar shoots bullet
 * IF YOU CANT BEAT GAME, CHANGE STAR # TO 1 IN PLAYSTATE
 * 
 *Sounds that occure:
 When cows collide with eachother, CowCollision.mid plays
 When cows collide with wall, Cow.mid plays
 When star collides with wall, Star.mid plays
 When GameState changes (TitleState,PlayState,and CreditState), GameChange.mid plays 
 When spaceship x position = 0 or 600, SpaceShip.mid plays
 When SPACEBAR is pressed in PLAYSTATE, Pew.mid plays
 */

 package particle_engine_4.example;

 import jm.music.data.*;
 import jm.util.*;
 import java.util.ArrayList;
 import java.nio.file.FileSystem;
 import java.nio.file.FileSystems;
 import processing.core.*;
 
 
 //RUBRIC 10% encapsulation
 public class App extends PApplet 
 {
 
	 static FileSystem sys = FileSystems.getDefault();
	 static String filePath = "mid" + sys.getSeparator();
	 String[] midiFiles = {"Pew", "Cow", "GameChange", "Star", "CowCollision", "Spaceship"}; //RUBRIC 10% 3+ midi sounds
	 MelodyManager melodyManager = new MelodyManager(); //RUBRIC MelodyManager implemation
	 static GameState currentState;
	 
 
	 public static void main(String[] args)
	  {
		 PApplet.main("particle_engine_4.example.App");
	 }
 
	 public void settings()
	  {
		 size(800, 600);
	 }
 
	 public void setup()
	  {
		 background(0);
		 addMidiFiles();
		 currentState = new TitleState(this, melodyManager); //starts at title state
	 }
 
	 //adds .mid to string files
	 public void addMidiFiles()
	  {
		 for (int i = 0; i < midiFiles.length; i++) {
			 melodyManager.addMidiFile(filePath + midiFiles[i] + ".mid");
		 }
	 }
 
	 public void draw() 
	 {
		 //handles GameState
		 background(0);
		 currentState.drawState();
 
		 if (currentState instanceof PlayState) {
			 ((PlayState) currentState).update();
		 }
 
		 //plays GameChange when transitioning game states
		 GameState nextState = currentState.transition();
		 if (nextState != null && nextState != currentState) {
			 currentState = nextState;
		 }
	 }
 
 
	 //handles key pressed for pew. is in game state, but overriden in playstate
	 public void keyPressed() 
	 {
		 currentState.handleInput();
		 if (currentState instanceof PlayState) 
		 {
			 ((PlayState) currentState).keyPressed();
		 }
	 }
 
	 public void keyReleased() {
		 if (currentState instanceof PlayState) 
		 {
			 ((PlayState) currentState).keyReleased();
		 }
	 }
 }
 