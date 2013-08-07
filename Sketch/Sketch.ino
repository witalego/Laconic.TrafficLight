#include <SPI.h>
#include <Ethernet.h>
#include <EthernetUdp.h>
#include "TrafficLight.h"
#include "UdpController.h"

const int LOOP_DELAY = 100;
const int RED_PIN = 9;
const int AMBER_PIN = 7;
const int GREEN_PIN = 5;
TrafficLight _traffic(RED_PIN, AMBER_PIN, GREEN_PIN);
UdpController _controller(&_traffic);

void setup()
{
    _traffic.Setup();
    _controller.Setup();
}

void loop()
{
    _controller.Loop();
    delay(LOOP_DELAY);
}
