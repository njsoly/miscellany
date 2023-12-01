#include "Arduino.h"
#include "Stream.h"
#include "USBAPI.h"

#include "NjsolyUtil.h"
#include "ShiftRegHC595.h"

ShiftRegHC595::ShiftRegHC595(uint8_t serPin,
							 uint8_t rclkPin,
							 uint8_t srclkPin,
							 uint8_t dataLengthBits,
							 Stream& stream,
							 uint8_t srclrNotPin,
							 uint8_t oeNotPin) {
	_ser = serPin;
	_rclk = rclkPin;
	_srclk = srclkPin;
	_srclrNot = srclrNotPin;
	_outputDisable = oeNotPin;
	_dataLength = dataLengthBits;
	_stream = &stream;
}
ShiftRegHC595::ShiftRegHC595(uint8_t serPin,
							 uint8_t rclkPin,
							 uint8_t srclkPin,
							 uint8_t dataLengthBits,
							 uint8_t srclrNotPin,
							 uint8_t oeNotPin) {
	_ser = serPin;
	_rclk = rclkPin;
	_srclk = srclkPin;
	_srclrNot = srclrNotPin;
	_outputDisable = oeNotPin;
	_dataLength = dataLengthBits;
}

void ShiftRegHC595::init() {
	pinMode(_ser, OUTPUT);
	digitalWrite(_ser, LOW);
	pinMode(_rclk, OUTPUT);
	digitalWrite(_rclk, LOW);
	pinMode(_srclk, OUTPUT);
	digitalWrite(_srclk, LOW);

	if (_outputDisable != NOT_A_PIN) {
		pinMode(_outputDisable, OUTPUT);
		digitalWrite(_outputDisable, HIGH);
		digitalWrite(_outputDisable, LOW);
	}

	if (_srclrNot != NOT_A_PIN) {
		pinMode(_srclrNot, OUTPUT);
		digitalWrite(_srclrNot, LOW);
		digitalWrite(_srclrNot, HIGH);
	}
}

void ShiftRegHC595::shiftOut(uint8_t value) {

	if (_outputDisable != NOT_A_PIN) {
		digitalWrite(_outputDisable, HIGH);
	}
	digitalWrite(_rclk, HIGH);

	digitalWrite(_ser, value == 0 ? LOW : HIGH);
	digitalWrite(_srclk, HIGH);
	digitalWrite(_srclk, LOW);

	digitalWrite(_rclk, LOW);
	if (_outputDisable != NOT_A_PIN) {
		digitalWrite(_outputDisable, LOW);
	}
}

void ShiftRegHC595::shiftOut(const uint8_t arr[], int arrLength) {

	if (_outputDisable != NOT_A_PIN) {
		digitalWrite(_outputDisable, HIGH);
	}
	digitalWrite(_rclk, HIGH);

    for (int i = arrLength - 1; i >= 0; i--) {
		digitalWrite(_ser, arr[i] == 0 ? LOW : HIGH);
		digitalWrite(_srclk, HIGH);
		digitalWrite(_srclk, LOW);
	}

	digitalWrite(_rclk, LOW);
	if (_outputDisable != NOT_A_PIN) {
		digitalWrite(_outputDisable, LOW);
	}
}

void ShiftRegHC595::clearRegister() {
	// clear with the SRCLR! input, if configured.
	// otherwise, clear by pushing out all zeros.
	if (_srclrNot != NOT_A_PIN) {
		digitalWrite(_srclrNot, LOW);
		digitalWrite(_srclrNot, HIGH);
	}
	// else {

		for (int i = 0; i < _dataLength; i++) {
			shiftOut(0);
		}
}
