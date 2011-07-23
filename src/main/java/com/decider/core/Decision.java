package com.decider.core;

import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import com.decider.core.Factor;
import com.decider.core.Choice;

public class Decision {

    private String label;
    private Set <Factor> factors;
    private Set <Choice> choices;
    private Set <Choice> winningChoices;

    public Decision(String label) {
	this.setLabel(label);
	this.factors = new HashSet <Factor>();
	this.choices = new HashSet <Choice>();
	this.winningChoices = new HashSet <Choice>();
    }

    public String getLabel() {
	return this.label;
    }
    
    public Set <Factor> getFactors() {
	return this.factors;
    }
    
    public Set <Choice> getChoices() {
	return this.choices;
    }
    
    public Set <Choice> getWinningChoices() {
	return this.winningChoices;
    }
    
    public void setLabel(String label) {
	this.label = label;
    }

    public void addFactor(Factor factor) {
	factor.setDecision(this);
	factor.setChoices(this.getChoices());
	this.factors.add(factor);
    }

    public void addChoice(Choice choice) {
	choice.setDecision(this);
	choice.setFactors(this.getFactors());
	this.choices.add(choice);
    }

    public void removeFactor(Factor factor) {
	factors.remove(factor);
	factor.remove();
    }

    public void removeChoice(Choice choice) {
	choices.remove(choice);
	choice.remove();
    }

    protected void computeWinningChoices() {

	Map <Choice, Integer> choiceVotes = new HashMap <Choice, Integer>();
	Integer maxVotes = Integer.MIN_VALUE;

	for (Factor factor : this.factors) {
	    Set <Choice> factorWinningChoices = factor.getWinningChoices();

	    for (Choice factorWinningChoice : factorWinningChoices) {

		// Calculate newVotes for this choice based on factor weight.
		Integer newVotes = factor.getWeight();
		if (choiceVotes.containsKey( factorWinningChoice )) {
		    newVotes += choiceVotes.get( factorWinningChoice );
		}
		choiceVotes.put( factorWinningChoice, newVotes);

		// Update maxVotes if necessary.
		if (newVotes > maxVotes) {
		    maxVotes = newVotes;
		}

	    }
	}

	// Find all choices with maxVotes votes.
	this.winningChoices = new HashSet <Choice>();
	for (Map.Entry <Choice, Integer> entry : choiceVotes.entrySet()) {
	    if (entry.getValue() == maxVotes) {
		this.winningChoices.add(entry.getKey());
	    }
	}

    }

}