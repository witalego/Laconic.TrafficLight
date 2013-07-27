#include "TrafficLight.h"

const int RED_PIN = 13;
const int AMBER_PIN = 11;
const int GREEN_PIN = 9;
const int DELAY = 750;

TrafficLight _traffic(RED_PIN, AMBER_PIN, GREEN_PIN);

void setup()
{
}

void loop()
{
    LightMode lights[] = {
        LIGHT_MODE_ALL,
        LIGHT_MODE_RED,
        LIGHT_MODE_AMBER,
        LIGHT_MODE_GREEN,
        LIGHT_MODE_RED_AMBER,
        LIGHT_MODE_AMBER_GREEN,
        LIGHT_MODE_RED_GREEN,
        LIGHT_MODE_NONE
    };

    for (int i = 0; i < 8; ++i)
    {
        _traffic.SetMode(lights[i]);
        delay(DELAY);
    }
}