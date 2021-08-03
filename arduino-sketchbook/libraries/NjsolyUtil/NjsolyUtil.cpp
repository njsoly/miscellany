#include "Arduino.h"
#include "USBAPI.h"
#include "NjsolyUtil.h"

NjsolyUtil::NjsolyUtil(int pin) {
//   isNumber(int s[]);
  _pin = pin;
}

bool NjsolyUtil::isNumber(char message[], int maxLength=32) {
  char zero = '0', nine = '9';
  int i = 0;
  bool isNumeric = true;
  for (i = 0; message[i] != 0 && i <= maxLength; i++) {
    if (i == 0 && message[i] == '-') {
      continue;
    }
    if (message[i] < '0' || message[i] > '9') {
      return false;
    }
  }
  if (i == 0) {
    return false;
  }
  return isNumeric;
}

void NjsolyUtil::noop() { }

int NjsolyUtil::charsToInt(char number[]) {
  if (!isNumber(number)) {
    return -1;
  }
  int n = 0;
  int i = 0;
  bool negative = false;

  if (number[0] == '-') {
    negative = true;
    i++;
  }
  for (i; number[i] != 0; i++){
    n *= 10;
    n += number[i] - '0';
  }
  if (negative) 
    n *= -1;
  
  return n;
}

void NjsolyUtil::logWhetherNumeric(char message[]) {
  bool isNumeric = isNumber(message);

  Serial.print("\""); Serial.print(message); Serial.print("\" is ");
  if (isNumeric == false) {
    Serial.print("NOT ");
  }
  Serial.print("a number.\n");
}