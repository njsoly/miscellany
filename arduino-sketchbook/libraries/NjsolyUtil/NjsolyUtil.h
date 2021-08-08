/* NjsolyUtil.h - Arduino Library for general util functions.
 * by Nate Solyntjes 
 * 2021-08-03
 */
#ifndef NjsolyUtil_h
#define NjsolyUtil_h

#include "Arduino.h"
#include "Stream.h"
#include "USBAPI.h"

// the #include statment and code go here...
class NjsolyUtil {
  public:
    NjsolyUtil(Stream &stream);
    bool isNumber(char message[], int maxLength);
    void noop();
    int charsToInt(char number[]);
    void logWhetherNumeric(char message[]);
    void rotateArrayRight(uint8_t arr[], int dataLength);
    void rotateArrayLeft(uint8_t arr[], int dataLength);
  private:
    Stream* _stream;
};

#endif /* NjsolyUtil_h */
