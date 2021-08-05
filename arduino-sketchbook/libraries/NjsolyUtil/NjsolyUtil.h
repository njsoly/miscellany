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
    NjsolyUtil(int pin, Stream &stream);
    bool isNumber(char message[], int maxLength);
    void noop();
    int charsToInt(char number[]);
    void logWhetherNumeric(char message[]);
  private:
    int _pin;
    Stream* _stream;
};

#endif /* NjsolyUtil_h */
