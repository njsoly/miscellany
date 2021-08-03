/* NjsolyUtil.h - Arduino Library for general util functions.
 * by Nate Solyntjes 
 * 2021-08-03
 */
#ifndef NjsolyUtil_h
#define NjsolyUtil_h

#include "Arduino.h"

// the #include statment and code go here...
class NjsolyUtil {
  public:
    NjsolyUtil(int pin);
    bool isNumber(char message[], int maxLength);
    void noop();
  private:
    int _pin;
};

#endif
