import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import 'flutter_social_share_method_channel.dart';

abstract class FlutterSocialSharePlatform extends PlatformInterface {
  /// Constructs a FlutterSocialSharePlatform.
  FlutterSocialSharePlatform() : super(token: _token);

  static final Object _token = Object();

  static FlutterSocialSharePlatform _instance = MethodChannelFlutterSocialShare();

  /// The default instance of [FlutterSocialSharePlatform] to use.
  ///
  /// Defaults to [MethodChannelFlutterSocialShare].
  static FlutterSocialSharePlatform get instance => _instance;

  /// Platform-specific implementations should set this with their own
  /// platform-specific class that extends [FlutterSocialSharePlatform] when
  /// they register themselves.
  static set instance(FlutterSocialSharePlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<String?> getPlatformVersion() {
    throw UnimplementedError('platformVersion() has not been implemented.');
  }
}
