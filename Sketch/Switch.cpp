#include "Arduino.h"
#include "Switch.h"

Switch::Switch(int pin)
{
    _pin = pin;

    pinMode(_pin, OUTPUT);
}

void Switch::On()
{
    digitalWrite(_pin, HIGH);
}

void Switch::Off()
{
    digitalWrite(_pin, LOW);
}

void Switch::Set(int state)
{
    digitalWrite(_pin, state == 0 ? LOW : HIGH);
}