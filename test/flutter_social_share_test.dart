import 'package:flutter_test/flutter_test.dart';
import 'package:flutter_social_share/flutter_social_share.dart';
import 'package:flutter_social_share/flutter_social_share_platform_interface.dart';
import 'package:flutter_social_share/flutter_social_share_method_channel.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

class MockFlutterSocialSharePlatform
    with MockPlatformInterfaceMixin
    implements FlutterSocialSharePlatform {

  @override
  Future<String?> getPlatformVersion() => Future.value('42');
}

void main() {
  final FlutterSocialSharePlatform initialPlatform = FlutterSocialSharePlatform.instance;

  test('$MethodChannelFlutterSocialShare is the default instance', () {
    expect(initialPlatform, isInstanceOf<MethodChannelFlutterSocialShare>());
  });

  test('getPlatformVersion', () async {
    FlutterSocialShare flutterSocialSharePlugin = FlutterSocialShare();
    MockFlutterSocialSharePlatform fakePlatform = MockFlutterSocialSharePlatform();
    FlutterSocialSharePlatform.instance = fakePlatform;

    expect(await flutterSocialSharePlugin.getPlatformVersion(), '42');
  });
}
