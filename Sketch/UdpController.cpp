#include <SPI.h>
#include <Ethernet.h>
#include <EthernetUdp.h>
#include "Arduino.h"
#include "UdpController.h"

byte MacAddress[] = {0x00, 0xAA, 0xBB, 0xCC, 0x28, 0x06};
char Buffer[UDP_TX_PACKET_MAX_SIZE];
EthernetUDP Udp;

UdpController::UdpController(TrafficLight* traffic)
{
    _traffic = traffic;
}

void UdpController::Setup()
{
    if (Ethernet.begin(MacAddress) == 0)
    {
        _traffic->ShowError();
        for(;;);
    }

    Udp.begin(Port);

    _traffic->ShowInconclusive();
    _lastUpdateMillis = millis();
}

void UdpController::Loop()
{
    unsigned long currentMillis = millis();
    int packetSize = Udp.parsePacket();

    if (packetSize && packetSize == 3)
    {
        Udp.read(Buffer, UDP_TX_PACKET_MAX_SIZE);

        if (Buffer[0] == 0x28 && Buffer[1] == 0x06)
        {
            _traffic->SetMode((LightMode)Buffer[2]);
            _lastUpdateMillis = currentMillis;
        }
    }

    if ((currentMillis - _lastUpdateMillis) > UpdateIntervalMillis)
    {
        _traffic->ShowInconclusive();
    }
}