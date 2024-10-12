/*
 * Sophie Knox
 * Particle Engine 4
 * 10/11/24
 * This project creates three sublasses of particles: an alien spaceship, cow, and stars.
 * Stars and cows bounce off screen. Spaceship is confined to x bounds. Cows collide with eachother
 * This superclass handles the game states, and when one state changes to the next, plays GameChange.mid
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

 import processing.core.PApplet;
 import java.util.ArrayList;
 
 abstract class GameState 
 {
     PApplet p;
     MelodyManager melodyManager; //RUBRIC MelodyManager implemation 
 
     public GameState(PApplet p, MelodyManager melodyManager)
      {
         this.p = p;
         this.melodyManager = melodyManager;
         playGameChange();
     }
 
     //RUBRIC 10% controller
     abstract void drawState();// displays current state
     abstract void handleInput(); //key and mouse control
     abstract GameState transition(); // transition logic
 

     //RUBRIC 10% sound for changing states
     public void playGameChange() 
     {
         int gameChangeIndex = 2;
         System.out.println("Playing GameChange.mid");
         melodyManager.start(gameChangeIndex);
 
         ArrayList<Integer> gameChangeMelody = melodyManager.players.get(gameChangeIndex).melody;
         if (gameChangeMelody != null && !gameChangeMelody.isEmpty())
          {
             System.out.println("Notes in GameChange.mid");
             for (int note : gameChangeMelody) 
             {
                 System.out.println("Note: " + note);
             }
         } else {
             System.out.println("No notes in GameChange.mid");
         }
     }
 
     //this will handle the pew midi file
     public void keyPressed()
      {
         //empty because it is overriden in playstate
     }
 
     public void keyReleased() 
     {
         //empty because it is overriden in playstate
     }
 }
 