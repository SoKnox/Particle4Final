/*
 * Sophie Knox
 * Particle Engine 4
 * 10/11/24
 * This project creates three sublasses of particles: an alien spaceship, cow, and stars.
 * Stars and cows bounce off screen. Spaceship is confined to x bounds. Cows collide with eachother
 * This subclass controls/draws spaceship and bullets and implements the sound for them with Pew.mid and SpaceShip.mid
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
 import processing.core.PVector;
 import java.util.ArrayList;
 
 public class Spaceship extends Particle 
 {
     private int alienColor;
     private ArrayList<PVector> bullets; // stores bullets as vectors
     private float speedFactor; // controls speed
     private MelodyManager melodyManager;
 
     public Spaceship(float x, float y, PApplet p, MelodyManager melodyManager) 
     {
         super(x, y, p);
         this.alienColor = p.color(124, 252, 0);
         this.bullets = new ArrayList<>();
         this.speedFactor = 1.5f; //speed
         this.melodyManager = melodyManager; //RUBRIC MelodyManager implemation
     }
 
     @Override
     public void display() 
     {
         // spaceship body
         float x = position.x;
         float y = position.y;
 
         p.fill(150);
         p.ellipse(x, y, 60, 20);
 
         p.fill(100, 200, 255);
         p.arc(x, y - 10, 40, 30, PApplet.PI, PApplet.TWO_PI); // dome
 
         //lights
         p.fill(255, 255, 0);
         p.ellipse(x - 20, y + 5, 10, 10); // L
         p.ellipse(x, y + 5, 10, 10);      // C
         p.ellipse(x + 20, y + 5, 10, 10); // R
 
         drawAlien(x, y - 10); //alien
 
         //bullets
         for (PVector bullet : bullets) {
             p.fill(255, 30, 30);  // red
             p.ellipse(bullet.x, bullet.y, 5, 5);  //small circle
         }
     }
 
     private void drawAlien(float x, float y) 
     {
         //head
         p.fill(alienColor); //color
         p.ellipse(x, y - 5, 20, 20);
 
         //eyes
         p.fill(0); //black
         p.ellipse(x - 7, y - 5, 6, 10); // L
         p.ellipse(x + 7, y - 5, 6, 10); // R
     }
 
     public void shoot() 
     {
         // makes new bullet and adds to list
         PVector newBullet = new PVector(position.x, position.y - 10);
         bullets.add(newBullet);
     }
 
     public void updateBullets(float speedFactor)
      {
         for (int i = bullets.size() - 1; i >= 0; i--) 
         {
             PVector bullet = bullets.get(i);
             bullet.y -= speedFactor; //bullet moves up
 
             if (bullet.y < 0)
              {
                 bullets.remove(i);//remove if off screen
             }
         }
     }
 
     //spaceship movement and shooting
     public void handleInput() 
     {
         if (p.keyPressed) {
             if (p.keyCode == PApplet.LEFT) 
             {
                 position.x -= 5;
             } else if (p.keyCode == PApplet.RIGHT)
              {
                 position.x += 5;
             }
 
             if (p.key == ' ') {
                 shoot(); //spacebar shoots
             }
         }
 
         //check for collision with screen edges
         if (position.x <= 0 || position.x >= p.width) {
             playSpaceshipMidi();
             position.x = PApplet.constrain(position.x, 0, p.width); //correct the position
         }
     }
 
     //RUBRIC 10% another sound that interactivly happens. Plays and prints Pew.mid notes when spacebar is pressed
     public void keyPressed() 
     {
         if (p.key == ' ') 
         {
             shoot();
             System.out.println("Playing Pew.mid");
             melodyManager.start(0);
 
             ArrayList<Integer> pewMelody = melodyManager.players.get(0).melody;
             if (pewMelody != null && !pewMelody.isEmpty())
              {
                 System.out.println("Notes in Pew.mid");
                 for (int note : pewMelody) {
                     System.out.println("Note: " + note);
                 }
             } else 
             {
                 System.out.println("No notes in Pew,mid");
             }
         }
     }
 // ends sound when released
     public void keyReleased() {
         if (p.key == ' ') {
             melodyManager.players.get(0).setToEnd();
         }
     }
 
     public ArrayList<PVector> getBullets() 
     {
         return bullets;
     }
 
     public float getSpeedFactor()
      {
         return speedFactor;
     }
 
     public PVector getPosition() {
         return position;
     }
 
     //RUBRIC 3.33% Play sound for particle colliding with wall. When ship x position = 0 || 600, SpaceShip.mid plays and notes are in terminal
     public void playSpaceshipMidi() 
     {
         int spaceshipMidiIndex = 5;  
         System.out.println("Playing Spaceship.mid");
         melodyManager.start(spaceshipMidiIndex);
 
         ArrayList<Integer> spaceshipMelody = melodyManager.players.get(spaceshipMidiIndex).melody;
         if (spaceshipMelody != null && !spaceshipMelody.isEmpty()) 
         {
             System.out.println("Notes in Spaceship.mid");
             for (int note : spaceshipMelody) 
             {
                 System.out.println("Note: " + note);
             }
         } else 
         {
             System.out.println("No notes in Spaceship.mid");
         }
     }
 }
 
  