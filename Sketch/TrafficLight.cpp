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
