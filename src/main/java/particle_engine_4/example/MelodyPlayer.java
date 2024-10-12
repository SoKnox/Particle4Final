/*
 * c2017-c2023 Courtney Brown 
 * 
 * Class: MelodyPlayer
 * Description: Sends a melody of midi notes to an external player/midi channel, revised 2024 for polyphonic playing
 * 
 */
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

 import java.util.*;
 
 // Send the MIDI elsewhere to play the notes
 public class MelodyPlayer {
     MidiBusCRCP outputMidiBus; // Sends midi notes to an external program (eg. Ableton, Logic, etc.)
 
     int note_index = 0; // Where we are in the notes
 
     float notems; // The value of a quaver or 1/4 note in millis
     float last_time; // The last time we called draw()
     boolean play; // Should we play?
     float bpm; // Beats per minute of melody
 
     double start_time; // Time at creation of the melody player in milliseconds
     double playStart = 0;
 
     double rhythm_multiplier; // Determines note length and onset of the next note
 
     ArrayList<Integer> melody; // List of pitches in order
     ArrayList<Double> rhythm; // List of note lengths (& thus time before next note, in order)
     ArrayList<Double> startTimes; // List of start times
 
     boolean hasRhythm; // Has there been a list of rhythms assigned?
     boolean hasMelody; // Has there been a list of pitches assigned?
     boolean hasStart = false;
 
     String outputBus; // Bus to send MIDI to -- change if you have named it something else or you are using Windows
 
     // List of playing notes to keep track off for note offs
     ArrayList<Double> playingRhythms = new ArrayList();
     ArrayList<Double> playingTimes = new ArrayList();
     ArrayList<Integer> playingPitches = new ArrayList();
 
     // Constructor -- initializes data)
     // Input is the tempo - bpm (beats per minute) or how fast to play the music
     MelodyPlayer(float tempo, String bus) {
         reset();
         setBPM(tempo);
         play = true;
         hasRhythm = false;
         rhythm_multiplier = 0.5f; // Default is 1/8 notes
         start_time = System.currentTimeMillis();
         outputBus = bus;
         setupMidi();
     }
 
     void setMelody(ArrayList<Integer> m) {
         melody = m;
         hasMelody = true;
     }
 
     void setRhythm(ArrayList<Double> r) {
         rhythm = r;
         hasRhythm = true;
     }
 
     void setStartTimes(ArrayList<Double> r) {
         startTimes = r;
         hasStart = true;
     }
 
     // Display all ports available to the MidiBus -- only output ports are relevant, however
     // If OS X, best to choose the IAC bus we created (for mac/OS X) so can send to an external program (eg DAW/sampler)
     // If Windows, why are you LIKE this??! -- TODO: install/document virtual port on Windows via 3rd party software
     void listDevices() {
         MidiBusCRCP.listDevices();
     }
 
     // Create the Midi port and bus to send the notes to
     void setupMidi() {
         outputMidiBus = new MidiBusCRCP(outputBus);
         // Or if on windows - use a windows virtual port
     }
 
     void setBPM(float tempo) {
         bpm = tempo;
         notems = (float) (((1.0 / (bpm / 60.0))) * 1000); // How many ms in a 1/4 note for this tempo
     }
 
     // Time since creation of the melody player -- mimics the processing function millis()
     int millis() {
         double millisNow = System.currentTimeMillis() - start_time;
         return (int) millisNow;
     }
 
     // Send the melody out in MIDI messages, in correct timing, to the external program
     void play() {
         int vel = 100; // Midi velocity -- TODO: change/assign if want to vary
         double cur_time = millis(); // What time is it now?
 
         // Send note off messages for any notes that have been playing
         sendNoteOff(cur_time);
 
         // Just do nothing if there is no melody (pitches)
         if (!hasMelody) {
             System.out.println("There is no melody in the notes given.");
             return;
         }
 
         // If we're at the end of the melody also do nothing
         if (atEndOfMelody()) {
             return;
         }
 
         // Playing the notes by sending the note ons at the correct times
         if (note_index < startTimes.size()) {
             play = cur_time - playStart >= notems * startTimes.get(note_index); // Should we send the note now? based on prev. note's duration
 
             // Send note on messages
             if (play && note_index < startTimes.size()) {
                 sendNoteOn(note_index, cur_time, vel);
                 note_index++;
                 boolean sameTime = true;
 
                 // Send note-on messages for the notes that occur at the same time as well (eg. a chord, a dyad)
                 while (note_index < startTimes.size() && sameTime) {
                     sameTime = (startTimes.get(note_index - 1).equals(startTimes.get(note_index)));
                     if (sameTime) {
                         sendNoteOn(note_index, cur_time, vel);
                         note_index++;
                     }
                 }
             }
         }
     }
 
     // Send a note on message and then add to playing note arrays
     void sendNoteOn(int note_index, double cur_time, int vel) {
         outputMidiBus.sendNoteOn(0, (int) melody.get(note_index), vel);
         playingRhythms.add(rhythm.get(note_index));
         playingTimes.add(cur_time);
         playingPitches.add(melody.get(note_index));
 
         // Print note information to the terminal
         System.out.println("Note On: Pitch = " + melody.get(note_index) + ", Velocity = " + vel + ", Time = " + cur_time);
     }
 
     // Send note off messages for the playing notes in our buffers
     void sendNoteOff(double cur_time) {
         for (int i = playingRhythms.size() - 1; i >= 0; i--) {
             if (playingRhythms.get(i) * notems <= cur_time - playingTimes.get(i)) {
                 outputMidiBus.sendNoteOff(0, (int) playingPitches.get(i), 0);
                 playingRhythms.remove(i);
                 playingPitches.remove(i);
                 playingTimes.remove(i);
 
                 // Print note off information to the terminal
                 System.out.println("Note Off: Pitch = " + playingPitches.get(i) + ", Time = " + cur_time);
             }
         }
     }
 
     // Reset note to 0
     void reset() {
         note_index = 0;
         playStart = millis();
 
         playingRhythms.clear();
         playingPitches.clear();
         playingTimes.clear();
     }
 
     // Set to ends
     void setToEnd() {
         note_index = melody.size();
 
         playingRhythms.clear();
         playingPitches.clear();
         playingTimes.clear();
     }
 
     // Have we reached the end of the melody?
     boolean atEndOfMelody() {
         return note_index >= melody.size();
     }
 }
 
 
 
 