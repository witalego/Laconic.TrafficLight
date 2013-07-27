#include "Arduino.h"

#ifndef Switch_h
#define Switch_h

class Switch
{
    private:
        int _pin;
    public:
        Switch(int pin);
        void On();
        void Off();
        void Set(int state);
};

#endif