#include "TrafficLight.h"

#ifndef UdpController_h
#define UdpController_h

class UdpController
{
    private:
        static const unsigned int Port = 2806;
        static const unsigned long UpdateIntervalMillis = 16000;
        unsigned long _lastUpdateMillis;
        TrafficLight* _traffic;

    public:
        UdpController(TrafficLight* traffic);
        void Setup();
        void Loop();
};

#endif