import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_testal_share.dart';

void main() {
  const MethodChannel channel = MethodChannel('flutter_social_share');

  TestWidgetsFlutterBinding.ensureInitialized();

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  test('getPlatformVersion', () async {
    // expect(await FlutterSocialShare.platformVersion, '42');
  });
}
