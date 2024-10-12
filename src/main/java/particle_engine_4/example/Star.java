/*
 * Sophie Knox
 * Particle Engine 4
 * 10/11/24
 * This project creates three sublasses of particles: an alien spaceship, cow, and stars.
 * Stars and cows bounce off screen. Spaceship is confined to x bounds. Cows collide with eachother
 * This subclass controls/draws draw and implements Star.mid sound when star collides with wall
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
 import processing.core.PConstants;
 import java.util.ArrayList;
 
 public class Star extends Particle 
 {
     private float radius;
     private int points;
     private float angleOffset;
     private MelodyManager melodyManager; //reference to MelodyManager
     private boolean played = false; //sound plays once
 
     public Star(float x, float y, PApplet p, MelodyManager melodyManager)
      {
         super(x, y, p);
         this.radius = 10;//size
         this.points = 5;//# of points
         this.angleOffset = PConstants.TWO_PI / points; //angle between two points
         this.melodyManager = melodyManager; //handles melody manager
     }
 
     @Override
     public void update(float speedFactor) 
     {
         super.update(speedFactor);
         if (checkWallCollision())
          {
             reverse();
             if (!played) 
             {
                 playStarMidi();
                 played = true; //prevents sound from playing imediatly
             }
         } else 
         {
             played = false; //no collision it resets
         }
     }
 
     //draw star particle
     @Override
     public void display() 
     {
         p.fill(255, 255, 51);
         p.pushMatrix();
         p.translate(position.x, position.y); //moves origin
         p.beginShape();
         for (int i = 0; i < points * 2; i++)
          {
             float angle = i * angleOffset / 2;
             float r = (i % 2 == 0) ? radius : radius / 2;//got this from the processing website
             float x = PApplet.cos(angle) * r;
             float y = PApplet.sin(angle) * r;
             p.vertex(x, y);
         }
         p.endShape(PConstants.CLOSE);
         p.popMatrix();
     }
 
     //radius for particle size for bounds
     public float getRadius() 
     {
         return radius;
     }
 
     @Override
     public float[] getBounds() 
     {
         return new float[] { position.x - radius, position.y - radius, radius * 2, radius * 2 };
     }
 
     //star wall collision
     private boolean checkWallCollision()
      {
         float particleSize = radius;//radius for star size
         boolean collided = false;
 
         //l and r walls
         if (position.x < particleSize / 2 || position.x > p.width - particleSize / 2)
          {
             collided = true;
         }
 
         //top and bottom walls
         if (position.y < particleSize / 2 || position.y > p.height - particleSize / 2)
          {
             collided = true;
         }
 
         return collided;
     }
 
     //RUBRIC 3.33% A different sound for particle colliding with wall
     //plays Star.mid print notes to the terminal
     private void playStarMidi() 
     {
         int starMidiIndex = 3;  
         System.out.println("Playing Star.mid");
         melodyManager.start(starMidiIndex);
 
         ArrayList<Integer> starMelody = melodyManager.players.get(starMidiIndex).melody;
         if (starMelody != null && !starMelody.isEmpty())
          {
             System.out.println("Notes in Star.mid");
             for (int note : starMelody)
              {
                 System.out.println("Note: " + note);
             }
         } else
          {
             System.out.println("No notes in Star.mid");
         }
     }
 }
 
 