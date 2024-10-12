
/*
 * Sophie Knox
 * Particle Engine 4
 * 10/11/24
 * This project creates three sublasses of particles: an alien spaceship, cow, and stars.
 * Stars and cows bounce off screen. Spaceship is confined to x bounds. Cows collide with eachother
 * This super class controls (like velociry) particle subclasses. 
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



//RUBRIC 5% Particle super class with data and behavior
package particle_engine_4.example;

import processing.core.PApplet;
import processing.core.PVector;

public class Particle 
{
    protected PVector position;
    protected PVector velocity;
    protected PApplet p;

    public Particle(float x, float y, PApplet p)
     {
        this.position = new PVector(x, y);
        this.velocity = new PVector(p.random(-1, 1), p.random(-1, 1)); //gives particles random velocity
        this.p = p;
    }

    public void update(float speedFactor) 
    {
        position.add(velocity.copy().mult(speedFactor));
    }

    public void display()
     {
        //default display that is overriden in subclasses
        p.ellipse(position.x, position.y, 10, 10);
    }

    public PVector getPosition()
     {
        return position;
    }

    public void setVelocity(PVector velocity) 
    {
        this.velocity = velocity;
    }

    public float[] getBounds()
     {
        return new float[]{position.x - 5, position.y - 5, 10, 10};
    }

    public void reverse()
     {
        velocity.x *= -1;
        velocity.y *= -1;
    }
}

 