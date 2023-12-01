#include "Arduino.h"
#include "Stream.h"
#include "USBAPI.h"

#include "NjsolyUtil.h"

NjsolyUtil::NjsolyUtil(Stream& stream) {
  _stream = &stream;
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

  _stream->print("\""); _stream->print(message); _stream->print("\" is ");
  if (isNumeric == false) {
    _stream->print("NOT ");
  }
  _stream->print("a number.\n");
}

void NjsolyUtil::rotateArrayRight(uint8_t arr[], int dataLength) {
  int temp = arr[dataLength - 1];
  for (int i = dataLength - 1; i > 0; i--) {
    arr[i] = arr[i - 1];
  }

  arr[0] = temp;
}

void NjsolyUtil::rotateArrayLeft(uint8_t arr[], int dataLength) {
  int temp = arr[0];
  for (int i = 0; i < dataLength - 1; i++) {
    arr[i] = arr[i + 1];
  }

  arr[dataLength - 1] = temp;
}
