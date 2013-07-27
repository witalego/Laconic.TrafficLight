#include "Arduino.h"
#include "Light.h"

Light::Light(int pin)
{
    _pin = pin;

    pinMode(_pin, OUTPUT);
}

void Light::On()
{
    digitalWrite(_pin, HIGH);
}

void Light::Off()
{
    digitalWrite(_pin, LOW);
}

void Light::Switch(byte state)
{
    digitalWrite(_pin, state == 0 ? LOW : HIGH);
}