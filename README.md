# SeamlessPay for Android

## Accept contactless credit/debit cards from VISA, Mastercard, Amex and more on your app

[![](https://jitpack.io/v/seamlesspayio/seamlesspay.svg)](https://jitpack.io/#seamlesspayio/seamlesspay)

SeamlessPay is a mobile library for Android that gives merchants the ability to read contactless cards from VISA, Mastercard, American Express and more through NFC.

## Getting started with the examples
Please note that we currently have two options in the SDK.

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