package org.witalego.server;

public enum LightMode
{
    None(0x00),
    Green(0x01),
    Yellow(0x02),
    YellowGreen(0x03),
    Red(0x04),
    RedGreen(0x05),
    RedYellow(0x06),
    All(0x07);

    private final int value;

    private LightMode(int value)
    {
        this.value = value;
    }

    public int getValue()
    {
        return value;
    }
}