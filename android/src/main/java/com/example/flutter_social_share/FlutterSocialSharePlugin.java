package com.example.flutter_social_share;
import java.util.List;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;
import java.util.Collections;

/**
 * FlutterSocialSharePlugin
 */
public class FlutterSocialSharePlugin implements MethodCallHandler, FlutterPlugin, ActivityAware {

  final private static String _methodWhatsApp = "whatsapp_share";
  final private static String _methodWhatsAppPersonal = "whatsapp_personal";
  final private static String _methodWhatsAppBusiness = "whatsapp_business_share";
  final private static String _methodFaceBook = "facebook_share";
  final private static String _methodTwitter = "twitter_share";
  final private static String _methodSystemShare = "system_share";
  final private static String _methodInstagramShare = "instagram_share";
  final private static String _methodTelegramShare = "telegram_share";
  final private static String _methodSmsShare = "sms_share";
  final private static String _methodMailShare = "mail_share";


  private Activity activity;
  private static CallbackManager callbackManager;
  private MethodChannel methodChannel;

  /**
   * Plugin registration.
   */
  public static void registerWith(Registrar registrar) {
    final FlutterSocialSharePlugin instance = new FlutterSocialSharePlugin();
    instance.onAttachedToEngine(registrar.messenger());
    instance.activity = registrar.activity();
  }

  @Override
  public void onAttachedToEngine(FlutterPluginBinding binding) {
    onAttachedToEngine(binding.getBinaryMessenger());
  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    methodChannel.setMethodCallHandler(null);
    methodChannel = null;
    activity = null;
  }

  private void onAttachedToEngine(BinaryMessenger messenger) {
    methodChannel = new MethodChannel(messenger, "flutter_social_share");
    methodChannel.setMethodCallHandler(this);
    callbackManager = CallbackManager.Factory.create();
  }

  /**
   * method
   *
   * @param call   methodCall
   * @param result Result
   */
  @Override
  public void onMethodCall(MethodCall call, @NonNull Result result) {
    String url, msg;
    switch (call.method) {
      case _methodFaceBook:
        url = call.argument("url");
        msg = call.argument("msg");
        shareToFacebook(url, msg, result);
        break;
      case _methodTwitter:
        url = call.argument("url");
        msg = call.argument("msg");
        shareToTwitter(url, msg, result);
        break;
      case _methodWhatsApp:
        msg = call.argument("msg");
        url = call.argument("url");
        shareWhatsApp(url, msg, result, false);
        break;
      case _methodWhatsAppBusiness:
        msg = call.argument("msg");
        url = call.argument("url");
        shareWhatsApp(url, msg, result, true);
        break;
      case _methodWhatsAppPersonal:
        msg = call.argument("msg");
        String phoneNumber = call.argument("phoneNumber");
        shareWhatsAppPersonal(msg, phoneNumber, result);
        break;
      case _methodSystemShare:
        msg = call.argument("msg");
        shareSystem(result, msg);
        break;
      case _methodInstagramShare:
        msg = call.argument("url");
        shareInstagramStory(msg, result);
        break;
      case _methodTelegramShare:
        msg = call.argument("msg");
        shareToTelegram(msg, result);
        break;
      case _methodSmsShare:
        msg = call.argument("msg");
        shareToSms(msg, result);
        break;
      case _methodMailShare:
        msg = call.argument("msg");
        String subject = call.argument("subject");
        List<String> recipients = call.argument("receipients"); // This retrieves a list of recipients

        shareToMail(msg,subject,recipients, result);
        break;
      default:
        result.notImplemented();
        break;
    }
  }

  /**
   * system share
   *
   * @param msg    String
   * @param result Result
   */
  private void shareSystem(Result result, String msg) {
    try {
      Intent textIntent = new Intent("android.intent.action.SEND");
      textIntent.setType("text/plain");
      textIntent.putExtra("android.intent.extra.TEXT", msg);
      activity.startActivity(Intent.createChooser(textIntent, "Share to"));
      result.success("success");
    } catch (Exception var7) {
      result.error("error", var7.toString(), "");
    }
  }

  /**
   * share to twitter
   *
   * @param url    String
   * @param msg    String
   * @param result Result
   */

  private void shareToTwitter(String url, String msg, Result result) {
    try {
        URL twitterUrl = new URL("https://twitter.com/intent/tweet?text=" + msg + "&url=" + url);
        Intent twitterIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(twitterUrl.toString()));
        activity.startActivity(twitterIntent);
      result.success("success");
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }
  }

  /**
   * share to Facebook
   *
   * @param url    String
   * @param msg    String
   * @param result Result
   */
  private void shareToFacebook(String url, String msg, Result result) {

    ShareDialog shareDialog = new ShareDialog(activity);
    // this part is optional
    shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
      @Override
      public void onSuccess(Sharer.Result result) {
        System.out.println("--------------------success");
      }

      @Override
      public void onCancel() {
        System.out.println("-----------------onCancel");
      }

      @Override
      public void onError(FacebookException error) {
        System.out.println("---------------onError");
      }
    });

    ShareLinkContent content = new ShareLinkContent.Builder()
            .setContentUrl(Uri.parse(url))
            .setQuote(msg)
            .build();
    if (ShareDialog.canShow(ShareLinkContent.class)) {
      shareDialog.show(content);
      result.success("success");
    }

  }

  /**
   * share to whatsapp
   *
   * @param msg                String
   * @param result             Result
   * @param shareToWhatsAppBiz boolean
   */
  private void shareWhatsApp(String imagePath, String msg, Result result, boolean shareToWhatsAppBiz) {
    try {
      Intent whatsappIntent = new Intent(Intent.ACTION_SEND);

      whatsappIntent.setPackage(shareToWhatsAppBiz ? "com.whatsapp.w4b" : "com.whatsapp");
      whatsappIntent.putExtra(Intent.EXTRA_TEXT, msg);
      // if the url is the not empty then get url of the file and share
      if (!TextUtils.isEmpty(imagePath)) {
        whatsappIntent.setType("*/*");
        System.out.print(imagePath+"url is not empty");
        File file = new File(imagePath);
        Uri fileUri = FileProvider.getUriForFile(activity, activity.getApplicationContext().getPackageName() + ".provider", file);
        whatsappIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        whatsappIntent.putExtra(Intent.EXTRA_STREAM, fileUri);
        whatsappIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
      }
      else {
        whatsappIntent.setType("text/plain");
      }
      activity.startActivity(whatsappIntent);
      result.success("success");
    } catch (Exception var9) {
      result.error("error", var9.toString(), "");
    }
  }
  /**
   * share to telegram
   *
   * @param msg                String
   * @param result             Result
   */

  private void shareToTelegram(String msg, Result result) {
    try {
      Intent telegramIntent = new Intent(Intent.ACTION_SEND);
      telegramIntent.setType("text/plain");
      telegramIntent.setPackage("org.telegram.messenger");
      telegramIntent.putExtra(Intent.EXTRA_TEXT, msg);
      try {
        activity.startActivity(telegramIntent);
        result.success("true");
      } catch (Exception ex) {
        result.success("false:Telegram app is not installed on your device");
      }
    } catch (Exception var9) {
      result.error("error", var9.toString(), "");
    }
  }

  /**
   * share to Sms
   *
   * @param msg                String
   * @param result             Result
   */
  private void shareToSms(String msg, Result result) {
    try {
      // Create the Uri for the SMS scheme
      Uri uri = Uri.parse("smsto:");
      Intent sendIntent = new Intent(Intent.ACTION_SENDTO, uri);

      // Set the SMS body
      sendIntent.putExtra("sms_body", msg);

      // Start the SMS activity
      activity.startActivity(sendIntent);
      result.success("SMS app opened successfully."); // Success message
    } catch (ActivityNotFoundException e) {
      // Handle case where no SMS app is found
      result.error("NO_SMS_APP", "No SMS app installed on the device.", null);
    } catch (Exception e) {
      // Handle any other exceptions
      result.error("ERROR", e.toString(), null);
    }
  }

  /**
   * share to mail
   *
   * @param msg                String
   * @param subject                String
   * @param recipients                String
   * @param result             Result
   */
  private void shareToMail(String msg, String subject, List<String> recipients, Result result) {
    try {
      Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
      emailIntent.setData(Uri.parse("mailto:")); // Only email apps should handle this

      // Set the subject and message
      emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject != null ? subject : ""); // Handle null subject
      emailIntent.putExtra(Intent.EXTRA_TEXT, msg != null ? msg : ""); // Handle null message

      // Check if recipients is not null and not empty
      if (recipients != null && !recipients.isEmpty()) {
        emailIntent.putExtra(Intent.EXTRA_EMAIL, recipients.toArray(new String[0])); // Convert List to Array
      }

      // Create chooser to select email client
      Intent chooser = Intent.createChooser(emailIntent, "Choose an email client");

      // Verify that there are email clients available
      if (chooser.resolveActivity(activity.getPackageManager()) != null) {
        activity.startActivity(chooser);
        result.success("Email app opened successfully."); // Success message
      } else {
        result.error("NO_EMAIL_APP", "No EMAIL app installed on the device.", null);
      }
    } catch (ActivityNotFoundException e) {
      result.error("NO_EMAIL_APP", "No EMAIL app installed on the device.", null);
    } catch (Exception e) {
      result.error("ERROR", e.toString(), null);
    }
  }



  /**
   * share whatsapp message to personal number
   *
   * @param msg         String
   * @param phoneNumber String with country code
   * @param result
   */
  private void shareWhatsAppPersonal(String msg, String phoneNumber, Result result) {
    String url = null;
    try {
      url = "https://api.whatsapp.com/send?phone=" + phoneNumber + "&text=" + URLEncoder.encode(msg, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    Intent i = new Intent(Intent.ACTION_VIEW);
    i.setPackage("com.whatsapp");
    i.setData(Uri.parse(url));
    activity.startActivity(i);
    result.success("success");
  }

  /**
   * share to instagram
   *
   * @param url    local image path
   * @param result flutterResult
   */
  private void shareInstagramStory(String url, Result result) {

    File file = new File(url);
    if (!file.exists()) {
      result.error("File not found", "The specified file does not exist.", "");
      return;
    }

    Uri fileUri = FileProvider.getUriForFile(activity, activity.getApplicationContext().getPackageName() + ".provider", file);
    Log.d("ShareInstagramStory", "File URI: " + fileUri.toString());

    Intent instagramIntent = new Intent(Intent.ACTION_SEND);
    instagramIntent.setType("image/*");
    instagramIntent.putExtra(Intent.EXTRA_STREAM, fileUri);
    instagramIntent.setPackage("com.instagram.android");
    instagramIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

    try {
      activity.startActivity(instagramIntent);
      result.success("Success");
    } catch (ActivityNotFoundException e) {
      result.error("Instagram not found", "Instagram is not installed on device.", "");
    } catch (SecurityException e) {
      result.error("Security Exception", "Permission denied for accessing the file.", "");
    } catch (Exception e) {
      result.error("Error", "An unexpected error occurred: " + e.getMessage(), "");
    }
    /*if (instagramInstalled()) {
      File file = new File(url);
      Uri fileUri = FileProvider.getUriForFile(activity, activity.getApplicationContext().getPackageName() + ".provider", file);

      Intent instagramIntent = new Intent(Intent.ACTION_SEND);
      instagramIntent.setType("image/*");
      instagramIntent.putExtra(Intent.EXTRA_STREAM, fileUri);
      instagramIntent.setPackage("com.instagram.android");
      try {
        activity.startActivity(instagramIntent);
        result.success("Success");
      } catch (ActivityNotFoundException e) {
        e.printStackTrace();
        result.success("Failure");
      }
    } else {
      result.error("Instagram not found", "Instagram is not installed on device.", "");
    }*/
  }

  @Override
  public void onAttachedToActivity(ActivityPluginBinding binding) {
    activity = binding.getActivity();
  }

  @Override
  public void onDetachedFromActivityForConfigChanges() {

  }

  @Override
  public void onReattachedToActivityForConfigChanges(ActivityPluginBinding binding) {
    activity = binding.getActivity();
  }

  @Override
  public void onDetachedFromActivity() {

  }

  ///Utils methods
  private boolean instagramInstalled() {
    try {
      if (activity != null) {
        activity.getPackageManager()
                .getApplicationInfo("com.instagram.android", 0);
        return true;
      } else {
        Log.d("App", "Instagram app is not installed on your device");
        return false;
      }
    } catch (PackageManager.NameNotFoundException e) {
      return false;
    }
//        return false;
  }
}