package ru.mpei;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;

public class CalcMyFunction extends Behaviour {
    @Override
    public void action() {

    }

    @Override
    public void onStart() {
        if (myAgent.getLocalName().equals("Agent1")) {
            ACLMessage message = new ACLMessage(ACLMessage.INFORM);
            message.setContent("0,5");
            message.addReceiver(new AID("Agent1", false));
            message.addReceiver(new AID("Agent2", false));
            message.addReceiver(new AID("Agent3", false));
            myAgent.send(message);
        }
    }

    @Override
    public boolean done() {
        return false;
    }
}
