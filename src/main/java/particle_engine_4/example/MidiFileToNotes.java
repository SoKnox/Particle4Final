
/*
 * c2017 Courtney Brown 
 * 
 * Class: MidiFileToNotes
 * Description: Uses JMusic to get notes & info from a midi file, revised 2024 to include harmony.
 * 
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
import jm.music.data.*;
import jm.util.*;

public class MidiFileToNotes {
    String filename;
    ArrayList<Integer> pitches; // The midi values of the notes
    ArrayList<Double> rhythms; // The rhythmic values (in jm notation)
    ArrayList<Double> startTimes; // Start times

    int whichLine = 0; // Which instrument, basically -- but "line of music" or midi channel of music to be the most precise
    boolean setSingleLine = false;

    ArrayList<jm.music.data.Note> melody; // List of notes in jm form --> melody + rhythm

    // Input for the constructor is the midi file that we are reading
    // f - is the filename of the midi file
    MidiFileToNotes(String f) {
        filename = f;
        processPitchesAsTokens();
    }

    // Set which line or instrument of the midi file
    // line - which line - 0 is the first line
    void setWhichLine(int line) {
        whichLine = line;
    }

    // Only read in one part of the score, the one indicated
    void setSingleLine(int index) {
        whichLine = index;
        setSingleLine = true;
    }

    // Read in all parts of the score
    void setToAllParts() {
        setSingleLine = false;
    }

    // Extract the midi data from jm.music format into separate arrays of pitch and melody and startTimes
    void processPitchesAsTokens() {

        // The pitches, melody, rhythms, and start times of all the notes
        pitches = new ArrayList<Integer>();
        melody = new ArrayList<jm.music.data.Note>();
        rhythms = new ArrayList<Double>();
        startTimes = new ArrayList<Double>();

        // Score names for JMusic
        String scoreName = "score_" + filename;
        Score theScore = new Score(scoreName);

        // Read the midi file into a score
        Read.midi(theScore, filename);

        // Extract the melody and all its parts, unless requested a single line
        int partStart = 0;
        int partEnd = theScore.getPartArray().length;
        if (setSingleLine) {
            partStart = whichLine - 1;
            partEnd = whichLine;
        }

        // Process the score here
        for (int whichPart = partStart; whichPart < partEnd; whichPart++) {
            Part part = theScore.getPart(whichPart);
            Phrase[] phrases = part.getPhraseArray();

            // Extract all the pitches and notes from the melody & put them in MIDI style-note-ish ordering using an insertion-style sort
            for (int i = 0; i < phrases.length; i++) {
                jm.music.data.Note[] notes = phrases[i].getNoteArray();
                for (int j = 0; j < notes.length; j++) {

                    int placeToAdd = 0;
                    boolean added = false;
                    while (placeToAdd < startTimes.size() && !added) {
                        added = phrases[i].getNoteStartTime(j) < startTimes.get(placeToAdd);
                        placeToAdd++;
                    }

                    if (added)
                        placeToAdd--;

                    pitches.add(placeToAdd, notes[j].getPitch());
                    rhythms.add(placeToAdd, notes[j].getDuration());
                    startTimes.add(placeToAdd, phrases[i].getNoteStartTime(j));
                    melody.add(placeToAdd, notes[j]);
                }
            }
        }
    }

    public Integer[] getPitches() {
        return pitches.toArray(new Integer[pitches.size()]);
    }

    public ArrayList<Integer> getPitchArray() {
        return pitches;
    }

    public ArrayList<Double> getRhythmArray() {
        return rhythms;
    }

    public ArrayList<Double> getStartTimeArray() {
        return startTimes;
    }

    public ArrayList<jm.music.data.Note> getMelody() {
        return melody;
    }

    public Double[] getRhythms() {
        return rhythms.toArray(new Double[rhythms.size()]);
    }

}
