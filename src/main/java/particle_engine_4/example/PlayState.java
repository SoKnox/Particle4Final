/*
 * Sophie Knox
 * Particle Engine 4
 * 10/11/24
 * This project creates three sublasses of particles: an alien spaceship, cow, and stars.
 * Stars and cows bounce off screen. Spaceship is confined to x bounds. Cows collide with eachother
 * This subclass controls/draws what happens when you play the game. It checks the collsion of particles and displays them properly
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
import processing.core.PVector;

public class PlayState extends GameState 
{
    private ArrayList<Particle> particles;
    private ArrayList<Cow> cows;
    private Spaceship spaceship;
    private int score;

    public PlayState(PApplet p, MelodyManager melodyManager)
     {
        super(p, melodyManager);
        particles = new ArrayList<>();
        cows = new ArrayList<>();
        spaceship = new Spaceship(p.width / 2, p.height - 50, p, melodyManager);
        score = 0;

        //RUBRIC 5% 20 particles, three subclasses (Star,Cow,Spaceship)
        // adds stars
        for (int i = 0; i < 17; i++) 
        {
            particles.add(new Star(p.random(p.width), p.random(p.height), p, melodyManager));
        }

        // adds cows
        for (int i = 0; i < 3; i++) 
        {
            cows.add(new Cow(p.random(p.width), p.random(p.height), p));
        }
    }

    public void update() 
    {
        // updates particles
        for (Particle particle : particles) 
        {
            particle.update(spaceship.getSpeedFactor());
        }

        // updates cows
        for (Cow cow : cows) 
        {
            cow.update(spaceship.getSpeedFactor());
        }

        //checks if cows collide with each other
        checkCowCollisions();

        // updates the bullets
        spaceship.updateBullets(spaceship.getSpeedFactor());

        //hecks for bullet collisions with stars and cows
        checkBulletCollisions();

        // checks to see if game is won
        if (particles.isEmpty())
         {
            App.currentState = new CreditState(p, true, score, melodyManager);
        }
    }


    private void checkBulletCollisions()
     {
        ArrayList<PVector> bullets = spaceship.getBullets();
        for (int i = bullets.size() - 1; i >= 0; i--) 
        {
            PVector bullet = bullets.get(i);

            //check collision with stars
            for (int j = particles.size() - 1; j >= 0; j--)
             {
                Particle particle = particles.get(j);
                if (isCollision(bullet, particle)) 
                {
                    particles.remove(j);
                    bullets.remove(i);
                    score++;
                    break;
                }
            }

            //check collision with cows
            for (int j = cows.size() - 1; j >= 0; j--)
             {
                Cow cow = cows.get(j);
                if (isCollision(bullet, cow)) 
                {
                    // Cow hit, player loses
                    App.currentState = new CreditState(p, false, score, melodyManager); //transition to lose screen
                    return;  //exit after losing
                }
            }
        }
    }

    private boolean isCollision(PVector bullet, Particle particle) 
    {
        float[] bounds = particle.getBounds();
        float particleX = bounds[0];
        float particleY = bounds[1];
        float particleWidth = bounds[2];
        float particleHeight = bounds[3];

        return bullet.x > particleX && bullet.x < particleX + particleWidth &&
               bullet.y > particleY && bullet.y < particleY + particleHeight;
    }

    private void checkCowCollisions() 
    {
        for (int i = 0; i < cows.size(); i++) 
        {
            for (int j = i + 1; j < cows.size(); j++)
             {
                cows.get(i).checkCollision(cows.get(j));
            }
        }
    }

    //displays cow,spaceship,and stars + score
    public void display() 
    {
        for (Particle particle : particles)
         {
            particle.display();
        }

        for (Cow cow : cows) 
        {
            cow.display();
        }

        spaceship.display();

        p.fill(255);
        p.text("Score: " + score, 40, 20);
    }

    @Override
    public void handleInput()
     {
        spaceship.handleInput(); 
    }

    @Override
    public void keyPressed() 
    {
        spaceship.keyPressed();
    }

    @Override
    public void keyReleased()
     {
        spaceship.keyReleased();
    }

    @Override
    public GameState transition()
     {
        return this;
    }

    @Override
    public void drawState()
     {
        update();
        display();
    }
}

 
 