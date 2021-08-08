#include <NjsolyUtil.h>
#include <HardwareSerial.h>
#include <ShiftRegHC595.h>

NjsolyUtil util(Serial);
ShiftRegHC595 shiftReg(1, 2, 3, 20);


void setup() {
  Serial.begin(38400);
  util.noop();
}

void loop() { 
  delay(10000);
}
