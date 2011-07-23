package com.decider.cli;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.decider.core.Decision;
import com.decider.core.Factor;

public class Decider {

    enum State { INIT, TERMINATED,
	    DECISION_CREATED, DECISION_LABELED,
	    DECISION_FACTORS_INPUT, DECISION_FACTORS_LABELED};

    private Decision decision;
    private State state;
    private Integer numFactors;
    private Integer numChoices;

    public Decider() {
	this.decision = null;
	this.state = State.INIT;
    }

    public static void main( String [] args ) {

	Decider decider = new Decider();

	// Event loop
	while (decider.isRunning()) {

	    decider.displayChoices();
	    decider.parseChoice();

	}
	
    }

    protected void displayChoices() {

	switch (this.state) {

	    case INIT:
		System.out.println( "Menu:" );
		System.out.println( "[1] Create new decision. " );
		this.displayQuitChoice();
		break;

	    case DECISION_CREATED:
		System.out.println( "Enter label for decision:" );
		break;

	    case DECISION_LABELED:
		System.out.println( "Enter number of factors:" );
		break;

	    case DECISION_FACTORS_INPUT:
		Integer factorNum = this.decision.getFactors().size() + 1;
		System.out.println( "Enter label for factor number " + factorNum.toString() + ":" );
		break;

	    case DECISION_FACTORS_LABELED:
		System.out.println( "Enter number of choices:" );
		break;

	}

    }

    protected void displayQuitChoice() {

	System.out.println( "[Q] Quit. " );
	
    }

    protected void parseChoice() {

	String line = read();

	switch (this.state) {
		
	    case INIT:
		if (!this.parseQuitChoice(line)) {
		    if (line.equals("1")) {
			this.decision = new Decision();
			this.state = Decider.State.DECISION_CREATED;
		    }
		}
		break;

	    case DECISION_CREATED:
		this.decision.setLabel(line);
		this.state = Decider.State.DECISION_LABELED;
		break;

	    case DECISION_LABELED:
		this.numFactors = Integer.parseInt(line);
		this.state = Decider.State.DECISION_FACTORS_INPUT;
		break;

	    case DECISION_FACTORS_INPUT:
		Factor f = new Factor(line, 1);
		this.decision.addFactor(f);
		if (this.decision.getFactors().size() == this.numFactors) {
		    this.state = Decider.State.DECISION_FACTORS_LABELED;
		}
		break;

	}
    }

    protected boolean parseQuitChoice(String line) {
	boolean shouldQuit = (line.equals("Q"));
	if (shouldQuit) {
	    this.state = Decider.State.TERMINATED;
	}
	return shouldQuit;
    }

    public boolean isRunning() {
	return (this.state != Decider.State.TERMINATED);
    }

    static String read() {

	try {
	    BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
	    String line = stdin.readLine();
	    return line;
	} catch (java.io.IOException e) {
	    System.err.println(e);
	}
	return "";
    }


}
