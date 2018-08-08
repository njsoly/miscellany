#ifndef LCD6100_H_
#define LCD6100_H_

/* 	An arduino library for the sparkfun LCD shield
 * 	featuring the Nokia 6100 LCD screen and three 
 * 	pushbuttons.
 *	
 *	
 */

#if defined(__ARM_KL25Z__)
	#include <MKL25Z4.H>
#endif

#include "misc_defs.h"
#include "FONT8x16.h"
#include "logo_spark.h"

#define PHILLIPS	0
#define EPSON		1

//*******************************************************
//						Macros
// set bit
#define sbi(address, mask)   ((address) |= (uint32_t)(1 << mask))
// clear bit
#define cbi(address, mask)   ((address) &= (uint32_t)~(1 << mask))
//*******************************************************

//********************************************************************
//
//					LCD Dimension Definitions
//
//********************************************************************
#define ROW_LENGTH	132
#define COL_HEIGHT	132
#define ENDPAGE     132
#define ENDCOL      130


//********************************************************************
//
//			PHILLIPS Controller Definitions
//
//********************************************************************
//LCD Commands
#define	NOPP		0x00	// No operation
#define	BSTRON		0x03	// Booster voltage on
#define SLEEPIN     0x10	// Sleep in
#define	SLEEPOUT	0x11	// Sleep out
#define	NORON		0x13	// Normal display mode on
#define	INVOFF		0x20	// Display inversion off
#define INVON      	0x21	// Display inversion on
#define	SETCON		0x25	// Set contrast
#define DISPOFF     0x28	// Display off
#define DISPON      0x29	// Display on
#define CASETP      0x2A	// Column address set
#define PASETP      0x2B	// Page address set
#define RAMWRP      0x2C	// Memory write
#define RGBSET	    0x2D	// Color set
#define	MADCTL		0x36	// Memory data access control
#define	COLMOD		0x3A	// Interface pixel format
#define DISCTR      0xB9	// Super frame inversion
#define	EC			0xC0	// Internal or external oscillator

#include "Epson_Defs.h"

//*******************************************************
//				12-Bit Color Definitions
//*******************************************************
#define RED			0x00F	// was 0xF00
#define BLUE		0xF00	// was 0x00F
#define	GREEN		0x0F0	// was 0x0F0

#define YELLOW		0x0FF	// was 0xFF0
#define CYAN		0xFF0	// was 0x0FF
#define MAGENTA		0xF0F	// was 0xF0F

#define COLOR_PINK		0xA6F	// was 0xF6A
#define ORANGE 		0x0AF	// was 0xF40
#define SKYBLUE		0xEC8	// was 0x8CE
#define PURPLE 		0x808	// was 0x808
#define TURQUOISE	0xDE4	// was 0x4ED
#define INDIGO  	0x804	// was 0x408
#define SLATE 		0x442	// was 0x244

#define BLACK		0x000	// was 0x000
#define WHITE		0xFFF	// was 0xFFF
#define GRAY 		0x888	// was 0x888

#define NAVY 		0x800	// was 0x008
#define TEAL 		0x880	// was 0x088
#define EMERALD		0x5C0	// was 0x0C5
#define OLIVE 		0x286	// was 0x682
#define MAROON 		0x008	// was 0x800
#define BROWN		0x22B	// was 0xB22
#define CRIMSON 	0x31D	// was 0xD13
#define ORCHID 		0xD7D	// was 0xD7D
#define SALMON 		0x78F	// was 0xF87
#define CORAL 		0x57F	// was 0xF75
#define GOLD 		0x0DF	// was 0xFD0


#define FULLCIRCLE 1
#define OPENSOUTH  2
#define OPENNORTH  3
#define OPENEAST   4
#define OPENWEST   5
#define OPENNORTHEAST 6
#define OPENNORTHWEST 7
#define OPENSOUTHEAST 8
#define OPENSOUTHWEST 9

#if defined(__ARM_KL25Z__)
// FRDM-KL25Z stuff
	// bit numbers
	#define LCD_RES_POS		13
	#define LCD_CS_POS			5
	#define LCD_DIO_POS			2
	#define LCD_SCK_POS			1

	// ports
	#define LCD_PORT_RES	PTA->PDOR
	#define LCD_PORT_CS		PTD->PDOR
	#define LCD_PORT_DIO	PTD->PDOR
	#define LCD_PORT_SCK	PTD->PDOR

	#define LCD_DDR_RES		PTA->PDDR
	#define LCD_DDR_CS		PTD->PDDR
	#define LCD_DDR_DIO		PTD->PDDR
	#define LCD_DDR_SCK		PTD->PDDR

#elif defined(__AVR_ATmega2560__)
// Arduino Mega stuff
#define LCD_RES_POS 5
#define LCD_CS_POS		6
#define LCD_DIO_POS		5
#define LCD_SCK_POS		7
	
#define LCD_PORT_RES	(PORTH)
#define LCD_PORT_CS 	(PORTH)
#define LCD_PORT_DIO	(PORTB)
#define LCD_PORT_SCK	(PORTB)
	
#define LCD_DDR_RES 	(DDRH)
#define LCD_DDR_CS		(DDRH)
#define LCD_DDR_DIO 	(DDRB)
#define LCD_DDR_SCK 	(DDRB)
#elif defined (__AVR_ATmega328p__)
#error "SNTHSNTHSNTSHNTHSNTH. no duemilanove stuff."
#endif


	
	static char x_offset = 0;
	static char y_offset = 0;


struct rgb {
	int r;
	int g;
	int b;
};

struct rgb getRGB(char r, char b, char g);

int pickColor(char r, char g, char b);

class Lcd6100 {
public:

	uint8_t driver;
	
	/**
	 * \brief default constructor.
	 */
	Lcd6100(){
		LCD_DDR_RES |= MASK(LCD_RES_POS);
		LCD_DDR_CS |= MASK(LCD_CS_POS);
		LCD_DDR_DIO |= MASK(LCD_DIO_POS);
		LCD_DDR_SCK |= MASK(LCD_SCK_POS);

	}
	
	/** 
	 *	\brief the foreground color to use as default.
	 */
	int fg;
	int bg;
	
	/** 
	 * \brief send a command to the LCD.
	 */
	void LCDCommand(unsigned char data);
	/** 
	 * \brief send a byte of data to the LCD.
	 */
	void LCDData(unsigned char data);
	
	/**
	 * \brief initialize the LCD with the PHILLIPS driver.
	 */
	void init();
	
	/**
	 * \brief initialize the LCD with the LCD's type (probably PHILLIPS).
	 * 
	 * \param type what type of LCD, between PHILLIPS and EPSON.
	 * 
	 * \return void 
	 */
	void init(int type);
	void setChar(char c, int x, int y, int fColor, int bColor);
	void setStr(char *pString, int x, int y, int fColor, int bColor);
	void setPixel(int color, unsigned char x, unsigned char y);
	void setCircle (int x0, int y0, int radius, int color);

	void setLine(int x0, int y0, int x1, int y1, int color);
	
	void setRect(int x0, int y0, int x1, int y1, unsigned char fill, int color);


	void clear(int color);
	
	void contrast(char setting){
		LCDCommand(SETCON);      // electronic volume, this is the contrast/brightness(EPSON)
		LCDData(setting);        // volume (contrast) setting - course adjustment,  -- original was 24

		LCDCommand(NOPP);         // nop(EPSON)
	}

private:
		
};


#endif // 
