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
    Lights lights[] = {
        LIGHTS_ALL,
        LIGHTS_RED,
        LIGHTS_AMBER,
        LIGHTS_GREEN,
        LIGHTS_RED_AMBER,
        LIGHTS_AMBER_GREEN,
        LIGHTS_RED_GREEN,
        LIGHTS_NONE
    };

    for (int i = 0; i < 8; ++i)
    {
        _traffic.Switch(lights[i]);
        delay(DELAY);
    }
}