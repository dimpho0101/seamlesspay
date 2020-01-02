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
Add the JitPack repository to your build file
```groovy
      allprojects {
          repositories {
              maven { url 'https://jitpack.io' }
          }
      }
```

### Step 2
Add the dependency
```groovy
dependencies {
 implementation 'com.github.seamlesspayio:seamlesspay:1.2.2-beta'
}
```

### Step 3
Add this to your manifest on the Activity where you want to use the library
```xml
    <!-- NoUi Example -->

        <activity
            android:name=".examples.activity.TapActivity"
            android:launchMode="singleTask">
          <intent-filter>
            <action android:name="android.nfc.action.TECH_DISCOVERED" />
            <category android:name="android.intent.category.DEFAULT" />
          </intent-filter>

          <meta-data
              android:name="android.nfc.action.TECH_DISCOVERED"
              android:resource="@xml/nfc_tech_list" />
        </activity>

     <!-- NoUi Example -->
```