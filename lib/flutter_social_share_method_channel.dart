import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'flutter_social_share_platform_interface.dart';

/// An implementation of [FlutterSocialSharePlatform] that uses method channels.
class MethodChannelFlutterSocialShare extends FlutterSocialSharePlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('flutter_social_share');

  @override
  Future<String?> getPlatformVersion() async {
    final version = await methodChannel.invokeMethod<String>('getPlatformVersion');
    return version;
  }
}
