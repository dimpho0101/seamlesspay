# Seamless Pay for Android - Beta

## Give users the ability to tap their credit card on their phone for a seamless checkout experience using NFC & contactless cards from VISA, Mastercard, Amex and more.

[![](https://jitpack.io/v/seamlesspayio/seamlesspay.svg)](https://jitpack.io/#seamlesspayio/seamlesspay)

Seamless Pay is a mobile library for Android that gives merchants the ability to read contactless cards from VISA, Mastercard, American Express and more through NFC.
Seamless Pay is a mobile library for Android which allows developers to capture credit card details by tapping it on the back of a mobile device that has NFC support.
Seamless Pay supports credit cards from VISA, Mastercard, Amex, Discover and more.

#### Supported Cards
 - Visa
 - Mastercard
 - American Express
 - Discover
 - Union Pay

## Getting started with the examples
Please note that we currently have two options in the SDK. You can use our prebuilt UI or you can provide your own UI.
You can find out more about this by using our examples.

Example Repository Clone Link : https://github.com/seamlesspayio/seamlesspay.git </br>
Example Repository Download Link: https://github.com/seamlesspayio/seamlesspay/archive/master.zip

### Support

- [x] Added contactless support for Activities
- [x] Added contactless support for Fragments

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
Please refer to the two xml files below. It is very important that you set these up correctly otherwise the library wont work.

#### Step 3.1 - Add an intent filer as well as meta-data to the Activity you want to use the library in. Use the xml below as a reference
```xml
    <!-- Example -->

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

     <!-- Example -->
```

#### Step 3.2 - Create an xml directory and then an nfc_tech_list.xml file in the res folder of your app. Use the xml below as a reference
```xml
    <!-- This the NFC Tech List File -->

        <?xml version="1.0" encoding="utf-8"?>
        <resources xmlns:tools="http://schemas.android.com/tools"
            xmlns:xliff="urn:oasis:names:tc:xliff:document:1.2"
            tools:ignore="MissingDefaultResource">

          <tech-list>
            <tech>android.nfc.tech.IsoDep</tech>
          </tech-list>
        </resources>

     <!-- This the NFC Tech List File  -->
```