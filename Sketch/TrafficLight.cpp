#include "Arduino.h"
#include "TrafficLight.h"

TrafficLight::TrafficLight(int redPin, int amberPin, int greenPin)
    : _red(redPin), _amber(amberPin), _green(greenPin)
{
}

void TrafficLight::SetMode(LightMode mode)
{
    _red.Set(mode & 0100);
    _amber.Set(mode & 0010);
    _green.Set(mode & 0001);
}

void TrafficLight::PlayInit()
{
    LightMode lights[] = {
        LIGHT_MODE_RED,
        LIGHT_MODE_AMBER,
        LIGHT_MODE_GREEN,
        LIGHT_MODE_NONE,
        LIGHT_MODE_ALL,
    };

    for (int i = 0; i < 5; ++i)
    {
        SetMode(lights[i]);
        delay(InitStepDelay);
    }

    SetMode(LIGHT_MODE_NONE);
}

void TrafficLight::ShowInconclusive()
{
    SetMode(LIGHT_MODE_AMBER);
}
