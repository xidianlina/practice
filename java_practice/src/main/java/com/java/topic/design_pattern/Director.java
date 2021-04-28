package com.java.topic.design_pattern;

//指挥者,用来指挥组装过程,也就是组装电脑的流程
public class Director {
    private Builder builder;

    public Director(Builder builder) {
        this.builder = builder;
    }

    public Computer createComputer(String cpu, String hardDisk, String mainBoard, String memory) {
        this.builder.createCpu(cpu);
        this.builder.createHardDisk(hardDisk);
        this.builder.createMainBoard(mainBoard);
        this.builder.createMainBoard(memory);

        return this.builder.createComputer();
    }
}
