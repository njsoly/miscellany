/**
 * Try out a few pins on the new 10-digit bar graph LEDs.
 * YSLB-102510B5-10
 * 
 * Hookup: 
 * Ground connected to IC pins 1-10 via one 100-ohm resistor
 *
 * Note: The slightly-beveled corner of the bar graph IC highlights pin 1.
 */

#define SEGMENTS 10

#include <Arduino.h>

const boolean DEBUG = false; 

// pins
const int graphPins[SEGMENTS] = {2, 3, 4, 5, 6, 7, 8, 9, 10, 11};

int graphData[SEGMENTS];

// last millis() count when the bar graph was pulsed / lit
long flashLast = 0L;
// how many millis to wait between flashing the bar graph
long flashPeriod = 2L;
// last millis() count when the demo data was stepped.
long demoLast = 0L;
// how many millis to wait between stepping the demo
long demoPeriod = 50L;

void setup() {

  Serial.begin(38400);

  // pin and data setup
  for (int i = 0; i < SEGMENTS; i++) {
    pinMode(graphPins[i], OUTPUT);
    digitalWrite(graphPins[i], LOW);
  }
  writeBarGraph(0x010, graphData, SEGMENTS);
  
  flashLast = millis();
}

void loop() {
  if (flashLast + flashPeriod < millis()) {
    flashBarGraph();
    flashLast = millis();
  }

  if (demoLast + demoPeriod < millis()) {
    stepDemo();
    demoLast = millis();
  }

}

void flashBarGraph() {
  for (int i = 0; i < SEGMENTS; i++){
    digitalWrite(graphPins[i], (graphData[i] == 1 ? HIGH : LOW));
  }

  delayMicroseconds(20);
  allPinsLow(graphPins, SEGMENTS);
}

// write bit 0 of x to dataArray[0], bit 1 to dataArray[1], etc.
void writeBarGraph(int x, int dataArray[], int dataArrayLength) {
  for (int i = 0; i < dataArrayLength; i++){
    dataArray[i] = 0x1 & x;
    x = (unsigned int)x >> 1;
  }
}

void stepDemo() {
  graphDataRotateRight(graphData, SEGMENTS);
}

void graphDataRotateRight(int graphData[], int dataLength) {
  int temp = graphData[dataLength - 1];
  for (int i = dataLength - 1; i > 0; i--) {
    graphData[i] = graphData[i - 1];
  }

  graphData[0] = temp;
}

void allPinsLow(int pins[], int pinCount) {
  for (int i = 0; i < pinCount; i++) {
    digitalWrite(pins[i], LOW);
  }
}
