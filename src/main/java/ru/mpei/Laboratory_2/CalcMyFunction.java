package ru.mpei.Laboratory_2;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;


import java.util.List;

public class CalcMyFunction extends Behaviour {
    private MessageTemplate messageTemplate;
    @Override
    public void onStart() {
        this.messageTemplate = MessageTemplate.MatchPerformative(ACLMessage.CFP);
    }

    @Override
    public void action() {
        ACLMessage receive = myAgent.receive(messageTemplate);
        if (receive != null) {
            String[] dateStr = receive.getContent().split(",");
            System.out.println("Начало расчета " + myAgent.getLocalName());

            SupportiveFunctionAgents support = new SupportiveFunctionAgents();
            List<Double> arg = null;

            if (myAgent.getLocalName().equals("Agent1")) {
                arg = support.Agent1(Double.parseDouble(dateStr[0]), Double.parseDouble(dateStr[1]));
            } else if (myAgent.getLocalName().equals("Agent2")) {
                arg = support.Agent2(Double.parseDouble(dateStr[0]), Double.parseDouble(dateStr[1]));
            } else if (myAgent.getLocalName().equals("Agent3")) {
                arg = support.Agent3(Double.parseDouble(dateStr[0]), Double.parseDouble(dateStr[1]));
            } else {
                System.out.println("Error");
            }

            String argStr = "";
            for (int i = 0; i < arg.size(); i++) {
                if (i != arg.size() -1 ) {
                    argStr = argStr + arg.get(i) + ",";
                } else {
                    argStr = argStr + arg.get(i);
                }
            }

            System.out.println(argStr);
//            System.out.println(receive.getSender().getLocalName());

            ACLMessage message = new ACLMessage(ACLMessage.PROPOSE);
            message.setContent(argStr);
            message.addReceiver(new AID(receive.getSender().getLocalName(), false));
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
