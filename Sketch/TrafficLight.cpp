#include "Arduino.h"
#include "TrafficLight.h"

TrafficLight::TrafficLight(int redPin, int amberPin, int greenPin)
    : _red(redPin), _amber(amberPin), _green(greenPin)
{
}

void TrafficLight::SetMode(LightMode mode)
{
    _red.Set(mode & 0x04);
    _amber.Set(mode & 0x02);
    _green.Set(mode & 0x01);
}

void TrafficLight::Setup()
{
    LightMode lights[] = {
        LIGHT_MODE_RED,
        LIGHT_MODE_AMBER,
        LIGHT_MODE_GREEN,
        LIGHT_MODE_NONE,
        LIGHT_MODE_ALL,
        LIGHT_MODE_NONE,
    };

    for (int i = 0; i < 6; ++i)
    {
        SetMode(lights[i]);
        delay(InitStepDelay);
    }
}

void TrafficLight::ShowInconclusive()
{
    SetMode(LIGHT_MODE_AMBER);
}

void TrafficLight::ShowError()
{
    SetMode(LIGHT_MODE_RED_GREEN);
}
