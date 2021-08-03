#include <NjsolyUtil.h>

NjsolyUtil util(13);

void setup() {
  Serial.begin(38400);
  util.noop();
}

void loop() { 
  delay(10000);
}
