package ru.mpei;

import jade.core.Agent;

public class FunctionAgent extends Agent {

    protected void setup() {
        System.out.println(getLocalName());
        this.addBehaviour(new CalcMyFunction());
        this.addBehaviour(new InitiateDistributedCalculation());
        this.addBehaviour(new CatchInitiative());
    }
}
