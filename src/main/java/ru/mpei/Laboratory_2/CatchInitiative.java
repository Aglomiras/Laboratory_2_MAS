package ru.mpei.Laboratory_2;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class CatchInitiative extends Behaviour {
    private MessageTemplate messageTemplate;
    @Override
    public void onStart() {
        this.messageTemplate = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
    }

    @Override
    public void action() {
        ACLMessage receive = myAgent.receive(this.messageTemplate);
        if (receive != null) {
            System.out.println("Old leader: " + receive.getSender().getLocalName());
            System.out.println("New leader: " + myAgent.getLocalName());
            System.out.println("Значения x и delta: " + Double.parseDouble(receive.getContent().split(",")[0]) + " " +
                    Double.parseDouble(receive.getContent().split(",")[1]));

            myAgent.addBehaviour(new InitiateDistributedCalculation(this.getAgent(), 2000,
                    Double.parseDouble(receive.getContent().split(",")[0]),
                    Double.parseDouble(receive.getContent().split(",")[1])));
        } else {
            block();
        }
    }

    @Override
    public boolean done() {
        return false;
    }

    @Override
    public int onEnd() {
        return super.onEnd();
    }
}
