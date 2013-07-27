#include "Arduino.h"
#include "Light.h"

#ifndef TrafficLight_h
#define TrafficLight_h

enum Lights
{
    LIGHTS_NONE = 0,
    LIGHTS_RED = 0100,
    LIGHTS_AMBER = 0010,
    LIGHTS_GREEN = 0001,
    LIGHTS_RED_AMBER = 0110,
    LIGHTS_AMBER_GREEN = 0011,
    LIGHTS_RED_GREEN = 0101,
    LIGHTS_ALL = 0111,
};

class TrafficLight
{
    private:
        Light _red;
        Light _amber;
        Light _green;
    public:
        TrafficLight(int redPin, int amberPin, int greenPin);
        void Switch(Lights lights);
};

#endif