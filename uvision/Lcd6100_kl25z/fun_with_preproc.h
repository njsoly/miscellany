#ifndef FUN_WITH_PREPROC_H_
#define FUN_WITH_PREPROC_H_


#if (!defined __cplusplus || __cplusplus >= 201103L)
#endif


#if (defined __cplusplus)
#line ln
#error "cplus is defined and line "line" worked."
#endif 

// test for case-sensitivity in preproc string comparison
#if (201303L != 201303l)
#define CASE_SENS 1
#else
#define CASE_SENS 0
#endif
#error "CASE_SENS="##CASE_SENS
#undef CASE_SENS

// test if uppercase characters compare higher than lowercase
#if (201303L > 201303l)
#define CAPS_GREATER 1
#else
#define CAPS_GREATER 0
#endif
#error "capital letters > lowercase: "CAPS_GREATER
#undef CAPS_GREATER

// test if numbers are greater than letters
#define ONE 1
#define AY a
#if (ONE > AY)
#define NUMS_GT_AZ 1
#else
#define NUMS_GT_AZ 0
#endif
#error "numbers are greater than letters: "NUMS_GT_AZ
#undef ONE
#undef AY





#endif // FUN_WITH_PREPROC_H_