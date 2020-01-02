# SeamlessPay for Android

## Accept contactless credit/debit cards from VISA, Mastercard, Amex and more on your app

[![](https://jitpack.io/v/seamlesspayio/seamlesspay.svg)](https://jitpack.io/#seamlesspayio/seamlesspay)

SeamlessPay is a mobile library for Android that gives merchants the ability to read contactless cards from VISA, Mastercard, American Express and more through NFC.

### Support

- [x] Add contactless support for activities
- [x] Add contactless support for Fragments

### Payment gateway support
If we have not listed your preferred Payment Gateway, please let us know at hello@seamlesspay.io and we will add it.

- [ ] Integration with Stripe Payments
- [ ] Integration with PayFast
- [ ] Integration with PayGate
- [ ] Integration with checkout.com
- [ ] Integration with VISA CyberSource
- [ ] Integration with MasterPass

## Setup Seamless Pay

### Step 1

```groovy
allprojects {
		repositories {
			maven { url 'https://jitpack.io' }
		}
	}
```

### Step 2

```groovy
dependencies {
	      implementation 'com.github.seamlesspayio:seamlesspay:1.2.2-beta'
}
```