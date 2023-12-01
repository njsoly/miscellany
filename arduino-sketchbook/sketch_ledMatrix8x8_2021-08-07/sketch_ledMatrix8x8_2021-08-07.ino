#include "NjsolyUtil.h"
#include "ShiftRegHC595.h"
/*
 * sketch_ledMatrix8x8_2021-08-07
 * follow up sketch_ledMatrix8x8_2021-08-06, but use shift registers
 * to 
 */

NjsolyUtil util(Serial);
ShiftRegHC595 shiftReg(
  4, // SER
  2, // SRCLK
  3, // RCLK
  16  // dataLength
);


void setup() {
  Serial.begin(38400);
  shiftReg.initPins();

}

void loop() {
  // put your main code here, to run repeatedly:

  loopAllTheLights();

}

void loopAllTheLights() {
  for(uint8_t n = 0; n < 200; n++) {
    for (uint8_t y = 0; y < 8; y++) {
      for (uint8_t x = 0; x < 8; x++) {
//        digitalWrite(rows[y], HIGH);
//        digitalWrite(cols[x], LOW);
        writeDot(y, x);
//        delayMicroseconds(4);
//        digitalWrite(rows[y], LOW);
//        digitalWrite(cols[x], HIGH);
//        delayMicroseconds(36);
      }
      delay(20);
    }
  }
}
uint8_t mask[16];
uint8_t* getMatrixBlankMask() {
  
  for(int i = 0; i < 8; i++){
    mask[i] = 1;
    mask[i + 8] = 0; 
  }
  return mask;
}

uint8_t* getMatrixWriteMask(uint8_t row, uint8_t col) {
  uint8_t* mask = getMatrixBlankMask();
  mask[15 - row] = 1;
  mask[7 - col] = 0;

  Serial.print("mask for ");
  Serial.print(row);
  Serial.print(", ");
  Serial.print(col);
  Serial.print(" is ");
  for (int i = 0; i < 8; i++) {
    Serial.print(mask[15 - i] > 0 ? "1," : "0,");
  }
  Serial.print(" ");
  for (int i = 8; i < 15; i++) {
    Serial.print(mask[15 - i] > 0 ? "1," : "0,");
  }
  Serial.println(mask[0] > 0 ? "1" : "0");
}

void writeDot(uint8_t row, uint8_t col) {
  
  shiftReg.shiftOut(getMatrixWriteMask(row, col), 16);
  delayMicroseconds(60);
  delay(1000);  
  shiftReg.shiftOut(getMatrixBlankMask(), 16);
  delayMicroseconds(36);

//  delay(1000);
}
