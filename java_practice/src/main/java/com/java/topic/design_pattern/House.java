package com.java.topic.design_pattern;

//产品对象:房子
public class House {
    // 打地基
    private String foundation;

    // 盖框架
    private String frame;

    // 浇筑
    private String pouring;

    public String getFoundation() {
        return foundation;
    }

    public void setFoundation(String foundation) {
        this.foundation = foundation;
    }

    public String getFrame() {
        return frame;
    }

    public void setFrame(String frame) {
        this.frame = frame;
    }

    public String getPouring() {
        return pouring;
    }

    public void setPouring(String pouring) {
        this.pouring = pouring;
    }

    @Override
    public String toString() {
        return "House{" +
                "foundation='" + foundation + '\'' +
                ", frame='" + frame + '\'' +
                ", pouring='" + pouring + '\'' +
                '}';
    }
}
