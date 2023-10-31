package ru.mpei.Draft1;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class CalcMyFunction extends Behaviour {
    private MessageTemplate messageTemplate;
    private double X;
    private double delta;
    @Override
    public void onStart() {
        this.messageTemplate = MessageTemplate.MatchPerformative(ACLMessage.PROPOSE);
    }

    @Override
    public void action() {
        ACLMessage receiveMessage = myAgent.receive(this.messageTemplate);
        if (receiveMessage != null)  {
            System.out.println("Начало расчета: " + myAgent.getLocalName());
            this.X = Double.parseDouble(receiveMessage.getContent().split(",")[0]);
            this.delta = Double.parseDouble(receiveMessage.getContent().split(",")[1]);

            SupportiveFunctionAgents support = new SupportiveFunctionAgents();

            ACLMessage message = new ACLMessage(ACLMessage.CFP);
            message.addReceiver(new AID(receiveMessage.getSender().getLocalName(), false));

            if (myAgent.getLocalName().equals("Agent1")) {
                message.setContent(support.Agent1(this.X - this.delta) + "," + support.Agent1(this.X) + "," + support.Agent1(this.X + this.delta));
            }
            if (myAgent.getLocalName().equals("Agent2")) {
                message.setContent(support.Agent2(this.X - this.delta) + "," + support.Agent2(this.X) + "," + support.Agent2(this.X + this.delta));
            }
            if (myAgent.getLocalName().equals("Agent3")) {
                message.setContent(support.Agent3(this.X - this.delta) + "," + support.Agent3(this.X) + "," + support.Agent3(this.X + this.delta));
            }

            myAgent.send(message);

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
