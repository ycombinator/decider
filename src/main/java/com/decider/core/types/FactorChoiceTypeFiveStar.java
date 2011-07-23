package com.decider.core.types;

import com.decider.core.types.FactorChoiceType;

public class FactorChoiceTypeFiveStar implements FactorChoiceType {

    public enum Choice { 

	ONE_STAR (1),
	TWO_STAR (2),
	THREE_STAR (3),
	FOUR_STAR (4),
	FIVE_STAR (5);
	
	private final Integer vote;

	Choice(Integer vote) {
	    this.vote = vote;
	}

	public Integer getVote() {
	    return this.vote;
	}

    };

    private Choice value;

    public FactorChoiceTypeFiveStar(Choice value) {
	this.value = value;
    }

    public Integer getVote() {
	return this.value.getVote();
    }

}