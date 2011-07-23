package com.decider.core;

import com.decider.core.Factor;
import com.decider.core.Choice;
import com.decider.core.types.FactorChoiceType;

public class FactorChoice <T extends FactorChoiceType> {

    private T value;
    private Factor <T> factor;
    private Choice choice;

    public FactorChoice() {
	this.setValue(null);
	this.setFactor(null);
	this.setChoice(null);
    }

    public T getValue() {
	return this.value;
    }
    
    public Factor <T> getFactor() {
	return this.factor;
    }

    public Choice getChoice() {
	return this.choice;
    }

    public void setValue(T value) {
	this.value = value;
	if (this.factor != null) {
	    this.factor.computeWinningChoices();
	}
    }

    /* package */ void setFactor(Factor <T> factor) {
	this.factor = factor;
    }
    
    /* package */ void setChoice(Choice <T> choice) {
	this.choice = choice;
    }

    public boolean equals(FactorChoice <T> anotherFactorChoice) {
	return (this.getFactor().equals(anotherFactorChoice.getFactor()))
	    && (this.getChoice().equals(anotherFactorChoice.getChoice()));

    }

}
