#include "TrafficLight.h"

const int RED_PIN = 13;
const int AMBER_PIN = 11;
const int GREEN_PIN = 9;

TrafficLight _traffic(RED_PIN, AMBER_PIN, GREEN_PIN);

void setup()
{
    _traffic.PlayInit();
}

void loop()
{
    delay(1000);
}