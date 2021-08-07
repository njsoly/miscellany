/* 
 * sketch_ledMatrix_8x8_2021-08-06 
 * 
 * A sketch to test out the hookups and operation of my random
 * 8x8 Red LED matrix, which I haven't used since buying nine years ago,
 * and neither the datasheet nor any real identifying information is 
 * present on the site it came from.
 * 
 * After testing it out a bit, it seems like it's at least very close
 * to being a KWM-30881AVB.
 * 
 * The sketch uses a GPIO pin for each of the 16 pins on the device.
 * 
 * njsoly 2021-08-07
 */

const uint8_t rows[8]{2, 3, 4, 5, 6, 7, 8, 9};
const uint8_t cols[8]{A5, A4, A3, A2, A1, A0, 11, 10};
const uint8_t big0[8][8]{
  {1,1,1,1,1,1,1,1},
  {1,0,0,0,0,0,0,1},
  {1,0,0,0,0,0,0,1},
  {1,0,0,0,0,0,0,1},
  {1,0,0,0,0,0,0,1},
  {1,0,0,0,0,0,0,1},
  {1,0,0,0,0,0,0,1},
  {1,1,1,1,1,1,1,1},
};
const uint8_t o2[8][8]{
  {0,0,0,0,0,0,0,0},
  {0,1,1,1,1,1,1,0},
  {0,1,0,0,0,0,1,0},
  {0,1,0,0,0,0,1,0},
  {0,1,0,0,0,0,1,0},
  {0,1,0,0,0,0,1,0},
  {0,1,1,1,1,1,1,0},
  {0,0,0,0,0,0,0,0},
};
const uint8_t o3[8][8]{
  {0,0,0,0,0,0,0,0},
  {0,0,0,0,0,0,0,0},
  {0,0,1,1,1,1,0,0},
  {0,0,1,0,0,1,0,0},
  {0,0,1,0,0,1,0,0},
  {0,0,1,1,1,1,0,0},
  {0,0,0,0,0,0,0,0},
  {0,0,0,0,0,0,0,0},
};
const uint8_t o4[8][8]{
  {0,0,0,0,0,0,0,0},
  {0,0,0,0,0,0,0,0},
  {0,0,0,0,0,0,0,0},
  {0,0,0,1,1,0,0,0},
  {0,0,0,1,1,0,0,0},
  {0,0,0,0,0,0,0,0},
  {0,0,0,0,0,0,0,0},
  {0,0,0,0,0,0,0,0},
};

void setup() {

  Serial.begin(38400);
  
  for (int i = 0; i < 8; i++){
    pinMode(rows[i], OUTPUT);
    pinMode(cols[i], OUTPUT);
    digitalWrite(cols[i], HIGH);
    digitalWrite(rows[i], LOW);
  }
}

void loop() {
  // put your main code here, to run repeatedly:

  for (int i = 0; i < 8; i++) {
    for (int j = 0; j < 8; j++) {
      for(int n = 0; n < 100; n++) {
        digitalWrite(rows[i], HIGH);
        digitalWrite(cols[j], LOW);
        delayMicroseconds(40);
        digitalWrite(rows[i], LOW);
        digitalWrite(cols[j], HIGH);
        delayMicroseconds(40);
      }
      delay(20);

    }
  }

  writeDisplay(big0);
  writeDisplay(o2);
  writeDisplay(o3);
  writeDisplay(o4);
  resetPins();
  delay(500);
  writeDisplay(o4);
  writeDisplay(o3);
  writeDisplay(o2);
  writeDisplay(big0);
  resetPins();
  delay(500);
  
}
void writeDisplay(uint8_t data[8][8]) {
  for(int n = 0; n < 666; n++) { 
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        if (data[i][j] == 1) {
          digitalWrite(rows[i], HIGH);
          digitalWrite(cols[j], LOW);
          delayMicroseconds(18);
          digitalWrite(rows[i], LOW);
          digitalWrite(cols[j], HIGH);
        }
        else {
          delayMicroseconds(19);
        }
      }
//      delayMicroseconds(40);
    }
//    delayMicroseconds(65);
  }
  resetPins();
  delayMicroseconds(200);
}

void resetPins() {
  for (int i = 7; i >= 0; i--) {
    digitalWrite(cols[i], HIGH);
    digitalWrite(rows[i], LOW);
  }
}
