# flutter_social_share_plugin

[![pub package](https://img.shields.io/pub/v/flutter_social_share_plugin.svg)](https://pub.dartlang.org/packages/flutter_social_share_plugin)
[![style: lint](https://img.shields.io/badge/style-lint-4BC0F5.svg)](https://pub.dev/packages/lint)

Flutter Plugin for sharing contents to social media.

You can use it share to Facebook , Instagram , WhatsApp(WhatsAppBusiness) , Twitter, telegram And System Share UI. 
Support Url and Text.

support:
 - Android & iOS :  Facebook,WhatsApp(WhatsAppBusiness),Twitter,Instagram,System Share

**Note: This plugin is still under development, and some APIs might not be available yet.  
Feedback and Pull Requests are most welcome!**

## Getting Started

add `flutter_social_share_plugin` as a [dependency in your pubspec.yaml file](https://flutter.io/platform-plugins/).

Please check the latest version before installation.
```
dependencies:
  flutter:
    sdk: flutter
  # add flutter_social_share_plugin
  flutter_social_share_plugin: ^0.11.0
```
## Setup 

#### Android

Add "facebook app id" to the application tag of AndroidManifest.xml
```
    <application>
       ...
       //add this 
        <meta-data
            android:name="com.facebook.sdk.ApplicationId"
            android:value="@string/facebook_app_id" />
            
        <provider
            android:name="com.facebook.FacebookContentProvider"
            android:authorities="com.facebook.app.FacebookContentProvider[facebook_app_id]"
            android:exported="false" />
    </application>
```

string.xml:
```
<?xml version="1.0" encoding="utf-8"?>
<resources>
<!-- Replace "343254889799245" with your Facebook App ID here. -->
    <string name="facebook_app_id">343254889799245</string>
</resources>
```

### Setup Whatsapp

Make sure you add whatsapp in AndroidManifest.xml.

````
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
...
    <queries>
        <package android:name="com.whatsapp" />
    </queries>
...
</manifest>
````
### Setup SMS

Make sure you add SMS in AndroidManifest.xml.

````
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
...
    <uses-permission android:name="android.permission.SEND_SMS" />
...
</manifest>
````

### Setup Instagram

Make sure you add SMS in AndroidManifest.xml.

````
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
...
    <application>
...
   <provider
            android:name="androidx.core.content.FileProvider"
            android:authorities="${applicationId}.provider"
            android:exported="false"
            android:grantUriPermissions="true">
            <meta-data
                android:name="android.support.FILE_PROVIDER_PATHS"
                android:resource="@xml/file_paths" />
        </provider>
...
    </application>
...
</manifest>
````

#### IOS
    
##### setup facebook

make sure you add below deatils in your plist file.


```
<key>FacebookAppID</key>
<string>fbid</string>
<key>CFBundleURLTypes</key>
	<array>
		<dict>
			<key>CFBundleURLSchemes</key>
			<array>
				<string>fb-your-fb-id</string>
			</array>
		</dict>
	</array>

```
Note-: Make sure you add fb in  at start of your fb Id in CFBundleURLSchemes.

Add below value in url scheme(Refer to example).


```<key>LSApplicationQueriesSchemes</key>
	<array>
		<string>fbauth2</string>
		<string>fbapi</string>
		<string>fbapi20130214</string>
		<string>fbapi20130410</string>
		<string>fbapi20130702</string>
		<string>fbapi20131010</string>
		<string>fbapi20131219</string>
		<string>fbapi20140410</string>
		<string>fbapi20140116</string>
		<string>fbapi20150313</string>
		<string>fbapi20150629</string>
		<string>fbapi20160328</string>
		<string>fbauth</string>
		<string>fb-messenger-share-api</string>
		<string>fbauth2</string>
		<string>fbshareextension</string>
		<string>tg</string>
	</array>
```

### Setup Whatsapp

Make sure you add whatsapp in plist.

````<key>LSApplicationQueriesSchemes</key>
        <array>
            <string>whatsapp</string>
        </array>
````

#### Setup Twiter

````<key>LSApplicationQueriesSchemes</key>
        <array>
            <string>twitter</string>
        </array>
````
#### Setup SMS

````<key>LSApplicationQueriesSchemes</key>
        <array>
            <string>sms</string>
        </array>
````

## Usage

#### Add the following imports to your Dart code:

```
import 'package:flutter_social_share_plugin/flutter_social_share_plugin.dart';
```


## Methods

### facebook
#### shareToFacebook({String msg, String url})

### Instagram
#### shareToInstagram({String url, Result result})   

### twitter
#### shareToTwitter({String msg, String url})   

### whatsapp
#### shareToWhatsApp({String msg,String imagePath})  
#### shareToWhatsApp4Biz({String msg,String imagePath})  (only android)
#### shareWhatsAppPersonalMessage(String message ,String phoneNumber)

### telegram
#### shareToTelegram({String msg})

### Sms
#### shareToSms({String msg})

### mail
#### shareToMail({required String mailBody,String mailSubject,List<String> mailRecipients})

### system
#### shareToSystem({String msg})   use system share ui

These methods will return "success" if they successfully jump to the corresponding app.

| Parameter  | Description                        |
| :------------ |:-----------------------------------|
| String msg  | Text message                       |
| String url  | Url url                            |
| String imagePath  | The local path of the image        |
| String mailBody  | Text mail Body                     |
| String mailSubject  | Text Mail subject                  |
| List<String> mailRecipients  | List of string for mail recipients |

## Example
```
 Container(
          width: double.infinity,
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.center,
            children: <Widget>[
              SizedBox(height: 30),
              ElevatedButton(
                  onPressed: () => onButtonTap(Share.twitter),
                  child: Text('share to twitter')),
              ElevatedButton(
                onPressed: () => onButtonTap(Share.whatsapp),
                child: Text('share to WhatsApp'),
              ),
              ElevatedButton(
                onPressed: () => onButtonTap(Share.whatsapp_business),
                child: Text('share to WhatsApp  Business'),
              ),
              ElevatedButton(
                onPressed: () => onButtonTap(Share.facebook),
                child: Text('share to  FaceBook'),
              ),
              ElevatedButton(
                onPressed: () => onButtonTap(Share.share_system),
                child: Text('share to System'),
              ),
            ],
          ),
        )
```


### Checkout the full example [here](https://github.com/khilanvitthani/flutter_social_share_plugin/blob/main/example/lib/main.dart) 


# flutter_social_share_plugin
