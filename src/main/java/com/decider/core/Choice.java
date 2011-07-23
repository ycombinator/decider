package com.decider.core;

import java.util.Set;
import com.decider.core.Decision;

public class Choice <T> {
    
    private String label;
    private Decision decision;
    FactorChoiceManager factorChoiceManager;

    public Choice(String label) {

	this.setLabel(label);
	this.decision = null;
	this.factorChoiceManager = FactorChoiceManager.getInstance();

    }

    public String getLabel() {
	return this.label;
    }

    public Decision getDecision() {
	return this.decision;
    }

    public void setLabel(String label) {
	this.label = label;
    }

    /* package */ void setDecision(Decision decision) {
	this.decision = decision;
    }
    
    /* package */ void setFactors(Set <Factor> factors) {
	for (Factor factor : factors) {
	    this.factorChoiceManager.create(factor, this);
	}
    }
    
    public void setFactorChoiceManager(FactorChoiceManager factorChoiceManager) {
	this.factorChoiceManager = factorChoiceManager;
    }
    
   /* package */ void remove() {
	this.factorChoiceManager.remove(this);
    }

}