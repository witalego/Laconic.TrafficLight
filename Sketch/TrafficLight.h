#include "Arduino.h"
#include "Switch.h"

#ifndef TrafficLight_h
#define TrafficLight_h

enum LightMode
{
    LIGHT_MODE_NONE = 0x00,
    LIGHT_MODE_GREEN = 0x01,
    LIGHT_MODE_AMBER = 0x02,
    LIGHT_MODE_AMBER_GREEN = 0x03,
    LIGHT_MODE_RED = 0x04,
    LIGHT_MODE_RED_GREEN = 0x05,
    LIGHT_MODE_RED_AMBER = 0x06,
    LIGHT_MODE_ALL = 0x07,
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
        void Setup();
        void ShowInconclusive();
        void ShowError();
};

#endif