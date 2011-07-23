package com.decider.core;

import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import com.decider.core.FactorChoice;

public class FactorChoiceManager {

    private static FactorChoiceManager instance = null;
    private Set <FactorChoice> factorChoices;
    private Map <Factor, Set <FactorChoice>> factorIndex;
    private Map <Choice, Set <FactorChoice>> choiceIndex;

    private FactorChoiceManager() {
	this.factorChoices = new HashSet <FactorChoice>();
	this.factorIndex = new HashMap <Factor, Set <FactorChoice>>();
	this.choiceIndex = new HashMap <Choice, Set <FactorChoice>>();
    }

    public static FactorChoiceManager getInstance() {
	if (FactorChoiceManager.instance == null) {
	    FactorChoiceManager.instance = new FactorChoiceManager();
	}
	return FactorChoiceManager.instance;
    }

    public FactorChoice create(Factor factor, Choice choice) {
	FactorChoice factorChoice = new FactorChoice ();
	factorChoice.setFactor(factor);
	factorChoice.setChoice(choice);

	if (!this.factorChoices.contains(factorChoice)) {

	    this.factorChoices.add(factorChoice);

	    Set <FactorChoice> indexFactorChoices;

	    // Update factor index
	    indexFactorChoices = this.get(factor);
	    indexFactorChoices.add(factorChoice);
	    this.set(factor, indexFactorChoices);
	    
	    // Update choice index
	    indexFactorChoices = this.get(choice);
	    indexFactorChoices.add(factorChoice);
	    this.set(choice, indexFactorChoices);
	    
	}

	return factorChoice;
    }

    public <T> Set <FactorChoice> get(Factor <T> factor) {
	Set <FactorChoice> indexFactorChoices = this.factorIndex.get(factor);
	if (indexFactorChoices == null) {
	    indexFactorChoices = new HashSet <FactorChoice>();
	}
	return indexFactorChoices;
    }
	
    public Set <FactorChoice> get(Choice choice) {
	Set <FactorChoice> indexFactorChoices = this.choiceIndex.get(choice);
	if (indexFactorChoices == null) {
	    indexFactorChoices = new HashSet <FactorChoice>();
	}
	return indexFactorChoices;
    }

    protected void set(Factor factor, Set <FactorChoice> indexFactorChoices) {
	this.factorIndex.put(factor, indexFactorChoices);
    }
	
    protected void set(Choice choice, Set <FactorChoice> indexFactorChoices) {
	this.choiceIndex.put(choice, indexFactorChoices);
    }
	
    public void remove(Factor factor) {
	// Get FactorChoices from factor index
	Set <FactorChoice> factorIndexFactorChoices = this.get(factor);

	// Cleanup set
	this.factorChoices.removeAll(factorIndexFactorChoices);

	// Cleanup choice index
	for (FactorChoice fc : factorIndexFactorChoices) {
	    Choice c = fc.getChoice();
	    Set <FactorChoice> choiceIndexFactorChoices = this.get(c);
	    choiceIndexFactorChoices.remove(fc);
	    this.set(c, choiceIndexFactorChoices);
	}

	// Cleanup factor index
	this.factorIndex.remove(factor);
							       
    }
	
    public void remove(Choice choice) {
	// Get FactorChoices from choice index
	Set <FactorChoice> choiceIndexFactorChoices = this.get(choice);

	// Cleanup set
	this.factorChoices.removeAll(choiceIndexFactorChoices);

	// Cleanup factor index
	for (FactorChoice fc : choiceIndexFactorChoices) {
	    Factor f = fc.getFactor();
	    Set <FactorChoice> factorIndexFactorChoices = this.get(f);
	    factorIndexFactorChoices.remove(fc);
	    this.set(f, factorIndexFactorChoices);
	}

	// Cleanup choice index
	this.choiceIndex.remove(choice);
							       
    }

}
