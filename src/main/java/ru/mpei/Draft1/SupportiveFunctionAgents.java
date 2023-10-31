package ru.mpei.Draft1;

public class SupportiveFunctionAgents {
    public String Agent1(double valueX) {
        return String.valueOf(Math.exp(-0.5 * valueX));
    }
    public String Agent2(double valueX) {
        return String.valueOf((0.5 * valueX) + 2);
    }
    public String Agent3(double valueX) {
        return String.valueOf(Math.cos(valueX));
    }
}
