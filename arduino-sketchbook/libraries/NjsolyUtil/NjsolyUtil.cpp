#include "Arduino.h"
#include "NjsolyUtil.h"

NjsolyUtil::NjsolyUtil(int pin) {
//   isNumber(int s[]);
  _pin = pin;
}

bool NjsolyUtil::isNumber(char message[], int maxLength) {
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
