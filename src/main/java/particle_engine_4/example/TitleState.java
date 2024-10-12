/*
 * Sophie Knox
 * Particle Engine 4
 * 10/11/24
 * This project creates three sublasses of particles: an alien spaceship, cow, and stars.
 * Stars and cows bounce off screen. Spaceship is confined to x bounds. Cows collide with eachother
 * This subclass controls/draws the title (beginning) screen with instructions
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
 
 public class TitleState extends GameState 
 {
 
     public TitleState(PApplet p, MelodyManager melodyManager)
      {
         super(p, melodyManager);
     }
 
     //titlescreen with instructions
     @Override
     public void drawState() 
     {
         p.background(0);
         p.fill(255);
         p.textAlign(PApplet.CENTER);
         p.textSize(32);
         p.text("Alien Shooter Game", p.width / 2, p.height / 2);
         p.textSize(16);
         p.text(
             "Shoot all the stars. Each star shot is a point. If you shoot a cow you lose.\n" +
             "* Left and right arrows control spaceship's x position\n" +
             "* Spacebar shoots bullets.\n" +
             "* Press 'P' to Play",
             p.width / 2,
             p.height / 2 + 40
         );
     }
 
     @Override
     public void handleInput()
      {
         if (p.keyPressed && p.key == 'p') 
         {
             //passes melody manager in play state
             App.currentState = new PlayState(p, melodyManager);
         }
     }
 
     @Override
     public GameState transition()
      {
         return this;
     }
 }
 
 