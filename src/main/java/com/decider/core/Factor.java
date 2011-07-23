package com.decider.core;

import java.util.Set;
import java.util.HashSet;
import com.decider.core.Decision;

public class Factor <T> {
    
    private String label;
    private Integer weight;
    private Decision decision;
    private Set <Choice> winningChoices;
    FactorChoiceManager factorChoiceManager;

    public Factor(String label, Integer weight) {

	this.setLabel(label);
	this.setWeight(weight);
	this.decision = null;
	this.winningChoices = new HashSet <Choice>();
	this.factorChoiceManager = FactorChoiceManager.getInstance();

    }

    public String getLabel() {
	return this.label;
    }

    public Integer getWeight() {
	return this.weight;
    }
    
    public Decision getDecision() {
	return this.decision;
    }

    public Set <Choice> getWinningChoices() {
	return this.winningChoices;
    }

    public void setLabel(String label) {
	this.label = label;
    }

    public void setWeight(Integer weight) {
	this.weight = weight;
	this.computeWinningChoices();
    }
    
    /* package */ void setDecision(Decision decision) {
	this.decision = decision;
    }

    /* package */ void setChoices(Set <Choice> choices) {
	for (Choice choice : choices) {
	    this.factorChoiceManager.create(this, choice);
	}
    }

    public void setFactorChoiceManager(FactorChoiceManager factorChoiceManager) {
	this.factorChoiceManager = factorChoiceManager;
    }
    
    /* package */ void remove() {
	this.factorChoiceManager.remove(this);
    }

    protected void computeWinningChoices() {
	
	Integer maxVotes = Integer.MIN_VALUE;

	Set <FactorChoice> factorChoices = this.factorChoiceManager.get(this);
	for ( FactorChoice factorChoice : factorChoices ) {

	    Integer factorChoiceVote = factorChoice.getValue().getVote();
	    if (factorChoiceVote > maxVotes) {
		this.winningChoices.clear();
		this.winningChoices.add(factorChoice.getChoice());
		maxVotes = factorChoiceVote;
	    } else if (factorChoiceVote == maxVotes) {
		this.winningChoices.add(factorChoice.getChoice());
	    }

	}

	this.decision.computeWinningChoices();

    }

}