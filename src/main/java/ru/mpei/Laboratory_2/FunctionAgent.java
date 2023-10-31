package ru.mpei.Laboratory_2;

import jade.core.Agent;

import java.util.Random;

public class FunctionAgent extends Agent {
    @Override
    protected void setup() {
        System.out.println("I is " + getLocalName() + " born!");

        if (this.getLocalName().equals("Agent1")) {
            this.addBehaviour(new InitiateDistributedCalculation(this, 500, randomNumbers(), randomNumbers()));
        }
        this.addBehaviour(new CalcMyFunction());
        this.addBehaviour(new CatchInitiative());
    }

    public static double randomNumbers() {
        Random r = new Random();
        double arg = r.nextDouble(10);
        return arg;
    }
}
