#include "Arduino.h"

#ifndef Light_h
#define Light_h

class Light
{
    private:
        int _pin;
    public:
        Light(int pin);
        void On();
        void Off();
        void Switch(byte state);
};

#endif