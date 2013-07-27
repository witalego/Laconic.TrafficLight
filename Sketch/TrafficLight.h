#include "Arduino.h"
#include "Switch.h"

#ifndef TrafficLight_h
#define TrafficLight_h

enum LightMode
{
    LIGHT_MODE_NONE = 0,
    LIGHT_MODE_RED = 0100,
    LIGHT_MODE_AMBER = 0010,
    LIGHT_MODE_GREEN = 0001,
    LIGHT_MODE_RED_AMBER = 0110,
    LIGHT_MODE_AMBER_GREEN = 0011,
    LIGHT_MODE_RED_GREEN = 0101,
    LIGHT_MODE_ALL = 0111,
};

class TrafficLight
{
    private:
        static const int InitStepDelay = 400;
        Switch _red;
        Switch _amber;
        Switch _green;
    public:
        TrafficLight(int redPin, int amberPin, int greenPin);
        void SetMode(LightMode mode);
        void PlayInit();
        void ShowInconclusive();
};

#endif