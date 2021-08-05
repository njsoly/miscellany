#include <NjsolyUtil.h>
#include <HardwareSerial.h>

NjsolyUtil util(13, Serial);

void setup() {
  Serial.begin(38400);
  util.noop();
}

void loop() { 
  delay(10000);
}
