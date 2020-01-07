# Seamless Pay for Android - Beta
# Please note that I have released Version 2 of the library. It is much simpler to integrate, check it out.
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
 implementation 'com.github.seamlesspayio:seamlesspay:1.2.4-beta'
}
```

## Now the code
### Follow this example to get started or download the sample app for more
```Java
public class BottomSheetTapActivityV2 extends AppCompatActivity {

  private ActivityTapBinding mBinding;

  private BottomSheetEmvReaderV2 mEmvReaderV2;

  private EmvViewModel mModelV;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mBinding = DataBindingUtil.setContentView(this, R.layout.activity_tap);
  }

  @Override
  protected void onStart() {
    super.onStart();
    configureReader();
    // Start reading for your card
    if (SDK_INT >= KITKAT) {
      mBinding.button.setOnClickListener(aView -> startReading());
    }
  }

  @Override
  protected void onStop() {
    super.onStop();
    // Stop reading for the card
    mEmvReaderV2.stopReading();
  }

  private void startReading() {
    final Observer<EmvCard> cardObserver = aS -> {
      if (aS != null)
        mBinding.text.setText(aS.getCardNumber());
    };
    mModelV.getIntentMutableLiveData().observe(this, cardObserver);
    if (SDK_INT >= KITKAT) {
      mEmvReaderV2.readCreditCard();
    }
  }

  public void configureReader() {
    mModelV = new ConfigureViewModel(this).createViewModel();
    mEmvReaderV2 = new BottomSheetEmvReaderV2(this, getSupportFragmentManager());
  }

}
```
