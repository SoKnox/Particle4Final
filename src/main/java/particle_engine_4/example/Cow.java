/*
 * Sophie Knox
 * Particle Engine 4
 * 10/11/24
 * This project creates three sublasses of particles: an alien spaceship, cow, and stars.
 * Stars and cows bounce off screen. Spaceship is confined to x bounds. Cows collide with eachother
 * This subclass controls/draws cow and handles the cow collison with wall and itself
 * if cow hits wall, it plays Cow.mid if cow hits cow, plays CowCollision.mid
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
 
 public class Cow extends Particle 
 {
     private int cowColor;
 
     public Cow(float x, float y, PApplet p) 
     {
         super(x, y, p);
         this.cowColor = p.color(255); // Default color of cow
     }
 
     @Override
     public void display() 
     {
         float x = position.x;
         float y = position.y;
 
         p.stroke(255); //
         p.strokeWeight(1);
 
         //body
         p.fill(cowColor);
         p.rect(x - 20, y, 40, 20);
 
         //legs
         p.rect(x - 15, y + 20, 5, 10);
         p.rect(x + 10, y + 20, 5, 10);
 
         //spots
         p.fill(0);  //black
         p.ellipse(x - 10, y + 5, 5, 5);
         p.ellipse(x + 10, y + 5, 8, 8);
 
         //head
         p.fill(cowColor);
         p.ellipse(x - 15, y - 10, 20, 20);
 
         //ears
         p.fill(0); //black
         p.ellipse(x - 25, y - 12, 12, 8); //L
         p.ellipse(x - 5, y - 12, 12, 8);  //R
 
         //eyes
         p.fill(0); //black
         p.ellipse(x - 20, y - 12, 6, 6); //L
         p.ellipse(x - 10, y - 12, 6, 6); //R
 
         //nose
         p.fill(255, 200, 200); //pink
         p.ellipse(x - 15, y - 3, 15, 8);
 
         p.noStroke(); //no more stroke
     }
 
     @Override
     public void update(float speedFactor) 
     {
         super.update(speedFactor); //calls update
         if (checkWallCollision()) 
         {
             playCowMidi(); //plays Cow.mid if it hits a wall
         }
     }
 
     //detect collision w walls
     private boolean checkWallCollision() 
     {
     
         float cowSize = 20;  //rough size of cow to detect collision
         boolean collided = false;
 
         //checks left and right walls
         if (position.x < cowSize / 2 || position.x > p.width - cowSize / 2) 
         {
             reverse();  //bounces cow of wall
             collided = true;
         }
 
         //checks top and bottom walls
         if (position.y < cowSize / 2 || position.y > p.height - cowSize / 2) {
             reverse();  //bounces cow off wall
             collided = true;
         }
 
         return collided;
     }
 
     //reverse the direction of the cow
     public void reverse() 
     {
         velocity.x *= -1;
         velocity.y *= -1;
     }
 
     //cow self collision checker
     public void checkCollision(Cow other)
      {
         float distance = PApplet.dist(this.position.x, this.position.y, other.position.x, other.position.y);
         if (distance < 40) 
         {
             this.reverse();
             other.reverse();
             playCowCollisionMidi(); //plays CowCollision.mid if cow collides with another cow
         }
     }
 
     //RUBRIC 3.333% A sound for particle colliding with screen
     //plays Cow.mid and prints out mid notes. If no sound, output will say no notes
     private void playCowMidi()
      {
         int cowMidiIndex = 1;  
         System.out.println("Playing Cow.mid");
         if (p instanceof App)
          {
             ((App) p).melodyManager.start(cowMidiIndex);
 
             // prints notes
             ArrayList<Integer> cowMelody = ((App) p).melodyManager.players.get(cowMidiIndex).melody;
             if (cowMelody != null && !cowMelody.isEmpty())
              {
                 System.out.println("Notes in Cow.mid:");
                 for (int note : cowMelody) 
                 {
                     System.out.println("Note: " + note);
                 }
             } else 
             {
                 System.out.println("No notes in Cow.mid");
             }
         }
     }
 
 
     //RUBRIC 10% sound for particle colliding with eachother
     //plays the CowCollision.mid + print its notes to terminal. If no notes it will say so in terminal.
     private void playCowCollisionMidi()
      {
         int cowCollisionMidiIndex = 4;  
         System.out.println("Playing CowCollision.mid");
         if (p instanceof App)
          {
             ((App) p).melodyManager.start(cowCollisionMidiIndex);
 
             //prints the notes
             ArrayList<Integer> cowCollisionMelody = ((App) p).melodyManager.players.get(cowCollisionMidiIndex).melody;
             if (cowCollisionMelody != null && !cowCollisionMelody.isEmpty())
              {
                 System.out.println("Notes in CowCollision.mid:");
                 for (int note : cowCollisionMelody) 
                 {
                     System.out.println("Note: " + note);
                 }
             } else 
             {
                 System.out.println("No notes in CowCollision.mid");
             }
         }
     }
 
     @Override
     public float[] getBounds() 
     {
         return new float[]{position.x - 20, position.y, 40, 20};
     }
 }
 
 
 
 
