#include "Arduino.h"
#include "TrafficLight.h"

TrafficLight::TrafficLight(int redPin, int amberPin, int greenPin)
    : _red(redPin), _amber(amberPin), _green(greenPin)
{
}

void TrafficLight::Switch(Lights lights)
{
    _red.Switch(lights & 0100);
    _amber.Switch(lights & 0010);
    _green.Switch(lights & 0001);
}
