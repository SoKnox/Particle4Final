/*
 * Sophie Knox
 * Particle Engine 4
 * 10/11/24
 * This project creates three sublasses of particles: an alien spaceship, cow, and stars.
 * Stars and cows bounce off screen. Spaceship is confined to x bounds. Cows collide with eachother
 * 
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

 import java.util.ArrayList;
 //RUBRIC MelodyManager implemation
 public class MelodyManager {
     ArrayList<MelodyPlayer> players; //play a midi sequence
     ArrayList<MidiFileToNotes> midiNotes; //read a midi file
 
     MelodyManager() {
         players = new ArrayList<>();
         midiNotes = new ArrayList<>();
     }
 
     //plays the midi file using the player -- so sends the midi to an external synth such as Kontakt or a DAW like Ableton or Logic
     public void playMelodies() {
         //NOTE: for assert() to work, you need to change the Java extension settings to run with assertions enabled
         assert(players != null); //this will throw an error if player is null -- eg. if you haven't called setup() first
         for (MelodyPlayer player: players)
             player.play(); //play each note in the sequence -- the player will determine whether is time for a note onset
         //checks time idk what
     }
 
     //opens the midi file, extracts a voice, then initializes a melody player with that midi voice (e.g. the melody)
     //filePath -- the name of the midi file to play
     void addMidiFile(String filePath) {
         int index = players.size();
         //Change the bus to the relevant port -- if you have named it something different OR you are using Windows
         players.add(new MelodyPlayer(100, "LoopBe Internal MIDI")); //sets up the player with your bus.
         //player.listDevices(); //prints available midi devices to the console -- find your device
 
         midiNotes.add(new MidiFileToNotes(filePath)); // creates a new MidiFileToNotes -- reminder -- ALL objects in Java
         // must
         // be created with "new". Note how every object is a pointer or
         // reference. Every. single. one.
 
         int noteCount = midiNotes.get(index).getPitchArray().size(); //get the number of notes in the midi file
 
         //NOTE: for assert() to work, you need to change the Java extension settings to run with assertions enabled
         assert(noteCount > 0); // make sure it got some notes (throw an error to alert you, the coder, if not)
 
         //sets the player to the melody to play the voice grabbed from the midi file above
         players.get(index).setMelody(midiNotes.get(index).getPitchArray());
         players.get(index).setRhythm(midiNotes.get(index).getRhythmArray());
         players.get(index).setStartTimes(midiNotes.get(index).getStartTimeArray());
 
         //start playing melody
     }
 
     void start(int index) {
         players.get(index).reset();
     }
 }
 